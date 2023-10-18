package proeend.hittable;

import proeend.material.Material;
import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.records.HitRecord;
import java.util.ArrayList;
import java.util.List;

public class TriangleMesh extends Hittable {
    private boolean isObj = true;
    private BoundingBox boundingBox;
    private Integer[] faceArray;
    private Integer[] vertexIndexArray;
    private Vector[] vertexArray;
    private Material material;
    public TriangleMesh(Integer[] faceArray, Integer[] vertexIndexArray, Vector[] vertexArray, Material material) {
        this.faceArray = faceArray;
        this.vertexIndexArray = vertexIndexArray;
        this.vertexArray = vertexArray;
        this.material = material;
        this.material = material;
        this.boundingBox = getBoundingbox();
    }

    @Override
    public boolean hit(Ray ray, Interval rayT, HitRecord rec) {
        //TODO maak minder slecht
        boolean tempHit = false;
        double closest = rayT.getMax();
        int j = 0;
        Vector v0,v1,v2;
        for (int i = 0;i<faceArray.length;i++) {
            v0 = vertexArray[vertexIndexArray[j]];
            v1 = vertexArray[vertexIndexArray[j+1]];
            v2 = vertexArray[vertexIndexArray[j+2]];
            /*oude code
            Triangle triangle = new Triangle(v0,v1,v2,material);
            if (triangle.hit(ray, rayT, rec)) {
                return true;
                //tempHit = true;
            }*/
            if (Triangle.MThit(ray, new Interval(rayT.getMin(),closest), rec, v0,v1,v2,material)){
                closest = rec.t;
                tempHit = true;
            }

            j += 3;
        }
        return tempHit;
    }

    public void toTriangles(){
        //niet heel goed doordacht, maar voor nu werkt het
        List<Integer> newFaceList = new ArrayList<>();
        List<Integer> newVertexIndexList = new ArrayList<>();
        int i = 0;
        for (Integer face: faceArray){
            if (face == 3){
                newFaceList.add(3);
                newVertexIndexList.add(vertexIndexArray[i]);
                newVertexIndexList.add(vertexIndexArray[i+1]);
                newVertexIndexList.add(vertexIndexArray[i+2]);
                i += 3;
            }
            if (face == 4){
                newFaceList.add(3);
                newFaceList.add(3);
                newVertexIndexList.add(vertexIndexArray[i]);
                newVertexIndexList.add(vertexIndexArray[i+1]);
                newVertexIndexList.add(vertexIndexArray[i+2]);

                newVertexIndexList.add(vertexIndexArray[i]);
                newVertexIndexList.add(vertexIndexArray[i+2]);
                newVertexIndexList.add(vertexIndexArray[i+3]);
                i +=4;
            }
        }
        this.faceArray = newFaceList.toArray(new Integer[0]);
        this.vertexIndexArray = newVertexIndexList.toArray(new Integer[0]);
        System.out.println("converted to triangles");
    }

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

        // Maakt een bounding box aan
        BoundingBox boundingBox = new BoundingBox(min, max);

        return boundingBox;
    }

}
