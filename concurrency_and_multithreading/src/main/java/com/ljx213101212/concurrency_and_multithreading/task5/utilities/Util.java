package com.ljx213101212.concurrency_and_multithreading.task5.utilities;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Util {

    public static Map<String, BigDecimal> initBalanceMap(Set<String> currencies) {
        Map<String, BigDecimal> balance = new HashMap<>();
        currencies.stream()
                .map(currency -> currency.split("-"))
                .flatMap(Arrays::stream)
                .distinct()
                .forEach(unit -> balance.put(unit, BigDecimal.ZERO));
        return balance;
    }
}
