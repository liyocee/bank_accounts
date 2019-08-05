package com.liyosi.api.account.transfer.failreasons;

import java.math.BigDecimal;

public class BalanceInsufficient extends TransferFailedReason {

  public BalanceInsufficient(String account, BigDecimal amount) {
    super(String.format("Account : %s has insufficient balance to transfer: %d", account, amount));
  }
}
