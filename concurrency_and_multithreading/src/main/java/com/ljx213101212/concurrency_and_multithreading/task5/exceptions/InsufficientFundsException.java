package com.ljx213101212.concurrency_and_multithreading.task5.exceptions;

public class InsufficientFundsException extends  RuntimeException {
    public InsufficientFundsException(String message) {
        super(message);
    }
}
