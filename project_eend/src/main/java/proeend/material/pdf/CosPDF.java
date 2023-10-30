package proeend.material.pdf;

import proeend.math.FastRandom;
import proeend.math.OrthonormalBase;
import proeend.math.Vector;

/**
 * Een implementatie van het PDF (Probability Density Function)-interface
 * voor het genereren van willekeurige richtingen volgens de cosinus van de invalshoek.
 */
public class CosPDF implements PDF {
    private final OrthonormalBase uvw = new OrthonormalBase();


    /**
     * Initialiseert een nieuwe CosPDF met de opgegeven basisvectoren.
     * @param w De basisvector om de PDF op te bouwen.
     */
    public CosPDF(Vector w) { uvw.buildFromW(w); }
    public double value(Vector direction) {
        double cosTheta = Vector.dot(Vector.unitVector(direction),uvw.w());
        return Math.max(0, cosTheta/Math.PI);
    }

    public Vector generate() {
        return uvw.local(randomCosineDirection());
    }
    /**
     * Genereer een willekeurige richting volgens de cosinus van de invalshoek.
     * @return Een willekeurige richting gegenereerd volgens de cosinus van de invalshoek.
     */
    public static Vector randomCosineDirection() {
        double r1 = FastRandom.random();
        double r2 = FastRandom.random();

        double fi = 2 * Math.PI * r1;
        double x = Math.cos(fi) * Math.sqrt(r2);
        double y = Math.sin(fi) * Math.sqrt(r2);
        double z = Math.sqrt( 1 - r2);

        return new Vector(x,y,z);
    }
}
