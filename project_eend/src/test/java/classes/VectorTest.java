package classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @Test
    void getX_expectX() {
        double expected = 1.4;
        Vector vector = new Vector(1.4,2.6,-3.5);
        double actual = vector.getX();
        assertEquals(expected, actual);

    }

    @Test
    void getY_expectY() {
        double expected = 3.5;
        Vector vector = new Vector(2.2,3.5,6.3);
        double actual = vector.getY();
        assertEquals(expected, actual);

    }

    @Test
    void getZ_expectZ() {
        double expected = 3.5;
        Vector vector = new Vector(2.2,3.5,6.3);
        double actual = vector.getY();
        assertEquals(expected, actual);
    }

    @Test
    void getCoordinates_expectAllCoordinates() {
        double[] expected = {0.1, 2.4, -1.5};
        Vector vector = new Vector(0.1, 2.4, -1.5);
        double[] actual = vector.getCoordinates();
        assertArrayEquals(expected, actual);

    }

    @Test
    void inverse_expectInverse() {
       Vector vector = new Vector(3,4,5);
       Vector expected = new Vector(-3,-4,-5);
       Vector actual = vector.inverse();
       assertArrayEquals(expected.getCoordinates(), actual.getCoordinates());
    }

    @Test
    void add_expectSum() {
        Vector vectorA = new Vector(1,2,3);
        Vector vectorB = new Vector(-3.5,-4.4,-3.5);
        Vector expected = new Vector(-2.5, -2.4, -0.5);
        Vector actual = Vector.add(vectorA, vectorB);
        assertArrayEquals(expected.getCoordinates(), actual.getCoordinates(), 0.001);

    }

    @Test
    void subtract_expectDifference() {
        Vector vectorA = new Vector(5.4,2.3,-3.4);
        Vector vectorB = new Vector(-54.3,-10.3,4.5);
        Vector expected = new Vector(59.7, 12.6, -7.9);
        Vector actual = Vector.subtract(vectorA, vectorB);
        assertArrayEquals(expected.getCoordinates(), actual.getCoordinates(), 0.001);
    }

    @Test
    void scale_expectScaled() {
        Vector vector = new Vector(5.4,2.3,-3.4);
        Vector expected = new Vector(8.1, 3.45, -4.6);
        Vector actual = vector.scale(1.5);
        assertArrayEquals(expected.getCoordinates(), actual.getCoordinates(), 0.001);
    }

    @Test
    void getDotProduct_expectDotProduct() {
        Vector vectorA = new Vector(5,4,13);
        Vector vectorB = new Vector(2,5,10);
        double[] coordsA = vectorA.getCoordinates();
        double[] coordsB = vectorB.getCoordinates();
        double expected = 160;
        double actual = Vector.GetDotProduct(vectorA, vectorB);
        assertEquals(expected, actual);
    }

    @Test
    void getCrossProduct_expectCrossProduct() {
        Vector vectorA = new Vector(3, -1, 2);
        Vector vectorB = new Vector(0, 2, -3);
        Vector expected = new Vector(-1, 9, 6);
        Vector actual = Vector.GetCrossProduct(vectorA, vectorB);
        assertArrayEquals(expected.getCoordinates(), actual.getCoordinates());

    }

    @Test
    void getLength_expectLength() {
        double expected = 7.43;
        Vector vector = new Vector(3, 4, -5.5);
        double actual = vector.GetLenght();
        assertEquals(expected, actual, 0.01);
    }
}
