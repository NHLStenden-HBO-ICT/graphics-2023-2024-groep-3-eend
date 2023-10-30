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
    public static Color toColor(int samplesPerPixel, boolean gamma, Vector vector) {
        Vector scaledColor = vector.scale(1.0 / samplesPerPixel);
        Vector gammaCorrectedColor = gammaCorrectCoordinates(scaledColor, gamma);
        Vector clampedColor = clampCoordinates(gammaCorrectedColor);

        return convertToRGB(clampedColor);
    }

    /**
     * Corrigeert de coördinaten met behulp van gamma.
     * @param color De kleur die gecorrigeerd moet worden.
     * @param gamma Geeft aan of de gamma gebruikt moet worden of niet.
     * @return De met gamma gecorrigeerde kleur.
     */
    private static Vector gammaCorrectCoordinates(Vector color, boolean gamma) {
        double r = gamma ? Math.sqrt(color.getX()) : color.getX();
        double g = gamma ? Math.sqrt(color.getY()) : color.getY();
        double b = gamma ? Math.sqrt(color.getZ()) : color.getZ();
        return new Vector(r, g, b);
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
