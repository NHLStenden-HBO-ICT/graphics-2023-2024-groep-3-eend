package proeend.material;

import proeend.Main;
import proeend.material.texture.SolidColor;
import proeend.material.texture.Texture;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.misc.HitRecord;

public class Emitter extends Material {
    public Emitter(Texture emission) {
        this.emission = emission;
    }
    public Emitter(Vector color) {
        this.emission = new SolidColor(color);
    }
    private Texture emission;
    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, Vector attenuation, Ray scattered) {
        return false;
    }
    @Override
    public Vector emit(double u, double v, Vector p) {
        return emission.value(u, v, p);
    }
}
