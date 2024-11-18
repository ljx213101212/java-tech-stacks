package com.ljx213101212.concurrency_and_multithreading.task5;

import com.ljx213101212.concurrency_and_multithreading.task5.model.Account;
import com.ljx213101212.concurrency_and_multithreading.task5.service.AccountService;

import java.math.BigDecimal;

public class CommandController {

    AccountService accountService;

    public CommandController() {
        accountService = new AccountService();
    }

    // ACCOUNT MANAGEMENT
    public void handleManageAccounts(String[] args) {
        if (args.length < 1) {
            System.out.println("No account operation specified. \n Usage: ma <(create, delete, read, update_balance)>");
            return;
        }

        String operation = args[0];
        switch (operation) {
            case "create":
                if (args.length < 2) {
                    System.out.println("Usage: create <email>");
                    return;
                }
                String email = args[1];
                accountService.createAccount(email);
                break;
            case "delete":
                if (args.length < 2) {
                    System.out.println("Usage: delete <accountId>");
                    return;
                }
                accountService.deleteAccount(args[1]);
                break;
            case "read":
                if (args.length < 2) {
                    System.out.println("Usage: read <accountId>");
                    return;
                }
                Account readAccount = accountService.getAccount(args[1]);
                System.out.println("Account info:" + readAccount.toString());
                break;
            case "update_balance":
                if (args.length < 3) {
                    System.out.println("Usage: update_balance <accountId> <currency> <balance>");
                    return;
                }
                accountService.updateAccount(args[1], args[2], new BigDecimal(args[3]));
                break;
            default:
                System.out.println("Unknown account operation: " + operation);
        }
    }
}
