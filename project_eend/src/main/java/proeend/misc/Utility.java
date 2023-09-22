package proeend.misc;

import proeend.hittable.HittableList;
import proeend.hittable.Sphere;
import proeend.hittable.Triangle;
import proeend.hittable.TriangleMesh;
import proeend.material.Lambertian;
import proeend.material.Mirror;
import proeend.math.Vector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utility {

    /**
     * compleet nutteloos
     */
    public static void Convert(String arg) {
        List<String> command= new ArrayList<String>();
        command.add("python3");
        command.add("converter.py");
        command.add(arg);
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        int exitCode;
        try {
            Process process = processBuilder.start();
            exitCode = process.waitFor();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(exitCode);
    }

    /**
     * laad een in de functie gedefineerde wereld in
     * @param selector je keuze
     */
    public static void loadWorld(HittableList world, int selector) {
        world.clear();
        Lambertian greyLambertian = new Lambertian(new Vector(.5,.5,.5));
        Lambertian yellowLambertian = new Lambertian(new Vector(1,1,0));
        Mirror redMirror = new Mirror(new Vector(1,.5,.5), .3);
        Mirror perfectMirror = new Mirror(new Vector(1,1,1),0);
        Mirror halfMirror = new Mirror(new Vector(1,1,1),.5);

        Vector v0 = new Vector(-1,-1,-2);
        Vector v1 = new Vector(1,-1,-2);
        Vector v2 = new Vector(0,1,-6);
        Vector v3 = new Vector(-1,1,-2);
        Vector v4 = new Vector(1,1,-2);


        switch (selector) {
            case 0:
                world.add(new Sphere(new Vector(0,0,-1),0.5, redMirror));
                world.add(new Sphere(new Vector(0,-100.5,-1), 100, perfectMirror));
                world.add(new Sphere(new Vector(-1,0,-1),.5,greyLambertian));

                break;
            case 1:
                world.add(new Sphere(new Vector(0,-100.5,-.55), 100, greyLambertian));
                world.add(new Sphere(new Vector(0,0,-.7),.5,perfectMirror));
                world.add(new Sphere(new Vector(-1,0,-.55),.5,yellowLambertian));
                world.add(new Sphere(new Vector(0,0,.7),.5,perfectMirror));
                world.add(new Sphere(new Vector(1,0,-.55),.5,redMirror));

                break;
            case 2:
                world.add(new Triangle(v0,v1,v2, halfMirror));
                world.add(new Triangle(v0,v2,v3,yellowLambertian));
                world.add(new Triangle(v1,v4,v2, greyLambertian));
                //world.add(new Sphere(new Vector(-1.5,-.5,-3),0.5, redMirror));
                //world.add(new Sphere(new Vector(0,.5,-3),0.5, redMirror));

                //world.add(new Triangle(v2,v1,v0, yellowLambertian));
                break;
            case 3:
                int[] faceArray = {3,3,3};
                int[] vertexIndexArray = {0,1,2,0,2,3,1,4,2};
                Vector[] vertexArray = {v0,v1,v2,v3,v4};
                world.add(new TriangleMesh(faceArray, vertexIndexArray, vertexArray, redMirror));
                break;
        }
    }



}
