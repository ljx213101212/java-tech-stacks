package com.ljx213101212.concurrency_and_multithreading.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DeadlockExample {
    private final List<Integer> numberList = new ArrayList<>();
    private final Random random = new Random();

    public static void main(String[] args) {
        DeadlockExample example = new DeadlockExample();
        example.startThreads();
    }

    public void startThreads() {
        Thread writerThread = new Thread(new WriterTask(), "Writer-Thread");
        Thread sumThread = new Thread(new SumTask(), "Sum-Thread");
        Thread sqrtSumSquaresThread = new Thread(new SqrtSumSquaresTask(), "SqrtSumSquares-Thread");

        writerThread.start();
        sumThread.start();
        sqrtSumSquaresThread.start();
    }

    class WriterTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (numberList) {
                    int number = random.nextInt(100); // Random number between 0 and 99
                    numberList.add(number);
                }
                // Sleep briefly to allow other threads to run (avoid starvation)
                try {
                    Thread.sleep(10); // Adjust as needed
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }


    class SumTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (numberList) {
                    int sum = 0;
                    for (int num : numberList) {
                        sum += num;
                    }
                    System.out.println("Sum of numbers: " + sum);
                }
                // Avoid starvation
                try {
                    Thread.sleep(100); // Adjust as needed
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    class SqrtSumSquaresTask implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (numberList) {
                    double sumSquares = 0;
                    for (int num : numberList) {
                        sumSquares += num * num;
                    }
                    double result = Math.sqrt(sumSquares);
                    System.out.println("Square root of sum of squares: " + result);
                }
                // Avoid starvation
                try {
                    Thread.sleep(100); // Adjust as needed
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

}

