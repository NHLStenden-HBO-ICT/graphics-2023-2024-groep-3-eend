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
    public int image_width = 256;
    private int image_height;

    public double getHeight() {
        return image_height;
    }
    private void init() {
        image_height = (int)(image_width/aspectRatio);
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

        Color color;
        int[] colorVec = new Vec(Math.random(),Math.random(),Math.random()).toColor();
        for (int y = 0; y < image_height; ++y){
             for (int x = 0; x < image_width; ++x){
                 color = Color.rgb((int) (Math.random()*255),(int)(Math.random()*255),(int)(Math.random()*255));
                 //color = Color.rgb(colorVec[0],colorVec[1],colorVec[2]);
                 pixelWriter.setColor(x, y, color);

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

}
