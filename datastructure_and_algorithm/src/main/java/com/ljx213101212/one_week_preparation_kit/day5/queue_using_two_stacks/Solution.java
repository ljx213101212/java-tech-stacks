package com.ljx213101212.one_week_preparation_kit.day5.queue_using_two_stacks;

import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws FileNotFoundException {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        // StringBuffer sb = new StringBuffer(new InputStreamReader(System.in));
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));


//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader reader = new BufferedReader(new FileReader(System.getenv("INPUT_PATH")));
        try {
            String line = reader.readLine();
            String[] params = line.split(" ");
            int queries = Integer.valueOf(params[0]);
            processInput(reader, queries);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }


    }

    public static void processInput(BufferedReader reader, int queries) throws IOException{

        Stack<String> s1 = new Stack<>();
        Stack<String> s2 = new Stack<>();
        for (int i = 0; i < queries; i++) {
            String line = reader.readLine();
            String[] params = line.split(" ");
            String param1 = params[0];
            if (param1.equals(String.valueOf("1"))) {
                String enqueueItem = params[1];
                s1.push(enqueueItem);
            } else {
                if (s2.size() == 0) {
                    while (s1.size() > 0) {
                        s2.push(s1.pop());
                    }
                }

                if (param1.equals(String.valueOf("2"))) {
                    s2.pop();
                }
                else if (param1.equals(String.valueOf("3"))){
                    System.out.println(s2.peek());
                }
            }
        }
    }
}

