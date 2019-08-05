package com.liyosi.core.mapper;

import com.liyosi.core.models.Currency;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CurrencyMapper implements ResultSetMapper<Currency> {
  @Override
  public Currency map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
    return new Currency(resultSet.getLong("id"), resultSet.getString("iso_code"));
  }
}
