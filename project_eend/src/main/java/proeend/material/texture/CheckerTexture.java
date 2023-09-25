package proeend.material.texture;

import proeend.math.Vector;

public class CheckerTexture extends Texture{
    public CheckerTexture(double scale, Texture even, Texture odd) {
        this.scale = 1.0/scale;
        this.even = even;
        this.odd = odd;
    }
    public CheckerTexture(double scale, Vector color1, Vector color2) {
        this.scale = 1.0/scale;
        even = new SolidColor(color1);
        odd = new SolidColor(color2);
    }
    private double scale;
    private Texture even;
    private Texture odd;
    @Override
    public Vector value(double u, double v, Vector p) {
        int xInteger = (int) (scale * p.x());
        int yInteger = (int) (scale * p.y());
        int zInteger = (int) (scale * p.z());

        boolean isEven = (xInteger + yInteger + zInteger) % 2 == 0;

        return isEven ? even.value(u, v, p) : odd.value(u, v, p);
    }
}
