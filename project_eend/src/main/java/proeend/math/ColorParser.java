package proeend.math;

public class ColorParser {

    /**
     * Zet een Vector om naar een array van kleur integers.
     * @param samplesPerPixel De hoeveelheid steekproeven per pixel.
     * @param gamma Geeft aan of wel of niet de kleur gamma-gecorrigeerd moet worden
     * @return RGB integer array, waarden tussen 0 en 255
     */
    public static int[] toColor(int samplesPerPixel, boolean gamma, Vector vector) {
        double[] scaledCoordinates = vector.scale(1.0 / samplesPerPixel).getCoordinates();
        double[] gammaCorrectedCoordinates = gammaCorrectCoordinates(scaledCoordinates, gamma);
        double[] clampedCoordinates = clampCoordinates(gammaCorrectedCoordinates);

        return convertToRGB(clampedCoordinates);
    }
    /**
     * Corrigeerd de coördinaten met behulp van gamma.
     * @param input De invoer van de geschaalde coördinaten.
     * @param gamma Geeft aan of de gamma gebruikt moet worden of niet.
     * @return De met gamma gecorrigeerde coördinaten.
     */
    private static double[]
    gammaCorrectCoordinates(double[] input, boolean gamma) {
        double[] corrected = new double[3];
        for (int i = 0; i < 3; i++) {
            corrected[i] = gamma ? Math.sqrt(input[i]) : input[i];
        }
        return corrected;
    }

    /**
     * Zorgt ervoor dat de coördinaten binnen de grenzen zijn.
     * @param input De geschaalde, met gamma gecorrigeerde coördinaten.
     * @return De begrensde coördinaten.
     */
    private static double[] clampCoordinates(double[] input) {
        double[] clamped = new double[3];
        Interval intensity = new Interval(0, .9999999);
        for (int i = 0; i < 3; i++) {
            clamped[i] = intensity.clamp(input[i]);
        }
        return clamped;
    }

    /**
     * Zet de uiteindelijke coördinaten om naar RGB.
     * @param input De geschaalde, met gamma gecorrigeerde, begrensde coördinaten.
     * @return De kleur.
     */
    private static int[] convertToRGB(double[] input) {
        int[] color = new int[3];
        for (int i = 0; i < 3; i++) {
            color[i] = (int) (input[i] * 255.99999);
        }
        return color;
    }
}