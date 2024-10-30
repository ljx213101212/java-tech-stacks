package com.ljx213101212.one_week_preparation_kit.day6.lego_blocks;

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
     * Complete the 'legoBlocks' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. INTEGER m
     */

    public static int legoBlocks(int n, int m) {
        // Write your code here
        final int MOD = 1_000_000_007;

        // Compute the number of ways to build a row of width m
        long[] rowWays = new long[m + 1];
        rowWays[0] = 1; // Base case: There's one way to build a row of width 0

        for (int width = 1; width <= m; width++) {
            for (int blockWidth = 1; blockWidth <= 4; blockWidth++) {
                if (width - blockWidth >= 0) {
                    rowWays[width] = (rowWays[width] + rowWays[width - blockWidth]) % MOD;
                }
            }
        }

        // Compute the total number of ways to build the wall without considering vertical splits
        long[] totalWays = new long[m + 1];
        for (int width = 1; width <= m; width++) {
            totalWays[width] = modPow(rowWays[width], n, MOD);
        }

        // Compute the number of solid walls (no vertical splits)
        long[] solidWays = new long[m + 1];
        solidWays[0] = 1; // Base case

        for (int width = 1; width <= m; width++) {
            solidWays[width] = totalWays[width];
            for (int k = 1; k < width; k++) {
                solidWays[width] = (solidWays[width] - (solidWays[k] * totalWays[width - k]) % MOD + MOD) % MOD;
            }
        }

        return (int) solidWays[m];
    }

    // Helper function to compute (base^exp) % mod using modular exponentiation
    public static long modPow(long base, int exp, int mod) {
        long result = 1;
        base = base % mod;

        while (exp > 0) {
            if ((exp & 1) == 1) { // If exp is odd
                result = (result * base) % mod;
            }
            exp = exp >> 1;       // Divide exp by 2
            base = (base * base) % mod;
        }

        return result;
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getenv("INPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

                int n = Integer.parseInt(firstMultipleInput[0]);

                int m = Integer.parseInt(firstMultipleInput[1]);

                int result = Result.legoBlocks(n, m);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
