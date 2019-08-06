package com.liyosi.core.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Account extends BaseModel {

 public enum AccountStatus {
  ACTIVE, DORMANT, CLOSED, FLAGGED
 }

 @NotNull
 private Long customerId;

 @NotNull
 private String name;

 @NotNull
 private String number;

 @NotNull
 private Timestamp openedOn;

 @Null
 private Timestamp closedOn;

 @NotNull
 private Long currencyId;

 @NotNull
 private BigDecimal balance;

 @NotNull
 private AccountStatus status = AccountStatus.ACTIVE;

 public Account(
     Long id,
     Long customerId,
     String name,
     String number,
     Timestamp openedOn,
     Timestamp closedOn,
     Long currencyId,
     AccountStatus status,
     BigDecimal balance) {
  super(id);
  this.customerId = customerId;
  this.name = name;
  this.number = number;
  this.openedOn = openedOn;
  this.closedOn = closedOn;
  this.currencyId = currencyId;
  this.status = status;
  this.balance = balance;
 }

 public Long getCustomerId() {
  return customerId;
 }

 public String getName() {
  return name;
 }

 public String getNumber() {
  return number;
 }

 public Timestamp getOpenedOn() {
  return openedOn;
 }

 public Timestamp getClosedOn() {
  return closedOn;
 }

 public Long getCurrencyId() {
  return currencyId;
 }

 public AccountStatus getStatus() {
  return status;
 }

 public BigDecimal getBalance() {
  return balance;
 }

 public Boolean isActive() {
  return this.status == AccountStatus.ACTIVE;
 }
}

