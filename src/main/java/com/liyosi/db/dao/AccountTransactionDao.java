package com.liyosi.db.dao;

import com.liyosi.core.mapper.AccountTransactionMapper;
import com.liyosi.core.models.AccountTransaction;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RegisterMapper(AccountTransactionMapper.class)
public interface AccountTransactionDao extends BaseDao {

  Logger LOGGER = LoggerFactory.getLogger(AccountTransactionDao.class);

  @SqlUpdate("CREATE TABLE IF NOT EXISTS account_transaction (" +
      "id BIGINT(20) PRIMARY KEY AUTO_INCREMENT, " +
      "debit_account BIGINT(20) NOT NULL," +
      "debit_amount DECIMAL(15,4) NOT NULL," +
      "credit_account BIGINT(20) NOT NULL," +
      "credit_amount DECIMAL(15,4) NOT NULL," +
      "conversion_rate DECIMAL(15,4) NOT NULL," +
      "transaction_id VARCHAR(255) NOT NULL," +
      "timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP)")
  void createTable();

  @SqlUpdate("INSERT INTO account_transaction (" +
      "id," +
      "debit_amount," +
      "debit_account," +
      "credit_amount," +
      "credit_account," +
      "conversion_rate," +
      "transaction_id," +
      "timestamp) VALUES(:id, :debitAmount, :debitAccount, :creditAmount, :conversionRate, :creditAccount, :transactionId, :timestamp)")
  void insert(@BindBean AccountTransaction accountTransaction);

  @SqlQuery("SELECT * FROM account_transaction LIMIT :limit")
  List<AccountTransaction> getAll(@Bind("limit") Long limit);

  @SqlQuery("SELECT * FROM account_transaction WHERE id = :id")
  AccountTransaction findById(@Bind("id") int id);

  @Override
  default void seedData() {
    LOGGER.info("Skipping seed for account_transaction table");
  }
}
