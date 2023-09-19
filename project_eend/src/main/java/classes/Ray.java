package classes;

public class Ray {

    public Vec origin;
    public Vec direction;


    public Ray(Vec origin, Vec direction) {
        this.origin = origin;
        this.direction = direction;
    }
    public Vec origin() {return origin;}
    public Vec direction() {return direction;}
    //geeft de vector terug die de ray bij die t bereikt
    public Vec at(double t) {
        return Vec.add(origin, Vec.scale(t, direction));
    }

}
