package com.ljx213101212.one_week_preparation_kit.day1.mini_max_sum;

import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'miniMaxSum' function below.
     *
     * The function accepts INTEGER_ARRAY arr as parameter.
     */

    public static void miniMaxSum(List<Integer> arr) {
        // Write your code here
        long minimum = Integer.MAX_VALUE;
        long maximum = Integer.MIN_VALUE;
        long sum = 0;

        for (long num: arr) {
            if (num < minimum) {
                minimum = num;
            }
            if (num > maximum) {
                maximum = num;
            }
            sum += num;
        }

        String minimumSum = String.valueOf(sum - maximum);
        String maximumSum = String.valueOf(sum - minimum);

        System.out.println(minimumSum + " " + maximumSum);

    }

}

//input: 1 2 3 4 5
public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(toList());

        Result.miniMaxSum(arr);

        bufferedReader.close();
    }
}
