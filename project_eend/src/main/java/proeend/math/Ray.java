package proeend.math;

public class Ray {

    public Vector origin;
    public Vector direction;
    public Ray(Vector origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }
    public Vector origin() {return origin;}
    public Vector direction() {return direction;}
    //geeft de vector terug die de ray bij die t bereikt
    public Vector at(double t) {
        return Vector.add(origin, Vector.scale(t, direction));
    }

}
