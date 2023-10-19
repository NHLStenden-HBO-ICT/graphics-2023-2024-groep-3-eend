package proeend.math;
/**
 * De Ray-klasse vertegenwoordigt een straal in de ruimte, gedefinieerd door een oorsprong (origin) en een richting (direction).
 */
public class Ray {

    private Vector origin;
    private Vector direction;

    /**
     * Constructor voor het maken van een straal met opgegeven oorsprong en richting.
     * @param origin    De oorsprong van de straal.
     * @param direction De richting van de straal.
     */
    public Ray(Vector origin, Vector direction) {
        this.origin = origin;
        this.direction = direction;
    }

    /**
     * Geeft de oorsprong van de straal terug.
     * @return De oorsprong van de straal.
     */
    public Vector origin() {
        return origin;
    }

    public Vector getDirection() {
        return direction;
    }

    /**
     * Geeft de vector terug die de straal bereikt op een bepaalde waarde van t.
     * @param t De waarde van t waarmee de positie langs de straal wordt berekend.
     * @return De vector die de straal bereikt op de opgegeven waarde van t.
     */
    public Vector at(double t) {
        return Vector.add(origin, Vector.scale(t, direction));
    }

    /**
     * Update de straal aan de hand van de herkomst en richting.
     * @param origin De herkomst van de straal.
     * @param direction De richting die de straal heeft.
     */
    public void updateRay(Vector origin, Vector direction){
        this.origin = origin;
        this.direction = direction;
    }
}
