package com.liyosi.db.dao;

import com.liyosi.core.mapper.CustomerMapper;
import com.liyosi.core.models.Customer;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(CustomerMapper.class)
public interface CustomerDao {

  @SqlUpdate("CREATE TABLE IF NOT EXISTS customer (id INTEGER PRIMARY KEY, name VARCHAR(255) )")
  void createTable();

  @SqlUpdate("insert into PERSON (name) values (:name)")
  void insert(@BindBean Customer customer);

  @SqlQuery("SELECT * FROM customer LIMIT :limit")
  List<Customer> getAll(@Bind("limit") Long limit);

  @SqlQuery("SELECT * FROM customer WHERE id = :id")
  Customer findById(@Bind("id") int id);
}
