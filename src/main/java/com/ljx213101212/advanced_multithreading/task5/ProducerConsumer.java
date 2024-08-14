package com.ljx213101212.advanced_multithreading.task5;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class ProducerConsumer {
    private static final int BUFFER_SIZE = 5;
    private static final Integer[] buffer = new Integer[BUFFER_SIZE];
    private static int in = 0;  // Index for next element to produce
    private static int out = 0;  // Index for next element to consume
    public static int item = 1;


    // Semaphores
    private static final Semaphore bufferSizeLimit = new Semaphore(BUFFER_SIZE);
    private static final Semaphore fillCount = new Semaphore(0);

    static class Producer implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    bufferSizeLimit.acquire();  // Wait for empty space
                    buffer[in] = item;
                    System.out.println("Produced: " + item + ", buffer: " + Arrays.toString(buffer));
                    item++;
                    in = (in + 1) % BUFFER_SIZE;
                    fillCount.release();   // Increment the fill count
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    static class Consumer implements Runnable {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    //bufferSizeLimit.acquire();  // Wait for empty space
                    fillCount.acquire();   // Wait for something to consume
                    Integer item = buffer[out];
                    buffer[out] = null;
                    System.out.println("Consumed: " + item + ", buffer: " + Arrays.toString(buffer));
                    out = (out + 1) % BUFFER_SIZE;
                    bufferSizeLimit.release();  // Increment the empty spaces count
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread producerThread = new Thread(new Producer());
        Thread consumerThread = new Thread(new Consumer());

        producerThread.start();
        consumerThread.start();

        try {
            Thread.sleep(200);//Let producer and consumer busy for 200ms
            producerThread.interrupt();//stop both producer and consumer
            consumerThread.interrupt();
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println(ProducerConsumer.item + " items have been produced and consumed.");
        System.out.println(Arrays.toString(ProducerConsumer.buffer));
    }
}