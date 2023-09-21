package proeend.misc;

import proeend.material.Material;
import proeend.math.Ray;
import proeend.math.Vector;

public class HitRecord {
    public Vector p;
    public Vector normal;
    public double t;
    public boolean frontFace;
    public Material material;
    //outwardNormal moet eenheidsvector zijn
    public void setFaceNormal(Ray ray, Vector outwardNormal) {
        frontFace = Vector.dot(ray.direction, outwardNormal) < 0;
        normal = frontFace ? outwardNormal: Vector.inverse(outwardNormal);
    }

    public void copy(HitRecord rec) {
        this.p = rec.p;
        this.normal = rec.normal;
        this.t = rec.t;
        this.frontFace = rec.frontFace;
        this.material = rec.material;
    }
}
