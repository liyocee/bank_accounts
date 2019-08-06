package com.liyosi.api.account.transfer.exceptions;

public abstract class TransferFailedException extends Exception {

  private String message;

  private Integer errorCode;

  public TransferFailedException(String message, Integer errorCode) {
    this.message = message;
    this.errorCode = errorCode;
  }

  public String getMessage() {
    return message;
  }

  public Integer getErrorCode() {
    return errorCode;
  }
}


