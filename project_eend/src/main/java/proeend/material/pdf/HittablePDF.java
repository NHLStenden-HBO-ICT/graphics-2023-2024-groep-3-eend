package proeend.material.pdf;

import proeend.hittable.Hittable;
import proeend.math.Vector;

/**
 * dit is dus een PDF náár een hittable
 */
public class HittablePDF extends PDF {
    private Hittable objects;
    private Vector origin;
    public HittablePDF(Hittable objects, Vector origin) {
        this.objects = objects;
        this.origin = origin;
    }
    @Override
    double value(Vector direction) {
        return objects.pdfValue(origin, direction);
    }

    @Override
    Vector generate() {
        return objects.random(origin);
    }
}
