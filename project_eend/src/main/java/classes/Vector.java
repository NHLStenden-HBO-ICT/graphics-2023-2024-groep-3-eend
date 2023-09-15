package classes;

public class Vector {
    private final double[] Coordinates;

    /**
     * Constructor for a standard vector, has no length.
     */
    public Vector(){
        this.Coordinates = new double[]{0.0, 0.0, 0.0};
    }

    /**
     * Constructor for two-dimensional vector.
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public Vector(double x, double y){
        this.Coordinates = new double[]{x,y, 0.0};
    }

    /**
     * Constructor for three-dimensional vector.
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @param z The z-coordinate
     */
    public Vector(double x, double y, double z){
        this.Coordinates = new double[]{x,y,z};
    }

    /**
     * Get X-coordinate from vector.
     * @return X-coordinate from vector
     */
    public double getX() {return Coordinates[0];}
    /**
     * Get Y-coordinate from vector.
     * @return Y-coordinate from vector
     */
    public double getY() {return Coordinates[1];}
    /**
     * Get Z-coordinate from vector.
     * @return Z-coordinate from vector
     */
    public double getZ() {return Coordinates[2];}

    /**
     * Get all coordinates from vector.
     * @return all coordinates from vector
     */
    public double[] getCoordinates(){
        return Coordinates;
    }

    /**
     * Inverse the vector.
     * @return Inversed coordinates from vector.
     */
    public classes.Vector inverse(){
        return new Vector(-Coordinates[0],-Coordinates[1],-Coordinates[2]);
    }
}
