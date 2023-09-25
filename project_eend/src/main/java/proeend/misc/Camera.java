package proeend.misc;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import proeend.hittable.Hittable;
import proeend.material.Normal;
import proeend.material.texture.Texture;
import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Camera {

    public static boolean block;
    static int frames = 0;
    private Vector u,v,w;
    public Vector cameraCenter = new Vector(0,0,0);
    public Vector lookat = new Vector(0,0,-1);
    public Vector up = new Vector(0,1,0);
    public double verticalFOV = Math.PI/2; //in radialen
    public int samplesPerPixel = 100;
    public int maxDepth = 50;
    public double aspectRatio = 16.0/9.0;
    public int imageWidth = 800;
    public Vector background = new Vector();
    private int imageHeight = (int)(imageWidth /aspectRatio);
    public double focalLength = 1.0;
    private double viewportHeight;
    public double viewportWidth = viewportHeight * (double) imageWidth /(double) imageHeight;
    public Vector viewportU = new Vector(viewportWidth,0,0);
    public Vector viewportV = new Vector(0,-viewportHeight,0);
    public Vector pixelDeltaU = Vector.scale((1.0/ imageWidth), viewportU);
    public Vector pixelDeltaV = Vector.scale((1.0/ imageHeight), viewportV);
    public Vector viewportUpperleft = Vector.add(Vector.add(Vector.add(cameraCenter, Vector.inverse(new Vector(0,0,focalLength))),
            Vector.inverse(Vector.scale(1.0/2.0,viewportU))), Vector.inverse(Vector.scale(1.0/2.0,viewportV)));
    public Vector pixel00 = Vector.add(viewportUpperleft, Vector.scale(1.0/2.0, Vector.add(pixelDeltaU,pixelDeltaV)));

    public double getHeight() {
        return imageHeight;
    }
    private void init() {
        focalLength = Vector.length(Vector.add(cameraCenter, Vector.inverse(lookat)));
        imageHeight = (int)(imageWidth /aspectRatio);
        imageHeight = (imageHeight <1) ? 1 : imageHeight;
        double h = Math.tan(verticalFOV/2);
        w = Vector.unitVector(Vector.add(cameraCenter, Vector.inverse(lookat)));
        u = Vector.unitVector(Vector.cross(up, w));
        v = Vector.cross(w,u);
        viewportHeight = 2*h*focalLength;
        viewportWidth = viewportHeight * (double) imageWidth /(double) imageHeight;
        viewportU = Vector.scale(viewportWidth, u);
        viewportV = Vector.scale(viewportHeight, Vector.inverse(v));
        pixelDeltaU = Vector.scale((1.0/ imageWidth), viewportU);
        pixelDeltaV = Vector.scale((1.0/ imageHeight), viewportV);
        viewportUpperleft = Vector.add(Vector.add(Vector.add(cameraCenter, Vector.inverse(Vector.scale(focalLength, w))),
                Vector.inverse(Vector.scale(1.0/2.0,viewportU))), Vector.inverse(Vector.scale(1.0/2.0,viewportV)));
        pixel00 = Vector.add(viewportUpperleft, Vector.scale(1.0/2.0, Vector.add(pixelDeltaU,pixelDeltaV)));
    }
    private void saveImage(WritableImage image) throws IOException{
        BufferedImage bufferedImage = new BufferedImage((int)image.getWidth(), (int)image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < (int) image.getWidth(); x++) {
            for (int y = 0; y < (int) image.getHeight(); y++) {
                int argb = image.getPixelReader().getArgb(x, y);
                bufferedImage.setRGB(x, y, argb);
            }
        }
        File output = new File("render.png");
        ImageIO.write(bufferedImage, "png", output);
        System.out.println("opgeslagen");
    }

    //gooit een plaatje 'render.ppm' in de src folder
    public WritableImage render(boolean save, Hittable world){

        init();
        block = true;
        WritableImage writableImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        //Color color;
        //double[] colorVec = new Vec(Math.random(),Math.random(),Math.random()).toColor();
        for (int y = 0; y < imageHeight; ++y){
            if (save)
                System.out.println(Integer.toString(y)+ "lines to go");

             for (int x = 0; x < imageWidth; ++x) {
                 Vector colorVec = new Vector();
                 for (int sample = 0; sample < samplesPerPixel; ++sample) {
                     Ray ray = getRay(x, y);
                     //colorVec = new Vec();
                     colorVec = Vector.add(colorVec, rayColor(ray, maxDepth, world));
                 }
                 int[] colors = colorVec.toColor(samplesPerPixel, save);
                 pixelWriter.setColor(x, y, Color.rgb(colors[0], colors[1], colors[2]));

             }
        }
        System.out.println("frame " + frames);
        frames++;
        block = false;
        if (save) {
            try {
                saveImage(writableImage);
            } catch (IOException e) {e.printStackTrace();}
        }
        return writableImage;
    }
    private Ray getRay(int x, int y) {
        Vector pixelcenter = Vector.add(Vector.add(pixel00, Vector.scale(x, pixelDeltaU)), Vector.scale(y, pixelDeltaV));
        Vector pixelSample = Vector.add(pixelcenter, pixelSampleSquare());

        Vector rayOrigin = cameraCenter;
        Vector direction = Vector.add(pixelSample, Vector.inverse(rayOrigin));
        return new Ray(rayOrigin, direction);

    }
    private Vector pixelSampleSquare() {
        double px = -.5 + Math.random();
        double py = -.5 + Math.random();
        return Vector.add(Vector.scale(px, pixelDeltaU), Vector.scale(py, pixelDeltaV));
    }
    public WritableImage render(Hittable world) {
        return render(false, world);
    }


    private Vector rayColor(Ray r, int depth, Hittable world) {
        if (world == null) {
            return Vector.randomVec();
        }
        if (depth<=0) {
            System.out.println("hit");
            return new Vector(.0,.0,.0);
        }
        HitRecord rec = new HitRecord();
        if (!world.hit(r, new Interval(0.00000001, Double.POSITIVE_INFINITY), rec))
            return background;

        Ray scattered = new Ray(new Vector(), new Vector());
        Vector attenuation = new Vector();
        Vector emissionColor = rec.material.emit(rec.u, rec.v, rec.p);
        if (!rec.material.scatter(r,rec,attenuation,scattered)) {
            if (rec.material instanceof Normal) {
                return Vector.scale(.5, new Vector(rec.normal.x()+1,
                        rec.normal.y()+1, rec.normal.z()+1));
            }

            return emissionColor;
        }

        Vector scatterColor = Vector.multiply(attenuation,rayColor(scattered,depth-1,world));

        return Vector.add(emissionColor, scatterColor);
    }

}
