package com.liyosi;

import com.liyosi.core.AccountService;
import com.liyosi.db.dao.AccountDao;
import com.liyosi.db.dao.AccountTransactionDao;
import com.liyosi.db.dao.CurrencyDao;
import com.liyosi.resources.AccountResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

public class BankAccountApplication extends Application<BankAaccountConfiguration> {

  public static void main(final String[] args) throws Exception {
    new BankAccountApplication().run(args);
  }

  @Override
  public String getName() {
    return "bank-account";
  }

  @Override
  public void initialize(final Bootstrap<BankAaccountConfiguration> bootstrap) {
  }

  @Override
  public void run(final BankAaccountConfiguration configuration,
                  final Environment environment) {

    final DBIFactory dbiFactory = new DBIFactory();
    final DBI jdbi = dbiFactory.build(environment, configuration.getDataSourceFactory(), "h2");

    final CurrencyDao currencyDao = jdbi.onDemand(CurrencyDao.class);
    final AccountDao accountDao = jdbi.onDemand(AccountDao.class);
    final AccountTransactionDao accountTransactionDao = jdbi.onDemand(AccountTransactionDao.class);

    final AccountService accountService = new AccountService(accountDao, accountTransactionDao, currencyDao);
    environment.jersey().register(new AccountResource(accountService));
  }
}
