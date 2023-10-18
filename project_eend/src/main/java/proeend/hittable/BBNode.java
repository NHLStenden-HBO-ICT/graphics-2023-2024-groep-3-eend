package proeend.hittable;

import proeend.math.*;
import proeend.records.HitRecord;

import java.util.Comparator;
import java.util.List;

/**
 * Een klasse die een bounding volume hierarchy boom voor hittable objecten vertegenwoordigt.
 */
public class BBNode extends Hittable{

    private final Hittable leftNode;
    private final Hittable rightNode;
    private final BoundingBox boundingBox;

    public BoundingBox getBoundingbox(){
        return boundingBox;
    }

    /**
     * Constructor voor een BVH-knoop op basis van een lijst van hittable objecten.
     * @param objects Een lijst van hittable objecten.
     */
    public BBNode(HittableList objects){
        this(objects.getObjects(), 0, objects.getObjects().size());

    }

    /**
     * Constructor voor een BVH-knoop op basis van een lijst van hittable objecten.
     * @param objects De lijst van hittable objecten.
     * @param start   Het beginindex van het aantal objecten.
     * @param end     Het eindindex van het aantal objecten.
     */
    public BBNode(List<Hittable> objects, int start, int end) {
        // Genereer een willekeurig getal tussen 0-2, dus 0, 1 of 2
        int axis = (int)(3 * Math.random());

        // Comparator met lambda expressie wordt gebruikt om twee objecten te vergelijken.
        Comparator<Hittable> comparator = (a, b) -> compareAxis(a, b, axis);

        int objectSpan = end - start;

        if (objectSpan == 1) {
            leftNode = rightNode = objects.get(start);
        } else if (objectSpan == 2) {
            // Als er slechts twee objecten zijn, sorteer ze en wijs links en rechts toe op basis van de sortering
            if (comparator.compare(objects.get(start), objects.get(start + 1)) < 0) {
                leftNode = objects.get(start);
                rightNode = objects.get(start + 1);
            } else {
                leftNode = objects.get(start + 1);
                rightNode = objects.get(start);
            }
        } else {
            // Sorteer de objecten op het geselecteerde as
            objects.subList(start, end).sort(comparator);

            // Bereken het midden van de objecten
            int mid = start + objectSpan / 2;

            // Recursief bouwen van de linker en rechter kinderen van de BVH-knoop
            leftNode = new BBNode(objects, start, mid);
            rightNode = new BBNode(objects, mid, end);
        }
            if (leftNode != rightNode) {
                boundingBox = new BoundingBox(leftNode.getBoundingbox(), rightNode.getBoundingbox());
            }
            else {
                boundingBox = leftNode.getBoundingbox();
            }
    }


    @Override
    public boolean hit(Ray ray, Interval rayT, HitRecord rec) {

        Interval copy = new Interval();
        copy.copy(rayT);

        if (!boundingBox.hit(ray, copy)) {
            return false;
        }

        boolean hitLeft = leftNode.hit(ray, rayT, rec);

        boolean hitRight = rightNode.hit(ray, new Interval(rayT.getMin(), hitLeft ? rec.t : rayT.getMax()), rec);


        return hitLeft || hitRight;
    }

    /**
     * Vergelijk twee hittable objecten op basis van een opgegeven as in de bounding box.
     * @param a Het eerste hittable object.
     * @param b Het tweede hittable object.
     * @param axis De as waarop de vergelijking wordt uitgevoerd (0 voor x-as, 1 voor y-as, 2 voor z-as).
     * @return Een negatief getal als a kleiner is dan b op de opgegeven as, 0 als ze gelijk zijn, of een positief getal als a groter is dan b.
     *
     **/
    public int compareAxis(Hittable a, Hittable b, int axis) {
        double aMin = a.getBoundingbox().axis(axis).getMin();
        double bMax = b.getBoundingbox().axis(axis).getMax();
        return Double.compare(aMin, bMax);
    }

}