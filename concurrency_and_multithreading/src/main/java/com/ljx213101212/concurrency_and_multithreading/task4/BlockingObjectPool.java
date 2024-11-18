package com.ljx213101212.concurrency_and_multithreading.task4;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Pool that block when it has not any items or it full

 */
public class BlockingObjectPool {

    private final Queue<Object> queue;
    private Integer queueSize = 0;
    private final Lock lock = new ReentrantLock();
    private final Condition notEmpty = lock.newCondition();
    private final Condition notFull = lock.newCondition();

    /**
     * Creates filled pool of passed size
     *
     * @param size of pool
     */
    public BlockingObjectPool(int size) {
        queue = new LinkedList<>();
        queueSize = size;
    }

    /**
     * Gets object from pool or blocks if pool is empty
     *
     * @return object from pool
     */
    public Object get() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    notEmpty.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return null;
                }
            }
            Object res = queue.poll();
            notFull.signal();
            return res;
        } finally {
            System.out.println("[GET]: Queue Size: " + queue.size());
            lock.unlock();
        }
    }

    /**
     * Puts object to pool or blocks if pool is full
     *
     * @param object to be taken back to pool
     */
    public void take(Object object) {
        lock.lock();

        try {
            while (queue.size() == queueSize) {
                try {
                    notFull.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
            queue.add(object);
            notEmpty.signal();
        } finally {
            System.out.println("[TAKE]: Queue Size: " + queue.size());
            lock.unlock();
        }
    }
}
