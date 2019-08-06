package com.liyosi.db.dao;

import com.liyosi.core.models.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class AccountDaoTest extends BaseDaoTestCase {

  private AccountDao accountDao;

  Timestamp now = new Timestamp(System.currentTimeMillis());
  private final Account account = new Account(
      10L, 20L, "Savings", "A001", now , now, 1L,
      Account.AccountStatus.ACTIVE, new BigDecimal(100));

  @BeforeEach
  public void setUp() throws Exception{
    super.setUp();
    accountDao = dbi.open(AccountDao.class);
    accountDao.createTable();
    accountDao.seedData();
  }

  @Test
  void insert_CreateAndRetrieveAccount_ShouldCreateAndRetrieveAnAccount() {
    accountDao.insert(account);
    Account retrievedAccount = accountDao.findById(account.getId());
    assertEquals(account.getNumber(), retrievedAccount.getNumber());
    assertEquals(account.getName(), retrievedAccount.getName());
    assertEquals(account.getId(), retrievedAccount.getId());
  }

  @Test
  void findByAccountNumber_AccountExists_ReturnMatchingAccount() {
    accountDao.insert(account);
    Account retrievedAccount = accountDao.findByAccountNumber(account.getNumber());
    assertEquals(account.getNumber(), retrievedAccount.getNumber());
  }

  @Test
  void findByAccountNumber_AccountDoesNotExist_ReturnNull() {
    Account retrievedAccount = accountDao.findByAccountNumber(account.getNumber());
    assertNull(retrievedAccount);
  }

  @Test
  void deleteById_AccountExists_DeleteAccount() {
    accountDao.insert(account);
    accountDao.deleteById(account.getId());
    Account retrievedAccount = accountDao.findById(account.getId());
    assertNull(retrievedAccount);
  }

  @Test
  void updateAccount_ExistingAccount_AccountShouldBeUpdated() {
    accountDao.insert(account);
    Account newAccount = new Account(
        10L, 30L, "Savings", "A001", now , now, 1L,
        Account.AccountStatus.ACTIVE, new BigDecimal(1000));

    accountDao.updateBalance(newAccount);
    Account retrievedAccount = accountDao.findById(account.getId());
    assertEquals(retrievedAccount.getId(), account.getId());
    assertNotEquals(retrievedAccount.getBalance(), account.getBalance());
  }
}
