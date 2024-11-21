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
public class Balance {
    private String currency;
    private Map<String, BigDecimal> balances;

    @Override
    public String toString() {
        return "Currency: " + currency + " Balances: " + balances.toString();
    }
}
