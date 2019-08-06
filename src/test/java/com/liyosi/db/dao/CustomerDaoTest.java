package com.liyosi.db.dao;

import com.liyosi.core.models.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CustomerDaoTest extends BaseDaoTestCase {

  private CustomerDao customerDao;

  private final Customer customer = new Customer(10L, "Ken Wad");

  @BeforeEach
  public void setUp() throws Exception{
    super.setUp();
    customerDao = dbi.open(CustomerDao.class);
    customerDao.createTable();
    customerDao.seedData();
  }

  @Test
  void create_CreateCustomer_ShouldCreateAndRetrieveCreatedCustomer() {
    customerDao.insert(customer);

    Customer retrievedCustomer = customerDao.findById(customer.getId());
    assertEquals(retrievedCustomer.getName(), customer.getName());
    assertEquals(retrievedCustomer.getId(), customer.getId());
  }

  @Test
  void deleteById_CustomerExists_ShouldDeleteCustomer() {
    customerDao.insert(customer);
    customerDao.deleteById(customer.getId());
    Customer retrievedCustomer = customerDao.findById(customer.getId());

    assertNull(retrievedCustomer);
  }
}
