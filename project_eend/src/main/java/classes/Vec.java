package classes;

public class Vec {
    // vectoren en (?)kleur
    private double[] d = new double[3]; //4e dimensie voor homogeen (alfa voor kleur)
    public Vec(){
        for (int i = 0; i <3; i++){
            d[i]=0;
        }
    }// misschien beter om meteen by de initialisatie al {0,0,0} te maken
    public Vec(double x, double y, double z){
        d[0]=x;
        d[1]=y;
        d[2]=z;
    }

    public static Vec multiply(Vec attenuation, Vec vec) {
        return new Vec(attenuation.x()* vec.x(), attenuation.y()* vec.y(),attenuation.z()* vec.z());
    }

    public static Vec reflect(Vec vec, Vec normal) {
        return add(vec, inverse(scale(2.0*dot(vec,normal),normal)));
    }

    public double x() {return d[0];}
    public double y() {return d[1];}
    public double z() {return d[2];}
    public double[] getAll(){
        return d;
    }
    public int[] toColor(int pixelPerSample, boolean gamma) {
        //scale(1.0/pixelPerSample, this);
            double s = (1.0/pixelPerSample);
        d[0] = d[0]*s;
        d[1] = d[1]*s;
        d[2] = d[2]*s;

        if (gamma) {
            d[0] = linearToGamma(d[0]);
            d[1] = linearToGamma(d[1]);
            d[2] = linearToGamma(d[2]);
        }
        Interval intensity = new Interval(0, .9999999);
        int[] color = new int[3];
        for (int i = 0; i < d.length; i++) {
            color[i] = (int) ((intensity.clamp(d[i]) *255.99999));
        }
        return color;
    }
    //verandert kleur naar gamma-aangepaste kleur e 1/2
    public double linearToGamma(double original) {
        return Math.sqrt(original);
    }
    //draait bestaande vector om
    public void invert(){
        Vec vec = scale(-1, this);
        this.d[0]= vec.x();
        this.d[1]= vec.y();
        this.d[2]= vec.z();
    }
    public static Vec inverse(Vec vec) {
        return new Vec(-vec.x(),-vec.y(),-vec.z());
    }
    //voeg twee vectoren bij elkaar (of haalt van elkaar af)
    public static Vec add(Vec vector1, Vec vector2) {
        return new Vec(vector1.x()+vector2.x(), vector1.y()+vector2.y(), vector1.z()+vector2.z());
    }
    //schaal een vector met scalar
    public static Vec scale(double scalar, Vec scaled) {
        return new Vec(scaled.x()*scalar, scaled.y()*scalar, scaled.z()*scalar);
    }
    //dotproduct
    public static double dot(Vec vector1, Vec vector2) {
        return (vector1.x()*vector2.x()+vector1.y()*vector2.y()+vector1.z()*vector2.z());
    }
    //TODO crossproduct
    public static Vec cross(Vec vector1, Vec vector2) {
        return new Vec();
    }

    public static double lengthSquared(Vec vec) {
        return (vec.x()*vec.x()+ vec.y()* vec.y()+ vec.z()* vec.z());
    }
    public static double length(Vec vec) {
        return Math.sqrt(lengthSquared(vec));
    }
    public static Vec unitVector(Vec vec) {
        return scale((1.0/length(vec)), vec);
    }

    public static Vec randomVec() {
        return new Vec(Math.random(), Math.random(), Math.random());
    }
    public static Vec randomVec(double min, double max) {
        return new Vec(
                min + Math.random() * (max-min),
                min + Math.random() * (max-min),
                min + Math.random() * (max-min));
    }
    public static Vec randomInUnitSphere() {
        while (true) {
            Vec p = randomVec(-1, 1);
            if (lengthSquared(p) < 1) {
                return p;
            }
        }
    }
    public static Vec randomUnitVec() {
        return unitVector(randomInUnitSphere());
    }
    public static Vec RandomUnitVecOnHemisphere(Vec normal) {
        Vec p = randomUnitVec();
        if (dot(p, normal) >0.0) {
            return p;
        }
        return inverse(p);
    }
}
