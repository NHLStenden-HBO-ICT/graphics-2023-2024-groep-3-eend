package proeend.hittable;

import proeend.math.*;
import proeend.misc.HitRecord;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Een klasse die een bounding volume hierarchy boom voor hittable objecten vertegenwoordigt.
 */
public class BBNode extends Hittable{

    private Hittable left;
    private Hittable right;
    private BoundingBox bbox;

    public BBNode(HittableList objects){
        new BBNode(objects.getObjects(), 0, objects.getObjects().size());
    }

    // constructor overloading
    public BBNode(List<Hittable> objects, int start, int end) {
        bbox = getBoundingbox();


        // Creëer een Random-object
        Random random = new Random();

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

            // Recursief bouwen van de linker- en rechterkinderen van de BVH-knoop
            left = new BBNode(objects, start, mid);
            right = new BBNode(objects, mid, end);
        }

        bbox = new BoundingBox(left.getBoundingbox(), right.getBoundingbox());

    }
    

    @Override
    public boolean hit(Ray r, Interval rayT, HitRecord rec) {
        if (!bbox.hit(r, rayT, rec)){
            return false;
        }

        boolean hitLeft = left.hit(r, rayT, rec);
        boolean hitRight = right.hit(r, new Interval(rayT.min, hitLeft ? rec.t : rayT.max), rec);

        return hitLeft || hitRight;
    }


}