package com.liyosi.db.dao;

import com.liyosi.core.models.AccountTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTransactionDaoTest extends BaseDaoTestCase {
  private AccountTransactionDao accountTransactionDao;

  private final Timestamp now = new Timestamp(System.currentTimeMillis());

  private final AccountTransaction accountTransaction = new AccountTransaction(
      1L, new BigDecimal(100), 1L, new BigDecimal(10L), new BigDecimal(10),
      1L, "txm111", now);


  @BeforeEach
  public void setUp() throws Exception{
    super.setUp();
    accountTransactionDao = dbi.open(AccountTransactionDao.class);
    accountTransactionDao.createTable();
    accountTransactionDao.seedData();
  }


  @Test
  void createAccountTransaction_GivenTransaction_CreateAndRetrieve() {
    accountTransactionDao.insert(accountTransaction);
    AccountTransaction retrievedTransaction = accountTransactionDao.findById(accountTransaction.getId());
    assertEquals(retrievedTransaction.getId(), accountTransaction.getId());
    assertEquals(retrievedTransaction.getTransactionId(), accountTransaction.getTransactionId());
  }
}
