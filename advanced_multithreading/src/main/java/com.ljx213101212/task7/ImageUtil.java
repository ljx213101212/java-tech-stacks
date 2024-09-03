package com.ljx213101212.task7;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {

    public static BufferedImage arrayToBufferedImage(int[] pixels, int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        int index = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int value = pixels[index++]; // Retrieve value, increment index
                int rgb = value | (value << 8) | (value << 16); // Convert to RGB
                image.setRGB(x, y, rgb);
            }
        }
        return image;
    }

    public static void saveImage(BufferedImage image, String fileName) {
        File file = new File(fileName);
        try {
            ImageIO.write(image, "jpeg", file); // using ImageIO to write file
        } catch (IOException e) {
            System.out.println("Error occurred in writing the image file.");
            e.printStackTrace();
        }
    }

    public static BufferedImage loadImage(String path) {
        try (InputStream is = ImageUtil.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("Cannot find resource: " + path);
            }
            return ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int[] imageToGrayscaleArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        int index = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;
                int gray = (int) (0.2989 * r + 0.5870 * g + 0.1140 * b);  // Simple weighted method for grayscale
                pixels[index++] = gray;
            }
        }
        return pixels;
    }
}
