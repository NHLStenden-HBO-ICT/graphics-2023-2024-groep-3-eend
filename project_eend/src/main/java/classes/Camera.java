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
    public Vec cameraCenter = new Vec();
    public Vec viewportU = new Vec(viewportWidth,0,0);
    public Vec viewportV = new Vec(0,-viewportHeight,0);
    public Vec pixelDeltaU = Vec.scale((1.0/ imageWidth), viewportU);
    public Vec pixelDeltaV = Vec.scale((1.0/ imageHeight), viewportV);
    public Vec viewportUpperleft = Vec.add(Vec.add(Vec.add(cameraCenter, Vec.inverse(new Vec(0,0,focalLength))),
            Vec.inverse(Vec.scale(1.0/2.0,viewportU))),Vec.inverse(Vec.scale(1.0/2.0,viewportV)));
    public Vec pixel00 = Vec.add(viewportUpperleft, Vec.scale(1.0/2.0, Vec.add(pixelDeltaU,pixelDeltaV)));

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
                 Vec colorVec = new Vec();
                 for (int sample = 0; sample < samplesPerPixel; ++sample) {
                     Ray ray = getRay(x, y);
                     //colorVec = new Vec();
                     colorVec = Vec.add(colorVec, rayColor(ray, maxDepth, world));
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
        Vec pixelcenter = Vec.add(Vec.add(pixel00, Vec.scale(x, pixelDeltaU)), Vec.scale(y, pixelDeltaV));
        Vec pixelSample = Vec.add(pixelcenter, pixelSampleSquare());

        Vec rayOrigin = cameraCenter;
        Vec direction = Vec.add(pixelSample, Vec.inverse(rayOrigin));
        return new Ray(rayOrigin, direction);

    }
    private Vec pixelSampleSquare() {
        double px = -.5 + Math.random();
        double py = -.5 + Math.random();
        return Vec.add(Vec.scale(px, pixelDeltaU), Vec.scale(py, pixelDeltaV));
    }
    public WritableImage render(Hittable world) {
        return render(false, world);
    }


    private Vec rayColor(Ray r, int depth, Hittable world) {
        if (world == null) {
            return Vec.randomVec();
        }
        if (depth<=0) {
            return new Vec(.5,.5,.5);
        }
        HitRecord rec = new HitRecord();
        if (world.hit(r, new Interval(0.00000001, Double.POSITIVE_INFINITY), rec)) {
            Ray scattered = Global.scattered;
            Vec attenuation = Global.attenuation;
            if (rec.mat.scatter(r,rec,attenuation,scattered)) {
                return Vec.multiply(attenuation,rayColor(scattered,depth-1,world));
            }
            return new Vec();
        }
        Vec unitDirection = Vec.unitVector(r.direction);
        double a = .5*(unitDirection.y()+1.0);
        return Vec.add(Vec.scale((1.0-a),new Vec(1,1,1)),Vec.scale(a, new Vec(.5,.7,1)));
    }

}
