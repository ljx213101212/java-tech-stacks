package com.ljx213101212.advanced_multithreading.task7;

import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class BlurMain {

    public static void main(String[] args) {
//        int[] source = createRandomArray(10_000_000);
        BufferedImage inputImage = ImageUtil.loadImage("./flowerblue.jpg");
        int[] source = ImageUtil.imageToGrayscaleArray(inputImage);
        int width = inputImage.getWidth(); // Define width and height assuming it forms a valid rectangle
        int height = source.length / width;
        int[] destination = new int[source.length];

        ForkBlur fb = new ForkBlur(source, 0, source.length, destination);
        ForkJoinPool pool = new ForkJoinPool();


        long startTime = System.currentTimeMillis();
        pool.invoke(fb);
        long endTime = System.currentTimeMillis();

        System.out.println("Time taken: " + (endTime - startTime) + " milliseconds");

        //check the image
        BufferedImage image = ImageUtil.arrayToBufferedImage(destination, width, height);
        ImageUtil.saveImage(image, "blurredImage.jpg");
    }

    public static int[] createRandomArray(int size) {
        Random r = new Random();
        int[] data = new int[size];
        for (int i = 0; i < size; i++) {
            data[i] = r.nextInt(256);
        }
        return data;
    }
}
