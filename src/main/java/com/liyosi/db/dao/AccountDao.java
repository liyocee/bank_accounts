package com.liyosi.db.dao;

import com.liyosi.core.mapper.AccountMapper;
import com.liyosi.core.models.Account;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;


@RegisterMapper(AccountMapper.class)
public interface AccountDao extends BaseDao {

  @SqlUpdate("CREATE TABLE IF NOT EXISTS account (" +
      "id BIGINT(20) PRIMARY KEY AUTO_INCREMENT, " +
      "customer_id BIGINT(20) NOT NULL," +
      "name VARCHAR(255) NOT NULL," +
      "number VARCHAR(255) NOT NULL UNIQUE," +
      "opened_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
      "closed_on TIMESTAMP NULL," +
      "currency_id INT(20) NOT NULL," +
      "balance DECIMAL(15,4) NOT NULL DEFAULT 0.0," +
      "status VARCHAR(25) DEFAULT 'ACTIVE' )")
  void createTable();

  @SqlUpdate("INSERT INTO account (" +
      "id," +
      "customer_id," +
      "name," +
      "balance," +
      "number," +
      "opened_on," +
      "closed_on," +
      "currency_id," +
      "status) VALUES (:id, :customerId, :name, :balance, :number, :openedOn, :closedOn, :currencyId, :status)")
  void insert(@BindBean Account account);

  @SqlQuery("SELECT * FROM account LIMIT :limit")
  List<Account> getAll(@Bind("limit") Long limit);

  @SqlQuery("SELECT * FROM account WHERE id = :id")
  Account findById(@Bind("id") int id);

  @SqlQuery("SELECT * FROM account WHERE number = :number")
  Account findByAccountNumber(@Bind("number") String number);


  @SqlUpdate("DELETE FROM account WHERE id = :id")
  void deleteById(@Bind("id") Long id);

  @SqlUpdate("UPDATE account SET balance = :balance WHERE id = :id")
  int update(@BindBean Account account);

  @Override
  default void seedData() {
    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
    Arrays.asList(
        new Account(1L, 1L, "Current Account - Salary", "acc_001",
            currentTimestamp, currentTimestamp, 1L, Account.AccountStatus.ACTIVE, new BigDecimal(1000)),
        new Account(2L, 2L, "Current Account - Bills", "acc_002",
            currentTimestamp, currentTimestamp, 1L, Account.AccountStatus.ACTIVE, new BigDecimal(0)))
        .forEach(account -> {
          this.deleteById(account.getId());
          this.insert(account);
        });
  }
}
