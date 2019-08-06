package com.liyosi.db.dao;

import com.liyosi.core.mapper.CurrencyMapper;
import com.liyosi.core.models.Currency;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Arrays;
import java.util.List;

@RegisterMapper(CurrencyMapper.class)
public interface CurrencyDao extends BaseDao {

  @SqlUpdate("CREATE TABLE IF NOT EXISTS currency (id BIGINT(5) PRIMARY KEY, iso_code VARCHAR(255) UNIQUE)")
  void createTable();

  @SqlUpdate("INSERT INTO currency (id, iso_code) VALUES (:id, :isoCode)")
  void insert(@BindBean Currency currency);

  @SqlQuery("SELECT * FROM currency LIMIT :limit")
  List<Currency> getAll(@Bind("limit") Long limit);

  @SqlQuery("SELECT * FROM currency WHERE id = :id")
  Currency findById(@Bind("id") Long id);

  @SqlQuery("SELECT * FROM currency WHERE iso_code = :isoCode")
  Currency findByIsoCode(@Bind("isoCode") String isoCode);


  @SqlUpdate("DELETE FROM currency WHERE id = :id")
  void deleteById(@Bind("id") Long id);

  @Override
  default void seedData() {
    Arrays.asList(
        new Currency(1L, "USD"),
        new Currency(2L, "KES")
    ).forEach(currency -> {
      this.deleteById(currency.getId());
      this.insert(currency);
    });
  }
}
