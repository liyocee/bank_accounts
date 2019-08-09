package com.liyosi.api.account.transfer.exceptions;

import com.liyosi.core.exceptions.ErrorCodes;

import javax.ws.rs.ext.Provider;

@Provider
public class AccountUnsupportedCurrencyException extends TransferFailedException {

  public AccountUnsupportedCurrencyException(String accountNumber, String currencyIsoCode) {
    super(String.format("Account number: %s doesn't support supplied currency: %s", accountNumber, currencyIsoCode),
        ErrorCodes.UNSUPPORTED_CURRENCY_FOR_ACCOUNT);
  }
}
