package proeend.material;

import proeend.records.ScatterRecord;
import proeend.material.texture.SolidColor;
import proeend.material.texture.Texture;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.records.HitRecord;

public class Emitter extends Material {
    public Emitter(Texture emission) {
        this.emission = emission;
    }
    public Emitter(Vector color) {
        this.emission = new SolidColor(color);
    }
    private Texture emission;
    @Override
    public boolean scatter(Ray rayIn, HitRecord rec, ScatterRecord scatterRecord) {
        return false;
    }
    @Override
    public Vector emit(Ray rayIn, HitRecord rec,double u, double v, Vector p) {
        if(!rec.frontFace)
            return new Vector();
        return emission.value(u, v, p);
    }
}
