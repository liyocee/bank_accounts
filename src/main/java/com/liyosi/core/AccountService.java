package com.liyosi.core;

import com.liyosi.api.account.transfer.AccountTransferTransaction;
import com.liyosi.api.account.transfer.exceptions.AccountDoesNotExistException;
import com.liyosi.api.account.transfer.exceptions.BalanceInsufficientException;
import com.liyosi.api.account.transfer.exceptions.InvalidCurrencyCodeException;
import com.liyosi.api.account.transfer.exceptions.TransferFailedException;
import com.liyosi.api.account.transfer.results.AccountTransferResults;
import com.liyosi.api.account.transfer.results.AccountTransferSuccessfulResults;
import com.liyosi.core.currency.CurrencyConversionService;
import com.liyosi.core.models.Account;
import com.liyosi.core.models.AccountTransaction;
import com.liyosi.core.models.Currency;
import com.liyosi.db.dao.AccountDao;
import com.liyosi.db.dao.AccountTransactionDao;
import com.liyosi.db.dao.CurrencyDao;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class AccountService {

  private AccountDao accountDao;

  private AccountTransactionDao accountTransactionDao;

  private CurrencyDao currencyDao;

  private CurrencyConversionService currencyConversionService;

  public AccountService(
      AccountDao accountDao,
      AccountTransactionDao accountTransactionDao,
      CurrencyDao currencyDao,
      CurrencyConversionService currencyConversionService) {
    this.accountDao = accountDao;
    this.accountTransactionDao = accountTransactionDao;
    this.currencyDao = currencyDao;
    this.currencyConversionService = currencyConversionService;
  }

  public @NotNull AccountTransferResults transfer(
      @NotNull AccountTransferTransaction accountTransferTransaction) throws TransferFailedException {
    // Does the currency code exist
    Currency currency = Optional.ofNullable(currencyDao
        .findByIsoCode(accountTransferTransaction.getIsoCurrencyCode()))
        .orElseThrow(() -> new InvalidCurrencyCodeException(accountTransferTransaction.getIsoCurrencyCode()));

    // Does the source account exist
    Account fromAccount = Optional.ofNullable(accountDao
        .findByAccountNumber(accountTransferTransaction.getFrom()))
        .orElseThrow(() -> new AccountDoesNotExistException(accountTransferTransaction.getFrom()));

    // Does the destination account exist
    Account toAccount = Optional.ofNullable(accountDao
        .findByAccountNumber(accountTransferTransaction.getTo()))
        .orElseThrow(() -> new AccountDoesNotExistException(accountTransferTransaction.getTo()));

    Currency targetAccountCurrency = currencyDao.findById(toAccount.getCurrencyId());

    CurrencyConversionService.ConvertedCurrency convertedCurrency = currencyConversionService.convert(
        accountTransferTransaction.getAmount(), currency, targetAccountCurrency);

    // Is the account balance sufficient
    if (accountTransferTransaction.getAmount().compareTo(fromAccount.getBalance()) > 0) {
      throw new BalanceInsufficientException(fromAccount.getNumber(), accountTransferTransaction.getAmount());
    }

    // all checks have passed, apply transfer
    createTransactions(fromAccount, toAccount, accountTransferTransaction.getAmount(), currency, convertedCurrency);

    return new AccountTransferSuccessfulResults(accountTransferTransaction);
  }

  private void createTransactions(
      Account from,
      Account to,
      BigDecimal amount,
      Currency currency,
      CurrencyConversionService.ConvertedCurrency convertedCurrency) {

    String transactionId = UUID.randomUUID().toString();

    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
    /**
     * Todo - do it transactionally
     */

    // debit source account
    accountTransactionDao.insert(new AccountTransaction(
        new Random().nextLong(),
        amount,
        from.getId(),
        convertedCurrency.getAmount(),
        convertedCurrency.getRate(),
        to.getId(),
        transactionId,
        currentTimestamp
    ));

    // update balances
    accountDao.update(new Account(
        from.getId(),
        from.getCustomerId(),
        from.getName(),
        from.getNumber(),
        from.getOpenedOn(),
        from.getClosedOn(),
        from.getCurrencyId(),
        from.getStatus(),
        from.getBalance().subtract(amount)
    ));

    // update balances
    accountDao.update(new Account(
        to.getId(),
        to.getCustomerId(),
        to.getName(),
        to.getNumber(),
        to.getOpenedOn(),
        to.getClosedOn(),
        to.getCurrencyId(),
        to.getStatus(),
        to.getBalance().add(amount)
    ));
  }
}
