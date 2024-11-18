package com.ljx213101212.concurrency_and_multithreading.task5;

import com.ljx213101212.concurrency_and_multithreading.task5.model.Account;
import com.ljx213101212.concurrency_and_multithreading.task5.service.AccountService;
import com.ljx213101212.concurrency_and_multithreading.task5.service.ExchangeService;
import com.ljx213101212.concurrency_and_multithreading.task5.utilities.Constant;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CurrencyExchangeApplication {


    public static void main(String[] args) {

        ExecutorService executor = Executors.newFixedThreadPool(5);
        ExchangeService exchangeService = new ExchangeService();
        CommandController commandController = new CommandController();
        AccountService accountService = new AccountService();

        Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdown));
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                try {
                    System.out.print("> ");
                    String input = scanner.nextLine();
                    if (input.trim().isEmpty()) {
                        //continue; // Skip if input is empty
                    }
                    String[] commands = input.split(" ");
                    String command = commands[0].toLowerCase();
                    switch (command) {
                        case "ma":// short cut for manage account
                            Runnable processCommand = () -> commandController.handleManageAccounts(Arrays.copyOfRange(commands, 1, commands.length));
                            executor.submit(processCommand);
                            //commandController.handleManageAccounts(Arrays.copyOfRange(commands, 1, commands.length));
                            break;

                        case "demo":
                            String demoAccountEmail = "demo@gmail.com";
                            Account demoAccount = accountService.createAccount(demoAccountEmail);

                            accountService.updateAccount(demoAccount.getId(), Constant.USD, new BigDecimal(1000));
                            accountService.updateAccount(demoAccount.getId(), Constant.SGD, new BigDecimal(1000));
                            accountService.updateAccount(demoAccount.getId(), Constant.CNY, new BigDecimal(1000));

                            // Simulate concurrent exchanges (all 6 possibilities for 3 currencies) (n * n - 1)
                            Runnable exchangeTask1 = () -> exchangeService.exchangeCurrency(demoAccount.getId(), Constant.USD, Constant.SGD, new BigDecimal("5"));
                            Runnable exchangeTask2 = () -> exchangeService.exchangeCurrency(demoAccount.getId(), Constant.USD, Constant.CNY, new BigDecimal("5"));
                            Runnable exchangeTask3 = () -> exchangeService.exchangeCurrency(demoAccount.getId(), Constant.SGD, Constant.USD, new BigDecimal("5"));
                            Runnable exchangeTask4 = () -> exchangeService.exchangeCurrency(demoAccount.getId(), Constant.SGD, Constant.CNY, new BigDecimal("5"));
                            Runnable exchangeTask5 = () -> exchangeService.exchangeCurrency(demoAccount.getId(), Constant.CNY, Constant.USD, new BigDecimal("5"));
                            Runnable exchangeTask6 = () -> exchangeService.exchangeCurrency(demoAccount.getId(), Constant.CNY, Constant.SGD, new BigDecimal("5"));

                            // exchange 5 units of all currencies to all other currencies 100 times.
                            for (int i = 1; i < 100; i++) {
                                executor.submit(exchangeTask1);
                                executor.submit(exchangeTask2);
                                executor.submit(exchangeTask3);
                                executor.submit(exchangeTask4);
                                executor.submit(exchangeTask5);
                                executor.submit(exchangeTask6);
                            }
                            break;
                        default:
                            System.out.println("Unknown command: " + command + " (supported command: ma, demo)");
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred: " + e.getMessage());
                }
            }
        }
    }
}
