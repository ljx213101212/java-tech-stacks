package com.ljx213101212.one_week_preparation_kit.day7.tree_preorder_traversal;


import java.util.*;
import java.io.*;

class Node {
    Node left;
    Node right;
    int data;

    Node(int data) {
        this.data = data;
        left = null;
        right = null;
    }
}

class Solution {

/* you only have to complete the function given below.
Node is defined as

class Node {
    int data;
    Node left;
    Node right;
}

*/

    public static void preOrder(Node root) {

        List<Node> result = new ArrayList<>();
        dfs(root, result);

        String ans = String.join(" ", result.stream().map(node -> String.valueOf(node.data)).toArray(String[]::new));
        System.out.println(ans);
    }

    public static void dfs(Node root, List<Node> result) {

        if (root == null) {
            return;
        }

        result.add(root);
        dfs(root.left, result);
        dfs(root.right, result);
    }

    public static Node insert(Node root, int data) {
        if(root == null) {
            return new Node(data);
        } else {
            Node cur;
            if(data <= root.data) {
                cur = insert(root.left, data);
                root.left = cur;
            } else {
                cur = insert(root.right, data);
                root.right = cur;
            }
            return root;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        //Scanner scan = new Scanner(System.in);
        Scanner scan = new Scanner(new FileReader(System.getenv("INPUT_PATH")));

        int t = scan.nextInt();
        Node root = null;
        while(t-- > 0) {
            int data = scan.nextInt();
            root = insert(root, data);
        }
        scan.close();
        preOrder(root);
    }
}