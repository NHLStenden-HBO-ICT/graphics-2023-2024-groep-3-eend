package classes;

public class VectorOld {
    private final double[] coordinates;

    /**
     * Constructor for a standard vector, has no length.
     */
    public VectorOld(){
        this.coordinates = new double[]{0.0, 0.0, 0.0};
    }

    /**
     * Constructor for two-dimensional vector.
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public VectorOld(double x, double y){
        this.coordinates = new double[]{x,y, 0.0};
    }

    /**
     * Constructor for three-dimensional vector.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     * @param z The z-coordinate.
     */
    public VectorOld(double x, double y, double z){
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
    public VectorOld inverse(){
        return new VectorOld(-coordinates[0],-coordinates[1],-coordinates[2]);
    }

    /**
     * Add second vector to first vector.
     * @param vectorA is the first vector.
     * @param vectorB is the second vector to add.
     * @return the result of the vector addition.
     */
    public static VectorOld add(VectorOld vectorA, VectorOld vectorB) {
        double x = vectorA.coordinates[0] + vectorB.coordinates[0];
        double y = vectorA.coordinates[1] + vectorB.coordinates[1];
        double z = vectorA.coordinates[2] + vectorB.coordinates[2];
        return new VectorOld(x, y, z);
    }

    /**
     * Subtract second vector to first vector.
     * @param vectorA is the first vector.
     * @param vectorB is the second vector to subtract.
     * @return the result of the vector subtraction.
     */
    public static VectorOld subtract(VectorOld vectorA, VectorOld vectorB) {
        double x = vectorA.coordinates[0] - vectorB.coordinates[0];
        double y = vectorA.coordinates[1] - vectorB.coordinates[1];
        double z = vectorA.coordinates[2] - vectorB.coordinates[2];
        return new VectorOld(x, y, z);
    }

    /**
     * Scale the vector by a scalar factor.
     * @param scalar is the scalar factor.
     * @return the scaled vector.
     */
    public VectorOld scale(double scalar) {
        double x = coordinates[0] * scalar;
        double y = coordinates[1] * scalar;
        double z = coordinates[2] * scalar;
        return new VectorOld(x, y, z);
    }

    /**
     * Calculate dot product of two vectors.
     * @param vectorA is vector A.
     * @param vectorB is vector B.
     * @return result of dot product calculation.
     */

    public static double GetDotProduct(VectorOld vectorA, VectorOld vectorB) {
        double result = 0.0;
        double[] coordsA = vectorA.coordinates;
        double[] coordsB = vectorB.coordinates;

        for (int i = 0; i < coordsA.length; i++) {
            result += coordsA[i] * coordsB[i];
        }

        return result;
    }

    /**
     * Calculate cross product of two vectors.
     * @param vectorA is first vector.
     * @param vectorB is second vector.
     * @return cross product.
     */
    public static VectorOld GetCrossProduct(VectorOld vectorA, VectorOld vectorB) {
        double[] coordsA = vectorA.coordinates;
        double[] coordsB = vectorB.coordinates;

        return new VectorOld(
                coordsA[1] * coordsB[2] - coordsA[2] * coordsB[1],
                coordsA[2] * coordsB[0] - coordsA[0] * coordsB[2],
                coordsA[0] * coordsB[1] - coordsA[1] * coordsB[0]
        );
    }

    /**
     * Calculate length of the vector.
     * @return length of the vector.
     */
    public double GetLength(){
        double result = 0.0;
        for (int i = 0; i < coordinates.length; i++){
            result += Math.pow(coordinates[i], 2);
        }
        return Math.sqrt(result);
    }



}
