package com.liyosi.db.dao;

import com.liyosi.core.models.Currency;
import io.dropwizard.lifecycle.Managed;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CurrencyDaoTest extends BaseDaoTestCase {
  private CurrencyDao currencyDao;

  private final Currency currency = new Currency(10L, "TSH");

  @BeforeEach
  public void setUp() throws Exception{
    super.setUp();
    currencyDao = dbi.open(CurrencyDao.class);
    currencyDao.createTable();
    currencyDao.seedData();
  }

  @AfterEach
  void tearDown() throws Exception {
    for (Managed obj : managed) {
      obj.stop();
    }
  }

  @Test
  void create_CreateCurrency_ShouldCreateAndRetrieveCurrency() {
    currencyDao.insert(currency);

    Currency retrievedCurrency = currencyDao.findById(currency.getId());
    assertEquals(currency.getIsoCode(), retrievedCurrency.getIsoCode());
  }

  @Test
  void findByIsoCode_CurrencyExists_ReturnMatchingAccount() {
    currencyDao.insert(currency);

    Currency retrievedCurrency = currencyDao.findByIsoCode(currency.getIsoCode());
    assertEquals(currency.getIsoCode(), retrievedCurrency.getIsoCode());
    assertEquals(currency.getId(), retrievedCurrency.getId());
  }

  @Test
  void findByIsoCode_CurrencyDoesNotExist_ReturnNull() {
    Currency retrievedCurrency = currencyDao.findByIsoCode(currency.getIsoCode());
    assertNull(retrievedCurrency);
  }
}
