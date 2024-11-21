package com.ljx213101212.concurrency_and_multithreading.task1;

public class DasExperiment {


    public static void main(String[] args) throws InterruptedException {
        System.out.println("Hello world!");
        ConcurrentModificationExceptionSample sample1 = new ConcurrentModificationExceptionSample();

        //sample1.sampleErrorMethod(); //throw ConcurrentModificationException
        //sample1.useSynchronizedMapAndFailFast();  //throw ConcurrentModificationException
        //sample1.useConcurrentHashMapAndOk(); //OK
        sample1.useThreadSafeMapAndOk();
    }
}