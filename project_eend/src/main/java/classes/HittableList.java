package classes;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HittableList extends Hittable {

    public List<Hittable> objects = new LinkedList<>();
    void add(Hittable object) {
        objects.add(object);
    }
    @Override
    boolean hit(Ray ray, double tMin, double tMax, HitRecord rec) {
        HitRecord tempRec = new HitRecord();
        boolean hitAnything = false;
        double closestSoFar = tMax;

        for (Hittable object: objects) {
            if (object.hit(ray, tMin, closestSoFar,tempRec)) {
                hitAnything = true;
                closestSoFar = tempRec.t;
                rec = tempRec;
            }
        }
        return hitAnything;
    }
}
