package proeend.material;

import proeend.ScatterRecord;
import proeend.misc.HitRecord;
import proeend.math.Ray;
import proeend.math.Vector;

public abstract class Material {

    /**
     * Geeft aan hoeveel licht wordt gebogen wanneer het door dat materiaal gaat
     */
    private double refractionIndex;


    public boolean scatter(Ray rayIn, HitRecord rec, ScatterRecord scatterRecord) {
        return false;
    }
    public Vector emit(Ray rayIn, HitRecord rec, double u, double v, Vector p) {
        return new Vector();
    }
    public double scatteringPDF (Ray rayIn, HitRecord rec, Ray scattered) {
        return 1;
    }

    public double getRefractionIndex() {
        return refractionIndex;
    }
}
