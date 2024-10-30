package com.ljx213101212.one_week_preparation_kit.day3.caesar_cipher;

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
     * Complete the 'caesarCipher' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts following parameters:
     *  1. STRING s
     *  2. INTEGER k
     */

    public static String caesarCipher(String s, int k) {
        // Write your code here

        //(26 - k + n) % 26
        // a -> 23   0 3
        // b -> 24   1 3
        // c -> 25   2

        // d -> 26 % 26
        StringBuilder sb = new StringBuilder();

        // List<Character> charList = new ArrayList<>(Arrays.asList(s.toCharArray()));
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c)) {
                int caseOffest =  (Character.isUpperCase(c) ? (int)'A' : (int)'a');
                int offset = (int)c - caseOffest;
                int mapped = (26 + k + offset) % 26;
                sb.append((char)(mapped + caseOffest));
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getenv("INPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        String s = bufferedReader.readLine();

        int k = Integer.parseInt(bufferedReader.readLine().trim());

        String result = Result.caesarCipher(s, k);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
