package com.liyosi;

import com.liyosi.api.account.transfer.exceptions.TransferFailedExceptionMapper;
import com.liyosi.core.AccountService;
import com.liyosi.core.currency.CurrencyConversionService;
import com.liyosi.core.currency.CurrencyRatesCache;
import com.liyosi.db.dao.*;
import com.liyosi.db.repository.TransactionRepository;
import com.liyosi.resources.AccountResource;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class BankAccountApplication extends Application<BankAaccountConfiguration> {

  private static final Logger LOGGER = LoggerFactory.getLogger(AccountResource.class);

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
    final CustomerDao customerDao = jdbi.onDemand(CustomerDao.class);
    final AccountDao accountDao = jdbi.onDemand(AccountDao.class);
    final AccountTransactionDao accountTransactionDao = jdbi.onDemand(AccountTransactionDao.class);
    final TransactionRepository transactionRepository = jdbi.onDemand(TransactionRepository.class);

    List<BaseDao> daos = Arrays.asList(currencyDao, currencyDao, accountDao, accountTransactionDao, customerDao);

    this.createTables(daos);

    this.seedData(daos);

    final CurrencyRatesCache currencyRatesCache = new CurrencyRatesCache();

    final CurrencyConversionService currencyConversionService = new CurrencyConversionService(currencyRatesCache);

    final AccountService accountService = new AccountService(
        accountDao,
        accountTransactionDao,
        currencyDao,
        currencyConversionService,
        transactionRepository);

    environment.jersey().register(new AccountResource(accountService));
    environment.jersey().register(new TransferFailedExceptionMapper());
  }

  private void createTables(List<BaseDao> daoList) {
    LOGGER.info("Creating tables");
    daoList.forEach(BaseDao::createTable);
  }

  private void seedData(List<BaseDao> daoList) {
    LOGGER.info("Creating seed data");
    daoList.forEach(BaseDao::seedData);
  }
}

