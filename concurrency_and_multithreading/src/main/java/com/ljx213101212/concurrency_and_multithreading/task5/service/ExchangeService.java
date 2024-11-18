package com.ljx213101212.concurrency_and_multithreading.task5.service;

import com.ljx213101212.concurrency_and_multithreading.task5.exceptions.ExchangeRateNotFoundException;
import com.ljx213101212.concurrency_and_multithreading.task5.exceptions.InsufficientFundsException;
import com.ljx213101212.concurrency_and_multithreading.task5.model.Account;
import com.ljx213101212.concurrency_and_multithreading.task5.model.dao.AccountDAO;
import com.ljx213101212.concurrency_and_multithreading.task5.model.dao.ExchangeRateDAO;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExchangeService {

    private static final Logger LOGGER = Logger.getLogger(ExchangeService.class.getName());

    private final AccountDAO accountDAO = new AccountDAO();
    private final ExchangeRateDAO exchangeRateDAO = new ExchangeRateDAO();
    private final ReentrantLock lock = new ReentrantLock();

    public void exchangeCurrency(String accountId, String fromCurrency, String toCurrency, BigDecimal amount) {
        lock.lock();
        try {
            Account account = accountDAO.getAccount(accountId);
            if (account == null) {
                throw new AccountNotFoundException("Account not found: " + accountId);
            }

            BigDecimal fromBalance = account.getBalance(fromCurrency);
            if (fromBalance == null || fromBalance.compareTo(amount) < 0) {
                throw new InsufficientFundsException("Insufficient funds in " + fromCurrency);
            }

            BigDecimal rate = exchangeRateDAO.getExchangeRate(fromCurrency, toCurrency);
            if (rate == null) {
                throw new ExchangeRateNotFoundException("Exchange rate not found for: " + fromCurrency + " to " + toCurrency);
            }

            BigDecimal toAmount = amount.multiply(rate);
            account.debit(fromCurrency, amount);
            account.credit(toCurrency, toAmount);
            accountDAO.saveAccount(account);

            LOGGER.info(String.format("Exchanged %s %s to %s %s for account %s", amount, fromCurrency, toAmount, toCurrency, accountId));
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Exchange failed", e);
        } finally {
            lock.unlock();
        }
    }

}
