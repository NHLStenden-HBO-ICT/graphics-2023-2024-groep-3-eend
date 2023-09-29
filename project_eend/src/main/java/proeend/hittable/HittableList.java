package proeend.hittable;

import proeend.math.Vector;
import proeend.misc.HitRecord;
import proeend.math.Interval;
import proeend.math.Ray;

import java.util.LinkedList;
import java.util.List;

public class HittableList extends Hittable {

    private List<Hittable> objects = new LinkedList<>();
    public void add(Hittable object) {
        objects.add(object);
    }
    public void clear() {objects.clear();}
    @Override
    public boolean hit(Ray ray, Interval rayT, HitRecord rec) {
        HitRecord tempRec = new HitRecord();
        boolean hasHitSomething = false;
        double closestSoFar = rayT.max;

        for (Hittable object: objects) {
            if (object.hit(ray, new Interval(rayT.min, closestSoFar),tempRec)) {
                //if (object instanceof Triangle)
                    //continue;
                hasHitSomething = true;

                closestSoFar = tempRec.t;
                rec.copy(tempRec);
                //oude
                //rec = tempRec;
            }
        }
        return hasHitSomething;
    }
    @Override
    public double pdfValue(Vector origin, Vector direction) {
        for (Hittable object : objects) {
            return object.pdfValue(origin,direction);
        }
        return 0;
    }
    @Override
    public Vector random(Vector origin) {
        for (Hittable object : objects) {
            return object.random(origin);
        }
        return new Vector(1,0,0);
    }

}
