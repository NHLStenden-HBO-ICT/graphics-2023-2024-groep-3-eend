package proeend.hittable;

import proeend.material.Material;
import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.misc.HitRecord;


/**
 * De "Triangle" klasse vertegenwoordigt een driehoekig object dat
 * door stralen kan worden geraakt in een ray tracing-toepassing.
 * Het bevat methoden om te bepalen of een straal dit object raakt
 * en om relevante informatie over het raakpunt vast te leggen.
 */
public class Triangle extends Hittable{
    /**
     * Constructor om een driehoek te initialiseren met zijn hoekpunten en materiaal.
     *
     * @param v0        Het eerste hoekpunt van de driehoek.
     * @param v1        Het tweede hoekpunt van de driehoek.
     * @param v2        Het derde hoekpunt van de driehoek.
     * @param material  Het materiaal dat aan de driehoek is toegewezen.
     */
    public Triangle(Vector v0, Vector v1, Vector v2, Material material) {
        this.v1 = v1;
        this.v2 = v2;
        this.v0 = v0;
        super.setMaterial(material);
    }
    public Vector v0,v1,v2;

    /**
     * Controleert of een gegeven straal dit driehoekige object raakt en berekent relevante raakpunten.
     *
     * @param ray   De straal die wordt getest op een raakpunt met de driehoek.
     * @param rayT  Het interval waarin de straal de driehoek kan raken.
     * @param rec   Het HitRecord-object waarin de raakpunten wordt opgeslagen.
     * @return      True als de straal de driehoek raakt, anders False.
     */
    @Override
    public boolean hit(Ray ray, Interval rayT, HitRecord rec) {
        Vector v0v1 = Vector.subtract(v1, v0);
        Vector v0v2 = Vector.subtract(v2, v0);

        Vector unitDir = Vector.unitVector(ray.direction());

        Vector pvec = Vector.cross(unitDir, v0v2);
        double det = Vector.dot(v0v1, pvec);

        if (det < 0) {
            return false;
        }

        double invDet = 1.0 / det;

        Vector tvec = Vector.subtract(ray.origin(), v0);
        double u = Vector.dot(tvec, pvec) * invDet;

        if (u < 0 || u > 1) {
            return false;
        }

        Vector qvec = Vector.cross(tvec, v0v1);
        double v = Vector.dot(unitDir, qvec) * invDet;

        if (v < 0 || u + v > 1) {
            return false;
        }

        double t = Vector.dot(v0v2, qvec) * invDet;

        if (t < rayT.getMin() || t > rayT.getMax()) {
            return false;
        }

        rec.setT(t);
        rec.setU(u);
        rec.setV(v);

        rec.setMaterial(getMaterial());

        Vector p = Vector.add(ray.origin(), Vector.scale(t, unitDir));
        rec.setP(p);

        Vector normal = Vector.unitVector(Vector.cross(v0v1, v0v2));
        rec.setNormal(normal);

        return true;
    }

}
