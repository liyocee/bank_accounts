package com.liyosi.api.account.transfer.exceptions;

import com.liyosi.api.account.transfer.failreasons.TransferFailedReason;

public class FailedTransferException extends Exception {

  private TransferFailedReason reason;
  public FailedTransferException(TransferFailedReason reason) {
    super(reason.getMessage());
    this.reason = reason;
  }
}
