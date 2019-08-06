package com.liyosi.api.account.transfer.exceptions;

import com.liyosi.core.exceptions.ErrorCodes;

import javax.ws.rs.ext.Provider;

@Provider
public class InvalidCurrencyCodeException extends TransferFailedException {

  public InvalidCurrencyCodeException(String currencyCode) {
    super(String.format("Currency code: %s is invalid", currencyCode), ErrorCodes.INVALID_CURRENCY);
  }
}
