package proeend.misc;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class ImageSaverTest {

    @Test
    public void testSaveImage() {
        // Maak een voorbeeld-WritableImage
        WritableImage image = new WritableImage(100, 100);
        PixelWriter writer = image.getPixelWriter();
        for (int x = 0; x < (int) image.getWidth(); x++) {
            for (int y = 0; y < (int) image.getHeight(); y++) {
                writer.setColor(x, y, Color.color(1,1,1));
            }
        }

        // Voer de saveImage-methode uit en controleer of deze geen uitzonderingen veroorzaakt
        assertDoesNotThrow(() -> ImageSaver.saveImage(image, 1));

        // Controleer of het bestand daadwerkelijk is opgeslagen
        File savedFile = new File(ImageSaver.saveImage(image, 1));  // Pas het pad aan indien nodig
        assertTrue(savedFile.exists(), "Het opgeslagen bestand bestaat");

        // Controleer of de bestandsnaam correct is gegenereerd
        String[] files = savedFile.list();
        assertNotNull(files, "Bestandenlijst is niet leeg");
        assertTrue(files.length > 0, "Er zijn bestanden opgeslagen");

        // Voeg extra assertions toe om specifieke aspecten van de gegenereerde bestandsnaam te controleren, bijv. bestandsnaampatroon
    }
}
