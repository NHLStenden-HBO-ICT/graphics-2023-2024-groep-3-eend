package proeend.hittable;

import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;

public class BoundingBox {
    Interval x, y, z;

    /**
     * Initialiseer een bounding box met twee vectoren als extrema.
     * @param a Een vector die een extreem van de bounding box vertegenwoordigt.
     * @param b Een vector die het andere extreem van de bounding box vertegenwoordigt.
     */
    public BoundingBox(Vector a, Vector b) {
        initializeBoundingBox(Vector.min(a,b), Vector.max(a,b));
    }


    /**
     * Initialiseer een bounding box rond een bol met het opgegeven middelpunt en straal.
     * @param center Het middelpunt van de bol.
     * @param radius De straal van de bol.
     */
    public BoundingBox(Vector center, double radius) {
        Vector min = new Vector(
                center.getX() - radius,
                center.getY() - radius,
                center.getZ() - radius
        );

        Vector max = new Vector(
                center.getX() + radius,
                center.getY() + radius,
                center.getZ() + radius
        );

        initializeBoundingBox(min, max);
    }

    /**
     * Maakt de bounding box op basis van twee extremen.
     * @param min Minimale uiterste van de bounding box.
     * @param max Maximale uiterste van de bounding box.
     */
    private void initializeBoundingBox(Vector min, Vector max) {
        x = new Interval(min.getX(), max.getX());
        y = new Interval(min.getY(), max.getY());
        z = new Interval(min.getZ(), max.getZ());
    }

    /**
     * Initialiseer een bounding box door twee bestaande bounding boxen te combineren.
     * @param boxA De eerste bounding box om te combineren.
     * @param boxB De tweede bounding box om te combineren.
     */
    public BoundingBox(BoundingBox boxA, BoundingBox boxB) {
        if (boxA != null && boxB != null) {
            x = new Interval().merge(boxA.x, boxB.x);
            y = new Interval().merge(boxA.y, boxB.y);
            z = new Interval().merge(boxA.z, boxB.z);
        }
    }
    /**
     * Initialiseer een bounding box met gegeven intervallen langs de x-, y- en z-assen.
     * @param ix Het interval langs de x-as.
     * @param iy Het interval langs de y-as.
     * @param iz Het interval langs de z-as.
     */
    public BoundingBox(Interval ix, Interval iy, Interval iz) {
        this.x = ix;
        this.y = iy;
        this.z = iz;
    }
    /**
     * Geeft het interval langs de opgegeven as.
     * @param n De index van de as (0 voor x, 1 voor y, 2 voor z).
     * @return Het interval langs de opgegeven as.
     */
     public Interval axis(int n) {
        if (n == 1) return y;
        if (n == 2) return z;
        return x;
    }
    /**
     * Vergroot de bounding box met een kleine marge om afrondingsfouten te voorkomen.
     * @return Een bounding box met een kleine marge.
     */
    public BoundingBox pad() {
        double delta = 0.0001;
        Interval[] intervals = {x, y, z};

        for (int i = 0; i < intervals.length; i++) {
            Interval current = intervals[i];
            intervals[i] = (current.getSize() >= delta) ? current : current.expand(delta);
        }

        return new BoundingBox(intervals[0], intervals[1], intervals[2]);
    }


    /**
     * Controleert of een lichtstraal de bounding box raakt.
     * @param ray De lichtstraal.
     * @param rayT Het interval waarbinnen hits met objecten worden gecontroleerd.
     * @return True als de lichtstraal de bounding box raakt, anders false.
     **/
    public boolean hit(Ray ray, Interval rayT) {

        for (int axis = 0; axis < 3; axis++) {

            double invD = 1.0 / ray.getDirection().axis(axis);
            double origin = ray.origin().axis(axis);

            double t0 = (axis(axis).getMin() - origin) * invD;
            double t1 = (axis(axis).getMax() - origin) * invD;

            if (invD < 0.0) {
                double temp = t0;
                t0 = t1;
                t1 = temp;
            }

            if(t0 > rayT.getMin()) rayT.setMin(t0);
            if(t1 < rayT.getMax()) rayT.setMax(t1);

            if(rayT.getMax() <= rayT.getMin()) return false;
        }
        return true;
    }
}



