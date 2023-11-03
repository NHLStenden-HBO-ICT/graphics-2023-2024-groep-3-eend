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
    private final boolean isObj = true;
    private final BoundingBox boundingBox;
    private Integer[] faceArray;
    private Integer[] vertexIndexArray;
    private Vector[] vertexArray;
    private final Material material;

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
        this.boundingBox = getBoundingbox();
    }

    @Override
    public boolean hit(Ray ray, Interval rayT, HitRecord rec) {
        //TODO Vreemd effect met meerdere kruisende objecten oplossen.
        boolean tempHit = false;
        double closest = rayT.getMax();
        int j = 0;
        Vector v0,v1,v2;
        for (int i = 0; i < faceArray.length; i++) {
            v0 = vertexArray[vertexIndexArray[j]];
            v1 = vertexArray[vertexIndexArray[j+1]];
            v2 = vertexArray[vertexIndexArray[j+2]];

            if (Triangle.MThit(ray, new Interval(rayT.getMin(),closest), rec, v0 ,v1, v2, material)){
                closest = rec.t;
                tempHit = true;
            }

            j += 3;
        }
        return tempHit;
    }

    public void translate(Vector translation){
        Vector[] newVertexArray = vertexArray;
        int i = 0;

        for (Vector vertex : vertexArray) {
            newVertexArray[i] = vertex.add(translation);
            i++;
        }

        vertexArray = newVertexArray;

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
        System.out.println("Converted to triangles");
    }

    private void addFirstThreeFaces(List<Integer> newFaceList, List<Integer> newVertexIndexList, int i) {
        newFaceList.add(3);
        newVertexIndexList.add(vertexIndexArray[i]);
        newVertexIndexList.add(vertexIndexArray[i +1]);
        newVertexIndexList.add(vertexIndexArray[i +2]);
    }

    /**
     * Haalt de bounding box van de mesh op.
     * @return De bounding box van de mesh of null als er geen vertices zijn.
     */
    public BoundingBox getBoundingbox() {

        if (vertexArray.length == 0) {
            return null; // Als er geen vertices zijn wordt er null teruggegeven
        }

        Vector min = vertexArray[0];
        Vector max = vertexArray[0];

        // Dit zorgt voor de minimale en maximale coördinaten.
        for (Vector vertex : vertexArray) {
            min = Vector.min(min, vertex);
            max = Vector.max(max, vertex);
        }

       return new BoundingBox(min, max);
    }

}
