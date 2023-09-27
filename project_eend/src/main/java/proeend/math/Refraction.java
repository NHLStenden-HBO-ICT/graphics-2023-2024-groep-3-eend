package proeend.math;

/**
 * This class provides methods for vector refraction calculations.
 */
public class Refraction {

    /**
     * Calculates the refraction of an incident ray given its direction, surface normal,
     * and the ratio of refractive indices.
     *
     * @param incidentRayDirection  The direction vector of the incident ray.
     * @param surfaceNormal         The surface normal vector.
     * @param refractiveIndexRatio  The ratio of refractive indices (refractive index of the incident medium / transmitted medium).
     * @return                      The refracted ray.
     */
    public static Vector refract(Vector incidentRayDirection, Vector surfaceNormal, double refractiveIndexRatio) {
        double cos_theta = Math.min(Vector.dot(Vector.scale(-1.0, incidentRayDirection), surfaceNormal), 1.0);
        Vector r_out_perp = Vector.scale(refractiveIndexRatio, Vector.add(incidentRayDirection, Vector.scale(cos_theta, surfaceNormal)));
        Vector r_out_parallel = Vector.scale(-Math.sqrt(Math.abs(1.0 - Vector.lengthSquared(r_out_perp))), surfaceNormal);
        return Vector.add(r_out_perp, r_out_parallel);
    }
}
