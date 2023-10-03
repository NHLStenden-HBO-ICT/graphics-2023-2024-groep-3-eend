package proeend.material;

import proeend.math.Ray;
import proeend.math.Vector;
import proeend.misc.HitRecord;

/**
 * A class representing a dielectric material.
 */
public class Dielectric extends Material {
    private final double refractionIndex; // Index of Refraction

    /**
     * Constructs a dielectric material with the given index of refraction.
     *
     * @param index_of_refraction The index of refraction for the material.
     */
    public Dielectric(double index_of_refraction) {
        refractionIndex = index_of_refraction;
    }

    /**
     * Scatter a ray of light when it interacts with this dielectric material.
     *
     * @param rayIn        The incident ray.
     * @param rec         The hit record containing information about the intersection.
     * @param attenuation The attenuation of the ray (color).
     * @param scattered   The scattered ray.
     * @return `true` if scattering occurs, `false` otherwise.
     */
    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, Vector attenuation, Ray scattered) {
        attenuation.setValues(1,1,1);
        double refraction_ratio = rec.isFrontFace() ? (1.0 / refractionIndex) : refractionIndex;

        Vector unit_direction = Vector.unitVector(rayIn.getDirection());
        Vector refracted = refract(unit_direction, rec.getNormal(), refraction_ratio);

        scattered.updateRay((rec.getP()), refracted);
        return true;
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
