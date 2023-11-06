package proeend.material.texture;

import proeend.math.Interval;
import proeend.math.Vector;


public class Image_Texture extends Texture{
    private final Image image;
    //Constructor maakt nieuwe image aan
    public Image_Texture(javafx.scene.image.Image image){
        this.image = new Image(image);
    }

    /** Geeft de RGB waardes terug van een snijpunt
     * @param u Is coordinaat 1 van het snijpunt
     * @param v Is coordinaat 2 van het snijpunt
     * @param p Is de driedimentionale positie van het punt
     * @return een Vector met 3 waardes
     */
    @Override
    public Vector value(double u, double v, Vector p) {
        if (image.getImage().getHeight() <= 0){
            return new Vector(0,1,1);
        }

        u = new Interval(0,1).clamp(u);
        v = 1.0 - new Interval(0,1).clamp(v);

        //Doe de punten * de hoogte of breedte
        int i = (int)(u * image.getImage().getWidth());
        int j = (int)(v * image.getImage().getHeight());

        return image.pixelData(i,j);

    }
}
