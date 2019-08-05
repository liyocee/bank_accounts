package com.liyosi;

import com.liyosi.core.AccountService;
import com.liyosi.resources.AccountResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

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
    // TODO: application initialization
  }

  @Override
  public void run(final BankAaccountConfiguration configuration,
                  final Environment environment) {

    final AccountService accountService = new AccountService();
    environment.jersey().register(new AccountResource(accountService));
  }

}
