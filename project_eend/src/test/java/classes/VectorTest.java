package classes;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorTest {

    @Test
    void getX_expectX() {
        double expected = 1.4;
        VectorOld vector = new VectorOld(1.4,2.6,-3.5);
        double actual = vector.getX();
        assertEquals(expected, actual);

    }

    @Test
    void getY_expectY() {
        double expected = 3.5;
        VectorOld vector = new VectorOld(2.2,3.5,6.3);
        double actual = vector.getY();
        assertEquals(expected, actual);

    }

    @Test
    void getZ_expectZ() {
        double expected = 3.5;
        VectorOld vector = new VectorOld(2.2,3.5,6.3);
        double actual = vector.getY();
        assertEquals(expected, actual);
    }

    @Test
    void getCoordinates_expectAllCoordinates() {
        double[] expected = {0.1, 2.4, -1.5};
        VectorOld vector = new VectorOld(0.1, 2.4, -1.5);
        double[] actual = vector.getCoordinates();
        assertArrayEquals(expected, actual);

    }

    @Test
    void inverse_expectInverse() {
       VectorOld vector = new VectorOld(3,4,5);
       VectorOld expected = new VectorOld(-3,-4,-5);
       VectorOld actual = vector.inverse();
       assertArrayEquals(expected.getCoordinates(), actual.getCoordinates());
    }

    @Test
    void add_expectSum() {
        VectorOld vectorA = new VectorOld(1,2,3);
        VectorOld vectorB = new VectorOld(-3.5,-4.4,-3.5);
        VectorOld expected = new VectorOld(-2.5, -2.4, -0.5);
        VectorOld actual = VectorOld.add(vectorA, vectorB);
        assertArrayEquals(expected.getCoordinates(), actual.getCoordinates(), 0.001);

    }

    @Test
    void subtract_expectDifference() {
        VectorOld vectorA = new VectorOld(5.4,2.3,-3.4);
        VectorOld vectorB = new VectorOld(-54.3,-10.3,4.5);
        VectorOld expected = new VectorOld(59.7, 12.6, -7.9);
        VectorOld actual = VectorOld.subtract(vectorA, vectorB);
        assertArrayEquals(expected.getCoordinates(), actual.getCoordinates(), 0.001);
    }

    @Test
    void scale_expectScaled() {
        VectorOld vector = new VectorOld(5.4,2.3,-3.4);
        VectorOld expected = new VectorOld(8.1, 3.45, -4.6);
        VectorOld actual = vector.scale(1.5);
        assertArrayEquals(expected.getCoordinates(), actual.getCoordinates(), 0.001);
    }

    @Test
    void getDotProduct_expectDotProduct() {
        VectorOld vectorA = new VectorOld(5,4,13);
        VectorOld vectorB = new VectorOld(2,5,10);
        double[] coordsA = vectorA.getCoordinates();
        double[] coordsB = vectorB.getCoordinates();
        double expected = 160;
        double actual = VectorOld.GetDotProduct(vectorA, vectorB);
        assertEquals(expected, actual);
    }

    @Test
    void getCrossProduct_expectCrossProduct() {
        VectorOld vectorA = new VectorOld(3, -1, 2);
        VectorOld vectorB = new VectorOld(0, 2, -3);
        VectorOld expected = new VectorOld(-1, 9, 6);
        VectorOld actual = VectorOld.GetCrossProduct(vectorA, vectorB);
        assertArrayEquals(expected.getCoordinates(), actual.getCoordinates());

    }

    @Test
    void getLength_expectLength() {
        double expected = 7.43;
        VectorOld vector = new VectorOld(3, 4, -5.5);
        double actual = vector.GetLength();
        assertEquals(expected, actual, 0.01);
    }
}
