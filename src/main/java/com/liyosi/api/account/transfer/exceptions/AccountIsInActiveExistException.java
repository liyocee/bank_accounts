package com.liyosi.api.account.transfer.exceptions;

import com.liyosi.core.exceptions.ErrorCodes;

import javax.ws.rs.ext.Provider;

@Provider
public class AccountIsInActiveExistException extends TransferFailedException {

  public AccountIsInActiveExistException(String accountNumber) {
    super(String.format("Account number: %s is not active", accountNumber), ErrorCodes.ACCOUNT_IN_ACTIVE);
  }
}
