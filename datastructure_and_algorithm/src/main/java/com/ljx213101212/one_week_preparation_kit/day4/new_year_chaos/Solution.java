package com.ljx213101212.one_week_preparation_kit.day4.new_year_chaos;

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
     * Complete the 'minimumBribes' function below.
     *
     * The function accepts INTEGER_ARRAY q as parameter.
     */

    public static void minimumBribes(List<Integer> q) {
        // Write your code here

        int bribes = 0;
        for (int i = q.size() - 1; i >= 0; i--) {
            int currentPosition = i + 1;
            int originalPosition = q.get(i);

            if (originalPosition - currentPosition > 2) {
                System.out.println("Too chaotic");
                return;
            }

            // Start from the person's original position minus 2 (or 0) up to the current position
            for (int j = Math.max(0, originalPosition - 2); j < i; j++) {
                if (q.get(j) > originalPosition) {
                    bribes++;
                }
            }
        }
        System.out.println(bribes);

    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getenv("INPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                int n = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> q = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                        .map(Integer::parseInt)
                        .collect(toList());

                Result.minimumBribes(q);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
    }
}

