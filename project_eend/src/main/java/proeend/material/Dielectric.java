package proeend.material;

import proeend.records.ScatterRecord;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.records.HitRecord;

/**
 * A class representing a dielectric material.
 */
public class Dielectric extends Material {
    private final double refractionIndex; // Index of Refraction

    /**
     * Constructs a dielectric material with the given index of refraction.
     *
     * @param indexOfRefraction The index of refraction for the material.
     */
    public Dielectric(double indexOfRefraction) {
        refractionIndex = indexOfRefraction;
    }

    /**
     * Scatter a ray of light when it interacts with this dielectric material.
     * @param rayIn The incident ray.
     * @param rec The hit record containing information about the intersection.
     * @param scRecord Houd de hoeveelheid steekproeven bij.
     * @return `true` if scattering occurs, `false` otherwise.
     */
    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, ScatterRecord scRecord) {
        scRecord.attenuation = new Vector(1,1,1);
        scRecord.pdf = null;
        scRecord.skipPDF = true;
        double refractionRatio = rec.isFrontFace() ? (1.0 / refractionIndex) : refractionIndex;

        Vector unitDirection = Vector.unitVector(rayIn.getDirection());

        double cosTheta = Math.min(Vector.dot(Vector.negate(unitDirection),rec.getNormal()),1.0);
        double sinTheta = Math.sqrt(1.0-cosTheta*cosTheta);

        boolean cannotRefract = refractionRatio * sinTheta > 1.0;

        Vector direction;
        if (cannotRefract || reflectance(cosTheta, refractionRatio) > Math.random())
            direction = Vector.reflect(unitDirection, rec.getNormal());
        else
            direction = refract(unitDirection,rec.normal,refractionRatio);

        //Vector refracted = refract(unitDirection, rec.getNormal(), refractionRatio);

        scRecord.skipRay.updateRay((rec.getP()), direction);
        return true;
    }

    private static double reflectance(double cos, double refInd) {
        double r0 = (1-refInd) / (1+refInd);
        r0 = r0*r0;
        return r0 + (1-r0)* Math.pow((1-cos),5);
    }

    /**
     * Bereken de gebroken lichtstraal (refractie) wanneer een straal invalt op een oppervlak.
     *
     * @param uv              De richting van de invallende straal.
     * @param n               De normaalvector van het oppervlak.
     * @param etai_over_etat  De verhouding van de brekingsindices.
     * @return De richting van de gebroken lichtstraal.
     */
    public Vector refract(Vector uv, Vector n, double etai_over_etat) {
        // Bereken de cosinus van de hoek tussen de invallende straal en de normaal.
        double cos_theta = Math.min(- Vector.dot(uv, n), 1.0);

        // Bereken het loodrechte deel van de gebroken lichtstraal.
        Vector r_out_perp = Vector.scale(etai_over_etat, Vector.add(uv, Vector.scale(cos_theta, n)));

        // Bereken het evenwijdige deel van de gebroken lichtstraal.
        double discriminant = 1.0 - Vector.lengthSquared(r_out_perp);
        if (discriminant > 0) {
            Vector r_out_parallel = Vector.scale(-Math.sqrt(discriminant), n);
            // Voeg de loodrechte en evenwijdige delen samen om de gebroken lichtstraal te verkrijgen.
            return Vector.add(r_out_perp, r_out_parallel);
        } else {
            // Als de discriminant negatief is, is er totale interne reflectie.
            return new Vector(0, 0, 0); // Geen breking, de straal wordt volledig gereflecteerd.
        }
    }




}
