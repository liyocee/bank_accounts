package com.liyosi.api.account.transfer.exceptions;

import com.liyosi.core.exceptions.ErrorCodes;

import javax.ws.rs.ext.Provider;

@Provider
public class AccountDoesNotExistException extends TransferFailedException {

  public AccountDoesNotExistException(String accountNumber) {
    super(String.format("Account number: %s does not exist", accountNumber), ErrorCodes.ACCOUNT_DOES_NOT_EXIST);
  }
}
