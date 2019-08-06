package com.liyosi.resources;

import com.liyosi.api.account.transfer.AccountTransferTransaction;
import com.liyosi.api.account.transfer.results.AccountTransferSuccessfulResults;
import com.liyosi.core.AccountService;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(DropwizardExtensionsSupport.class)
class AccountResourceTest {

  private static final String targetUrl = "/api/v1/account/transfer";
  private static final AccountService mockAccountService = mock(AccountService.class);
  private static final int unprocessableEntityStatusCode = 422;

  private static final ResourceExtension resources = ResourceExtension.builder()
      .addResource(new AccountResource(mockAccountService))
      .build();
  private AccountTransferTransaction accountTransferTransaction;

  @BeforeEach
  public void setUp() {
    accountTransferTransaction = new AccountTransferTransaction(
        "acc_001", "acc_002", new BigDecimal(10), "UGX");
  }

  @AfterEach
  public void tearDown() {
    Mockito.reset(mockAccountService);
  }

  @Test
  void transfer_ValidRequest_SuccessfullyTransferMoney() throws Exception {
    AccountTransferSuccessfulResults transferResults = new AccountTransferSuccessfulResults(accountTransferTransaction);
    Mockito.doReturn(transferResults) .when(mockAccountService).transfer(ArgumentMatchers.any());

    Response response = resources.client().target(targetUrl)
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(accountTransferTransaction, MediaType.APPLICATION_JSON_TYPE));

    assertEquals(response.getStatusInfo(), Response.Status.OK);
    AccountTransferSuccessfulResults transferResponse = response.readEntity(AccountTransferSuccessfulResults.class);
    assertEquals(transferResponse.getMessage(), transferResults.getMessage());
  }

  @Test
  void transfer_InvalidRequestMissingSourceAccount_ReturnUnprocessableEntity() throws Exception {
    AccountTransferTransaction invalidAccountTransferTransaction = new AccountTransferTransaction(
        "", "acc_002", new BigDecimal(10), "UGX");
    Response transferResponse = resources.client().target(targetUrl)
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(invalidAccountTransferTransaction, MediaType.APPLICATION_JSON_TYPE));
    assertEquals(transferResponse.getStatusInfo().getStatusCode(), unprocessableEntityStatusCode);
    Mockito.verify(mockAccountService, Mockito.never()).transfer(ArgumentMatchers.any());
  }

  @Test
  void transfer_InvalidRequestMissingTargetAccount_ReturnUnprocessableEntity() throws Exception {
    AccountTransferTransaction invalidAccountTransferTransaction = new AccountTransferTransaction(
        "acc_002", "", new BigDecimal(10), "UGX");
    Response transferResponse = resources.client().target(targetUrl)
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(invalidAccountTransferTransaction, MediaType.APPLICATION_JSON_TYPE));
    assertEquals(transferResponse.getStatusInfo().getStatusCode(), unprocessableEntityStatusCode);
    Mockito.verify(mockAccountService, Mockito.never()).transfer(ArgumentMatchers.any());
  }

  @Test
  void transfer_InvalidRequestMissingAmount_ReturnUnprocessableEntity() throws Exception {
    AccountTransferTransaction invalidAccountTransferTransaction = new AccountTransferTransaction(
        "acc_002", "acc_005", null, "UGX");
    Response transferResponse = resources.client().target(targetUrl)
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(invalidAccountTransferTransaction, MediaType.APPLICATION_JSON_TYPE));
    assertEquals(transferResponse.getStatusInfo().getStatusCode(), unprocessableEntityStatusCode);
    Mockito.verify(mockAccountService, Mockito.never()).transfer(ArgumentMatchers.any());
  }

  @Test
  void transfer_InvalidRequestMissingCurrencyCode_ReturnUnprocessableEntity() throws Exception {
    AccountTransferTransaction invalidAccountTransferTransaction = new AccountTransferTransaction(
        "acc_002", "acc_003", new BigDecimal(10), null);
    Response transferResponse = resources.client().target(targetUrl)
        .request(MediaType.APPLICATION_JSON)
        .post(Entity.entity(invalidAccountTransferTransaction, MediaType.APPLICATION_JSON_TYPE));
    assertEquals(transferResponse.getStatusInfo().getStatusCode(), unprocessableEntityStatusCode);
    Mockito.verify(mockAccountService, Mockito.never()).transfer(ArgumentMatchers.any());
  }
}
