package proeend.misc;

import proeend.material.Material;
import proeend.math.Ray;
import proeend.math.Vector;

/**
 * Een klasse die informatie opslaat over een botsing (hit) tussen een lichtstraal en een object in een 3D-scène.
 */
public class HitRecord {
    public Vector p;
    public Vector normal;
    public double t;
    public boolean frontFace;
    //voor driehoeken en textures
    public double u,v,w = 0.0;
    public double pdf = 1;
    public Material material;
    //outwardNormal moet eenheidsvector zijn

    /**
     * Constructor voor een HitRecord-object.
     */
    public HitRecord() {
        // Initialiseer alle velden
    }

    /**
     * Haal het raakpunt (punt van impact) van de lichtstraal met het object op.
     *
     * @return Het raakpunt.
     */
    public Vector getP() {
        return p;
    }

    /**
     * Stel het raakpunt (punt van impact) van de lichtstraal met het object in.
     *
     * @param p Het nieuwe raakpunt.
     */
    public void setP(Vector p) {
        this.p = p;
    }

    /**
     * Haal de normaalvector op het raakpunt op van het oppervlak van het object.
     *
     * @return De normaalvector.
     */
    public Vector getNormal() {
        return normal;
    }

    /**
     * Stel de normaalvector in op het raakpunt van het oppervlak van het object.
     *
     * @param normal De nieuwe normaalvector.
     */
    public void setNormal(Vector normal) {
        this.normal = normal;
    }

    /**
     * Haal de parameter 't' op die de afstand vertegenwoordigt van het beginpunt van de lichtstraal tot het raakpunt.
     *
     * @return De parameter 't'.
     */
    public double getT() {
        return t;
    }

    /**
     * Stel de parameter 't' in die de afstand vertegenwoordigt van het beginpunt van de lichtstraal tot het raakpunt.
     *
     * @param t De nieuwe parameter 't'.
     */
    public void setT(double t) {
        this.t = t;
    }

    /**
     * Geeft aan of het frontale oppervlak van het object wordt geraakt.
     *
     * @return True als het frontale oppervlak wordt geraakt, anders false.
     */
    public boolean isFrontFace() {
        return frontFace;
    }

    /**
     * Stel in of het frontale oppervlak van het object wordt geraakt.
     *
     * @param frontFace True als het frontale oppervlak wordt geraakt, anders false.
     */
    public void setFrontFace(boolean frontFace) {
        this.frontFace = frontFace;
    }

    /**
     * Haal de textuurcoördinaat 'u' op op het raakpunt van het oppervlak van het object.
     *
     * @return De textuurcoördinaat 'u'.
     */
    public double getU() {
        return u;
    }

    /**
     * Stel de textuurcoördinaat 'u' in op het raakpunt van het oppervlak van het object.
     *
     * @param u De nieuwe textuurcoördinaat 'u'.
     */
    public void setU(double u) {
        this.u = u;
    }

    /**
     * Haal de textuurcoördinaat 'v' op op het raakpunt van het oppervlak van het object.
     *
     * @return De textuurcoördinaat 'v'.
     */
    public double getV() {
        return v;
    }

    /**
     * Stel de textuurcoördinaat 'v' in op het raakpunt van het oppervlak van het object.
     *
     * @param v De nieuwe textuurcoördinaat 'v'.
     */
    public void setV(double v) {
        this.v = v;
    }

    /**
     * Haal het materiaal op van het object waarop is gebotst.
     *
     * @return Het materiaal.
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Stel het materiaal in van het object waarop is gebotst.
     *
     * @param material Het nieuwe materiaal.
     */
    public void setMaterial(Material material) {
        this.material = material;
    }

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
        this.w = rec.w;
        this.pdf = rec.pdf;
    }
}
