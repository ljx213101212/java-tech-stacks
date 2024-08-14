package com.ljx213101212.advanced_multithreading.task2;

import java.util.concurrent.RecursiveAction;

public class MergeSortAction extends RecursiveAction {

    private final int[] arr;

    public MergeSortAction(int[] arr) {
        this.arr = arr;
    }

    @Override
    public void compute() {
        if (arr.length < 2) return;
        int mid = arr.length / 2;

        int[] left = new int[mid];
        System.arraycopy(arr, 0, left, 0, mid);

        int[] right = new int[arr.length - mid];
        System.arraycopy(arr, mid, right, 0, arr.length - mid);

        invokeAll(new MergeSortAction(left), new MergeSortAction(right));
        merge(left, right);
    }

    private void merge(int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] < right[j])
                arr[k++] = left[i++];
            else
                arr[k++] = right[j++];
        }
        while (i < left.length) {
            arr[k++] = left[i++];
        }
        while (j < right.length) {
            arr[k++] = right[j++];
        }
    }
}