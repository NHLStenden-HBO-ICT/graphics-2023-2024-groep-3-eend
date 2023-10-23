package proeend.math;

public class Vector {
    private double[] coordinates;

    // constanten x, y en z geven de x, y en z coördinaten aan bij het opvragen ervan.
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
     * Initialiseert een nieuwe vector met standaard coördinaten (0, 0, 0).
     */

    public Vector(){
        this.coordinates = new double[]{0,0,0};
    }
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

    private void set(double x, double y, double z){
        this.coordinates[0]= x;
        this.coordinates[1]= y;
        this.coordinates[2]= z;
    }
/*    *//**
     * Initialiseert een nieuwe vector met de opgegeven coördinaten.
     * @param x De x-coördinaat.
     * @param y De y-coördinaat.
     * @param z De z-coördinaat.
     *//*
    public Vector(double x, double y, double z) {
        coordinates = new double[]{x, y, z};
    }*/

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
        return add(vec, negate(scale(2.0*dot(vec,normal),normal)));
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

    public double[] getCoordinates(){
        return coordinates;
    }

    public void copy(Vector copy) {
        this.coordinates = copy.coordinates;
    }

    /**
     * Berekent de inverse.
     */
    public void invert(){
        Vector vec = scale(-1, this);
        this.coordinates[0]= vec.getX();
        this.coordinates[1]= vec.getY();
        this.coordinates[2]= vec.getZ();
    }

    /**
     * Roteert de vector om de Z-as met de opgegeven hoek.
     * @param angle De rotatiehoek in radialen
     */
    public void rotateZ(double angle) {
        double cosA = Math.cos(angle);
        double sinA = Math.sin(angle);
        double newX = cosA * coordinates[x] - sinA * coordinates[y];
        double newY = sinA * coordinates[x] + cosA * coordinates[y];
        coordinates[x] = newX;
        coordinates[y] = newY;
    }

    /**
     * Roteert de vector om de Y-as met de opgegeven hoek.
     * @param angle De rotatiehoek in radialen.
     */
    public Vector rotateY(double angle) {
        double cosA = Math.cos(angle);
        double sinA = Math.sin(angle);
        double newX = cosA * coordinates[x] + sinA * coordinates[z];
        double newZ = -sinA * coordinates[x] + cosA * coordinates[z];
        coordinates[x] = newX;
        coordinates[z] = newZ;

        return this;
    }

    /**
     * Maakt de vector negatief.
     * @param vec
     * @return
     */
    public static Vector negate(Vector vec) {
        return new Vector(-vec.getX(),-vec.getY(),-vec.getZ());
    }

    /**
     * Telt twee vectoren bij elkaar op.
     * @param vecA De eerste vector.
     * @param vecB De tweede vector.
     * @return Het resultaat van de optelling.
     */
    public static Vector add(Vector vecA, Vector vecB) {
        return new Vector(vecA.getX() + vecB.getX(), vecA.getY() + vecB.getY(), vecA.getZ() + vecB.getZ());
    }

    public Vector subtract(Vector vectorToSubtract){
        coordinates[x] -= vectorToSubtract.coordinates[x];
        coordinates[y] -= vectorToSubtract.coordinates[y];
        coordinates[z] -= vectorToSubtract.coordinates[x];
        return this;
    }




    /**
     * Schaalt de opgegeven vector met een scalaire waarde.
     * @param scalar De scalaire waarde waarmee de vector wordt geschaald.
     * @param scaled De vector die wordt geschaald.
     * @return Het geschaalde resultaat.
     */
    public static Vector scale(double scalar, Vector scaled) {
        return new Vector(scaled.getX() * scalar, scaled.getY() * scalar, scaled.getZ() * scalar);
    }

    public Vector scale(double scalar) {
        coordinates[x] *= scalar;
        coordinates[y] *= scalar;
        coordinates[z] *= scalar;
        return this;
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
        return (vec.getX() * vec.getX() + vec.getY() * vec.getY() + vec.getZ() * vec.getZ());
    }

    /**
     * Berekent de lengte van de vector.
     * @param vec De vector waarvan de lengte wordt berekend.
     * @return De lengte van de vector.
     */
    public static double length(Vector vec) {
        return Math.sqrt(lengthSquared(vec));
    }

    /**
     * Berekent de eenheidsvector (een vector met lengte 1) van de opgegeven vector.
     * @param vec De vector waarvan de eenheidsvector wordt berekend.
     * @return De eenheidsvector van de vector.
     */
    public static Vector unitVector(Vector vec) {
        return scale((1.0 / length(vec)), vec);
    }

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
        return (dot(p, normal) > 0.0) ? p : negate(p);
    }

    /**
     * Genereert een willekeurige eenheidsvector op de eenheidssfeer.
     * @return Een willekeurige eenheidsvector op de eenheidssfeer.
     */
    public static Vector randomOnUnitSphere() {
        return randomInUnitSphere().toUnitVector();
    }

    /**
     * Bereken de vector met minimale waarden voor elke as tussen twee vectoren.
     * @param vectorA de eerste vector
     * @param vectorB de tweede vector
     * @return Een vector met de minimale waarden van elke as.
     */
    public static Vector min(Vector vectorA, Vector vectorB) {
        double x = Math.min(vectorA.getX(), vectorB.getX());
        double y = Math.min(vectorA.getY(), vectorB.getY());
        double z = Math.min(vectorA.getZ(), vectorB.getZ());
        return new Vector(x, y, z);
    }

    /**
     * Bepaal de vector met maximale waarden voor elke as tussen twee vectoren.
     * @param vector1 De eerste vector.
     * @param vector2 De tweede vector.
     * @return Een nieuwe vector met de maximale waarden van elke as.
     */
    public static Vector max(Vector vector1, Vector vector2) {
        double x = Math.max(vector1.getX(), vector2.getX());
        double y = Math.max(vector1.getY(), vector2.getY());
        double z = Math.max(vector1.getZ(), vector2.getZ());
        return new Vector(x, y, z);
    }
}