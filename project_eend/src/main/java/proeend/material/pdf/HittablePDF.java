package proeend.material.pdf;

import proeend.hittable.Hittable;
import proeend.math.Vector;

/**
 * Implementatie van het PDF (Probability Density Function)-interface voor
 * het genereren van willekeurige richtingen gebaseerd op een Hittable-object.
 * Dit PDF vertegenwoordigt de waarschijnlijkheidsdichtheid van lichtstralen die botsen met een Hittable-object.
 */
public class HittablePDF implements PDF {
    private final Hittable objects;
    private final Vector origin;

    /**
     * Initialiseert een nieuwe HittablePDF met het opgegeven Hittable-object en oorsprong.
     * @param objects Het Hittable-object waarop het PDF is gebaseerd.
     * @param origin  De oorsprong van de lichtstraal waarvoor het PDF wordt berekend.
     */
    public HittablePDF(Hittable objects, Vector origin) {
        this.objects = objects;
        this.origin = origin;
    }
    public double value(Vector direction) {
        return objects.pdfValue(origin, direction);
    }
    public Vector generate() {
        return objects.random(origin);
    }
}
