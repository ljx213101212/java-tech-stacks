package com.ljx213101212.concurrency_and_multithreading.task5.model.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.ljx213101212.concurrency_and_multithreading.task5.utilities.Constant.ExCHANGE_RATE_DIR;

public class ExchangeRateDAO {

    private final Map<String, BigDecimal> exchangeRates = new ConcurrentHashMap<>();

    public ExchangeRateDAO() {
        populateExchangeRate();
    }

    public BigDecimal getExchangeRate(String fromCurrency, String toCurrency) {
        return exchangeRates.get(fromCurrency + "-" + toCurrency);
    }

    public Map<String, BigDecimal> getExchangeRates() {
        return this.exchangeRates;
    }

    private void populateExchangeRate() {
        Path path = Paths.get(ExCHANGE_RATE_DIR);
        if (!Files.exists(path)) {
            System.out.println("[Internal Error]: ExCHANGE_RATE_DIR: " + path.toString() + " not found");
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String currencyPair = parts[0].trim(); // e.g., USD-SGD
                    double rate = Double.parseDouble(parts[1].trim()); // e.g., 1.334587
                    exchangeRates.put(currencyPair, new BigDecimal(rate));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot parse exchange rate dir: " + path.toString(), e);
        }
    }
}
