package proeend.misc;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;


/**
 * Deze klasse bevat methoden voor het mengen van twee afbeeldingen op basis van opgegeven mengparameters.
 */
public class ImageBlender {
    /**
     * Mengt twee afbeeldingen op basis van opgegeven parameters.
     *
     * @param baseImage De basisafbeelding waaraan de overlay-afbeelding wordt toegevoegd.
     * @param overlayImage De afbeelding die aan de basisafbeelding wordt toegevoegd.
     * @param blendFactor De meng factor die de intensiteit van de overlay bepaalt.
     * @param similarityThreshold De drempelwaarde voor het bepalen van kleur gelijkenis.
     * @return De resulterende gemengde afbeelding.
     */
    public static WritableImage blendImages(WritableImage baseImage, WritableImage overlayImage, double blendFactor, double similarityThreshold) {
        int width = (int) Math.floor(baseImage.getWidth());
        int height = (int) Math.floor(baseImage.getHeight());

        PixelReader averageImagePixelReader = baseImage.getPixelReader();
        PixelReader overlayImagePixelReader = overlayImage.getPixelReader();
        WritableImage resultImage = new WritableImage(width, height);
        PixelWriter resultImagePixelWriter  = resultImage.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color baseColor = averageImagePixelReader.getColor(x, y);
                Color overlayColor = overlayImagePixelReader.getColor(x, y);

                // Check if the colors are similar based on a similarityThreshold
                if (shouldBlur(baseColor, overlayColor, similarityThreshold)) {
                    double blendedRed = interpolate(baseColor.getRed(), overlayColor.getRed(), blendFactor);
                    double blendedGreen = interpolate(baseColor.getGreen(), overlayColor.getGreen(), blendFactor);
                    double blendedBlue = interpolate(baseColor.getBlue(), overlayColor.getBlue(), blendFactor);

                    resultImagePixelWriter.setColor(x, y, new Color(blendedRed, blendedGreen, blendedBlue, 1.0));
                } else {
                    // Pixels with different colors are not blurred
                    resultImagePixelWriter .setColor(x, y, overlayColor);
                }
            }
        }

        return resultImage;
    }

    /**
     * Controleert of twee kleuren vergelijkbaar zijn op basis van een drempelwaarde.
     * @param averageColor De kleur uit de basisafbeelding.
     * @param newColor De kleur uit de overlay-afbeelding.
     * @param threshold De drempelwaarde voor het bepalen van de overeenkomst.
     * @return True als de kleuren vergelijkbaar zijn, anders False.
     */
    private static boolean shouldBlur(Color averageColor, Color newColor, double threshold) {
        return Math.abs(averageColor.getRed() - newColor.getRed()) < threshold;
    }

    /**
     * Interpoleert tussen twee (kleur)
     * waarden met behulp van een mengfactor.
     *
     * @param a De eerste waarde om te interpoleren.
     * @param b De tweede waarde om te interpoleren.
     * @param alpha De mengfactor.
     * @return De geïnterpoleerde waarde.
     */
    private static double interpolate(double a, double b, double alpha) {
        return a * (1.0 - alpha) + b * alpha;
    }
}
