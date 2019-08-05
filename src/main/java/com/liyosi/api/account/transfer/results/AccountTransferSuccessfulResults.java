package com.liyosi.api.account.transfer.results;

import com.liyosi.api.account.transfer.AccountTransferTransaction;

public class AccountTransferSuccessfulResults extends AccountTransferResults {

  public AccountTransferSuccessfulResults() {
  }

  public AccountTransferSuccessfulResults(AccountTransferTransaction transferTransaction) {
    super(transferTransaction, String.format(
        "Successfully transferred %s", AccountTransferSuccessfulResults.createResultMessage(transferTransaction)));
  }
}
