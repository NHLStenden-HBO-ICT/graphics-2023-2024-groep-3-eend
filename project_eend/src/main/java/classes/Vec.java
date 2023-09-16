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
    public double x() {return d[0];}
    public double y() {return d[1];}
    public double z() {return d[2];}
    public double[] getAll(){
        return d;
    }
    public int[] toColor() {
        int[] color = new int[3];
        for (int i = 0; i < d.length; i++) {
            color[i] = (int) (d[i] *255.99999);
        }
        return color;
    }
    //draait bestaande vector om
    public void invert(){
        Vec vec = scale(-1, this);
        this.d[0]= vec.x();
        this.d[1]= vec.y();
        this.d[2]= vec.z();
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


}
