package classes;

public class Vector {
    private final double[] coordinates;

    /**
     * Constructor for a standard vector, has no length.
     */
    public Vector(){
        this.coordinates = new double[]{0.0, 0.0, 0.0};
    }

    /**
     * Constructor for two-dimensional vector.
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public Vector(double x, double y){
        this.coordinates = new double[]{x,y, 0.0};
    }

    /**
     * Constructor for three-dimensional vector.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param z The z-coordinate.
     */
    public Vector(double x, double y, double z){
        this.coordinates = new double[]{x,y,z};
    }

    /**
     * Get X-coordinate from vector.
     * @return X-coordinate from vector.
     */
    public double getX() {return coordinates[0];}
    /**
     * Get Y-coordinate from vector.
     * @return Y-coordinate from vector.
     */
    public double getY() {return coordinates[1];}
    /**
     * Get Z-coordinate from vector.
     * @return Z-coordinate from vector.
     */
    public double getZ() {return coordinates[2];}

    /**
     * Get all coordinates from vector.
     * @return all coordinates from vector.
     */
    public double[] getCoordinates(){
        return coordinates;
    }

    /**
     * Inverse the vector.
     * @return Inversed coordinates from vector.
     */
    public classes.Vector inverse(){
        return new Vector(-coordinates[0],-coordinates[1],-coordinates[2]);
    }

    /**
     * Add first vector to second vector.
     * @param secondVector is the second vector.
     * @return the result of the vector addition.
     */
    public Vector add(Vector secondVector) {
        double x = this.coordinates[0] + secondVector.coordinates[0];
        double y = this.coordinates[1] + secondVector.coordinates[1];
        double z = this.coordinates[2] + secondVector.coordinates[2];
        return new Vector(x, y, z);
    }

    /**
     * Subtract second vector from first vector.
     * @param secondVector is the second vector.
     * @return the result of the vector subtraction.
     */
    public Vector subtract(Vector secondVector) {
        double x = this.coordinates[0] - secondVector.coordinates[0];
        double y = this.coordinates[1] - secondVector.coordinates[1];
        double z = this.coordinates[2] - secondVector.coordinates[2];
        return new Vector(x, y, z);
    }

    /**
     * Scale the vector by a scalar factor.
     * @param scalar is the scalar factor.
     * @return the scaled vector.
     */
    public Vector scale(double scalar) {
        double x = coordinates[0] * scalar;
        double y = coordinates[1] * scalar;
        double z = coordinates[2] * scalar;
        return new Vector(x, y, z);
    }

    //dotproductz
    public static double dot(Vec vector1, Vec vector2) {
        return (vector1.x()*vector2.x()+vector1.y()*vector2.y()+vector1.z()*vector2.z());
    }
    //TODO crossproduct
    public static Vec cross(Vec vector1, Vec vector2) {
        return new Vec();
    }

}
