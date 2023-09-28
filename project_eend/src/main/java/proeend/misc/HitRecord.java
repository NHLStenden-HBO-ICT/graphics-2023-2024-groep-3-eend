package proeend.misc;

import proeend.material.Material;
import proeend.math.Ray;
import proeend.math.Vector;

/**
 * Een klasse die informatie opslaat over een botsing (hit) tussen een lichtstraal en een object in een 3D-scène.
 */
public class HitRecord {
    /** Het raakpunt (punt van impact) van de lichtstraal met het object. */
    public Vector p;

    /** De normaalvector op het raakpunt van het oppervlak van het object. */
    public Vector normal;

    /** De parameter 't' die de afstand vertegenwoordigt van het beginpunt van de lichtstraal tot het raakpunt. */
    public double t;

    /** Geeft aan of het frontale oppervlak van het object wordt geraakt. */
    public boolean frontFace;

    /**
     * De textuurcoördinaten 'u' en 'v' op het raakpunt van het oppervlak van het object.
     * Deze waarden worden vaak gebruikt bij texture mapping.
     */
    public double u, v;

    /** Het materiaal van het object waarop is gebotst. */
    public Material material;

    /**
     * Bepaalt de normaalvector op basis van de richting van de lichtstraal en de uitgaande normaalvector.
     *
     * @param ray           De lichtstraal die is gebruikt voor de botsing.
     * @param outwardNormal De uitgaande normaalvector op het raakpunt van het oppervlak.
     */
    public void setFaceNormal(Ray ray, Vector outwardNormal) {
        frontFace = Vector.dot(ray.direction, outwardNormal) < 0;
        normal = frontFace ? outwardNormal : Vector.inverse(outwardNormal);
    }

    /**
     * Kopieert gegevens van een ander HitRecord-object naar dit object.
     *
     * @param rec Het HitRecord-object waarnaar de gegevens worden gekopieerd.
     */
    public void copy(HitRecord rec) {
        this.p = rec.p;
        this.normal = rec.normal;
        this.t = rec.t;
        this.frontFace = rec.frontFace;
        this.material = rec.material;
        this.u = rec.u;
        this.v = rec.v;
    }
}
