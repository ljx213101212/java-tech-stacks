package com.ljx213101212.task6;

import java.util.*;
import java.util.concurrent.ForkJoinPool;

public class SumOfSquares {
    static final int SIZE = 500;
    static final double[] ARRAY = new double[SIZE];

    static {
        Random random = new Random();
        for (int i = 0; i < SIZE; i++) {
            ARRAY[i] = random.nextDouble();
        }
    }

    public static BenchMark runLinear() {
        long startTime, endTime;
        double sum;
        startTime = System.currentTimeMillis();
        sum = 0;
        for (double v : ARRAY) {
            sum += v * v;
        }
        endTime = System.currentTimeMillis();
        long timeCost = endTime - startTime;
        return new BenchMark(timeCost, "Linear", sum);
    }

    public static BenchMark runLinearByStreamAPI() {
        long startTime, endTime;
        double sum;
        startTime = System.currentTimeMillis();
        sum = Arrays.stream(ARRAY)
                .map(v -> v * v)
                .sum();
        endTime = System.currentTimeMillis();
        long timeCost = endTime - startTime;
        return new BenchMark(timeCost, "Linear (StreamAPI)", sum);
    }

    public static BenchMark runLinearByParallelStreamAPI() {
        long startTime, endTime;
        double sum;
        startTime = System.currentTimeMillis();
        sum = Arrays.stream(ARRAY)
                .parallel()
                .map(v -> v * v)
                .sum();
        endTime = System.currentTimeMillis();
        long timeCost = endTime - startTime;
        return new BenchMark(timeCost, "Linear (ParallelStreamAPI)", sum);
    }

    public static BenchMark runForkJoin() {
        long startTime, endTime;
        double sum;
        ForkJoinPool pool = new ForkJoinPool();
        SumOfSquaresTask task = new SumOfSquaresTask(ARRAY, 0, ARRAY.length);
        startTime = System.currentTimeMillis();
        pool.invoke(task);
        sum = task.getResult();
        endTime = System.currentTimeMillis();
        long timeCost = endTime - startTime;
        return new BenchMark(timeCost, "ForkJoin", sum);
    }


    public static void main(String[] args) {

        List<BenchMark> benchMarkList = new ArrayList<>();
        // Linear Calculation
        BenchMark linear = runLinear();
        benchMarkList.add(linear);

        // Linear Calculation by Stream API
        BenchMark linearByStream = runLinearByStreamAPI();
        benchMarkList.add(linearByStream);

        // Linear Calculation by Parallel Stream API
        BenchMark linearByParallelStreamAPI = runLinearByParallelStreamAPI();
        benchMarkList.add(linearByParallelStreamAPI);
        // ForkJoin calculation
        BenchMark forkJoin = runForkJoin();
        benchMarkList.add(forkJoin);

        benchMarkList.sort(Comparator.comparing(BenchMark::timeCost));

        System.out.println("The Ranking of The most efficient method: ");
        for (int i = 0; i < benchMarkList.size(); i++) {
            BenchMark mark = benchMarkList.get(i);
            int rank = i + 1;
            System.out.println(rank + "." + mark.methodName() + " ->  costs: " + mark.timeCost() + " ms, answer: " + mark.answer());
        }
    }
}

