package classes;

public class Vec {
    // vectoren en (?)kleur
    private double[] d = new double[3]; //4e dimensie (alfa voor kleur)
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
    public Vec inverse(){
        return new Vec(-d[0],-d[1],-d[2]);
    }
}
