package classes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Camera {

    public static int image_width = 256;
    public static int image_height = 256;

    //gooit een plaatje 'render.ppm' in de src folder
    public static void render(){

        String image = "P3\n" + image_width + " " + image_height + "\n" + "255\n";
        File output = new File("render.ppm");

        for (int j = 0; j < image_height; ++j){
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
