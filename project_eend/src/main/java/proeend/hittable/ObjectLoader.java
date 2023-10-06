package proeend.hittable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import proeend.material.Material;
import proeend.material.texture.Texture;
import proeend.math.Vector;

public class ObjectLoader {


    public static TriangleMesh loadObj(String filepath, Material material) throws IOException {

        List<Vector> vertexList = new ArrayList<>();
        List<Integer> vertexIndexList = new ArrayList<>();
        List<Integer> faceList = new ArrayList<>();

        //omdat bij een obj bestand de vertexindexarray met 1 geindexeerd is los ik dat zo nu op
        vertexList.add(new Vector());

        int[] vertexIndexArray;
        int[] faceArray;
        int[] normalArray;
        int[] textureArray;
        File file = new File(filepath);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] lineA = line.split(" ");

            if (Objects.equals(lineA[0], "v")){
                double x = Double.parseDouble(lineA[1]);
                double y = Double.parseDouble(lineA[2]);
                double z = Double.parseDouble(lineA[3]);
                vertexList.add(new Vector(x,y,z));
            }
            if (Objects.equals( lineA[0], "f")){
                faceList.add(lineA.length-1);
                for (int j = 1; j <lineA.length; j++) {
                    String[] faceSplit = lineA[j].split("/");
                    vertexIndexList.add(Integer.parseInt(faceSplit[0]));
                }
            }
        }
        System.out.println("object \""+filepath+"\" loaded from file");
        return new TriangleMesh(faceList.toArray(new Integer[0]),vertexIndexList.toArray(new Integer[0]),vertexList.toArray(new Vector[0]),material);
    }
    public void polyToTriangleMesh() {
        //return new Mesh();
    }
}
