package com.ljx213101212.concurrency_and_multithreading.task4;

import java.util.ArrayList;
import java.util.List;

public class BlockObjectPoolExample {
    public static void main(String[] args) throws InterruptedException {
        var pool = new BlockingObjectPool(100);

        List<Thread> takerThreads = new ArrayList<>();
        List<Thread> getterThreads = new ArrayList<>();

        for (int i = 1; i < 3; i++) {
            var taker = new Thread(() -> {
                while(true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                       break;
                    }
                    pool.take(new Object());
                }
            });
            taker.start();
            takerThreads.add(taker);
        }

        for (int i = 1; i < 3; i++) {
            var getter = new Thread(() -> {
                while(true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                       break;
                    }
                    pool.get();
                }
            });
            getter.start();
            getterThreads.add(getter);
        }

        System.out.println("Multiple threads are running for 10 seconds");
        Thread.sleep(10000); //let takers / getters threads run 10 seconds

        takerThreads.forEach(Thread::interrupt);
        getterThreads.forEach(Thread::interrupt);
        getterThreads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        getterThreads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        System.out.println("Program Runs 10 seconds and complete!");
    }
}
