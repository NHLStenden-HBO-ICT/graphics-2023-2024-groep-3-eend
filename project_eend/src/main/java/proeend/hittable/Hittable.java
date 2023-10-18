package proeend.hittable;

import proeend.math.Vector;
import proeend.records.HitRecord;
import proeend.math.Interval;
import proeend.math.Ray;

/**
 * Abstracte klasse die dient als basis voor hittable objecten in een 3D-scène.
 * Hittable objecten kunnen worden geraakt door een lichtstraal (Ray).
 */
public abstract class Hittable{

    public abstract BoundingBox getBoundingbox();

    /**
     * Constructor voor een hittable object zonder een gespecificeerd materiaal.
     */
    public Hittable(){}

    /**
     * Bepaalt of een lichtstraal (Ray) het hittable object raakt en berekent de raakpunten.
     *
     * @param ray   De lichtstraal die wordt getest op botsing.
     * @param rayT  Een interval dat de geldige tijdstippen van de lichtstraal vertegenwoordigt.
     * @param rec   Een HitRecord-object om informatie over de botsing op te slaan.
     * @return      True als de lichtstraal het object raakt, anders false.
     */
    public abstract boolean hit(Ray ray, Interval rayT, HitRecord rec);

    public double pdfValue(Vector origin, Vector direction) {
        return 0;
    }

    public Vector random(Vector origin) {
        return new Vector(1,0,0);
    }

}
