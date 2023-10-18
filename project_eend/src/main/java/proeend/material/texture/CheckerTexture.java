package proeend.material.texture;

import proeend.math.Vector;

public class CheckerTexture extends Texture{
    private double scale;
    private Texture even;
    private Texture odd;

    /**
     * Constructor voor CheckerTexture met opgegeven schaal, even textuur en oneven textuur.
     * @param scale De schaal van de geruite textuur.
     * @param even  De even textuur die wordt toegepast op even coördinaten.
     * @param odd   De oneven textuur die wordt toegepast op oneven coördinaten.
     */
    public CheckerTexture(double scale, Texture even, Texture odd) {
        this.scale = 1.0/scale;
        this.even = even;
        this.odd = odd;
    }

    /**
     * Constructor voor CheckerTexture met opgegeven schaal, kleur 1 en kleur 2.
     * @param scale   De schaal van de geruite textuur.
     * @param color1  De kleur die wordt toegepast op even coördinaten.
     * @param color2  De kleur die wordt toegepast op oneven coördinaten.
     */
    public CheckerTexture(double scale, Vector color1, Vector color2) {
        this.scale = 1.0/scale;
        even = new SolidColor(color1);
        odd = new SolidColor(color2);
    }

    @Override
    public Vector value(double u, double v, Vector p) {
        int scaledX = (int) (scale * p.getX());
        int scaledY = (int) (scale * p.getY());
        int scaledZ = (int) (scale * p.getZ());

        boolean isEven = (scaledX + scaledY + scaledZ) % 2 == 0;

        return isEven ? even.value(u, v, p) : odd.value(u, v, p);
    }
}
