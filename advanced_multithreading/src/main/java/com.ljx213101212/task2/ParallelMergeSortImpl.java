package com.ljx213101212.task2;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;

public class ParallelMergeSortImpl implements MergeSort {
    @Override
    public void sort(int[] arr) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        forkJoinPool.invoke(new MergeSortAction(arr));
    }

    public static void main(String[] args) {
        int[] array = {9, 3, 1, 5, 13, 12};
        MergeSort sortImpl = new ParallelMergeSortImpl();
        sortImpl.sort(array);
        System.out.println(Arrays.toString(array));
    }
}

