package com.ljx213101212.task4;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class EmployeeManager {
    private EmployeeService employeeService = new EmployeeService();

    public CompletableFuture<Void> displayEmployeeSalary() {
        return employeeService.hiredEmployees()
                .thenCompose(employees -> {
                    List<CompletableFuture<Employee>> enrichedEmployees = employees.stream()
                            .map(employee -> employeeService.getSalary(employee.getId())
                                    .thenApply(salary -> {
                                        employee.setSalary(salary);
                                        return employee;
                                    })).collect(Collectors.toList());

                    return CompletableFuture.allOf(enrichedEmployees.toArray(new CompletableFuture[0]))
                            .thenApply(v -> enrichedEmployees.stream()
                                    .map(CompletableFuture::join)
                                    .collect(Collectors.toList()));
                })
                .thenAccept(employees -> {
                    employees.forEach(employee ->
                            System.out.println("Employee ID: " + employee.getId() + ", Salary: " + employee.getSalary()));
                });
    }
}
