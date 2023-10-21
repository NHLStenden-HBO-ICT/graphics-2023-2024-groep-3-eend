package proeend.misc;

import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ImageSaver {
    /**
     * Slaat het gegenereerde beeld op als een PNG-bestand.
     *
     * @param image Het te opslaan beeld.
     * @throws IOException Als er een fout optreedt bij het opslaan van het beeld.
     */
    public static void saveImage(WritableImage image, int samplesPerPixel)  {

            BufferedImage bufferedImage = new BufferedImage((int) image.getWidth(), (int) image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            for (int x = 0; x < (int) image.getWidth(); x++) {
                for (int y = 0; y < (int) image.getHeight(); y++) {
                    int argb = image.getPixelReader().getArgb(x, y);
                    bufferedImage.setRGB(x, y, argb);
                }
            }
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
            LocalDateTime now = LocalDateTime.now();
            File output = new File("renders/" + dtf.format(now) + "_" + image.getWidth() + "x" + image.getHeight() + "_s" + samplesPerPixel + ".png");

        try {
            ImageIO.write(bufferedImage, "png", output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("opgeslagen");
    }
}
