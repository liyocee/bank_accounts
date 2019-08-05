package com.liyosi.core.mapper;

import com.liyosi.core.models.AccountTransaction;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountTransactionMapper implements ResultSetMapper<AccountTransaction> {
  @Override
  public AccountTransaction map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
    return new AccountTransaction(
        resultSet.getLong("id"),
        resultSet.getBigDecimal("debit_amount"),
        resultSet.getLong("debit_account"),
        resultSet.getBigDecimal("credit_amount"),
        resultSet.getLong("credit_account"),
        resultSet.getString("transaction_id"),
        resultSet.getTimestamp("timestamp")
    );
  }
}
