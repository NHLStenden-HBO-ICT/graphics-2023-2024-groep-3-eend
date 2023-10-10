package proeend.hittable;

import proeend.misc.HitRecord;
import proeend.math.Interval;
import proeend.math.Ray;

import java.util.LinkedList;
import java.util.List;

/**
 * Een lijst van hittable objecten die kunnen worden getroffen door een lichtstraal in een 3D-scène.
 */
public class HittableList extends Hittable {

    private List<Hittable> objects = new LinkedList<>();

    /**
     * Voegt een hittable object toe aan de lijst.
     *
     * @param object Het hittable object dat aan de lijst wordt toegevoegd.
     */
    public void add(Hittable object) {
        objects.add(object);
    }

    /**
     * Maakt de lijst van hittable objecten leeg.
     */
    public void clear() {
        objects.clear();
    }

    /**
     * Bepaalt of een lichtstraal een van de hittable objecten in de lijst treft en
     * vult het HitRecord met de informatie over de dichtstbijzijnde botsing.
     *
     * @param ray   De lichtstraal die wordt getest op botsingen.
     * @param rayT  Het interval waarin mogelijke botsingen worden gecontroleerd.
     * @param rec   Het HitRecord dat wordt gevuld met informatie over de botsing.
     * @return True als de lichtstraal een botsing heeft met een van de objecten in de lijst, anders false.
     */
    @Override
    public boolean hit(Ray ray, Interval rayT, HitRecord rec) {
        HitRecord tempRec = new HitRecord();
        boolean hasHitSomething = false;
        double closestSoFar = rayT.max;

        for (Hittable object : objects) {
            if (object.hit(ray, new Interval(rayT.min, closestSoFar), tempRec)) {
                hasHitSomething = true;
                closestSoFar = tempRec.getT();
                rec.copy(tempRec);
            }
        }
        return hasHitSomething;
    }
}
