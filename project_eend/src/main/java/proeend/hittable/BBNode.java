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

    public BoundingBox getBoundingbox(){
        return boundingBox;
    }

    public BBNode(HittableList objects){
        this(objects.getObjects(), 0, objects.getObjects().size());
        //boundingBox = objects.getBoundingBox();

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
            if (left != right) {
                boundingBox = new BoundingBox(left.getBoundingbox(), right.getBoundingbox());
            }
            else {
                boundingBox = left.getBoundingbox();
            }
    }

    @Override
    public boolean hit(Ray r, Interval rayT, HitRecord rec) {
        if (!boundingBox.hit(r, rayT)) {
            return false;
        }

        boolean hitLeft = left.hit(r, rayT, rec);
        boolean hitRight = right.hit(r, new Interval(rayT.getMin(), hitLeft ? rec.t : rayT.getMax()), rec);

        /* if (hitLeft){
            System.out.println("left");
        }

        if (hitRight){
            System.out.println("right");
        }*/

        return hitLeft || hitRight;
    }

}