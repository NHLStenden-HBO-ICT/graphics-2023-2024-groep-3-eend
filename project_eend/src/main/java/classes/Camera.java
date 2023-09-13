package classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
        String image = "P3\n" + image_width + " " + image_height + "\n" + "255\n";
        File output = new File("render.ppm");

        for (int j = 0; j < image_height; ++j){
            System.out.println("Lines remaining "+Integer.toString(image_height-j));
            for (int i = 0; i < image_width; ++i){
                image = image + Integer.toString(i) + " " + Integer.toString(j) + " 0" + "\n";
            }
        }

        try {
            FileWriter writer = new FileWriter(output);
            writer.write(image);
            writer.close();
            //System.out.println(image);
        } catch (IOException e){
            e.printStackTrace();
            }

        String currentPath = null;
        try {
            currentPath = new File(".").getCanonicalPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Current dir:" + currentPath);
        System.out.println("image gerenderd");
    }

}
