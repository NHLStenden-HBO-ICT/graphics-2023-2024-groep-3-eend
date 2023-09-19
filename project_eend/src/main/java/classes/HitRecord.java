package classes;

public class HitRecord {
    public static Vector p;
    public static Vector normal;
    public static double t;
    public static boolean frontFace;
    public static Material material;
    //outwardNormal moet eenheidsvector zijn
    public void setFaceNormal(Ray ray, Vector outwardNormal) {
        frontFace = Vector.dot(ray.direction, outwardNormal) < 0;
        normal = frontFace ? outwardNormal: Vector.inverse(outwardNormal);
    }
}
