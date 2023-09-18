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
    public double aspectRatio = 16.0/9.0;
    public int image_width = 800;
    private int image_height = (int)(image_width/aspectRatio);
    public double focalLength = 1.0;
    public double viewportHeight = 2.0;
    public double viewportWidth = viewportHeight * (double)image_width/(double)image_height;
    public Vec cameraCenter = new Vec();
    public Vec viewportU = new Vec(viewportWidth,0,0);
    public Vec viewportV = new Vec(0,-viewportHeight,0);
    public Vec pixelDeltaU = Vec.scale((1.0/image_width), viewportU);
    public Vec pixelDeltaV = Vec.scale((1.0/image_height), viewportV);
    public Vec viewportUpperleft = Vec.add(Vec.add(Vec.add(cameraCenter, Vec.inverse(new Vec(0,0,focalLength))),
            Vec.inverse(Vec.scale(1.0/2.0,viewportU))),Vec.inverse(Vec.scale(1.0/2.0,viewportV)));
    public Vec pixel00 = Vec.add(viewportUpperleft, Vec.scale(1.0/2.0, Vec.add(pixelDeltaU,pixelDeltaV)));

    public double getHeight() {
        return image_height;
    }
    private void init() {
        //image_height
        image_height = (image_height<1) ? 1 : image_height;

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
    }

    //gooit een plaatje 'render.ppm' in de src folder
    public WritableImage render(boolean save){

        init();
        WritableImage writableImage = new WritableImage(image_width,image_height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        //Color color;
        //double[] colorVec = new Vec(Math.random(),Math.random(),Math.random()).toColor();
        for (int y = 0; y < image_height; ++y){
             for (int x = 0; x < image_width; ++x){
                 Vec pixelcenter = Vec.add(Vec.add(pixel00, Vec.scale(x, pixelDeltaU)), Vec.scale(y, pixelDeltaV));
                 Vec direction = Vec.add(pixelcenter, Vec.inverse(cameraCenter));
                 Ray ray = new Ray(cameraCenter, direction);
                 int[] colors = rayColor(ray).toColor();
                 pixelWriter.setColor(x,y, Color.rgb(colors[0],colors[1],colors[2]));
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
    public WritableImage render() {
        return render(false);
    }
    private double hitSphere(Vec center, double radius, Ray ray) {
        Vec OC = Vec.add(ray.origin(), Vec.inverse(center));
        double a = Vec.lengthSquared(ray.direction());
        double halfb = Vec.dot(OC, ray.direction());
        double c = Vec.lengthSquared(OC) - radius*radius;
        double D = halfb*halfb - a*c;
        if (D < 0) {
            return -1.0;
        }
        return ((-halfb - Math.sqrt(D))/a);

    }
    private Vec rayColor(Ray r) {
        double t = hitSphere(new Vec(0,0,-1), 0.5, r);
        if (t>0.0)
        {
            Vec N = Vec.add(r.at(t), Vec.inverse(new Vec(0,0,-1)));
            return Vec.scale(0.5, new Vec(N.x()+1.0,N.y()+1.0,N.z()+1.0));
        }
        return new Vec(Math.random(),Math.random(),Math.random());
    }

}
