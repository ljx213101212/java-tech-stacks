package com.ljx213101212.concurrency_and_multithreading.task5.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    private String id;

    private String email;

    private Map<String, BigDecimal> balances;

    @Override
    public String toString() {
        return "Account ID: " + id + ", User Email: " + email + ",  Balance: " + balances.toString();
    }

    public BigDecimal getBalance(String currency) {
        return balances.getOrDefault(currency, BigDecimal.ZERO);
    }

    public void debit(String currency, BigDecimal amount) {
        balances.put(currency, getBalance(currency).subtract(amount));
    }

    public void credit(String currency, BigDecimal amount) {
        balances.put(currency, getBalance(currency).add(amount));
    }
}
