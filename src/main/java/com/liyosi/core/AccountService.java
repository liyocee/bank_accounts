package com.liyosi.core;

import com.liyosi.api.account.transfer.AccountTransferTransaction;
import com.liyosi.api.account.transfer.exceptions.*;
import com.liyosi.api.account.transfer.results.AccountTransferResults;
import com.liyosi.api.account.transfer.results.AccountTransferSuccessfulResults;
import com.liyosi.core.currency.CurrencyConversionService;
import com.liyosi.core.models.Account;
import com.liyosi.core.models.AccountTransaction;
import com.liyosi.core.models.Currency;
import com.liyosi.db.dao.AccountDao;
import com.liyosi.db.dao.AccountTransactionDao;
import com.liyosi.db.dao.CurrencyDao;
import com.liyosi.db.repository.TransactionRepository;

import javax.inject.Singleton;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Singleton
public class AccountService {

  private AccountDao accountDao;

  private AccountTransactionDao accountTransactionDao;

  private CurrencyDao currencyDao;

  private CurrencyConversionService currencyConversionService;

  private TransactionRepository transactionRepository;

  public AccountService(
      AccountDao accountDao,
      AccountTransactionDao accountTransactionDao,
      CurrencyDao currencyDao,
      CurrencyConversionService currencyConversionService,
      TransactionRepository transactionRepository) {
    this.accountDao = accountDao;
    this.accountTransactionDao = accountTransactionDao;
    this.currencyDao = currencyDao;
    this.currencyConversionService = currencyConversionService;
    this.transactionRepository = transactionRepository;
  }

  public @NotNull synchronized AccountTransferResults transfer(
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

    // Is source account active
    this.checkIfAccountIsActive(fromAccount);
    this.checkIfAccountIsActive(toAccount);

    Currency targetAccountCurrency = currencyDao.findById(toAccount.getCurrencyId());

    CurrencyConversionService.ConvertedCurrency convertedCurrency = currencyConversionService.convert(
        accountTransferTransaction.getAmount(), currency, targetAccountCurrency);

    // Is the account balance sufficient
    if (accountTransferTransaction.getAmount().compareTo(fromAccount.getBalance()) > 0) {
      throw new BalanceInsufficientException(fromAccount.getNumber(), accountTransferTransaction.getAmount());
    }

    // all checks have passed, apply transfer
    createTransactions(fromAccount, toAccount, accountTransferTransaction.getAmount(), convertedCurrency);

    return new AccountTransferSuccessfulResults(accountTransferTransaction);
  }

  private void checkIfAccountIsActive(Account account) throws AccountIsInActiveExistException{
    if (!account.isActive()) {
      throw new AccountIsInActiveExistException(account.getNumber()) ;
    }
  }

  private void createTransactions(
      Account from,
      Account to,
      BigDecimal amount,
      CurrencyConversionService.ConvertedCurrency convertedCurrency) {

    String transactionId = UUID.randomUUID().toString();

    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

    // debit source account, credit target
    AccountTransaction transaction = new AccountTransaction(
        new Random().nextLong(),
        amount,
        from.getId(),
        convertedCurrency.getAmount(),
        convertedCurrency.getRate(),
        to.getId(),
        transactionId,
        currentTimestamp
    );

    Account updatedSourceAccount = new Account(
        from.getId(),
        from.getCustomerId(),
        from.getName(),
        from.getNumber(),
        from.getOpenedOn(),
        from.getClosedOn(),
        from.getCurrencyId(),
        from.getStatus(),
        from.getBalance().subtract(amount)
    );

    Account updatedTargetAccount = new Account(
        to.getId(),
        to.getCustomerId(),
        to.getName(),
        to.getNumber(),
        to.getOpenedOn(),
        to.getClosedOn(),
        to.getCurrencyId(),
        to.getStatus(),
        to.getBalance().add(amount)
    );
    transactionRepository.transferMoney(transaction, updatedSourceAccount, updatedTargetAccount);
  }
}
