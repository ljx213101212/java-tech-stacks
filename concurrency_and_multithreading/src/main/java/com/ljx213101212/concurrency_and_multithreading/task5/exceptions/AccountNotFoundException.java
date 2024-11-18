package com.ljx213101212.concurrency_and_multithreading.task5.exceptions;

class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}

