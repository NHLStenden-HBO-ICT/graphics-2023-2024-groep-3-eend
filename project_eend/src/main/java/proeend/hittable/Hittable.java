package proeend.hittable;

import proeend.math.Vector;
import proeend.material.Material;
import proeend.misc.HitRecord;
import proeend.math.Interval;
import proeend.math.Ray;

/**
 * Abstracte klasse die dient als basis voor hittable objecten in een 3D-scène.
 * Hittable objecten kunnen worden geraakt door een lichtstraal (Ray).
 */
public abstract class Hittable {

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

    private Material material; // Het materiaal van het hittable object.

    /**
     * Constructor voor een hittable object zonder een gespecificeerd materiaal.
     */
    public Hittable(){}

    /**
     * Constructor voor een hittable object met een gespecificeerd materiaal.
     *
     * @param material  Het materiaal van het hittable object.
     */
    public Hittable(Material material){
        this.material = material;
    }

    /**
     * Haal het materiaal op dat aan dit hittable object is gekoppeld.
     *
     * @return  Het materiaal van het hittable object.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Stel het materiaal in dat aan dit hittable object is gekoppeld.
     *
     * @param material  Het nieuwe materiaal voor het hittable object.
     */
    public void setMaterial(Material material) {
        this.material = material;
    }
}
