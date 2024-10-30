package com.ljx213101212.one_week_preparation_kit.day1.time_conversion;

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
     * Complete the 'timeConversion' function below.
     *
     * The function is expected to return a STRING.
     * The function accepts STRING s as parameter.
     */

    public static String timeConversion(String s) {
        // Write your code here

        //Get AM,PM
        int AMIndex = s.indexOf("AM");
        int PMIndex = s.indexOf("PM");

        int endIndex = AMIndex == -1 ? PMIndex : AMIndex;
        String fullTimeString = s.substring(0, endIndex);

        List<String> hhmmss = new ArrayList<>(List.of(fullTimeString.split(":")));

        String hh = hhmmss.get(0);

        if (AMIndex != -1 && hh.equals("12")) {
            hhmmss.set(0, "00");
        } else if (PMIndex != -1 && !hh.equals("12")) {
            hhmmss.set(0, String.valueOf(Integer.valueOf(hh) + 12));
        }

        StringBuilder ans = new StringBuilder("");
        for (String str: hhmmss) {
            ans.append(str);
            ans.append(":");
        }
        ans.delete(ans.length() - 1, ans.length());
        return String.join(":", hhmmss);
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String s = bufferedReader.readLine();

        String result = Result.timeConversion(s);

        bufferedWriter.write(result);
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
