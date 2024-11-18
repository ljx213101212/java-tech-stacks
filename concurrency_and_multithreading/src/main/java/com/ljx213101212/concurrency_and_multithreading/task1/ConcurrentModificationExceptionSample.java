package com.ljx213101212.concurrency_and_multithreading.task1;

import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentModificationExceptionSample {

    Map<Integer, Integer> hashMap = new HashMap<>();
    Map<Integer, Integer> synchronizedMap = Collections.synchronizedMap(new HashMap<>());
    Map<Integer, Integer> concurrentHashMap = new ConcurrentHashMap<>();
    Map<Integer, Integer> myThreadSafeMap = new ThreadSafeMap<>();
    //use Java Reflection to access private field "modCount"
    //The HashMap maintains an internal counter called modCount,
    //which is incremented every time the map undergoes a structural modification (adding or removing keys).
    private void printHashMapModCount() {
        try {
            Field modCountField = HashMap.class.getDeclaredField("modCount");
            modCountField.setAccessible(true);
            int modCount = (int) modCountField.get(hashMap);
            System.out.println("Current modCount: " + modCount);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void useHashMapAndThrow() throws InterruptedException {

        Thread thread1 = new Thread(() -> {
            for (int i = 1; i < 10; i++) {
                hashMap.put(i, i);
                printHashMapModCount();
            }

            System.out.println("thread 1 is completed");
        }, "thread-1");

        Thread thread2 = new Thread(() -> {
            // Iterator mechanism:
            // Iterator checks "modCount" ,
            // If it has changed,  iterator throws ConcurrentModificationException
            for (var entry : hashMap.entrySet()) {
                hashMap.put(255, entry.getValue() + hashMap.getOrDefault(255, 0)); //throw exception
                printHashMapModCount(); // Current ModCount: 10 -> structural modification triggered.
            }
            System.out.println("Should throw exception before this line");
        }, "thread-2");

        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
    }

    public void useConcurrentHashMapAndOk() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            for (int i = 1; i < 10; i++) {
                concurrentHashMap.put(i, i);
            }
            System.out.println("thread 1 is completed");
        }, "thread-1");

        Thread thread2 = new Thread(() -> {

            for (var entry : concurrentHashMap.entrySet()) {
                if (entry.getKey() != 255) {
                    concurrentHashMap.put(255, entry.getValue() + concurrentHashMap.getOrDefault(255, 0));
                    //no exception
                }
            }
            System.out.println("Sum: " +  concurrentHashMap.getOrDefault(255, 0));
        }, "thread-2");

        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
    }

    public void useSynchronizedMapAndFailFast() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            for (int i = 1; i < 10; i++) {
                synchronizedMap.put(i, i);
            }
            System.out.println("thread 1 is completed");
        }, "thread-1");

        Thread thread2 = new Thread(() -> {
            Set<Map.Entry<Integer, Integer>> s = synchronizedMap.entrySet();  // Needn't be in synchronized block
            synchronized (synchronizedMap) {  // Synchronizing on m, not s!
                Iterator i = s.iterator(); // Must be in synchronized block
                while (i.hasNext()) {
                    Map.Entry<Integer, Integer> entry = (Map.Entry<Integer, Integer>) i.next();
                    if (entry.getKey() != 255) {
                        synchronizedMap.put(255, entry.getValue() + synchronizedMap.getOrDefault(255, 0));
                    }
                }

            }
            System.out.println("Sum: " +  synchronizedMap.getOrDefault(255, 0));
        }, "thread-2");


        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
    }

    public void useThreadSafeMapAndOk() throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            for (int i = 1; i < 10; i++) {
                myThreadSafeMap.put(i, i);
            }
            System.out.println("thread 1 is completed");
        }, "thread-1");

        Thread thread2 = new Thread(() -> {

            for (var entry : myThreadSafeMap.entrySet()) {
                if (entry.getKey() != 255) {
                    myThreadSafeMap.put(255, entry.getValue() + myThreadSafeMap.getOrDefault(255, 0));
                    //no exception
                }
            }
            System.out.println("Sum: " +  myThreadSafeMap.getOrDefault(255, 0));
        }, "thread-2");

        thread1.start();
        thread1.join();
        thread2.start();
        thread2.join();
    }
}


