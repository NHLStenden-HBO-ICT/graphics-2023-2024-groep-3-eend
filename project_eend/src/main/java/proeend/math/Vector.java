package proeend.math;
/**
 * De `Vector` klasse representeert een driedimensionale vector in de ruimte.
 */
public class Vector {
    private double[] coordinates;

    // constanten x, y en z representeren de x, y en z coördinaten.
    private final int x = 0, y = 1, z = 2;

    public double getX() {
        return coordinates[x];
    }

    public double getY() {
        return coordinates[y];
    }

    public double getZ() {
        return coordinates[z];
    }

    private static final int POOL_SIZE = 200;
    private static final Vector[] vectorPool = new Vector[POOL_SIZE];
    private static int poolIndex = 0;

    /**
     * Construeert een nieuwe vector met standaard coördinaten (0, 0, 0).
     */
    public Vector(){
        this.coordinates = new double[]{0,0,0};
    }
    /**
     * Construeert een nieuwe vector met opgegeven coördinaten.
     *
     * @param x De x-coördinaat van de vector.
     * @param y De y-coördinaat van de vector.
     * @param z De z-coördinaat van de vector.
     */
    public Vector(double x, double y, double z) {
        if (poolIndex < vectorPool.length) {
            Vector vector = vectorPool[poolIndex];
            if (vector == null) {
                this.coordinates = new double[]{x, y, z};
                vectorPool[poolIndex] = this;
            } else {
                vector.set(x, y, z);
            }
            poolIndex++;
        } else {
            this.coordinates = new double[]{x, y, z};
        }
    }

    /**
     * Stel de coördinaten in van de vector
     * @param x-coördinaat
     * @param y-coördinaat
     * @param z-coördinaat
     */
    private void set(double x, double y, double z){
        this.coordinates[0]= x;
        this.coordinates[1]= y;
        this.coordinates[2]= z;
    }

    /**
     * Vermenigvuldigt twee vectoren.
     * @param vecA De eerste vector.
     * @param vecB De tweede vector.
     * @return Het resultaat van de vermenigvuldiging.
     */
    public static Vector multiply(Vector vecA, Vector vecB) {
        return new Vector(vecA.getX()* vecB.getX(), vecA.getY()* vecB.getY(),vecA.getZ()* vecB.getZ());
    }

    /**
     * Reflecteert een vector ten opzichte van de normaal vector.
     * @param vec    De oorspronkelijke vector.
     * @param normal De normaal vector.
     * @return Het gereflecteerde vector.
     */
    public static Vector reflect(Vector vec, Vector normal) {
        return vec.add(normal.scale(2.0*dot(vec,normal)).invert());
    }

    /**
     * Zet 0, 1 of 2 om naar x, y of z.
     * @param n Getal dat omgezet wordt naar een as.
     * @return De as.
     */
    public double axis(int n) {
        if (n == 1) return coordinates[y];
        if (n == 2) return coordinates[z];
        return coordinates[x];
    }

    /**
     * Roteer de camera om de X as.
     * @param angle de hoek waarom de camera draait.
     * @return de nieuwe vector waar de camera langs kijkt.
     */
    public Vector rotateX(double angle) {
        return rotate(angle, 1, 2);
    }
    /**
     * Rooter de camera om de Z as.
     * @param angle de hoek waarom de camera draait.
     * @return de nieuwe vector waar de camera langs kijkt.
     */
    public Vector rotateZ(double angle) {
        return rotate(angle, 0, 1);
    }

    /**
     * Roteer de camera om de Y as.
     * @param angle de hoek waarom de camera draait.
     * @return de nieuwe vector waar de camera langs kijkt.
     */
    public Vector rotateY(double angle) {
        return rotate(angle, 0, 2);
    }

    /**
     * Roteert de huidige vector met de opgegeven hoek (in radialen) rond de opgegeven assen in het 3D-ruimte.
     *
     * @param angle  De rotatiehoek in radialen.
     * @param axis1  De eerste as (0, 1 of 2) rond welke wordt geroteerd.
     * @param axis2  De tweede as (0, 1 of 2) rond welke wordt geroteerd.
     * @return De huidige vector na de rotatie.
     */
    private Vector rotate(double angle, int axis1, int axis2) {
        double cosA = Math.cos(angle);
        double sinA = Math.sin(angle);
        double axis1Value = coordinates[axis1];
        double axis2Value = coordinates[axis2];

        coordinates[axis1] = cosA * axis1Value - sinA * axis2Value;
        coordinates[axis2] = sinA * axis1Value + cosA * axis2Value;

        return this;
    }

    /**
     * Creëert een nieuwe vector die het negatief van de huidige vector voorstelt.
     * De negatieve vector heeft dezelfde lengte als de oorspronkelijke vector, maar wijst in de tegenovergestelde richting.
     *
     * @return Het negatief van de huidige vector.
     */
    public Vector invert(){
        return new Vector(-this.getX(),-this.getY(),-this.getZ());
    }

    /**
     * Voegt de huidige vector samen met een andere vector door hun overeenkomstige coördinaten op te tellen.
     *
     * @param vec De vector waarmee de huidige vector wordt opgeteld.
     * @return Een nieuwe vector die het resultaat is van de optelling.
     */
    public Vector add(Vector vec) {
        return new Vector(this.getX() + vec.getX(), this.getY() + vec.getY(), this.getZ() + vec.getZ());
    }
    /**
     * Schaalt de opgegeven vector met een scalaire waarde.
     * @param scalar De scalaire waarde waarmee de vector wordt geschaald.
     * @return Het geschaalde resultaat.
     */
    public Vector scale(double scalar) {
        return new Vector(this.getX() * scalar, this.getY() * scalar, this.getZ() * scalar);
    }

