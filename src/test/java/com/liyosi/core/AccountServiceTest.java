package com.liyosi.core;

import com.liyosi.api.account.transfer.AccountTransferTransaction;
import com.liyosi.api.account.transfer.exceptions.*;
import com.liyosi.core.currency.CurrencyConversionService;
import com.liyosi.core.models.Account;
import com.liyosi.core.models.Currency;
import com.liyosi.db.dao.AccountDao;
import com.liyosi.db.dao.AccountTransactionDao;
import com.liyosi.db.dao.CurrencyDao;
import com.liyosi.db.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

class AccountServiceTest {

  @Mock
  private AccountDao mockAccountDao;

  @Mock
  private AccountTransactionDao mockAccountTransactionDao;

  @Mock
  private CurrencyDao mockCurrencyDao;

  @Mock
  private CurrencyConversionService mockCurrencyConversionService;

  @Mock
  private TransactionRepository mockTransactionRepository;

  private final Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
  private AccountTransferTransaction accountTransferTransaction;
  private AccountService accountService;
  private Currency currency;
  private Account sourceAccount;
  private Account targetAccount;
  private Account inActiveAccount;
  private CurrencyConversionService.ConvertedCurrency convertedCurrency;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
    accountTransferTransaction = new AccountTransferTransaction(
        "acc_001", "acc_002", new BigDecimal(10), "UGX");

    accountService = new AccountService(
        mockAccountDao, mockAccountTransactionDao, mockCurrencyDao, mockCurrencyConversionService,
        mockTransactionRepository);

    currency = new Currency(10L, "UGX");
    sourceAccount = new Account(
        10L,
        1L,
        "Savings",
        "0001",
        currentTimestamp,
        null,
        currency.getId(),
        Account.AccountStatus.ACTIVE,
        new BigDecimal(1000));

    targetAccount = new Account(
        11L,
        2L,
        "Savings",
        "0002",
        currentTimestamp,
        null,
        currency.getId(),
        Account.AccountStatus.ACTIVE,
        new BigDecimal(100));

    inActiveAccount= new Account(
        10L,
        1L,
        "Savings",
        "0001",
        currentTimestamp,
        null,
        currency.getId(),
        Account.AccountStatus.DORMANT,
        new BigDecimal(1000));

  }

  @Test
  void transfer_ThrowInvalidCurrencyCodeException_InvalidCurrency() {
    doReturn(null).when(mockCurrencyDao).findByIsoCode(ArgumentMatchers.any());
    assertThrows(InvalidCurrencyCodeException.class, () -> accountService.transfer(accountTransferTransaction));
  }

  @Test
  void transfer_ThrowAccountDoesNotExistException_MissingSourceAccount() {
    doReturn(currency).when(mockCurrencyDao).findByIsoCode(ArgumentMatchers.any());
    doReturn(null).when(mockAccountDao).findByAccountNumber(accountTransferTransaction.getFrom());
    assertThrows(AccountDoesNotExistException.class, () -> accountService.transfer(accountTransferTransaction));
  }

  @Test
  void transfer_ThrowAccountDoesNotExistException_MissingTargetAccount() {
    doReturn(currency).when(mockCurrencyDao).findByIsoCode(ArgumentMatchers.any());
    doReturn(sourceAccount).when(mockAccountDao).findByAccountNumber(accountTransferTransaction.getFrom());
    doReturn(null).when(mockAccountDao).findByAccountNumber(accountTransferTransaction.getTo());
    assertThrows(AccountDoesNotExistException.class, () -> accountService.transfer(accountTransferTransaction));
  }

  @Test
  void transfer_ThrowAccountIsInActiveExistException_SourceAccountInactive() {
    doReturn(currency).when(mockCurrencyDao).findByIsoCode(ArgumentMatchers.any());
    doReturn(inActiveAccount).when(mockAccountDao).findByAccountNumber(accountTransferTransaction.getFrom());
    doReturn(sourceAccount).when(mockAccountDao).findByAccountNumber(accountTransferTransaction.getTo());
    assertThrows(AccountIsInActiveExistException.class, () -> accountService.transfer(accountTransferTransaction));
  }

  @Test
  void transfer_ThrowAccountIsInActiveExistException_TargetAccountInactive() {
    doReturn(currency).when(mockCurrencyDao).findByIsoCode(ArgumentMatchers.any());
    doReturn(sourceAccount).when(mockAccountDao).findByAccountNumber(accountTransferTransaction.getFrom());
    doReturn(inActiveAccount).when(mockAccountDao).findByAccountNumber(accountTransferTransaction.getTo());
    assertThrows(AccountIsInActiveExistException.class, () -> accountService.transfer(accountTransferTransaction));
  }

  @Test
  void transfer_ThrowCurrencyConversionException_CannotFetchExchangeRate() throws Exception {
    doReturn(currency).when(mockCurrencyDao).findByIsoCode(ArgumentMatchers.any());
    doReturn(sourceAccount).when(mockAccountDao).findByAccountNumber(accountTransferTransaction.getFrom());
    doReturn(targetAccount).when(mockAccountDao).findByAccountNumber(accountTransferTransaction.getTo());
    doThrow(CurrencyConversionException.class).when(mockCurrencyConversionService).convert(
        ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
    assertThrows(CurrencyConversionException.class, () -> accountService.transfer(accountTransferTransaction));
  }

  @Test
  void transfer_ThrowBalanceInsufficientException_InsufficientFundsInSourceAccount() throws Exception {
    AccountTransferTransaction accountTransactionBig = new AccountTransferTransaction(
        "acc_001", "acc_002", new BigDecimal(1000000), "UGX");

    convertedCurrency = new CurrencyConversionService.ConvertedCurrency(
        new BigDecimal(1.0), accountTransactionBig.getAmount());

    doReturn(currency).when(mockCurrencyDao).findByIsoCode(ArgumentMatchers.any());
    doReturn(sourceAccount).when(mockAccountDao).findByAccountNumber(accountTransactionBig.getFrom());
    doReturn(targetAccount).when(mockAccountDao).findByAccountNumber(accountTransactionBig.getTo());
    doReturn(convertedCurrency).when(mockCurrencyConversionService).convert(
        ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());

    assertThrows(BalanceInsufficientException.class, () -> accountService.transfer(accountTransactionBig));
  }

  @Test
  void transfer_TransferMoneySuccessfully() throws Exception {

    convertedCurrency = new CurrencyConversionService.ConvertedCurrency(
        new BigDecimal(1.0), accountTransferTransaction.getAmount());

    doReturn(currency).when(mockCurrencyDao).findByIsoCode(ArgumentMatchers.any());
    doReturn(sourceAccount).when(mockAccountDao).findByAccountNumber(accountTransferTransaction.getFrom());
    doReturn(targetAccount).when(mockAccountDao).findByAccountNumber(accountTransferTransaction.getTo());
    doReturn(convertedCurrency).when(mockCurrencyConversionService).convert(
        ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());

    accountService.transfer(accountTransferTransaction);

    Mockito.verify(mockTransactionRepository, Mockito.times(1))
        .transferMoney(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any());
  }
}
