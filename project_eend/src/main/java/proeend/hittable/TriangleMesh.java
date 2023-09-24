package proeend.hittable;

import proeend.material.Material;
import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.misc.HitRecord;

public class TriangleMesh extends Hittable {
    private boolean isObj = true;
    private int[] faceArray;
    private int[] vertexIndexArray;
    private Vector[] vertexArray;
    private Material material;
    public TriangleMesh(int[] faceArray, int[] vertexIndexArray, Vector[] vertexArray, Material material) {
        this.faceArray = faceArray;
        this.vertexIndexArray = vertexIndexArray;
        this.vertexArray = vertexArray;
        this.material = material;
    }

    @Override
    public boolean hit(Ray ray, Interval rayT, HitRecord rec) {
        boolean tempHit = false;
        int j = 0;
        Vector v0,v1,v2;
        for (int i = 0;i<faceArray.length;i++) {
            v0 = vertexArray[vertexIndexArray[j]];
            v1 = vertexArray[vertexIndexArray[j+1]];
            v2 = vertexArray[vertexIndexArray[j+2]];
            Triangle triangle = new Triangle(v0,v1,v2,material);
            if (triangle.hit(ray, rayT, rec)) {
                return true;
                //tempHit = true;
            }

            j += 3;
        }
        return tempHit;
    }
}
