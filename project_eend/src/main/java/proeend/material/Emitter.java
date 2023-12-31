package proeend.material;

import proeend.material.texture.SolidColor;
import proeend.material.texture.Texture;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.records.HitRecord;

/**
 * Maakt een materiaal dat licht uitstraalt aan.
 */
public class Emitter extends Material {
    private final Texture emission;

    public Emitter(Texture emission) {
        this.emission = emission;
    }

    public Emitter(Vector color) {
        this.emission = new SolidColor(color);
    }

    @Override
    public Vector emit(Ray rayIn, HitRecord rec, double u, double v, Vector p) {
        if(!rec.frontFace)
            return new Vector();
        return emission.value(u, v, p);
    }
}
