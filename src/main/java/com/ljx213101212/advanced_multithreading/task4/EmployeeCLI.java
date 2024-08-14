package com.ljx213101212.advanced_multithreading.task4;

import java.util.concurrent.ExecutionException;

public class EmployeeCLI {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        EmployeeManager employeeManager = new EmployeeManager();
        employeeManager.displayEmployeeSalary().get();
    }
}
