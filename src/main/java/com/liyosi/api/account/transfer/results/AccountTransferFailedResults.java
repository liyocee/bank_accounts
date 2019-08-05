package com.liyosi.api.account.transfer.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liyosi.api.account.transfer.AccountTransferTransaction;
import com.liyosi.api.account.transfer.failreasons.TransferFailedReason;

import javax.validation.constraints.NotNull;

public class AccountTransferFailedResults  extends AccountTransferResults {

  @NotNull
  private TransferFailedReason reason;

  public AccountTransferFailedResults() {
  }

  public AccountTransferFailedResults(AccountTransferTransaction transferTransaction) {
    super(transferTransaction, String.format(
        "Couldn't transfer %s", AccountTransferFailedResults.createResultMessage(transferTransaction)));
  }

  @JsonProperty("reason")
  public TransferFailedReason getReason() {
    return reason;
  }
}
