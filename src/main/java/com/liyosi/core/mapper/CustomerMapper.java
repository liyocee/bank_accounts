package com.liyosi.core.mapper;

import com.liyosi.core.models.Customer;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements ResultSetMapper<Customer> {
  @Override
  public Customer map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
    return new Customer(
        resultSet.getLong("id"),
        resultSet.getString("name"));
  }
}
