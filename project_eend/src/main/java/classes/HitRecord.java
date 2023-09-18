package classes;

public class HitRecord {
    public Vec p;
    public Vec normal;
    public double t;
    public boolean frontFace;
    //outwardNormal moet eenheidsvector zijn
    public void setFaceNormal(Ray ray, Vec outwardNormal) {
        frontFace = Vec.dot(ray.direction, outwardNormal) < 0;
        normal = frontFace ? outwardNormal: Vec.inverse(outwardNormal);
    }
}
