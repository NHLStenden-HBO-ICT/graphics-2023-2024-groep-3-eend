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

    private final Vector v0,v1,v2;
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
        boundingBox = getBoundingbox();
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
        Vector v0v1 = Vector.add(v1, Vector.negate(v0));
        Vector v0v2 = Vector.add(v2, Vector.negate(v0));   //deze twee kloppen
        Vector unitDir = Vector.unitVector(ray.getDirection());
        //Vector pvec = Vector.cross(Vector.unitVector(ray.direction()), v0v2);
        Vector pvec = Vector.cross(unitDir, v0v2);
        double det = Vector.dot(v0v1, pvec);
        if (det<0) {
            return false;
        }
        //determinant lijkt niet goed te zijn
        //System.out.println(det);
        double invDet = 1/det;

        Vector tvec = Vector.add(ray.origin(), Vector.negate(v0));
        double u = Vector.dot(tvec,pvec)*invDet;
        if (u < 0 || u > 1) return false;

        Vector qvec = Vector.cross(tvec, v0v1);
        double v = Vector.dot(unitDir, qvec)*invDet;
        if (v < 0 || u + v > 1) return false;

        double t = Vector.dot(v0v2, qvec)*invDet;
        if (t < 0)
            return false;

        //if (rec.t < 0) {return false;}
        //rec.t = Vector.dot(v0v2, qvec);
        //ray.direction = unitDir;
        //rec.p = ray.at(rec.t);

        //rec.normal = Vector.cross(v0v1,v0v2);
        rec.setU(u);
        rec.setV(v);
        rec.setT(t);
        rec.setMaterial(material);
        rec.setP(Vector.add(ray.origin(), Vector.scale(rec.getT(), unitDir)));
        Vector normal = Vector.unitVector(Vector.cross(v0v1, v0v2));
        rec.setNormal(normal);
        rec.setFaceNormal(ray, rec.normal);

        //rec.setFaceNormal(ray,rec.normal);
        //System.out.println(v+u);
        //rec.normal = pvec;
        return true;
    }

    public BoundingBox getBoundingbox() {
        Vector min = Vector.min(Vector.min(v0, v1), v2);
        Vector max = Vector.max(Vector.max(v0, v1), v2);

        return new BoundingBox(min, max).pad();
    }

    /**
     * deze static method bestaat eigenlijk alleen voor de TriangleMesh hit method, als het nodig is, zou je via
     * deze ook de andere kunnen doen
     * @return of ie raakt of niet
     */
    public static boolean MThit(Ray ray, Interval rayT, HitRecord rec, Vector v0, Vector v1, Vector v2, Material material) {
        Vector v0v1 = Vector.add(v1, Vector.negate(v0));
        Vector v0v2 = Vector.add(v2, Vector.negate(v0));   //deze twee kloppen
        Vector unitDir = Vector.unitVector(ray.getDirection());
        //Vector pvec = Vector.cross(Vector.unitVector(ray.direction()), v0v2);
        Vector pvec = Vector.cross(unitDir, v0v2);
        double det = Vector.dot(v0v1, pvec);
        if (det<0) {
            return false;
        }
        //determinant lijkt niet goed te zijn
        //System.out.println(det);
        double invDet = 1/det;

        Vector tvec = Vector.add(ray.origin(), Vector.negate(v0));
        double u = Vector.dot(tvec,pvec)*invDet;
        if (u < 0 || u > 1) return false;

        Vector qvec = Vector.cross(tvec, v0v1);
        double v = Vector.dot(unitDir, qvec)*invDet;
        if (v < 0 || u + v > 1) return false;

        double t = Vector.dot(v0v2, qvec)*invDet;
        if (t < 0)
            return false;

        //if (rec.t < 0) {return false;}
        //rec.t = Vector.dot(v0v2, qvec);
        //ray.direction = unitDir;
        //rec.p = ray.at(rec.t);

        //rec.normal = Vector.cross(v0v1,v0v2);
        rec.setU(u);
        rec.setV(v);
        rec.setT(t);
        rec.setMaterial(material);
        rec.setP(Vector.add(ray.origin(), Vector.scale(rec.getT(), unitDir)));
        Vector normal = Vector.unitVector(Vector.cross(v0v1, v0v2));
        rec.setNormal(normal);
        rec.setFaceNormal(ray, rec.normal);

        //rec.setFaceNormal(ray,rec.normal);
        //System.out.println(v+u);
        //rec.normal = pvec;
        return true;
    }
}
