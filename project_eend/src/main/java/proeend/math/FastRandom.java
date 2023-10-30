package proeend.math;

import java.util.Random;

public class FastRandom {
    private static final Random random = new Random();
    public static double random() {
        return random.nextDouble();
    }
}
