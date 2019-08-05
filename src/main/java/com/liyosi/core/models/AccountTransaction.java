package com.liyosi.core.models;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class AccountTransaction extends BaseModel {

  @NotNull
  private String transactionId;

  @NotNull
  private BigDecimal debitAmount;

  @NotNull
  private Long debitAccount;

  @NotNull
  private BigDecimal creditAmount;

  @NotNull
  private Long creditAccount;

  @NotNull
  private Timestamp timestamp;

  public AccountTransaction(
      Long id,
      BigDecimal debitAmount,
      Long debitAccount,
      BigDecimal creditAmount,
      Long creditAccount,
      String transactionId,
      Timestamp timestamp) {
    super(id);
    this.transactionId = transactionId;
    this.debitAmount = debitAmount;
    this.debitAccount = debitAccount;
    this.creditAmount = creditAmount;
    this.creditAccount = creditAccount;
    this.timestamp = timestamp;
  }

  public BigDecimal getDebitAmount() {
    return debitAmount;
  }

  public Long getDebitAccount() {
    return debitAccount;
  }

  public BigDecimal getCreditAmount() {
    return creditAmount;
  }

  public Long getCreditAccount() {
    return creditAccount;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public String getTransactionId() {
    return transactionId;
  }
}