    /**
     * Berekent het inwendig product (dotproduct) van twee vectoren.
     * @param vecA De eerste vector.
     * @param vecB De tweede vector.
     * @return Het inwendig product van de twee vectoren.
     */
    public static double dot(Vector vecA, Vector vecB) {
        return (vecA.getX() * vecB.getX() + vecA.getY() * vecB.getY() + vecA.getZ() * vecB.getZ());
    }

    /**
     * Berekent het kruisproduct van twee vectoren.
     * @param vecA De eerste vector.
     * @param vecB De tweede vector.
     * @return Het kruisproduct van de twee vectoren.
     */
    public static Vector cross(Vector vecA, Vector vecB) {
        return new Vector(
                vecA.getY() * vecB.getZ() - vecA.getZ() * vecB.getY(),
                vecA.getZ() * vecB.getX() - vecA.getX() * vecB.getZ(),
                vecA.getX() * vecB.getY() - vecA.getY() * vecB.getX()
        );
    }

    /**
     * Berekent het kwadraat van de lengte van de vector.
     * @param vec De vector waarvan het kwadraat van de lengte wordt berekend.
     * @return Het kwadraat van de lengte van de vector.
     */
    public static double lengthSquared(Vector vec) {
        return dot(vec, vec);
    }

    /**
     * Berekent de lengte van de vector.
     * @param vec De vector waarvan de lengte wordt berekend.
     * @return De lengte van de vector.
     */
    public static double length(Vector vec) {
        return Math.sqrt(dot(vec, vec));
    }

    /**
     * Berekent de eenheidsvector (een vector met lengte 1) van de opgegeven vector.
     * @param vec De vector waarvan de eenheidsvector wordt berekend.
     * @return De eenheidsvector van de vector.
     */
    public static Vector unitVector(Vector vec) {
        return vec.scale((1.0 / length(vec)));
    }

    /**
     * Converteert de huidige vector naar een eenheidsvector.
     * Als de huidige vector een nulvector is, blijft deze ongewijzigd.
     *
     * @return De eenheidsvector die wordt gerepresenteerd door de huidige vector.
     */
    public Vector toUnitVector() {
        double length = length(this);
        if (length != 0) {
            coordinates[x] /= length;
            coordinates[y] /= length;
            coordinates[z] /= length;
        }
        return this;
    }

    /**
     * Genereert een willekeurige vector met waarden tussen 0 en 1.
     * @return Een willekeurige vector.
     */
    public static Vector randomVec() {
        return new Vector(FastRandom.random(), FastRandom.random(), FastRandom.random());
    }

    /**
     * Genereert een willekeurige vector met waarden tussen het opgegeven minimum en maximum.
     * @param min Het minimum voor de willekeurige vectorwaarden.
     * @param max Het maximum voor de willekeurige vectorwaarden.
     * @return Een willekeurige vector binnen het opgegeven bereik.
     */
    public static Vector randomVec(double min, double max) {
        return new Vector(
                min + FastRandom.random() * (max - min),
                min + FastRandom.random() * (max - min),
                min + FastRandom.random() * (max - min));
    }

    /**
     * Genereert een willekeurige vector binnen de eenheidssfeer.
     *
     * @return Een willekeurige vector binnen de eenheidssfeer.
     */
    public static Vector randomInUnitSphere() {
        Vector vec;

        do {
            vec = randomVec(-1, 1);
        } while (lengthSquared(vec) >= 1);

        return vec.toUnitVector();
    }

    /**
     * Genereert een willekeurige eenheidsvector op het halfrond ten opzichte van de opgegeven normaalvector.
     * @param normal De normaalvector.
     * @return Een willekeurige eenheidsvector op het halfrond.
     */
    public static Vector RandomUnitVecOnHemisphere(Vector normal) {
        Vector p = randomOnUnitSphere();
        return (dot(p, normal) > 0.0) ? p : p.invert();
    }

    /**
     * Genereert een willekeurige eenheidsvector op de eenheidssfeer.
     * @return Een willekeurige eenheidsvector op de eenheidssfeer.
     */
    public static Vector randomOnUnitSphere() {
        return randomInUnitSphere().toUnitVector();
    }

    /**
     * Berekent de vector met minimale waarden voor elke as tussen twee vectoren.
     * @param vecA de eerste vector
     * @param vecB de tweede vector
     * @return Een vector met de minimale waarden van elke as.
     */
    public static Vector min(Vector vecA, Vector vecB) {
        double x = Math.min(vecA.getX(), vecB.getX());
        double y = Math.min(vecA.getY(), vecB.getY());
        double z = Math.min(vecA.getZ(), vecB.getZ());
        return new Vector(x, y, z);
    }

    /**
     * Bepaalt de vector met maximale waarden voor elke as tussen twee vectoren.
     * @param vecA De eerste vector.
     * @param vecB De tweede vector.
     * @return Een nieuwe vector met de maximale waarden van elke as.
     */
    public static Vector max(Vector vecA, Vector vecB) {
        double x = Math.max(vecA.getX(), vecB.getX());
        double y = Math.max(vecA.getY(), vecB.getY());
        double z = Math.max(vecA.getZ(), vecB.getZ());
        return new Vector(x, y, z);
    }
}