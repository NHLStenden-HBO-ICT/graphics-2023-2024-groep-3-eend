package proeend.misc;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class ImageBlender {
    public static WritableImage blendImages(WritableImage image1, WritableImage image2) {
        int height = (int) Math.floor(image2.getHeight());
        int width = (int) Math.floor(image2.getWidth());

        PixelReader reader1 = image1.getPixelReader();
        PixelReader reader2 = image2.getPixelReader();
        WritableImage resultImage = new WritableImage(width, height);
        PixelWriter writer = resultImage.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color1 = reader1.getColor(x, y);
                Color color2 = reader2.getColor(x, y);

                double alpha = .1;

                // Linear interpolation blending
                double blendedRed = interpolate(color1.getRed(), color2.getRed(), alpha);
                double blendedGreen = interpolate(color1.getGreen(), color2.getGreen(), alpha);
                double blendedBlue = interpolate(color1.getBlue(), color2.getBlue(), alpha);


                writer.setColor(x, y, new Color(blendedRed, blendedGreen, blendedBlue, 1.0));
            }
        }

        return resultImage;
    }

    private static double interpolate(double a, double b, double alpha) {
        return a * (1.0 - alpha) + b * alpha;
    }
}
