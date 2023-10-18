package proeend.material.texture;

import proeend.math.Vector;

/**
 * Bepaald de textuur van een object.
 */
public abstract class Texture {

    /**
     * Maakt de kleur.
     * @param u De u-coördinaat van het punt.
     * @param v De v-coördinaat van het punt.
     * @param p De driedimensionale positie van het punt.
     * @return De kleur emissie.
     */
    public abstract Vector value(double u, double v, Vector p);
}
