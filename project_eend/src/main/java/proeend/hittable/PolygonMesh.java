package proeend.hittable;

import proeend.material.Material;
import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.records.HitRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Een klasse die een verzameling driehoeken vertegenwoordigt als een hittable object.
 */
public class PolygonMesh extends Hittable {
    private Integer[] faceArray;
    private Integer[] vertexIndexArray;
    private Vector[] vertexArray;
    private Material material;
    private BoundingBox boundingBox;

    /**
     * Maakt een nieuw TriangleMesh-object met de opgegeven geometrische en een materiaal.
     *
     * @param faceArray        Een array met het aantal zijden voor elk vlak.
     * @param vertexIndexArray Een array met de indexen van de vertices voor elk vlak.
     * @param vertexArray      Een array met de vertices van het mesh.
     * @param material         Het materiaal van het mesh.
     */
    public PolygonMesh(Integer[] faceArray, Integer[] vertexIndexArray, Vector[] vertexArray, Material material) {
        this.faceArray = faceArray;
        this.vertexIndexArray = vertexIndexArray;
        this.vertexArray = vertexArray;
        this.material = material;
        if(vertexArray.length != 0){
            setBounddingBox();
        }
    }

    public void setBounddingBox() {

        Vector min = vertexArray[0];
        Vector max = vertexArray[0];

        for (Vector vertex : vertexArray) {
            min = Vector.min(min, vertex);
            max = Vector.max(max, vertex);
        }

        boundingBox = new BoundingBox(min, max);
    }

    public void setMaterial(Material material){
        this.material = material;
    }

    @Override
    public BoundingBox getBoundingbox() {
        return boundingBox;
    }

    @Override
    public boolean hit(Ray ray, Interval rayT, HitRecord rec) {

        boolean hitAnything = false;

        double closest = rayT.getMax();

        Triangle triangle;

        int vertexIndex = 0;

        for (int i = 0; i < faceArray.length; i++) {

            Vector vertexIndex0 = vertexArray[vertexIndexArray[vertexIndex]];
            Vector vertexIndex1 = vertexArray[vertexIndexArray[vertexIndex + 1]];
            Vector vertexIndex2 = vertexArray[vertexIndexArray[vertexIndex + 2]];

            triangle = new Triangle(vertexIndex0, vertexIndex1, vertexIndex2, material);

            if (triangle.hit(ray, new Interval(rayT.getMin(),closest), rec)){
                closest = rec.getT();
                hitAnything = true;
            }

            vertexIndex += 3;
        }
        return hitAnything;
    }

    public void translate(Vector translation){
        for (int i = 0; i < vertexArray.length; i++) {
            vertexArray[i] = vertexArray[i].add(translation);
        }
    }

    public void rotate(double angle) {
         Vector center = calculateCenter();

        // Verschuif de vertices zodat het centrum het oorsprongspunt wordt
        for (int i = 0; i < vertexArray.length; i++) {
            vertexArray[i] = vertexArray[i].add(center.invert());
        }

        // Voer de rotatie uit
        double angleInRadians = Math.toRadians(angle);
        double cosTheta = Math.cos(angleInRadians);
        double sinTheta = Math.sin(angleInRadians);

        for (int i = 0; i < vertexArray.length; i++) {
            double x = vertexArray[i].getX();
            double z = vertexArray[i].getZ();
            vertexArray[i] = new Vector(x * cosTheta - z * sinTheta, vertexArray[i].getY(), x * sinTheta + z * cosTheta);
        }

        // Verschuif de vertices terug naar hun oorspronkelijke positie
        for (int i = 0; i < vertexArray.length; i++) {
            vertexArray[i] = vertexArray[i].add(center);
        }
    }

    private Vector calculateCenter() {
        int length = vertexArray.length;
        if (length == 0) {
            return new Vector(0, 0, 0); // Geen centrum als er geen vertices zijn
        }

        double sumX = 0, sumY = 0, sumZ = 0;

        for (Vector vertex : vertexArray) {
            sumX += vertex.getX();
            sumY += vertex.getY();
            sumZ += vertex.getZ();
        }

        return new Vector(sumX / length, sumY / length, sumZ / length);
    }


    /**
     * Zet veelhoekige vlakken om in driehoeken.
     */
    public void ConvertToTriangles(){
        List<Integer> newFaceList = new ArrayList<>();
        List<Integer> newVertexIndexList = new ArrayList<>();
        int i = 0;

        for (Integer face: faceArray){
            if (face == 3){
                addFirstThreeFaces(newFaceList, newVertexIndexList, i);
                i += 3;
            } if (face == 4){
                newFaceList.add(3);
                addFirstThreeFaces(newFaceList, newVertexIndexList, i);
                newVertexIndexList.add(vertexIndexArray[i]);
                newVertexIndexList.add(vertexIndexArray[i+2]);
                newVertexIndexList.add(vertexIndexArray[i+3]);
                i +=4;
            }
        }

        this.faceArray = newFaceList.toArray(new Integer[0]);
        this.vertexIndexArray = newVertexIndexList.toArray(new Integer[0]);
    }

    private void addFirstThreeFaces(List<Integer> newFaceList, List<Integer> newVertexIndexList, int i) {
        newFaceList.add(3);
        newVertexIndexList.add(vertexIndexArray[i]);
        newVertexIndexList.add(vertexIndexArray[i +1]);
        newVertexIndexList.add(vertexIndexArray[i +2]);
    }



}
