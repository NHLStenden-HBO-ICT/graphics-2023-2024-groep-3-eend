package classes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HittableList extends Hittable {

    public List<Hittable> objects = new LinkedList<>();
    public void add(Hittable object) {
        objects.add(object);
    }
    @Override
    public boolean hit(Ray ray, Interval rayT, HitRecord rec) {
        HitRecord tempRec = new HitRecord();
        boolean hitAnything = false;
        double closestSoFar = rayT.max;

        for (Hittable object: objects) {
            if (object.hit(ray, new Interval(rayT.min, closestSoFar),tempRec)) {
                hitAnything = true;
                closestSoFar = tempRec.t;
                rec = tempRec;
            }
        }
        return hitAnything;
    }
}
