package com.liyosi.db.dao;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.lifecycle.setup.LifecycleEnvironment;
import io.dropwizard.logging.BootstrapLogging;
import io.dropwizard.setup.Environment;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.ArgumentCaptor;
import org.skife.jdbi.v2.DBI;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import static org.mockito.Mockito.*;

public class BaseDaoTestCase {
  private final DataSourceFactory hsqlConfig = new DataSourceFactory();

  {
    BootstrapLogging.bootstrap();
    hsqlConfig.setUrl("jdbc:h2:mem:JDBITest-" + System.currentTimeMillis());
    hsqlConfig.setUser("sa");
    hsqlConfig.setDriverClass("org.h2.Driver");
    hsqlConfig.setValidationQuery("SELECT 1");
  }

  private final HealthCheckRegistry healthChecks = mock(HealthCheckRegistry.class);
  private final LifecycleEnvironment lifecycleEnvironment = mock(LifecycleEnvironment.class);
  private final Environment environment = mock(Environment.class);
  private final DBIFactory factory = new DBIFactory();
  private final MetricRegistry metricRegistry = new MetricRegistry();
  protected final List<Managed> managed = new ArrayList<>();
  protected DBI dbi;


  @BeforeEach
  public void setUp() throws Exception {
    when(environment.healthChecks()).thenReturn(healthChecks);
    when(environment.lifecycle()).thenReturn(lifecycleEnvironment);
    when(environment.metrics()).thenReturn(metricRegistry);
    when(environment.getHealthCheckExecutorService()).thenReturn(Executors.newSingleThreadExecutor());

    dbi = factory.build(environment, hsqlConfig, "hsql");
    final ArgumentCaptor<Managed> managedCaptor = ArgumentCaptor.forClass(Managed.class);
    verify(lifecycleEnvironment).manage(managedCaptor.capture());
    managed.addAll(managedCaptor.getAllValues());
    for (Managed obj : managed) {
      obj.start();
    }
  }
}
