package com.liyosi.resources;

import com.codahale.metrics.annotation.Timed;
import com.liyosi.api.account.transfer.AccountTransferTransaction;
import com.liyosi.api.account.transfer.exceptions.TransferFailedException;
import com.liyosi.api.account.transfer.results.AccountTransferResults;
import com.liyosi.core.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/api/v1/account")
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {
  private static final Logger LOGGER = LoggerFactory.getLogger(AccountResource.class);

  private final AccountService accountService;

  public AccountResource(AccountService accountService) {
    this.accountService = accountService;
  }

  @POST
  @Timed
  @Path("/transfer")
  public AccountTransferResults transfer(
      @NotNull @Valid AccountTransferTransaction accountTransferTransaction) throws TransferFailedException {
    LOGGER.info(
        "About to initiate transfer from account: {} to account: {}",
        accountTransferTransaction.getFrom(),
        accountTransferTransaction.getTo());

    return accountService.transfer(accountTransferTransaction);
  }
}
