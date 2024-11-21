package com.ljx213101212.concurrency_and_multithreading.task5.service;

import com.ljx213101212.concurrency_and_multithreading.task5.model.Account;
import com.ljx213101212.concurrency_and_multithreading.task5.model.dao.AccountDAO;
import com.ljx213101212.concurrency_and_multithreading.task5.model.dao.ExchangeRateDAO;
import com.ljx213101212.concurrency_and_multithreading.task5.utilities.Util;

import java.math.BigDecimal;
import java.util.UUID;

public class AccountService {

    AccountDAO accountDAO;
    ExchangeRateDAO exchangeRateDAO;
    public AccountService() {
        accountDAO = new AccountDAO();
        exchangeRateDAO = new ExchangeRateDAO();
    }

    public Account createAccount(String userEmail) {

        String accountId = UUID.randomUUID().toString();
        Account account = new Account(accountId, userEmail, Util.initBalanceMap(exchangeRateDAO.getExchangeRates().keySet()));
        accountDAO.saveAccount(account);
        System.out.println("Account created with ID: " + accountId);
        return account;
    }

    public Account getAccount(String accountId) {
        Account readAccount = accountDAO.getAccount(accountId);
        if (readAccount != null) {
            System.out.println(readAccount);
        } else {
            System.out.println("Account not found");
        }
        return readAccount;
    }

    public synchronized void updateAccount(String updateAccountId, String currency,  BigDecimal balance) {
        //Utility.getAccountById(accountId)
        //Utility.saveAccount(account)

        Account updateAccount = accountDAO.getAccount(updateAccountId);
        if (updateAccount != null) {
            var newBalance = updateAccount.getBalances();
            newBalance.put(currency, balance);
            Account updatedAccount = new Account(updateAccount.getId(), updateAccount.getEmail(), newBalance);
            accountDAO.saveAccount(updatedAccount);
            System.out.println("Account updated with new balance: " + currency + ": " + balance);
        } else {
            System.out.println("Account not found");
        }
    }

    public void deleteAccount(String accountId) {
        //Utility.deleteAccount(accountId)
        if (accountDAO.deleteAccount(accountId)) {
            System.out.println("Account deleted: " + accountId);
        } else {
            System.out.println("Account not found: " + accountId);
        }

    }


}
