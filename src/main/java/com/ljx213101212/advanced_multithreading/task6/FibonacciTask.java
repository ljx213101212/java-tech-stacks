package com.ljx213101212.advanced_multithreading.task6;

import java.util.concurrent.RecursiveTask;

public class FibonacciTask extends RecursiveTask<Long> {
    private final int n;

    public FibonacciTask(int n) {
        this.n = n;
    }

    @Override
    protected Long compute() {
        if (n <= 10) {
            // Use linear algorithm for small n
            return fib(n);
        } else {
            FibonacciTask f1 = new FibonacciTask(n - 1);
            FibonacciTask f2 = new FibonacciTask(n - 2);
            f1.fork(); // fork f1 task
            f2.fork();
            //f2.join() first because it has less word load to make the program more efficient.
            return f2.join() + f1.join();
        }
    }

    private long fib(int n) {
        long[] f = new long[n + 1];
        f[0] = 0;
        f[1] = 1;
        for(int i = 2; i <= n; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f[n];
    }


}