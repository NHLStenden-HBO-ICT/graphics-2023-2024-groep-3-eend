package proeend.misc;

import javafx.scene.image.WritableImage;
import proeend.Main;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Deze klasse biedt methoden om een gegenereerde afbeelding op te slaan als een PNG-bestand.
 */
public class ImageSaver {
    /**
     * Slaat de gegeven afbeelding op als een PNG-bestand op basis van de opgegeven samples per pixel.
     *
     * @param image De afbeelding die moet worden opgeslagen.
     * @param samplesPerPixel Het aantal samples per pixel.
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
            String filename = "renders/" + dtf.format(now) + "_" + image.getWidth() + "x" + image.getHeight() + "_s" + samplesPerPixel + ".png";
            File output = new File(filename);

        try {
            ImageIO.write(bufferedImage, "png", output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Main.startScreen.setInfoLabel("Image saved in " + filename);
    }
}
