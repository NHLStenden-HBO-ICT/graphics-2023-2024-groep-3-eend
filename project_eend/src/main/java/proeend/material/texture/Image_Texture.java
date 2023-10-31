package proeend.material.texture;

import proeend.material.texture.Image;
import proeend.math.Interval;
import proeend.math.Vector;


public class Image_Texture extends Texture{
    private Image image;
    public Image_Texture(String imageFilename){
        this.image = new Image(imageFilename);
    }
    @Override
    public Vector value(double u, double v, Vector p) {
        if (image.height() <= 0){
            return new Vector(0,1,1);
        }

        u = new Interval(0,1).clamp(u);
        v = 1.0 - new Interval(0,1).clamp(v);

        int i = (int)(u * image.width());//data u is 0.0 dus i zal 0 blijven
        int j = (int)(v * image.height());
        int[] pixel = image.pixelData(i,j);

        double colorScale = 1.0 / 255.0;

        return new Vector(colorScale * pixel[0], colorScale*pixel[1], colorScale*pixel[2]);

    }
}
