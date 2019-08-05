package com.liyosi.core;

import com.liyosi.api.account.transfer.AccountTransferTransaction;
import com.liyosi.api.account.transfer.results.AccountTransferResults;
import com.liyosi.api.account.transfer.results.AccountTransferSuccessfulResults;
import com.liyosi.db.dao.AccountDao;
import com.liyosi.db.dao.AccountTransactionDao;
import com.liyosi.db.dao.CurrencyDao;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AccountService {

  private AccountDao accountDao;

  private AccountTransactionDao accountTransactionDao;

  private CurrencyDao currencyDao;

  public AccountService(AccountDao accountDao, AccountTransactionDao accountTransactionDao, CurrencyDao currencyDao) {
    this.accountDao = accountDao;
    this.accountTransactionDao = accountTransactionDao;
    this.currencyDao = currencyDao;
  }

  public @NotNull AccountTransferResults transfer(@NotNull AccountTransferTransaction accountTransferTransaction) {


    return new AccountTransferSuccessfulResults(accountTransferTransaction);
  }

  private Boolean doesAccountExist(String accountNumber) {
    return false;
  }

  private Boolean isBalanceSufficient(BigDecimal amount) {
    return false;
  }
}
