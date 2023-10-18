/**
 * De SolidColor-klasse vertegenwoordigt een solide kleurtextuur die altijd dezelfde kleurwaarde retourneert,
 * ongeacht de coördinaten van het punt waarop de textuur wordt geëvalueerd.
 */
package proeend.material.texture;

import proeend.math.Vector;

public class SolidColor extends Texture {
    private Vector color;

    /**
     * Constructor voor SolidColor met opgegeven RGB-kleurwaarden.
     * @param red   De roodwaarde van de solide kleur.
     * @param green De groenwaarde van de solide kleur.
     * @param blue  De blauwwaarde van de solide kleur.
     */
    public SolidColor(double red, double green, double blue) {
        color = new Vector(red, green, blue);
    }

    /**
     * Constructor voor SolidColor met opgegeven kleurvector.
     * @param color De kleurvector die de solide kleur vertegenwoordigt.
     */
    public SolidColor(Vector color) {
        this.color = color;
    }

    /**
     * Berekent de kleurwaarde van de solide kleurtextuur.
     * @param u De u-coördinaat van het punt (niet gebruikt voor solide kleurtextuur).
     * @param v De v-coördinaat van het punt (niet gebruikt voor solide kleurtextuur).
     * @param p De driedimensionale positie van het punt (niet gebruikt voor solide kleurtextuur).
     * @return De kleurwaarde van de solide kleurtextuur.
     */
    @Override
    public Vector value(double u, double v, Vector p) {
        return color;
    }
}
