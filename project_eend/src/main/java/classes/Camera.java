package classes;

import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Image;

public class Camera {

    public double aspectRatio = 1.0;
    public int image_width = 256;
    private int image_height;

    private void init() {
        image_height = (int)(image_width/aspectRatio);
        image_height = (image_height<1)? 1 : image_height;

    }

    //gooit een plaatje 'render.ppm' in de src folder
    public void render(){

        init();

        BufferedImage bufferedImage = new java.awt.image.BufferedImage(image_width, image_height, BufferedImage.TYPE_INT_RGB);

        for (int j = 0; j < image_height; ++j){
             for (int i = 0; i < image_width; ++i){

                 bufferedImage.setRGB(j, i, new java.awt.Color(j,i,0).getRGB());
            }
        }

        try {
            File output = new File("render.png");
            ImageIO.write(bufferedImage, "png", output);

        } catch (IOException e){
            e.printStackTrace();
            }
        System.out.println("image gerenderd");
    }

}
