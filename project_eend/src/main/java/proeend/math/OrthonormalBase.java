package proeend.math;

/**
 * Maakt een Vector vanaf een genormaliseerde basis.
 */
public class OrthonormalBase {
    public OrthonormalBase(){}

    private final Vector[] axis = new Vector[3];

    public Vector u() {return axis[0];}
    public Vector v() {return axis[1];}
    public Vector w() {return axis[2];}

    /**
     * Zet een vector om naar een vector met een genormaliseerde basis.
     * @param a Vector met waarden van de x, y en z as.
     * @return Aangepaste vector.
     */
    public Vector local(Vector a) {
        return u().scale(a.getX()).add(v().scale(a.getY())).add(w().scale(a.getZ()));
    }


    /**
     * Maakt de genormaliseerde basis aan de hand van de meegegeven vector.
     * @param w De vector die aangepast wordt.
     */
    public void buildFromW(Vector w) {
        Vector unitW = w.toUnitVector();
        Vector a = (Math.abs(unitW.getX()) > .9) ? new Vector(0,1,0) : new Vector(1,0,0);
        Vector v = Vector.cross(unitW,a).toUnitVector();
        Vector u = Vector.cross(unitW,v);
        axis[0] = u;
        axis[1] = v;
        axis[2] = w;
    }
}
