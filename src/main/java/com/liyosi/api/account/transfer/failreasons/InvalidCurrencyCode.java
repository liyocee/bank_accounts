package com.liyosi.api.account.transfer.failreasons;

public class InvalidCurrencyCode extends TransferFailedReason {
  public InvalidCurrencyCode(String currencyCode) {
    super(String.format("Currency code: %s is invalid", currencyCode));
  }
}
