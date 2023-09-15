package classes;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Image;
import javafx.scene.paint.Color;
public class Camera {

    public double aspectRatio = 16.0/9.0;
    public int image_width = 256;
    private int image_height;

    public double getHeight() {
        return image_height;
    }
    private void init() {
        image_height = (int)(image_width/aspectRatio);
        image_height = (image_height<1)? 1 : image_height;

    }
    private void saveImage(BufferedImage image) throws IOException{
        File output = new File("render.png");
        ImageIO.write(image, "png", output);

    }

    //gooit een plaatje 'render.ppm' in de src folder
    public WritableImage render(boolean save){

        init();
        WritableImage writableImage = new WritableImage(image_width,image_height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        Color color;
        for (int y = 0; y < image_height; ++y){
             for (int x = 0; x < image_width; ++x){
                 color = Color.rgb(x%256,y%256,0);
                 pixelWriter.setColor(x, y, color);

            }
        }
        System.out.println("image gerenderd");
        return writableImage;
    }
    public WritableImage render() {
        return render(true);
    }

}
