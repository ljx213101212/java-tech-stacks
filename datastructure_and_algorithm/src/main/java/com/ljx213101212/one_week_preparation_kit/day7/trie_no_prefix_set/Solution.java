package com.ljx213101212.one_week_preparation_kit.day7.trie_no_prefix_set;

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
     * Complete the 'noPrefix' function below.
     *
     * The function accepts STRING_ARRAY words as parameter.
     */

    static class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        Boolean isEndOfWord = false;
    }

    public static Boolean hasPrefix(String word, TrieNode node) {

        for (Character c : word.toCharArray()) {
            if (!node.children.containsKey(c)) {
                node.children.put(c, new TrieNode());
            }
            node = node.children.get(c);
            if (node.isEndOfWord) {
                return true;
            }
        }

        if (node.children.size() > 0) {
            return true;
        }
        node.isEndOfWord = true;
        return false;
    }

    public static void noPrefix(List<String> words) {
        // Write your code here
        TrieNode node = new TrieNode();
        for (String word : words) {
            if (hasPrefix(word, node)) {
                System.out.println("BAD SET");
                System.out.println(word);
                return;
            }
        }
        System.out.println("GOOD SET");
    }

}

public class Solution {
    public static void main(String[] args) throws IOException {
        //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getenv("INPUT_PATH")));

        int n = Integer.parseInt(bufferedReader.readLine().trim());

        List<String> words = IntStream.range(0, n).mapToObj(i -> {
                    try {
                        return bufferedReader.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                })
                .collect(toList());

        Result.noPrefix(words);

        bufferedReader.close();
    }
}
