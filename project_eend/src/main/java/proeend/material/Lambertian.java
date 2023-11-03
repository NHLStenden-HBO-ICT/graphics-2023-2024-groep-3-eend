package proeend.material;

import proeend.records.ScatterRecord;
import proeend.material.pdf.CosPDF;
import proeend.material.texture.*;
import proeend.material.texture.SolidColor;
import proeend.material.texture.Texture;
import proeend.records.HitRecord;
import proeend.math.Ray;
import proeend.math.Vector;

/**
 * Maakt het Lambertian materiaal aan.
 */
public class Lambertian extends Material{
    private final Texture albedo; // hoeveelheid lichtreflectie

    /**
     * Maakt een Lambertian materiaal aan.
     * @param albedo Hoeveelheid weerkaatsing.
     */
    public Lambertian(Vector albedo) {
        this.albedo = new SolidColor(albedo); {
        }
    }

    /**
     * Stelt de textuur van het materiaal in.
     * @param texture De textuur.
     */
    public Lambertian(Texture texture) {
        this.albedo = texture;
    }

    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, ScatterRecord scatterRecord) {

        scatterRecord.attenuation = albedo.value(rec.getU(), rec.getV(), rec.getP());
        scatterRecord.pdf = new CosPDF(rec.normal);
        scatterRecord.skipPDF = false;

        return true;
    }
    @Override
    public double scatteringPDF (Ray rayIn, HitRecord rec, Ray scattered) {
        var cosTheta = Vector.dot(rec.normal, scattered.getDirection().toUnitVector());
        return cosTheta < 0 ? 0 : cosTheta/Math.PI;
    }
}
