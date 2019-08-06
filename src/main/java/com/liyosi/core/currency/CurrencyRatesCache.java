package com.liyosi.core.currency;

import com.liyosi.core.models.Currency;

import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.util.Random;

public class CurrencyRatesCache {

  @Null
  BigDecimal exchangeRate(Currency base, Currency target) {
    if (base.getIsoCode() == target.getIsoCode()) {
      return new BigDecimal(1.0F);
    }
    // Todo - this can e fetched from some a currency rates webservice
    return new BigDecimal(new Random().nextFloat());
  }
}
