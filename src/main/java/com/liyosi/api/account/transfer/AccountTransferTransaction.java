package com.liyosi.api.account.transfer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.jackson.JsonSnakeCase;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

@JsonSnakeCase
public class AccountTransferTransaction {
  @NotEmpty
  @NotBlank
  private String from;

  @NotEmpty
  @NotBlank
  private String to;

  private BigDecimal amount;

  @NotEmpty
  @NotBlank
  private String isoCurrencyCode;

  public AccountTransferTransaction() {
  }

  @JsonCreator
  public AccountTransferTransaction(
      @JsonProperty("from") String from,
      @JsonProperty("to") String to,
      @JsonProperty("amount") BigDecimal amount,
      @JsonProperty("iso_currency_code") String isoCurrencyCode) {
    this.from = from;
    this.to = to;
    this.amount = amount;
    this.isoCurrencyCode = isoCurrencyCode;
  }

  @JsonProperty("from")
  public String getFrom() {
    return from;
  }

  @JsonProperty("to")
  public String getTo() {
    return to;
  }

  @JsonProperty("amount")
  public BigDecimal getAmount() {
    return amount;
  }

  @JsonProperty("iso_currency_code")
  public String getIsoCurrencyCode() {
    return isoCurrencyCode;
  }
}
