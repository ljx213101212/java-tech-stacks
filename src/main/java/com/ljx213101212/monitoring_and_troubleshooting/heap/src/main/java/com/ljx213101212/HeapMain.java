package com.ljx213101212;

public class HeapMain {

    public static void main(String[] args) {
        Process process = new Process();
        System.out.println("Press any key to proceed");
        process.waitKeyPress();
        process.execute();
    }

}
