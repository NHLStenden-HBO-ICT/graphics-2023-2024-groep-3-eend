package proeend.hittable;

import proeend.material.Material;
import proeend.misc.HitRecord;
import proeend.math.Interval;
import proeend.math.Ray;

public abstract class Hittable {
    public abstract boolean hit(Ray ray, Interval rayT, HitRecord rec);

    private Material material;

    public Hittable(){}

    public Hittable(Material material){
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
