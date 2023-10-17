package proeend.material;

import proeend.records.HitRecord;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.records.ScatterRecord;

public class Mirror extends Material{
    private Vector albedo;
    private double fuzz;
    public Mirror(Vector color, double fuzz) {
        albedo = color;
        this.fuzz = Math.min(fuzz, 1.0);}


    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, ScatterRecord scatterRecord) {
        scatterRecord.attenuation = albedo;
        scatterRecord.pdf = null;
        scatterRecord.skipPDF = true;
        Vector reflected = Vector.reflect(Vector.unitVector(rayIn.direction()),rec.normal);
        scatterRecord.skipRay = new Ray(rec.p, Vector.add(reflected,Vector.scale(fuzz,Vector.randomOnUnitSphere())));
        return true;
        /*
        Vector reflected = Vector.reflect(Vector.unitVector(rayIn.direction()),rec.normal);
        Vector direction = Vector.add(reflected, Vector.scale(fuzz, Vector.randomOnUnitSphere()));
        scattered.origin = rec.p;
        scattered.direction = direction;
        attenuation.copy(albedo);
         */
        //wat er eerst stond
        //Global.scattered = new Ray(rec.p,
        //        Vector.add(reflected, Vector.scale(fuzz, Vector.randomUnitVec())));
        //Global.attenuation = albedo;
        //return (Vector.dot(scattered.direction(), rec.normal) > 0);
    }
}
