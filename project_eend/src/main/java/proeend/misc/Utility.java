package proeend.misc;

import proeend.hittable.*;
import proeend.material.*;
import proeend.material.texture.CheckerTexture;
import proeend.math.Vector;
import proeend.material.texture.Image;
import proeend.material.texture.*;
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

        Image_Texture earthTexture = new Image_Texture("project_eend/ModelTextureImages/world.jpeg");
        Lambertian earth_surface = new Lambertian(earthTexture);

        Image_Texture sunTexture = new Image_Texture("project_eend/ModelTextureImages/zon.jpg");
        Emitter sunEmitter = new Emitter(sunTexture);

        Image_Texture iceTexture = new Image_Texture("project_eend/ModelTextureImages/IceTexture.jpg");
        Lambertian ice_surface = new Lambertian(iceTexture);

        Image_Texture backgroundTexture = new Image_Texture("project_eend/ModelTextureImages/BergBeter.jpg");
        Lambertian background_surface = new Lambertian(backgroundTexture);

        Mirror iceMirror = new Mirror(new Vector(0,0.7,1),0.15);

        Mirror perfectMirror = new Mirror(new Vector(1,1,1),0);
        Mirror halfMirror = new Mirror(new Vector(100,100,100),.5);
        Normal normal = new Normal();
        CheckerTexture checkerTexture = new CheckerTexture(0.1, new Vector(1,1,1), new Vector(.5,.5,.5));
        Lambertian errorCheckers = new Lambertian(checkerTexture);
        Emitter whiteLight = new Emitter(new Vector(4,4,4));
        Emitter blueLight = new Emitter(new Vector(.3,.8,.9));
        Lambertian whiteLambertian = new Lambertian(new Vector(1,1,1));

        //Dielectric ice = new Dielectric(1.31);
        Dielectric ice = new Dielectric(1.31, new Vector(1,1,1));
        Dielectric salt = new Dielectric(1.54);

        //Driehoekig vlak voor ondergrond
        Vector vect1 = new Vector(-35,-1.1,20);
        Vector vect2 = new Vector(35,-1.1,20);
        Vector vect3 = new Vector(0,-1.1,-40);
        //Laag ijs erbovenop
        Vector vect4 = new Vector(-35,-1.101,20);
        Vector vect5 = new Vector(35,-1.101,20);
        Vector vect6 = new Vector(0,-1.101,-40);

        //Driehoekig vlak achtergrond
        Vector vect7 = new Vector(-150,-5,-50);
        Vector vect8 = new Vector(150,-5,-50);
        Vector vect9 = new Vector(0,155,-50);


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

                //world.add(new Sphere(new Vector(1.5, 0, 3), 3, sunEmitter));

                //Zon
                //world.add(new Sphere(new Vector(1.5, 0, 3), 3, sunEmitter));
                //lights.add(new Sphere(new Vector(1.5, 0, 3), 3, sunEmitter));
                //world.add(new Sphere(new Vector(3.5, 2, -2), 0.5, sunEmitter));
                //lights.add(new Sphere(new Vector(3.5, 2, -2), 0.5, sunEmitter));
                //world.add(new Sphere(new Vector(3.5, 2, -1), 0.5, sunEmitter));
                //lights.add(new Sphere(new Vector(3.5, 2, -1), 0.5, sunEmitter));


                //Witte bol
                //world.add(new Sphere(new Vector(1.5, 0, 3), 3, whiteLight));
                //lights.add(new Sphere(new Vector(1.5, 0, 3), 3, whiteLight));
                world.add(new Sphere(new Vector(0, 14, 6), 6, whiteLight));
                lights.add(new Sphere(new Vector(0, 14, 6), 6, whiteLight));

                //Catch bol
                world.add(new Sphere(new Vector(55,55,-48),-.5,blueLambertian));

                //Aarde bol
                world.add(new Sphere(new Vector(1, 0.2, -1.5), 1.2, earth_surface));

                //Ijs ondergrond
                //world.add(new Triangle(vect1, vect2, vect3, ice_surface));
                //world.add(new Triangle(vect1, vect2, vect3, ice));
                //world.add(new Triangle(vect4, vect5, vect6, ice_surface));

                world.add(new Quad(new Vector(-20,-1.0,3),new Vector(50,0,0),new Vector(0,0,-20),ice));
                world.add(new Quad(new Vector(-20,-1.001,3),new Vector(50,0,0),new Vector(0,0,-20),ice_surface));

                //lights.add(new Triangle(vect4,vect5,vect6,whiteLight));
                //Achtergrond
                //world.add(new Triangle(vect7, vect8, vect9, background_surface));

                world.add(new Quad(new Vector(-20,-2,-5),new Vector(50,0,-5),new Vector(0,30,-5), background_surface));
                //world.add(new Sphere(new Vector(0, -203.5, -3.55), 200, ice_surface));

            }

            default -> {
                System.out.println("foute wereldkeuze");
                world.add(new Sphere(new Vector(0, 0, -1), 1, normal));

                lights.add(new Sphere(new Vector(1, 2, -.55), 500, whiteLambertian));

            }
        }
    }
}
