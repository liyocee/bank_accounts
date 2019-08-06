package com.liyosi.core.mapper;

import com.liyosi.core.models.Account;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by liyosi on Aug, 2019
 */
public class AccountMapper implements ResultSetMapper<Account> {
  @Override
  public Account map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
    return new Account(
        resultSet.getLong("id"),
        resultSet.getLong("customer_id"),
        resultSet.getString("name"),
        resultSet.getString("number"),
        resultSet.getTimestamp("opened_on"),
        resultSet.getTimestamp("closed_on"),
        resultSet.getLong("currency_id"),
        Account.AccountStatus.valueOf(resultSet.getString("status")),
        resultSet.getBigDecimal("balance")
    );
  }
}
