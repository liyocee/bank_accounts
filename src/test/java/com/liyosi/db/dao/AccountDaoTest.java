package com.liyosi.db.dao;

import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(DropwizardExtensionsSupport.class)
class AccountDaoTest {

  public DAOTestExtension daoTestRule = DAOTestExtension.newBuilder()
      .addEntityClass(AccountDao.class)
      .build();

  private AccountDao accountDao;

  @BeforeEach
  void setUp() {

  }
}
