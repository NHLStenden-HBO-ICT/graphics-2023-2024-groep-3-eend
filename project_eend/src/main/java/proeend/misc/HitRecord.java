package proeend.misc;

import proeend.material.Material;
import proeend.math.Ray;
import proeend.math.Vector;

/**
 * Een HitRecord houdt informatie bij over een botsing tussen een straal en een object.
 */
public class HitRecord {
    /** De positie (punt) waar de botsing plaatsvond. */
    public Vector p;

    /** De normaalvector op het punt van botsing. */
    public Vector normal;

    /** De afstand langs de straal waar de botsing plaatsvond. */
    public double t;

    /**
     * Geeft aan of de straal aan de voorkant (front face) van het object raakte.
     * Dit wordt gebruikt om te bepalen of de normaal naar binnen of naar buiten wijst.
     */
    public boolean frontFace;

    /** Voor driehoeken en texture-mapping: u-coördinaat. */
    public double u;

    /** Voor driehoeken en texture-mapping: v-coördinaat. */
    public double v;

    /** Voor driehoeken en texture-mapping: w-coördinaat. */
    public double w = 0.0;

    /** Het materiaal van het getroffen object. */
    public Material material;

    /**
     * Stelt de normaalvector van het front face van het object in op basis van de
     * inkomende straal en de naar buiten gerichte normaalvector.
     *
     * @param ray            De inkomende straal.
     * @param outwardNormal De naar buiten gerichte normaalvector op het botsingspunt.
     */
    public void setFaceNormal(Ray ray, Vector outwardNormal) {
        frontFace = Vector.dot(ray.direction, outwardNormal) < 0;
        normal = frontFace ? outwardNormal : Vector.inverse(outwardNormal);
    }

    /**
     * Kopieert de eigenschappen van een ander HitRecord naar dit HitRecord.
     *
     * @param rec Het andere HitRecord om te kopiëren.
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
