package classes;

public class HitRecord {
    public static Vec p;
    public static Vec normal;
    public static double t;
    public static boolean frontFace;
    public static Material mat;
    //outwardNormal moet eenheidsvector zijn
    public void setFaceNormal(Ray ray, Vec outwardNormal) {
        frontFace = Vec.dot(ray.direction, outwardNormal) < 0;
        normal = frontFace ? outwardNormal: Vec.inverse(outwardNormal);
    }
}
