package com.liyosi.db.dao;

import com.liyosi.core.mapper.AccountMapper;
import com.liyosi.core.models.Account;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

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
      "status VARCHAR(25) DEFAULT 'ACTIVE' )")
  void createTable();

  @SqlUpdate("INSERT INTO account (" +
      "id," +
      "customer_id," +
      "name," +
      "number," +
      "opened_on," +
      "closed_on," +
      "currency_id," +
      "status) VALUES (:id, :customerId, :name, :number, :openedOn, :closedOn, :currencyId, :status)")
  void insert(@BindBean Account account);

  @SqlQuery("SELECT * FROM account LIMIT :limit")
  List<Account> getAll(@Bind("limit") Long limit);

  @SqlQuery("SELECT * FROM account WHERE id = :id")
  Account findById(@Bind("id") int id);


  @SqlUpdate("DELETE FROM account WHERE id = :id")
  void deleteById(@Bind("id") Long id);

  @Override
  default void seedData() {
    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
    Arrays.asList(
        new Account(1L, 1L, "Current Account - Salary", "acc_001",
            currentTimestamp, currentTimestamp, 1L, Account.AccountStatus.ACTIVE),
        new Account(2L, 2L, "Current Account - Bills", "acc_002",
            currentTimestamp, currentTimestamp, 1L, Account.AccountStatus.ACTIVE))
        .forEach(account -> {
          this.deleteById(account.getId());
          this.insert(account);
        });
  }
}
