package proeend.misc;

import proeend.hittable.*;
import proeend.material.*;
import proeend.material.texture.CheckerTexture;
import proeend.math.Vector;
import proeend.material.texture.Image;
import proeend.material.texture.Image_Texture;

/**
 * Beheerd de instellingen van de wereld.
 */
public class Utility {

    /**
     * Maakt de wereld aan.
     * @param world De scene.
     * @param lights De lichten binnen de scene.
     * @param selector De geselecteerde wereld.
     */
    public static void loadWorld(HittableList world, HittableList lights,int selector) {
        world.clear();
        lights.clear();
        Lambertian greyLambertian = new Lambertian(new Vector(.5,.5,.5));
        Lambertian yellowLambertian = new Lambertian(new Vector(1,1,0));
        Lambertian blueLambertian = new Lambertian(new Vector(0.1,0.4,0.7));
        Mirror redMirror = new Mirror(new Vector(1,.5,.5), 1);

        Image earth = new Image("project_eend/ModelTextureImages/1.jpg");

        Mirror perfectMirror = new Mirror(new Vector(1,1,1),0);
        Mirror halfMirror = new Mirror(new Vector(100,100,100),.5);
        Normal normal = new Normal();
        CheckerTexture checkerTexture = new CheckerTexture(10, new Vector(.6,.1,.7), new Vector());
        Lambertian errorCheckers = new Lambertian(checkerTexture);
        Emitter whiteLight = new Emitter(new Vector(4,4,4));
        Emitter blueLight = new Emitter(new Vector(.3,.8,.9));
        Lambertian whiteLambertian = new Lambertian(new Vector(1,1,1));
        Dielectric ice = new Dielectric(1.31);
        Dielectric salt = new Dielectric(1.54);


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

        Integer[] faceArray = {3,3,3};
        Integer[] vertexIndexArray = {0,1,2,0,2,3,1,4,2};
        Vector[] vertexArray = {v0,v1,v2,v3,v4,v5,v6,v7,v8,v9,v10};

        switch (selector) {
            case 0 -> {
                // Voeg willekeurige objecten toe aan de wereld
                world.add(new Sphere(new Vector(0, 0, -1), 0.5, redMirror));
                world.add(new Sphere(new Vector(0, -100.5, -1), 100, blueLambertian));
                world.add(new Sphere(new Vector(0, 0, 0.5), .9, ice));
                world.add(new Sphere(new Vector(-1, 0, -1), 0.5, greyLambertian));

                // Voeg een willekeurig lichtobject toe
                Hittable light0 = new Sphere(new Vector(1, 0, 0), 0.5, blueLight);
                world.add(light0);
                lights.add(light0);
            }
            case 1 -> {
                world.add(new Sphere(new Vector(2, 0, -.55), .5, yellowLambertian));
                world.add(new Sphere(new Vector(1.5, 0, 1.55), .5, salt));
                world.add(new Sphere(new Vector(0, -103.5, -.55), 100, greyLambertian));
                world.add(new Sphere(new Vector(1, 2, -.55), .5, whiteLight));
                lights.add(new Sphere(new Vector(1, 2, -.55), .5, whiteLight));
            }
            case 2 -> {
                world.add(new Triangle(v0, v1, v2, normal));
                world.add(new Triangle(v0, v2, v3, normal));
                world.add(new Triangle(v1, v4, v2, normal));
                lights.add(new Sphere(new Vector(1, 2, -.55), 500, whiteLambertian));
            }
            case 3 -> {
                faceArray = new Integer[]{3, 3, 3};
                vertexIndexArray = new Integer[]{0, 1, 2, 0, 2, 3, 1, 4, 2};
                world.add(new PolygonMesh(faceArray, vertexIndexArray, vertexArray, normal));
                world.add(new Sphere(v1, .3, whiteLight));
                lights.add(new Sphere(new Vector(1, 2, -.55), 500, whiteLambertian));
            }
            case 4 -> {
                faceArray = new Integer[]{4};
                vertexIndexArray = new Integer[]{0, 1, 2, 4};
                vertexArray = new Vector[]{v0, v1, v2, v3, v4};
                //world.add(new TriangleMesh(faceArray, vertexIndexArray, vertexArray, redMirror).toTriangleMesh());

                lights.add(new Sphere(new Vector(1, 2, -.55), 500, whiteLambertian));
            }
            case 5 -> {
                world.add(new Sphere(new Vector(1, 0, -2), .5, whiteLambertian));
                world.add(new Sphere(v1, .3, whiteLight));
                lights.add(new Sphere(new Vector(1, 2, -.55), 500, whiteLambertian));
            }
            case 6 -> { //box
                faceArray = new Integer[]{3, 3, 3, 3};
                vertexIndexArray = new Integer[]{5, 6, 7, 6, 8, 7, 8, 9, 10, 7, 8, 10};
                world.add(new PolygonMesh(faceArray, vertexIndexArray, vertexArray, errorCheckers));
                lights.add(new Sphere(new Vector(1, 2, -.55), 500, whiteLambertian));
            }
            case 7 -> {
                Vector bv1 = new Vector(0, 0, -1.5);
                Vector bv2 = new Vector(0, .5, -1.5);
                Vector bv3 = new Vector(1, .5, -1.5);
                Triangle behindSphere = new Triangle(bv1, bv3, bv2, whiteLight);
                world.add(behindSphere);
                world.add(new Sphere(new Vector(0, 0, -1), 1, normal));
                lights.add(new Sphere(new Vector(1, 2, -.55), 500, whiteLambertian));
            }
            case 8 -> { //Textures test
                //world.add(new Sphere(new Vector(0,0,-1),0.5, redMirror));
                //world.add(new Sphere(new Vector(0,-100.5,-1), 100, halfMirror));
                //world.add(new Sphere(new Vector(0,0,0.5),.2,glass));

                world.add(new Sphere(new Vector(1.5, 0, 3), 2, whiteLight));

                lights.add(new Sphere(new Vector(1.5, 0, 3), 2, whiteLight));

                //Texture test
                world.add(new Sphere(new Vector(0, 0, -1), 1, earth));
            }

            default -> {
                System.out.println("foute wereldkeuze");
                world.add(new Sphere(new Vector(0, 0, -1), 1, normal));

                lights.add(new Sphere(new Vector(1, 2, -.55), 500, whiteLambertian));

            }
        }
    }
}
