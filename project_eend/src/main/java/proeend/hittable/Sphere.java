package proeend.hittable;

import proeend.misc.HitRecord;
import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.material.Material;

/**
 * Een sfeerobject dat kan worden getroffen door een lichtstraal in een 3D-scène.
 */
public class Sphere extends Hittable {

    private final double radius;
    private final Vector center;

    /**
     * Creëer een nieuwe sfeer met het opgegeven middelpunt, straal en materiaal.
     *
     * @param center   Het middelpunt van de sfeer.
     * @param radius   De straal van de sfeer.
     * @param material Het materiaal van de sfeer.
     */
    public Sphere(Vector center, double radius, Material material) {
        this.center = center;
        this.radius = radius;
        super.setMaterial(material);
    }

    /**
     * Bepaalt of een lichtstraal een botsing heeft met de sfeer en vult het HitRecord met de informatie over de botsing.
     *
     * @param ray   De lichtstraal die wordt getest op botsingen.
     * @param rayT  Het interval waarin mogelijke botsingen worden gecontroleerd.
     * @param rec   Het HitRecord dat wordt gevuld met informatie over de botsing.
     * @return True als de lichtstraal een botsing heeft met de sfeer, anders false.
     */
    @Override
    public boolean hit(Ray ray, Interval rayT, HitRecord rec) {

        Vector OC = Vector.add(ray.origin(), Vector.inverse(center));
        double a = Vector.lengthSquared(ray.direction());
        double halfb = Vector.dot(OC, ray.direction());
        double c = Vector.lengthSquared(OC) - radius * radius;
        double D = halfb * halfb - a * c;
        if (D < 0) {
            return false;
        }

        double sqrtD = Math.sqrt(D);
        double root = ((-halfb - sqrtD) / a);
        if (!rayT.surrounds(root)) {
            root = ((-halfb - sqrtD) / a);
            if (!rayT.surrounds(root)) {
                return false;
            }
        }
        rec.setT(root);
        rec.setP(ray.at(rec.getT()));
        rec.setMaterial(getMaterial());
        Vector outwardNormal = Vector.scale((1.0 / radius), Vector.add(rec.getP(), Vector.inverse(center)));
        rec.setFaceNormal(ray, outwardNormal);
        return true;
    }
}
