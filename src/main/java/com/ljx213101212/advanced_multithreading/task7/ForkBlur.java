package com.ljx213101212.advanced_multithreading.task7;

import java.util.concurrent.RecursiveAction;

public class ForkBlur extends RecursiveAction {

    private int[] source;
    private int start;
    private int length;
    private int[] destination;
    private int blurWidth = 15; // Processing window size, must be odd

    public ForkBlur(int[] source, int start, int length, int[] destination) {
        this.source = source;
        this.start = start;
        this.length = length;
        this.destination = destination;
    }

    // Average pixels from source, write results into destination.
    protected void computeDirectly() {
        int sidePixels = (blurWidth - 1) / 2;
        for (int index = start; index < start + length; index++) {
            float rt = 0, total = 0;
            for (int mi = -sidePixels; mi <= sidePixels; mi++) {
                int mindex = Math.min(Math.max(mi + index, 0), source.length - 1);
                int pixel = source[mindex];
                rt += pixel;
                total++;
            }
            destination[index] = (int)(rt / total);
        }
    }

    @Override
    protected void compute() {
        if (length < 100) {
            computeDirectly();
            return;
        }

        int split = length / 2;

        invokeAll(new ForkBlur(source, start, split, destination),
                new ForkBlur(source, start + split, length - split, destination));
    }
}