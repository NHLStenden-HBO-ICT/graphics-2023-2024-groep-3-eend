package proeend.material.texture;

import proeend.math.Vector;

public class SolidColor extends Texture{
    private Vector color;
    public SolidColor(double red, double green, double blue)
    {
        color = new Vector(red,green,blue);
    }
    public SolidColor(Vector color) {
        this.color = color;
    }
    @Override
    public Vector value(double u, double v, Vector p) {
        return color;
    }
}
