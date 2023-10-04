package proeend.hittable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import proeend.math.Vector;

public class ObjectLoader {


    public static PolyMesh loadObj(String filepath) throws IOException {

        List<Vector> vertexList = new ArrayList<>();
        List<Integer> vertexIndexList = new ArrayList<>();

        int[] vertexIndexArray;
        int[] faceArray;
        int[] normalArray;
        int[] textureArray;
        File file = new File(filepath);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            System.out.println(scanner.nextLine());
        }
        return new PolyMesh();
    }
    public void polyToTriangleMesh() {
        //return new Mesh();
    }
}
