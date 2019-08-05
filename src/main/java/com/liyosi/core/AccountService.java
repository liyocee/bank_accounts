package com.liyosi.core;

import com.liyosi.api.account.transfer.AccountTransferTransaction;
import com.liyosi.api.account.transfer.results.AccountTransferResults;
import com.liyosi.api.account.transfer.results.AccountTransferSuccessfulResults;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AccountService {

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
