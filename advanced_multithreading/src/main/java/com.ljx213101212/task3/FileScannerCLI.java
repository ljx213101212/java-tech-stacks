package com.ljx213101212.task3;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;


public class FileScannerCLI {


    public static void main(String[] args) throws InterruptedException {
        try {

            //Step1. add shutdown hook to support (Ctrl + C for exiting the program)
            FileScannerUtil.addShutDownHook(Thread.currentThread());
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter directory path to scan:");
            File folder = new File(scanner.nextLine());

            //Step2. integrate FileScanner Task with FJP
            ForkJoinPool pool = ForkJoinPool.commonPool();
            FileScannerTask scannerTask = new FileScannerTask(folder);

            //Step3. Simulating the animation for long task.
            Thread animeThread = FileScannerUtil.animeThread();
            animeThread.start();
            //potential long task
            FolderStats stats = pool.invoke(scannerTask);
            animeThread.interrupt();
            animeThread.join();

            //Step4. Print out the final results.
            System.out.println("Scan complete!");
            System.out.println("Files: " + stats.fileCount);
            System.out.println("Folders: " + stats.folderCount);
            System.out.println("Total Size: " + stats.formatSize(stats.size));
        } catch (Exception ex) {
            System.out.println("System was interrupted.");
        }
    }
}
