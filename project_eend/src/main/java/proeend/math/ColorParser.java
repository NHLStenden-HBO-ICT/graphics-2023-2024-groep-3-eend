package proeend.math;

import javafx.scene.paint.Color;

public class ColorParser {

    /**
     * Zet een Vector om naar een Color object.
     * @param samplesPerPixel De hoeveelheid steekproeven per pixel.
     * @param gamma Geeft aan of wel of niet de kleur gamma-gecorrigeerd moet worden.
     * @param vector De Vector die moet worden omgezet naar een Color object.
     * @return Een Color object dat de kleur vertegenwoordigt.
     */
    public static Color toColor(int samplesPerPixel, Vector vector, boolean save) {
        Vector scaledColor = vector.scale(1.0 / samplesPerPixel);
        Vector gammaCorrectedColor = gammaCorrectCoordinates(scaledColor, save, 0, 0);
        Vector clampedColor = clampCoordinates(gammaCorrectedColor);

        return convertToRGB(clampedColor);
    }

    /**
     * Corrigeert de coördinaten met behulp van gamma.
     * @param color De kleur die gecorrigeerd moet worden.
     * @param correctGama Geeft aan of de gamma gebruikt moet worden of niet.
     * @return De met gamma gecorrigeerde kleur.
     */
    private static Vector gammaCorrectCoordinates(Vector color, boolean correctGamma, double saturationFactor, double brightnessFactor) {

        if(correctGamma){
            brightnessFactor = 0;
        }

        double red;
        double green;
        double blue;

        // Convert RGB to HSL
        double max = Math.max(color.getX(), Math.max(color.getY(), color.getZ()));
        double min = Math.min(color.getX(), Math.min(color.getY(), color.getZ()));
        double luminance = (max + min) / 2.0;

        double delta = max - min;
        double saturation = 0.0;

        if (delta > 0.0) {
            if (luminance <= 0.5) {
                saturation = delta / (max + min);
            } else {
                saturation = delta / (2.0 - max - min);
            }
        }

        double hue = 0.0;

        if (delta > 0.0) {
            if (max == color.getX()) {
                hue = (color.getY() - color.getZ()) / delta + (color.getY() < color.getZ() ? 6.0 : 0.0);
            } else if (max == color.getY()) {
                hue = (color.getZ() - color.getX()) / delta + 2.0;
            } else {
                hue = (color.getX() - color.getY()) / delta + 4.0;
            }
        }

        hue /= 6.0;

        // Modify the brightness factor based on the luminance
        double customBrightnessFactor = brightnessFactor + luminance * 0.5; // You can adjust the curve here

        // Increase saturation
        saturation += saturationFactor;

        // Clamp saturation to [0, 1]
        saturation = Math.min(1.0, Math.max(0.0, saturation));

        // Modify brightness
        luminance += customBrightnessFactor;

        // Clamp brightness to [0, 1]
        luminance = Math.min(1.0, Math.max(0.0, luminance));

        // Convert HSL back to RGB
        double q = luminance < 0.5 ? luminance * (1.0 + saturation) : (luminance + saturation - luminance * saturation);
        double p = 2.0 * luminance - q;

        red = hue2rgb(p, q, hue + 1.0 / 3.0);
        green = hue2rgb(p, q, hue);
        blue = hue2rgb(p, q, hue - 1.0 / 3.0);

        if (correctGamma) {
            double gamma = 2.2;
            red = Math.pow(red, 1.0 / gamma);
            green = Math.pow(green, 1.0 / gamma);
            blue = Math.pow(blue, 1.0 / gamma);
        }

        return new Vector(red, green, blue);
    }

    private static double hue2rgb(double p, double q, double t) {
        if (t < 0.0) t += 1.0;
        if (t > 1.0) t -= 1.0;
        if (t < 1.0 / 6.0) return p + (q - p) * 6.0 * t;
        if (t < 1.0 / 2.0) return q;
        if (t < 2.0 / 3.0) return p + (q - p) * (2.0 / 3.0 - t) * 6.0;
        return p;
    }

    /**
     * Zorgt ervoor dat de kleurcomponenten binnen de grenzen zijn.
     * @param color De kleurcomponenten die moeten worden begrensd.
     * @return De begrensde kleurcomponenten.
     */
    private static Vector clampCoordinates(Vector color) {
        double r = clamp(color.getX());
        double g = clamp(color.getY());
        double b = clamp(color.getZ());
        return new Vector(r, g, b);
    }

    private static double clamp(double x) {
        return Math.min(1.0, Math.max(0.0, x));
    }

    /**
     * Zet de uiteindelijke kleur om naar een Color object.
     * @param color De kleur die geconverteerd moet worden naar een RGB Color.
     * @return Een Color object dat de kleur vertegenwoordigt.
     */
    private static Color convertToRGB(Vector color) {
        double r = color.getX();
        double g = color.getY();
        double b = color.getZ();
        return Color.color(r, g, b);
    }
}
