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

                //Color tonemappedColor = applyACESTonemapping(overlayColor);

                // Check if the colors are similar based on a similarityThreshold
                if (shouldBlur(baseColor, overlayColor, similarityThreshold)) {
                    double blendedRed = interpolate(baseColor.getRed(), overlayColor.getRed(), blendFactor);
                    double blendedGreen = interpolate(baseColor.getGreen(), overlayColor.getGreen(), blendFactor);
                    double blendedBlue = interpolate(baseColor.getBlue(), overlayColor.getBlue(), blendFactor);

                    overlayColor = new Color(blendedRed, blendedGreen, blendedBlue, 1.0);;
                }

                resultImagePixelWriter.setColor(x, y, overlayColor);
            }
        }

        return resultImage;
    }

    public static WritableImage applyACESAndTonemap(WritableImage inputImage) {
        int width = (int) Math.floor(inputImage.getWidth());
        int height = (int) Math.floor(inputImage.getHeight());
        PixelReader inputImagePixelReader = inputImage.getPixelReader();
        WritableImage tonemappedImage = new WritableImage(width, height);
        PixelWriter tonemappedImagePixelWriter = tonemappedImage.getPixelWriter();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color inputColor = inputImagePixelReader.getColor(x, y);
                Color tonemappedColor = applyACESTonemapping(inputColor);
                tonemappedImagePixelWriter.setColor(x, y, tonemappedColor);
            }
        }

        return tonemappedImage;
    }

    private static Color applyACESTonemapping(Color color) {
        double a = 2.5; // highlights compressor
        double b = 0.03; // higher lifts shadows
        double c = 3; //
        double d = 0.59;
        double e = 0.14;

        double red = Math.max(0.0, Math.min(1.0, color.getRed()));
        double green = Math.max(0.0, Math.min(1.0, color.getGreen()));
        double blue = Math.max(0.0, Math.min(1.0, color.getBlue()));

        double luminance = 0.2126 * red + 0.7152 * green + 0.0722 * blue;

        double toneMappedRed = (red * (a * red + b)) / (red * (c * red + d) + e);
        double toneMappedGreen = (green * (a * green + b)) / (green * (c * green + d) + e);
        double toneMappedBlue = (blue * (a * blue + b)) / (blue * (c * blue + d) + e);

        // Scale the values to the range [0, 255]
        int scaledRed = (int) (toneMappedRed * 255);
        int scaledGreen = (int) (toneMappedGreen * 255);
        int scaledBlue = (int) (toneMappedBlue * 255);

        return new Color(scaledRed / 255.0, scaledGreen / 255.0, scaledBlue / 255.0, 1);
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

