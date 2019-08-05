package com.liyosi.api.account.transfer.failreasons;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class TransferFailedReason {
  private String message;

  public TransferFailedReason(String message) {
    this.message = message;
  }

  @JsonProperty("message")
  public String getMessage() {
    return message;
  }
}
