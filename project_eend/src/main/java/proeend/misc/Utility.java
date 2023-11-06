package proeend.misc;

import javafx.scene.image.Image;
import proeend.hittable.*;
import proeend.material.*;
import proeend.material.texture.CheckerTexture;
import proeend.material.texture.Image_Texture;
import proeend.math.Vector;

/**
 * Beheerd de instellingen van de wereld.
 */
public class Utility {
    private static Lambertian greyLambertian = new Lambertian(new Vector(.5,.5,.5));
    private static Lambertian yellowLambertian = new Lambertian(new Vector(1,1,0));
    private static Lambertian blueLambertian = new Lambertian(new Vector(0.1,0.4,0.7));
    private static Mirror redMirror = new Mirror(new Vector(1,.5,.5), 1);



    Mirror iceMirror = new Mirror(new Vector(0,0.7,1),0.15);

    private static final Mirror perfectMirror = new Mirror(new Vector(1,1,1),0);
    Mirror halfMirror = new Mirror(new Vector(100,100,100),.5);
    static Normal normal = new Normal();
    static CheckerTexture checkerTexture = new CheckerTexture(0.1, new Vector(1,1,1), new Vector(.5,.5,.5));
    static Lambertian errorCheckers = new Lambertian(checkerTexture);
    static Emitter whiteLight = new Emitter(new Vector(4,4,4));
    static Emitter blueLight = new Emitter(new Vector(.3,.8,.9));
    static Lambertian whiteLambertian = new Lambertian(new Vector(1,1,1));

    //Dielectric ice = new Dielectric(1.31);
    static Dielectric ice = new Dielectric(1.31, new Vector(1,1,1));
    static Dielectric salt = new Dielectric(1.54);

    //Driehoekig vlak voor ondergrond
    static Vector vect1 = new Vector(-35,-1.1,20);
    static Vector vect2 = new Vector(35,-1.1,20);
    static Vector vect3 = new Vector(0,-1.1,-40);
    //Laag ijs erbovenop
    static Vector vect4 = new Vector(-35,-1.101,20);
    static Vector vect5 = new Vector(35,-1.101,20);
    static Vector vect6 = new Vector(0,-1.101,-40);

    //Driehoekig vlak achtergrond
    Vector vect7 = new Vector(-150,-5,-50);
    Vector vect8 = new Vector(150,-5,-50);
    Vector vect9 = new Vector(0,155,-50);


    static Vector v0 = new Vector(-1,-1,-2);
    static Vector v1 = new Vector(1,-1,-2);
    static Vector v2 = new Vector(0,.3,-2);
    static Vector v3 = new Vector(-1,1,-2);
    static Vector v4 = new Vector(1,1,-2);

    static Vector v5 = new Vector(-250,-50,5);
    static Vector v6 = new Vector(250, -50, 5);
    static Vector v7 = new Vector(-250, -50, -105);
    static Vector v8 = new Vector(250,-50,-105);
    static Vector v9 = new Vector(250, 50, -105);
    static Vector v10 = new Vector(-250, 50, -105);

    static Integer[] faceArray = {3,3,3};
    static Integer[] vertexIndexArray = {0,1,2,0,2,3,1,4,2};
    static Vector[] vertexArray = {v0,v1,v2,v3,v4,v5,v6,v7,v8,v9,v10};

    private static final Lambertian white = new Lambertian(new Vector(1, 1, 0));
    //Emitter white = new Emitter(new Vector(1,1,1));

    private static Lambertian earth_surface;
    private static Emitter sunEmitter;
    private static Lambertian ice_surface;
    private static Lambertian background_surface;
    private static Lambertian bergenSurface;

