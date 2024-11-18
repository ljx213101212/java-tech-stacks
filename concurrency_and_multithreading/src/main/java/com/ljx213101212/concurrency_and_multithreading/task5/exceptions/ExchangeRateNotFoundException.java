package com.ljx213101212.concurrency_and_multithreading.task5.exceptions;

public class ExchangeRateNotFoundException extends RuntimeException{
    public ExchangeRateNotFoundException(String message) {
        super(message);
    }
}
