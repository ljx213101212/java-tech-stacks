package com.ljx213101212.task4;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class EmployeeService {

    public static HashMap<Integer, Double> salaryMap = new HashMap<>();
    static {
        salaryMap.put(1, 5000.0);
        salaryMap.put(2, 8000.0);
        salaryMap.put(3, 6500.0);
    }


    public CompletableFuture<List<Employee>> hiredEmployees()  {
        return CompletableFuture.supplyAsync(() -> List.of(new Employee(1), new Employee(2), new Employee(3)),  CompletableFuture.delayedExecutor(200, TimeUnit.MILLISECONDS));
    }

    public CompletableFuture<Double> getSalary(int employeeId) {
        return CompletableFuture.supplyAsync(() -> {
            // Simulated salary fetch based on employee ID
            return salaryMap.containsKey(employeeId) ? salaryMap.get(employeeId) : 1000;
        }, CompletableFuture.delayedExecutor(200, TimeUnit.MILLISECONDS));
    }
}