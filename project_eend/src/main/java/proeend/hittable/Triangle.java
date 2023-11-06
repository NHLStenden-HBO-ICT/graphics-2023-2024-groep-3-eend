package proeend.hittable;

import proeend.material.Material;
import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.records.HitRecord;

/**
 * De "Triangle" klasse vertegenwoordigt een driehoekig object dat
 * door stralen kan worden geraakt in een ray tracing-toepassing.
 * Het bevat methoden om te bepalen of een straal dit object raakt
 * en om relevante informatie over het raakpunt vast te leggen.
 */
public class Triangle extends Hittable{

    private Vector v0;
    private Vector v1;
    private Vector v2;
    private final Material material;
    private double area;
    private final BoundingBox boundingBox;

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
        this.material = material;
        boundingBox = setBoundingbox();
    }

    @Override
    public BoundingBox getBoundingbox() {
        return boundingBox;
    }

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
        Vector v0v1 = v1.add(v0.invert());
        Vector v0v2 = v2.add(v0.invert());
        Vector unitDir = ray.getDirection().toUnitVector();
        Vector pvec = Vector.cross(unitDir, v0v2);
        double det = Vector.dot(v0v1, pvec);

        if (det<0) {
            return false;
        }

        double invDet = 1/det;
        Vector tvec = ray.origin().add(v0.invert());
        double u = Vector.dot(tvec,pvec)*invDet;

        if (u < 0 || u > 1) return false;

        Vector qvec = Vector.cross(tvec, v0v1);
        double v = Vector.dot(unitDir, qvec)*invDet;

        if (v < 0 || u + v > 1) return false;

        double t = Vector.dot(v0v2, qvec)*invDet;

        if (t < 0) return false;

        rec.setU(u);
        rec.setV(v);
        rec.setT(t);
        rec.setMaterial(material);
        rec.setP(ray.origin().add(unitDir.scale(rec.getT())));
        Vector normal = Vector.cross(v0v1, v0v2).toUnitVector();
        rec.setNormal(normal);
        rec.setFaceNormal(ray, normal);

        return true;
    }


    public BoundingBox setBoundingbox() {
        Vector min = Vector.min(Vector.min(v0, v1), v2);
        Vector max = Vector.max(Vector.max(v0, v1), v2);

        return new BoundingBox(min, max).pad();
    }
}
