package com.ljx213101212.concurrency_and_multithreading.task5.model.dao;

import com.ljx213101212.concurrency_and_multithreading.task5.model.Account;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static com.ljx213101212.concurrency_and_multithreading.task5.utilities.Constant.ACCOUNT_DATA_DIR;

public class AccountDAO {


    public AccountDAO() {
    }

    public Account getAccount(String accountId) {
        Path path = Paths.get(ACCOUNT_DATA_DIR, accountId + ".txt");
        if (!Files.exists(path)) {
            return null;
        }
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();
            if (line == null) {
                throw new IllegalArgumentException("File is empty");
            }
            // Split the first line to get account ID and email
            String[] parts = line.split(",");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid format for account ID and email");
            }
            String email = parts[1].trim();

            // Read the currency balances from the remaining lines
            Map<String, BigDecimal> balances = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                parts = line.split(",");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Invalid format for currency and balance: " + line);
                }
                String currency = parts[0].trim();
                BigDecimal balance = new BigDecimal(parts[1].trim());
                balances.put(currency, balance);
            }
            return new Account(accountId, email, balances);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read account", e);
        }
    }

    public void saveAccount(Account account) {
        Path path = Paths.get(ACCOUNT_DATA_DIR, account.getId() + ".txt");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            // Write the first line with account ID and email
            writer.write(account.getId() + "," + account.getEmail());
            writer.newLine();


            // Write each currency and balance on a separate line
            Map<String, BigDecimal> balances = account.getBalances();
            for (Map.Entry<String, BigDecimal> entry : balances.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue());
                writer.newLine();
            }
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save account", e);
        }
    }

    public boolean deleteAccount(String accountId) {
        Path path = Paths.get(ACCOUNT_DATA_DIR, accountId + ".txt");
        try {
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete account", e);
        }
    }


}
