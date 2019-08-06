package com.liyosi.core.currency;

import com.liyosi.api.account.transfer.exceptions.CurrencyConversionException;
import com.liyosi.core.models.Currency;

import java.math.BigDecimal;
import java.util.Optional;

public class CurrencyConversionService {

  private CurrencyRatesCache currencyRatesCache;

  public static class ConvertedCurrency {
    private BigDecimal rate;

    private BigDecimal amount;

    public ConvertedCurrency(BigDecimal rate, BigDecimal amount) {
      this.rate = rate;
      this.amount = amount;
    }

    public BigDecimal getRate() {
      return rate;
    }

    public BigDecimal getAmount() {
      return amount;
    }
  }

  public CurrencyConversionService(CurrencyRatesCache currencyRatesCache) {
    this.currencyRatesCache = currencyRatesCache;
  }

  public ConvertedCurrency convert(BigDecimal amount, Currency base, Currency target) throws CurrencyConversionException {
    BigDecimal rate = Optional
        .ofNullable(currencyRatesCache.exchangeRate(base, target))
        .orElseThrow(() -> new CurrencyConversionException(base, target));
    return new ConvertedCurrency(rate, amount.multiply(rate));
  }
}
