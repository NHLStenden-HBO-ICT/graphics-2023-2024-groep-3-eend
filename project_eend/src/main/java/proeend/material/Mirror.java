package proeend.material;

import proeend.records.HitRecord;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.records.ScatterRecord;

/**
 * Spiegelend materiaal.
 */
public class Mirror extends Material{
    private final Vector albedo;
    private final double fuzz;

    /**
     * Maakt een spiegel aan.
     * @param color De kleur van de spiegel.
     * @param fuzz De hoeveelheid spiegeling.
     */
    public Mirror(Vector color, double fuzz) {
        albedo = color;
        this.fuzz = Math.min(fuzz, 1.0);
    }

    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, ScatterRecord scatterRecord) {
        scatterRecord.attenuation = albedo;
        scatterRecord.pdf = null;
        scatterRecord.skipPDF = true;
        Vector reflected = Vector.reflect(rayIn.getDirection().toUnitVector(), rec.normal);
        scatterRecord.skipRay = new Ray(rec.p, reflected.add(Vector.randomOnUnitSphere().scale(fuzz)));
        return true;
        //wat er eerst stond
    }
}
