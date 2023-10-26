package proeend.misc;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageBlender {
    public static WritableImage blendImages(WritableImage averageImage, WritableImage newRenderedImage, double alpha, double threshold) {
        int width = (int) Math.floor(averageImage.getWidth());
        int height = (int) Math.floor(averageImage.getHeight());

        PixelReader averageImagePixelReader = averageImage.getPixelReader();
        PixelReader newRenderedImagePixelReader = newRenderedImage.getPixelReader();
        WritableImage resultImage = new WritableImage(width, height);
        PixelWriter writer = resultImage.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color averageColor = averageImagePixelReader.getColor(x, y);
                Color newColor = newRenderedImagePixelReader.getColor(x, y);

                // Check if the colors are similar based on a threshold
                if (shouldBlur(averageColor, newColor, threshold)) {
                    double blendedRed = interpolate(averageColor.getRed(), newColor.getRed(), alpha);
                    double blendedGreen = interpolate(averageColor.getGreen(), newColor.getGreen(), alpha);
                    double blendedBlue = interpolate(averageColor.getBlue(), newColor.getBlue(), alpha);

                    writer.setColor(x, y, new Color(blendedRed, blendedGreen, blendedBlue, 1.0));
                } else {
                    // Pixels with different colors are not blurred
                    writer.setColor(x, y, newColor);
                }
            }
        }

        return resultImage;
    }

    private static boolean shouldBlur(Color averageColor, Color newColor, double threshold) {
        // Add custom conditions to determine which colors to blur
        // Example: Blur if the red component of the new color is above a certain threshold
        return Math.abs(averageColor.getRed() - newColor.getRed()) < threshold;
    }
    private static boolean colorSimilar(Color color1, Color color2, double threshold) {
        double redDiff = Math.abs(color1.getRed() - color2.getRed());
        double greenDiff = Math.abs(color1.getGreen() - color2.getGreen());
        double blueDiff = Math.abs(color1.getBlue() - color2.getBlue());

        return redDiff < threshold && greenDiff < threshold && blueDiff < threshold;
    }

    private static double interpolate(double a, double b, double alpha) {
        return a * (1.0 - alpha) + b * alpha;
    }
}
