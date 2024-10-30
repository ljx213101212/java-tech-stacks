package com.ljx213101212.one_week_preparation_kit.day6.simple_text_editor;

import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws FileNotFoundException {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */

        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader reader = new BufferedReader(new FileReader(System.getenv("INPUT_PATH")));
        try {
            int op = Integer.valueOf(reader.readLine());
            processOperations(reader, op);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static void processOperations(BufferedReader reader, int operations) throws IOException{

        StringBuilder sb = new StringBuilder();
        //    StringBuilder backUp = new StringBuilder();
        Stack<String> backup = new Stack<>();
        backup.push("");
        for (int i = 0; i < operations; i++) {
            String line = reader.readLine();
            //System.out.println(line);
            if (line.equals("4")) {
                backup.pop();
                sb = new StringBuilder(backup.peek());
            } else {
                String[] params = line.split(" ");
                int op = Integer.valueOf(params[0]);
                String param = params[1];

                if (op == 1) {
                    sb.append(param);
                    backup.push(sb.toString());
                } else if (op == 2) {
                    sb.delete(sb.length() - Integer.valueOf(param), sb.length());
                    backup.push(sb.toString());
                } else if (op == 3) {
                    System.out.println(sb.charAt(Integer.valueOf(param) - 1));
                }

            }
            //System.out.println("sb: " + sb.toString() + " backup: " + backup.toString());
        }
    }
}
