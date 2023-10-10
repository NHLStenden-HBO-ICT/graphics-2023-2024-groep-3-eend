package proeend.misc;

import proeend.hittable.*;
import proeend.material.*;
import proeend.material.texture.CheckerTexture;
import proeend.material.texture.Texture;
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
        Normal normal = new Normal();
        CheckerTexture checkerTexture = new CheckerTexture(.1, new Vector(.6,.1,.7), new Vector());
        Lambertian errorCheckers = new Lambertian(checkerTexture);
        Emitter whiteLight = new Emitter(new Vector(40,40,20));
        Lambertian whiteLambertian = new Lambertian(new Vector(1,1,1));
        Dielectric glass = new Dielectric(1.31);


        Vector v0 = new Vector(-1,-1,-2);
        Vector v1 = new Vector(1,-1,-2);
        Vector v2 = new Vector(0,.3,-2);
        Vector v3 = new Vector(-1,1,-2);
        Vector v4 = new Vector(1,1,-2);

        Vector v5 = new Vector(-250,-50,5);
        Vector v6 = new Vector(250, -50, 5);
        Vector v7 = new Vector(-250, -50, -105);
        Vector v8 = new Vector(250,-50,-105);
        Vector v9 = new Vector(250, 50, -105);
        Vector v10 = new Vector(-250, 50, -105);

        int[] faceArray = {3,3,3};
        int[] vertexIndexArray = {0,1,2,0,2,3,1,4,2};
        Vector[] vertexArray = {v0,v1,v2,v3,v4,v5,v6,v7,v8,v9,v10};



        switch (selector) {

            case 0:
                world.add(new Sphere(new Vector(0,0,-1),0.5, redMirror));
                world.add(new Sphere(new Vector(0,-100.5,-1), 100, halfMirror));
                world.add(new Sphere(new Vector(0,0,0.5),.2,glass));
                world.add(new Sphere(new Vector(-1,0,-1),.5,greyLambertian));
                world.add(new Sphere(new Vector(1.5,0,3),2,whiteLight));
                // world.add(vierkant);


                break;
            case 1:
                world.add(new Sphere(new Vector(0,-100.5,-.55), 100, greyLambertian));
                world.add(new Sphere(new Vector(0,0,-.7),.5,glass));
                world.add(new Sphere(new Vector(-1,0,-.55),.5,yellowLambertian));
                world.add(new Sphere(new Vector(0,0,.7),.5,normal));
                world.add(new Sphere(new Vector(1,0,-.55),.3,whiteLight));

                break;
            case 2:
                world.add(new Triangle(v0,v1,v2, normal));
                world.add(new Triangle(v0,v2,v3,normal));
                world.add(new Triangle(v1,v4,v2, normal));
                //world.add(new Sphere(new Vector(-1.5,-.5,-3),0.5, redMirror));
                //world.add(new Sphere(new Vector(0,.5,-3),0.5, redMirror));

                //world.add(new Triangle(v2,v1,v0, yellowLambertian));
                break;
            case 3:
                faceArray = new int[]{3,3,3};
                vertexIndexArray = new int[]{0,1,2,0,2,3,1,4,2};
                world.add(new TriangleMesh(faceArray, vertexIndexArray, vertexArray, glass));
                world.add(new Sphere(v1, .3, whiteLight));

                break;
            case 4:
                faceArray = new int[]{4};
                vertexIndexArray = new int[]{0,1,2,4};
                vertexArray = new Vector[]{v0,v1,v2,v3,v4};
                //world.add(new TriangleMesh(faceArray, vertexIndexArray, vertexArray, redMirror).toTriangleMesh());
                break;
            case 5:
                world.add(new Sphere(new Vector(1,0,-2), .5, whiteLambertian));
                world.add(new Sphere(v1, .3, whiteLight));
                break;
            case 6:
                faceArray = new int[]{3,3,3,3};
                vertexIndexArray = new int[]{5,6,7,6,8,7,8,9,10,7,8,10};
                world.add(new TriangleMesh(faceArray, vertexIndexArray, vertexArray, errorCheckers));
            default:
                System.out.println("foute wereldkeuze");
                break;
        }
    }



}
