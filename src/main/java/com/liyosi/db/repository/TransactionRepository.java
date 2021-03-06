package com.liyosi.db.repository;

import com.liyosi.core.models.Account;
import com.liyosi.core.models.AccountTransaction;
import com.liyosi.db.dao.AccountDao;
import com.liyosi.db.dao.AccountTransactionDao;
import org.skife.jdbi.v2.sqlobject.CreateSqlObject;
import org.skife.jdbi.v2.sqlobject.Transaction;

public abstract class TransactionRepository {

  @CreateSqlObject
  abstract AccountDao accountDao();

  @CreateSqlObject
  abstract AccountTransactionDao accountTransactionDao();

  @Transaction
  public void transferMoney(AccountTransaction accountTransaction, Account sourceAccount, Account targetAccount) {
    accountTransactionDao().insert(accountTransaction);
    accountDao().updateBalance(sourceAccount);
    accountDao().updateBalance(targetAccount);
  }
}
