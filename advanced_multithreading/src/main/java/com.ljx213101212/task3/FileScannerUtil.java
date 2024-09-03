package com.ljx213101212.task3;

public class FileScannerUtil {

    public static Thread animeThread() {
        return new Thread(() -> {
            String[] anim = new String[]{".", "..", "..."};
            int x = 0;
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\rScanning " + anim[x++ % anim.length]);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
    }

    public static void addShutDownHook(Thread mainThread) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    System.out.println("Shutting down ...");
                    mainThread.join(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("Shutdown cleanup interrupted.");
                }
            }
        });
    }
}
