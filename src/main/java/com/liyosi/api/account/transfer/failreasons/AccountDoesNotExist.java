package com.liyosi.api.account.transfer.failreasons;

public class AccountDoesNotExist extends TransferFailedReason {

  public AccountDoesNotExist(String accountNumber) {
    super(String.format("Account number: %s does not exist", accountNumber));
  }
}
