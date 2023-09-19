package classes;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Camera {

    static int frames = 0;
    public int samplesPerPixel = 100;
    public int maxDepth = 50;
    public double aspectRatio = 16.0/9.0;
    public int imageWidth = 800;
    private int imageHeight = (int)(imageWidth /aspectRatio);
    public double focalLength = 1.0;
    public double viewportHeight = 2.0;
    public double viewportWidth = viewportHeight * (double) imageWidth /(double) imageHeight;
    public Vector cameraCenter = new Vector();
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
        //image_height
        imageHeight = (imageHeight <1) ? 1 : imageHeight;

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
        WritableImage writableImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        //Color color;
        //double[] colorVec = new Vec(Math.random(),Math.random(),Math.random()).toColor();
        for (int y = 0; y < imageHeight; ++y){
            //System.out.println(Integer.toString(y)+ "lines to go");
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
            return new Vector(.5,.5,.5);
        }
        HitRecord rec = new HitRecord();
        if (world.hit(r, new Interval(0.00000001, Double.POSITIVE_INFINITY), rec)) {
            Ray scattered = Global.scattered;
            Vector attenuation = Global.attenuation;
            if (rec.mat.scatter(r,rec,attenuation,scattered)) {
                return Vector.multiply(attenuation,rayColor(scattered,depth-1,world));
            }
            return new Vector();
        }
        Vector unitDirection = Vector.unitVector(r.direction);
        double a = .5*(unitDirection.y()+1.0);
        return Vector.add(Vector.scale((1.0-a),new Vector(1,1,1)), Vector.scale(a, new Vector(.5,.7,1)));
    }

}
