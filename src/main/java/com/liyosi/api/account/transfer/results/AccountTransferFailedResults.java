package com.liyosi.api.account.transfer.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liyosi.api.account.transfer.AccountTransferTransaction;
import com.liyosi.api.account.transfer.exceptions.TransferFailedException;

import javax.validation.constraints.NotNull;

public class AccountTransferFailedResults  extends AccountTransferResults {

  @NotNull
  private TransferFailedException reason;

  public AccountTransferFailedResults() {
  }

  public AccountTransferFailedResults(AccountTransferTransaction transferTransaction) {
    super(transferTransaction, String.format(
        "Couldn't transfer %s", AccountTransferFailedResults.createResultMessage(transferTransaction)));
  }

  @JsonProperty("reason")
  public TransferFailedException getReason() {
    return reason;
  }
}
