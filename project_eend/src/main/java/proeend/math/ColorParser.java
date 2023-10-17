package proeend.math;

public class ColorParser {

    /**
     * zet een Vector om naar een array van kleur integers
     * @param samplesPerPixel
     * @param gamma
     * of wel of niet de kleur gamma-gecorrigeerd moet worden
     * @return
     * RGB integer array, waarden tussen 0 en 255
     */

    public static int[] toColor(int samplesPerPixel, boolean gamma, Vector vector) {
        double[] scaledCoordinates = scaleCoordinates((1.0 / samplesPerPixel), vector);
        double[] gammaCorrectedCoordinates = gammaCorrectCoordinates(scaledCoordinates, gamma);
        double[] clampedCoordinates = clampCoordinates(gammaCorrectedCoordinates);

        int[] color = convertToRGB(clampedCoordinates);
        return color;
    }

    private static double[] scaleCoordinates(double scale, Vector vector) {
        double[] scaled = new double[3];
        for (int i = 0; i < 3; i++) {
            scaled[i] = vector.getCoordinates()[i] * scale;
        }
        return scaled;
    }

    private static double[] gammaCorrectCoordinates(double[] input, boolean gamma) {
        double[] corrected = new double[3];
        for (int i = 0; i < 3; i++) {
            corrected[i] = gamma ? toGamma(input[i]) : input[i];
        }
        return corrected;
    }

    private static double toGamma(double original) {
        return Math.sqrt(original);
    }

    private static double[] clampCoordinates(double[] input) {
        double[] clamped = new double[3];
        Interval intensity = new Interval(0, .9999999);
        for (int i = 0; i < 3; i++) {
            clamped[i] = intensity.clamp(input[i]);
        }
        return clamped;
    }

    private static int[] convertToRGB(double[] input) {
        int[] color = new int[3];
        for (int i = 0; i < 3; i++) {
            color[i] = (int) (input[i] * 255.99999);
        }
        return color;
    }


}