    public static Image getImage(String pathname) {
        try {
            return new Image("file:" + pathname);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadImages() {
        Image_Texture earthTexture = new Image_Texture(Utility.getImage("project_eend/ModelTextureImages/world.jpeg"));
        earth_surface = new Lambertian(earthTexture);

        Image_Texture sunTexture = new Image_Texture(Utility.getImage("project_eend/ModelTextureImages/zon.jpg"));
        sunEmitter = new Emitter(sunTexture);

        Image_Texture iceTexture = new Image_Texture(Utility.getImage("project_eend/ModelTextureImages/IceTexture.jpeg"));
        ice_surface = new Lambertian(iceTexture);

        Image_Texture backgroundTexture = new Image_Texture(Utility.getImage("project_eend/ModelTextureImages/BergBeter.jpg"));
        background_surface = new Lambertian(backgroundTexture);

        Image_Texture bergenTexture = new Image_Texture(Utility.getImage("project_eend/ModelTextureImages/bergen.jpg"));
        bergenSurface = new Lambertian(bergenTexture);

    }

    /**
     * Maakt de wereld aan.
     * @param world De scene.
     * @param lights De lichten binnen de scene.
     * @param selector De geselecteerde wereld.
     */
    public static void loadWorld(HittableList world, HittableList lights,int selector) {
        world.clear();
        lights.clear();

        PolygonMesh object = null;
        PolygonMesh object2 = null;
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
                world.add(new Sphere(new Vector(0, -100.5, -.55), 100, greyLambertian));
                world.add(new Sphere(new Vector(0, 0, -.55), .5, perfectMirror));
                world.add(new Sphere(new Vector(-2.5, 1.5, -.55), 1, redMirror));


                world.add(new Sphere(new Vector(1, 4, -.55), .5, whiteLight));
                lights.add(new Sphere(new Vector(1, 4, -.55), .5, whiteLight));
            }
            case 2 -> {

                world.add(new Sphere(new Vector(0, 14, 6), 6, whiteLight));
                lights.add(new Sphere(new Vector(0, 14, 6), 6, whiteLight));

                //Catch bol
                world.add(new Sphere(new Vector(55,55,-48),-.5,blueLambertian));

                //Aarde bol
                world.add(new Sphere(new Vector(1, 0.2, -2), 1.2, earth_surface));
                world.add(new Sphere(new Vector(.7, 0, 1.7), .5, ice));


                //Ijs ondergrond
                world.add(new Quad(new Vector(-20,-1.0,3),new Vector(50,0,0),new Vector(0,0,-20),ice));
                world.add(new Quad(new Vector(-20,-1.001,3),new Vector(50,0,0),new Vector(0,0,-20),ice_surface));

                // Achtergrond
                world.add(new Quad(new Vector(-20,-5,-5),new Vector(50,0,-5),new Vector(0,30,-5), bergenSurface));

            }
            case 3 -> {
                object = ObjectLoader.loadObj(ObjectPaths.DUCK, yellowLambertian);
                if(object != null /*&& object2 != null*/){
                    object.rotate(90);
                    object.translate(new Vector(0, -.1, 1.8));
                    object.setBounddingBox();
                    world.add(object);
                }

                world.add(new Sphere(new Vector(0, 14, 6), 6, whiteLight));
                lights.add(new Sphere(new Vector(0, 14, 6), 6, whiteLight));

                //Catch bol
                world.add(new Sphere(new Vector(55,55,-48),-.5,blueLambertian));

                //Aarde bol
                world.add(new Sphere(new Vector(1, 0.2, -2), 1.2, earth_surface));

                // Ijs bol
                world.add(new Sphere(new Vector(.7, 0, 1.7), .5, ice));

                // Ijs plaat
                world.add(new Quad(new Vector(-20,-1.0,3),new Vector(50,0,0),new Vector(0,0,-20),ice));
                // Ijs plaat texture onder het ijs
                world.add(new Quad(new Vector(-20,-1.001,3),new Vector(50,0,0),new Vector(0,0,-20),ice_surface));

                // Achtergrond
                world.add(new Quad(new Vector(-20,-5,-5),new Vector(50,0,-5),new Vector(0,30,-5), bergenSurface));

            }

            default -> {
                System.out.println("foute wereldkeuze");
                world.add(new Sphere(new Vector(0, 0, -1), 1, normal));

                lights.add(new Sphere(new Vector(1, 2, -.55), 500, whiteLambertian));

            }
        }
    }
}
