package com.ljx213101212.one_week_preparation_kit.day5.balanced_brackets;

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
     * Complete the 'isBalanced' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING s as parameter.
     */

    public static String isBalanced(String s) {
        // Write your code here
        Stack<Character> buffer = new Stack<>();
        for (Character c : s.toCharArray()) {

            if (buffer.size() == 0) {
                buffer.push(c);
                continue;
            }

            Character top = buffer.peek();
            if (top.equals('{') && c.equals('}')) {
                buffer.pop();
            } else if (top.equals('(') && c.equals(')')) {
                buffer.pop();
            } else if (top.equals('[') && c.equals(']')) {
                buffer.pop();
            } else {
                buffer.push(c);
            }
        }

        return buffer.size() > 0 ? "NO" : "YES";
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
                String s = bufferedReader.readLine();

                String result = Result.isBalanced(s);

                bufferedWriter.write(result);
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}

