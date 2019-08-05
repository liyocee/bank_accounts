package com.liyosi.db.dao;

import com.liyosi.core.mapper.AccountMapper;
import com.liyosi.core.models.Account;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;


@RegisterMapper(AccountMapper.class)
public interface AccountDao {

  @SqlUpdate("CREATE TABLE IF NOT EXISTS account (" +
      "id BIGINT(20) PRIMARY KEY AUTO_INCREMENT, " +
      "customer_id BIGINT(20) NOT NULL," +
      "name VARCHAR(255) NOT NULL," +
      "number VARCHAR(255) NOT NULL," +
      "opened_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP," +
      "closed_on TIMESTAMP NULL," +
      "currency_id INT(20) NOT NULL," +
      "status TINYINT(1) DEFAULT 1 )")
  void createTable();

  @SqlUpdate("insert into account (" +
      "customer_id," +
      "name," +
      "number," +
      "opened_on," +
      "closed_on," +
      "currency_id," +
      "status) values (" +
      ":name," +
      ":number," +
      ":opened_on," +
      ":closed_on," +
      ":currency_id," +
      ":status)")
  void insert(@BindBean Account account);

  @SqlQuery("SELECT * FROM account LIMIT :limit")
  List<Account> getAll(@Bind("limit") Long limit);

  @SqlQuery("SELECT * FROM account WHERE id = :id")
  Account findById(@Bind("id") int id);
}
