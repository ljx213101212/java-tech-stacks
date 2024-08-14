package com.ljx213101212.advanced_multithreading.task4;

public class Employee {

    private int id;
    private double salary;

    public Employee(int theId) {
        id = theId;
        salary = 1000; // default salary is 1000
    }

    public Employee(int theId, double theSalary) {
        id = theId;
        salary = theSalary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
