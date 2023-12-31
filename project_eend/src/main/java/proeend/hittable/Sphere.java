package proeend.hittable;

import proeend.math.*;
import proeend.records.HitRecord;
import proeend.material.Material;
import static java.lang.Math.PI;

/**
 * Een sfeerobject dat kan worden geraakt door een lichtstraal in een 3D-scène.
 */
public class Sphere extends Hittable {

    private final double radius;
    private final Vector center;
    private final BoundingBox boundingBox;

    private final Material material;

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
        this.material = material;
        boundingBox = new BoundingBox(center, radius);
    }


    @Override
    public BoundingBox getBoundingbox(){
        return boundingBox;
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


        Vector OC = ray.origin().add(center.invert());
        double a = Vector.lengthSquared(ray.getDirection());
        double halfb = Vector.dot(OC, ray.getDirection());
        double c = Vector.lengthSquared(OC) - radius * radius;
        double D = halfb * halfb - a * c;
        if (D < 0) {
            return false;
        }

        double sqrtD = Math.sqrt(D);
        double root = ((-halfb - sqrtD) / a);
        if (rayT.surrounds(root)) {
            root = ((-halfb - sqrtD) / a);
            if (rayT.surrounds(root)) {
                return false;
            }
        }
        rec.setT(root);
        rec.setP(ray.at(rec.getT()));
        rec.setMaterial(material);
        Vector outwardNormal = (rec.getP().add(center.invert()).scale((1.0 / radius)));
        rec.setFaceNormal(ray, outwardNormal);
        get_sphere_uv(outwardNormal, rec);

        return true;
    }
    /**
     * Bereken de waarschijnlijkheid dichtheid functiewaarde (PDF-waarde) voor een gegeerde richting vanuit een bepaalde oorsprong.
     * De PDF-waarde wordt gebruikt in ray tracing om de kans te bepalen dat een lichtstraal in deze richting een object raakt.
     *
     * @param origin     De oorsprong van de straal.
     * @param direction  De gewenste richting.
     * @return De PDF-waarde voor de opgegeven richting vanuit de oorsprong.
     */
    @Override
    public double pdfValue(Vector origin, Vector direction) {
        HitRecord hitRecord = new HitRecord();
        if (!hit(new Ray(origin,direction),new Interval(0.001, Double.POSITIVE_INFINITY), hitRecord)) {
            return 0;
        }
        double cosThetaMax = Math.sqrt(1-radius*radius/Vector.lengthSquared(center.add(origin.invert())));
        double solidAngle = 2*Math.PI*(1-cosThetaMax);

        return 1.0/solidAngle;
    }

    /**
     *
     * @param p Gegeven punt op de sphere
     * @param u Geeft waarde [0,1] van de angle rond de y as van X=-1
     * @param v Geeft waarde [0,1] van angle van Y=-1 naar y=+1
     */


    public void get_sphere_uv(Vector p, HitRecord rec){ //krijgt u=0,0 en v=0,0 en word u=waarde boven 0 en v netzo alleen geeft hij de waarde niet terug
        double theta = Math.acos(-p.getY());
        double phi = Math.atan2(-p.getZ(), p.getX()) + PI;
        rec.setU( phi / (2 * PI));
        rec.setV(theta / PI);
    }

    /**
     * genereer willekeurig vanuit origin een vector richting dit object
     */
    @Override
    public Vector random(Vector origin) {
        Vector direction = center.add(origin.invert());
        double distanceSquared = Vector.lengthSquared(direction);
        OrthonormalBase uvw = new OrthonormalBase();
        uvw.buildFromW(direction);
        return uvw.local(randomToSphere(radius, distanceSquared));
    }
    private Vector randomToSphere(double radius, double distanceSquared) {
        double r1 = FastRandom.random();
        double r2 = FastRandom.random();
        double z = 1 + r2 * (Math.sqrt(1-radius*radius/distanceSquared)-1);

        double fi = 2*Math.PI*r1;
        double x = Math.cos(fi)*Math.sqrt(1-z*z);
        double y = Math.sin(fi)*Math.sqrt(1-z*z);

        return new Vector(x,y,z);
    }


}
