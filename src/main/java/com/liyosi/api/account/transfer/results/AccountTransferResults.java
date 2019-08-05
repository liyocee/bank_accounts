package com.liyosi.api.account.transfer.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.liyosi.api.account.transfer.AccountTransferTransaction;

import javax.validation.constraints.NotNull;

public abstract class AccountTransferResults {

  @NotNull
  private String message;

  private AccountTransferTransaction transferTransaction;

  public AccountTransferResults() {
  }

  public AccountTransferResults(AccountTransferTransaction transferTransaction, String message) {
    this.transferTransaction = transferTransaction;
    this.message = message;
  }

  public static String createResultMessage(AccountTransferTransaction accountTransferTransaction) {
    return String.format(
        "%s %f from: %s to %s",
        accountTransferTransaction.getIsoCurrencyCode(),
        accountTransferTransaction.getAmount(),
        accountTransferTransaction.getFrom(),
        accountTransferTransaction.getTo());
  }

  @JsonProperty("message")
  public String getMessage() {
    return message;
  }
}
