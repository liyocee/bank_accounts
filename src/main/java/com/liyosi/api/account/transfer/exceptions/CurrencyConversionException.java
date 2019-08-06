package com.liyosi.api.account.transfer.exceptions;

import com.liyosi.core.exceptions.ErrorCodes;
import com.liyosi.core.models.Currency;

import javax.ws.rs.ext.Provider;

@Provider
public class CurrencyConversionException extends TransferFailedException {
  public CurrencyConversionException(Currency base, Currency target) {
    super(String.format(
        "Cannot convert currency from: %s to %s . Problem fetching exchange rates", base.getIsoCode(), target.getIsoCode()),
        ErrorCodes.CURRENCY_CONVERSION_ERROR);
  }
}
