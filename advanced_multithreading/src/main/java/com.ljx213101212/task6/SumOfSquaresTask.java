package com.ljx213101212.task6;

import java.util.concurrent.RecursiveAction;

class SumOfSquaresTask extends RecursiveAction {
    private static final long serialVersionUID = 1L;
    private final double[] array;
    private final int lo, hi;
    private double result;
    private static final int THRESHOLD = 10_000_000; // Can tune this threshold based on profiling results

    public SumOfSquaresTask(double[] array, int lo, int hi) {
        this.array = array;
        this.lo = lo;
        this.hi = hi;
    }

    protected void compute() {
        if (hi - lo <= THRESHOLD) {
            double localSum = 0;
            for (int i = lo; i < hi; i++) {
                localSum += array[i] * array[i];
            }
            result = localSum;
        } else {
            int mid = (lo + hi) >>> 1;
            SumOfSquaresTask left = new SumOfSquaresTask(array, lo, mid);
            SumOfSquaresTask right = new SumOfSquaresTask(array, mid, hi);
            invokeAll(left, right);
            result = left.result + right.result;
        }
    }

    public double getResult() {
        return result;
    }
}
