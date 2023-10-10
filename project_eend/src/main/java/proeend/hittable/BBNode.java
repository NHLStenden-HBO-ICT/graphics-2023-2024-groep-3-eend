package proeend.hittable;

import proeend.math.*;
import proeend.misc.HitRecord;

import java.util.Comparator;
import java.util.List;

/**
 * Een klasse die een bounding volume hierarchy boom voor hittable objecten vertegenwoordigt.
 */
public class BBNode extends Hittable{

    private Hittable left;
    private Hittable right;
    private BoundingBox boundingBox;

    public BBNode(HittableList objects){
        this(objects.getObjects(), 0, objects.getObjects().size());
    }

    // constructor overloading
    public BBNode(List<Hittable> objects, int start, int end) {

        // Genereer een willekeurig getal tussen 0 (inclusief) en 2 (exclusief)
        int axis = (int)(3 * Math.random());

        Comparator<Hittable> comparator = (axis == 0) ? new BoxXCompare(): (axis == 1) ? new BoxYCompare() : new BoxZCompare();

        int objectSpan = end - start;

        if (objectSpan == 1) {
            left = right = objects.get(start);
        } else if (objectSpan == 2) {
            // Als er slechts twee objecten zijn, sorteer ze en wijs links en rechts toe op basis van de sortering
            if (comparator.compare(objects.get(start), objects.get(start + 1)) < 0) {
                left = objects.get(start);
                right = objects.get(start + 1);
            } else {
                left = objects.get(start + 1);
                right = objects.get(start);
            }
        } else {
            // Sorteer de objecten op het geselecteerde as
            objects.subList(start, end).sort(comparator);

            // Bereken het midden van de objecten
            int mid = start + objectSpan / 2;

            // Recursief bouwen van de linker en rechter kinderen van de BVH-knoop
            left = new BBNode(objects, start, mid);
            right = new BBNode(objects, mid, end);
        }

        //TODO de bbox wordt niet goed aangemaakt, blijft null. Dat zorgt later voor de NullExceptionReference
            boundingBox = new BoundingBox(left.getBoundingbox(), right.getBoundingbox());

    }

    private static Vector getMin() {
        Vector min = new Vector(0,0,0);
        return min;
    }

    private static Vector getMax() {
        Vector max = new Vector(1080,1080,1080);
        return max;
    }




    @Override
    public boolean hit(Ray r, Interval rayT, HitRecord rec) {
        if (!boundingBox.hit(r, rayT, rec)) {
            return false;
        }

        boolean hitLeft = left.hit(r, rayT, rec);
        boolean hitRight = right.hit(r, new Interval(rayT.min, hitLeft ? rec.t : rayT.max), rec);

        return hitLeft || hitRight;
    }


}