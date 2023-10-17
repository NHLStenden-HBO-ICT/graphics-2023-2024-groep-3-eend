package proeend.math;

public class Vector {
    // vectoren en (?)kleur
    private double[] d = new double[3]; //4e dimensie voor homogeen (alfa voor kleur)
    public Vector(){
        for (int i = 0; i <3; i++){
            d[i]=0;
        }
    }// misschien beter om meteen by de initialisatie al {0,0,0} te maken
    public Vector(double x, double y, double z){
        d[0]=x;
        d[1]=y;
        d[2]=z;
    }

    public void setValues(double x, double y, double z){
        d[0]=x;
        d[1]=y;
        d[2]=z;
    }

    public static Vector multiply(Vector attenuation, Vector vec) {
        return new Vector(attenuation.x()* vec.x(), attenuation.y()* vec.y(),attenuation.z()* vec.z());
    }


    public static Vector reflect(Vector vec, Vector normal) {
        return add(vec, inverse(scale(2.0*dot(vec,normal),normal)));
    }

    public double x() {return d[0];}
    public double y() {return d[1];}
    public double z() {return d[2];}


    public double axis(int n) {
        if (n == 1) return d[1];
        if (n == 2) return d[2];
        return d[0];
    }


    public double[] getCoordinates(){
        return d;
    }

    public void copy(Vector copied) {
        this.d = copied.d;
    }
    public void invert(){
        Vector vec = scale(-1, this);
        this.d[0]= vec.x();
        this.d[1]= vec.y();
        this.d[2]= vec.z();
    }
    public void rotateZ(double angle) {
        double x = d[0];
        d[0] = d[0]*Math.cos(angle)-d[1]*Math.sin(angle);
        d[1] = x*Math.sin(angle)+d[1]*Math.cos(angle);
    }
    public void rotateY(double angle) {
        double x = d[0];
        d[0] = d[0]*Math.cos(angle)+d[2]*Math.sin(angle);
        d[2] = -x  *Math.sin(angle)+d[2]*Math.cos(angle);
    }
    public static Vector inverse(Vector vec) {
        return new Vector(-vec.x(),-vec.y(),-vec.z());
    }
    //voeg twee vectoren bij elkaar (of haalt van elkaar af)
    public static Vector add(Vector vector1, Vector vector2) {
        return new Vector(vector1.x()+vector2.x(), vector1.y()+vector2.y(), vector1.z()+vector2.z());
    }
    //schaal een vector met scalar
    public static Vector scale(double scalar, Vector scaled) {
        return new Vector(scaled.x()*scalar, scaled.y()*scalar, scaled.z()*scalar);
    }

    //dotproduct
    public static double dot(Vector vector1, Vector vector2) {
        return (vector1.x()*vector2.x()+vector1.y()*vector2.y()+vector1.z()*vector2.z());
    }
    //TODO crossproduct
    public static Vector cross(Vector vector1, Vector vector2) {
        return new Vector(
                vector1.y() * vector2.z() - vector1.z() * vector2.y(),
                vector1.z() * vector2.x() - vector1.x() * vector2.z(),
                vector1.x() * vector2.y() - vector1.y() * vector2.x()
        );
    }

    public static double lengthSquared(Vector vec) {
        return (vec.x()*vec.x()+ vec.y()* vec.y()+ vec.z()* vec.z());
    }
    public static double length(Vector vec) {
        return Math.sqrt(lengthSquared(vec));
    }
    public static Vector unitVector(Vector vec) {
        return scale((1.0/length(vec)), vec);
    }

    public static Vector randomVec() {
        return new Vector(Math.random(), Math.random(), Math.random());
    }
    public static Vector randomVec(double min, double max) {
        return new Vector(
                min + Math.random() * (max-min),
                min + Math.random() * (max-min),
                min + Math.random() * (max-min));
    }
    public static Vector randomInUnitSphere() {
        while (true) {
            Vector p = randomVec(-1, 1);
            if (lengthSquared(p) < 1) {
                return p;
            }
        }
    }
    public static Vector randomOnUnitSphere() {
        return unitVector(randomInUnitSphere());
    }
    public static Vector RandomUnitVecOnHemisphere(Vector normal) {
        Vector p = randomOnUnitSphere();
        if (dot(p, normal) >0.0) {
            return p;
        }
        return inverse(p);
    }

    /**
     * Subtract second vector to first vector.
     * @param vectorA is the first vector.
     * @param vectorB is the second vector to subtract.
     * @return the result of the vector subtraction.
     */
    public static Vector subtract(Vector vectorA, Vector vectorB) {
        double x = vectorA.d[0] - vectorB.d[0];
        double y = vectorA.d[1] - vectorB.d[1];
        double z = vectorA.d[2] - vectorB.d[2];
        return new Vector(x, y, z);
    }

    public Vector getNormal(int axis, double v) {

        if (axis == 1) return new Vector(0,v,0);
        if (axis == 2) return new Vector(0,0,v);
        return new Vector(v,0,0);

    }

    // Method to find the component-wise minimum between two vectors
    public static Vector min(Vector vector1, Vector vector2) {
        double x = Math.min(vector1.x(), vector2.x());
        double y = Math.min(vector1.y(), vector2.y());
        double z = Math.min(vector1.z(), vector2.z());
        return new Vector(x, y, z);
    }

    // Method to find the component-wise maximum between two vectors
    public static Vector max(Vector vector1, Vector vector2) {
        double x = Math.max(vector1.x(), vector2.x());
        double y = Math.max(vector1.y(), vector2.y());
        double z = Math.max(vector1.z(), vector2.z());
        return new Vector(x, y, z);
    }
}