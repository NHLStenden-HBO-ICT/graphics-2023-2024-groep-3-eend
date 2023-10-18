package proeend.material;

import proeend.math.Ray;
import proeend.math.Vector;
import proeend.records.HitRecord;
import proeend.records.ScatterRecord;

public abstract class Material {

    /**
     * Strooi een lichtstraal.
     * @param rayIn De invallende straal.
     * @param rec Bevat informatie over de kruising.
     * @param scatterRecord Houd de hoeveelheid steekproeven bij.
     * @return `true` als er gestraald wordt, anders `false`.
     */
    public boolean scatter(Ray rayIn, HitRecord rec, ScatterRecord scatterRecord) {
        return false;
    }

    /**
     * Geeft licht.
     * @param rayIn De invallende straal.
     * @param rec Bevat informatie over de kruising.
     * @param u De u-coördinaat van het punt.
     * @param v De v-coördinaat van het punt.
     * @param p De driedimensionale positie van het punt.
     * @return De kleur emissie.
     */
    public Vector emit(Ray rayIn, HitRecord rec, double u, double v, Vector p) {
        return new Vector();
    }

    /**
     * Bepaald de spreiding van het licht.
     * @param rayIn De invallende straal.
     * @param rec Bevat informatie over de kruising.
     * @param scattered De spreiding.
     * @return De PDF waarde.
     */
    public double scatteringPDF (Ray rayIn, HitRecord rec, Ray scattered) {
        return 1;
    }
}
