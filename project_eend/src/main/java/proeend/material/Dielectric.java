package proeend.material;

import proeend.math.FastRandom;
import proeend.records.ScatterRecord;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.records.HitRecord;

/**
 * Maakt een dielectric materiaal aan.
 */
public class Dielectric extends Material {
    private final double refractionIndex; // Index of Refraction

    /**
     * Construeert een dielectric materiaal met de gegeven hoeveelheid lichtbreking
     * @param indexOfRefraction De hoeveelheid lichtbreking.
     */
    public Dielectric(double indexOfRefraction) {
        refractionIndex = indexOfRefraction;
    }

    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, ScatterRecord scRecord) {
        scRecord.attenuation = new Vector(1,1,1);
        scRecord.pdf = null;
        scRecord.skipPDF = true;
        double refractionRatio = rec.isFrontFace() ? (1.0 / refractionIndex) : refractionIndex;

        Vector unitDirection = rayIn.getDirection().toUnitVector();

        double cosTheta = Math.min(Vector.dot(unitDirection.invert(),rec.getNormal()),1.0);
        double sinTheta = Math.sqrt(1.0-cosTheta*cosTheta);

        boolean cannotRefract = refractionRatio * sinTheta > 1.0;

        Vector direction;
        if (cannotRefract || reflectance(cosTheta, refractionRatio) > FastRandom.random())
            direction = Vector.reflect(unitDirection, rec.getNormal());
        else
            direction = refract(unitDirection,rec.normal,refractionRatio);

        //Vector refracted = refract(unitDirection, rec.getNormal(), refractionRatio);

        scRecord.skipRay.updateRay((rec.getP()), direction);
        return true;
    }

    /**
     * Reflecteert een vector.
     * @param cos De cosinus.
     * @param refInd De reflectance index.
     * @return De gereflecteerde vector.
     */
    private static double reflectance(double cos, double refInd) {
        double r0 = (1-refInd) / (1+refInd);
        r0 = r0*r0;
        return r0 + (1-r0)* Math.pow((1-cos),5);
    }

    /**
     * Bereken de gebroken lichtstraal (refractie) wanneer een straal invalt op een oppervlak.
     * @param uv              De richting van de invallende straal.
     * @param normal               De normaalvector van het oppervlak.
     * @param etai_over_etat  De verhouding van de brekingsindices.
     * @return De richting van de gebroken lichtstraal.
     */
    public Vector refract(Vector uv, Vector normal, double etai_over_etat) {
        // Bereken de cosinus van de hoek tussen de invallende straal en de normaal.
        double cos_theta = Math.min(- Vector.dot(uv, normal), 1.0);

        // Bereken het loodrechte deel van de gebroken lichtstraal.
        Vector r_out_perp = uv.add(normal.scale(cos_theta)).scale(etai_over_etat);

        // Bereken het evenwijdige deel van de gebroken lichtstraal.
        double discriminant = 1.0 - Vector.lengthSquared(r_out_perp);
        if (discriminant > 0) {
            Vector r_out_parallel = normal.scale(-Math.sqrt(discriminant));
            // Voeg de loodrechte en evenwijdige delen samen om de gebroken lichtstraal te verkrijgen.
            return r_out_perp.add(r_out_parallel);
        } else {
            // Als de discriminant negatief is, is er totale interne reflectie.
            return new Vector(0, 0, 0); // Geen breking, de straal wordt volledig gereflecteerd.
        }
    }
}
