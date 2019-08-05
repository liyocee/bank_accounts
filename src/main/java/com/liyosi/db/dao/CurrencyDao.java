package com.liyosi.db.dao;

import com.liyosi.core.mapper.CurrencyMapper;
import com.liyosi.core.models.Currency;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

@RegisterMapper(CurrencyMapper.class)
public interface CurrencyDao {

  @SqlUpdate("CREATE TABLE IF NOT EXISTS currency (id INTEGER PRIMARY KEY, iso_code VARCHAR(255) )")
  void createTable();

  @SqlUpdate("insert into currency (iso_code) values (:iso_code)")
  void insert(@BindBean Currency currency);

  @SqlQuery("SELECT * FROM currency LIMIT :limit")
  List<Currency> getAll(@Bind("limit") Long limit);

  @SqlQuery("SELECT * FROM currency WHERE id = :id")
  Currency findById(@Bind("id") int id);
}
