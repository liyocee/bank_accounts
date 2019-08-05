package com.liyosi.api.account.transfer.exceptions;

import com.liyosi.core.exceptions.ErrorCodes;

import javax.ws.rs.ext.Provider;
import java.math.BigDecimal;

@Provider
public class BalanceInsufficientException extends TransferFailedException {
  public BalanceInsufficientException(String account, BigDecimal amount) {
    super(String.format("Account : %s has insufficient balance to transfer: %s", account, amount.toString()),
        ErrorCodes.BALANCE_INSUFFICIENT);
  }
}
