package com.ljx213101212.advanced_multithreading;

import com.ljx213101212.task6.FibonacciTask;
import org.junit.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.Assert.assertEquals;


public class FibonacciTaskTest {

    @Test
    public void testFibonacci45() {
        assertEquals(1134903170L, new ForkJoinPool().invoke(new FibonacciTask(45)).longValue());
    }

    @Test
    public void testFibonacci10() {
        assertEquals(55L, new ForkJoinPool().invoke(new FibonacciTask(10)).longValue());
    }
}