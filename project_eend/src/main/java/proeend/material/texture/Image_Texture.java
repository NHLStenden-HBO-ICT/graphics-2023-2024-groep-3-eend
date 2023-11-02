package proeend.material.texture;

import proeend.material.texture.Image;
import proeend.math.Interval;
import proeend.math.Vector;


public class Image_Texture extends Texture{
    private Image image;
    //Constructor maakt nieuwe image aan
    public Image_Texture(String imageFilename){
        this.image = new Image(imageFilename);
    }

    /** Geeft de RGB waardes terug van een snijpunt
     * @param u Is coordinaat 1 van het snijpunt
     * @param v Is coordinaat 2 van het snijpunt
     * @param p Is de driedimentionale positie van het punt
     * @return een Vector met 3 waardes
     */
    @Override
    public Vector value(double u, double v, Vector p) {
        if (image.height() <= 0){
            return new Vector(0,1,1);
        }
        //u coordinaat van een punt clampen zodat de waarde niet buiten de range word
        u = new Interval(0,1).clamp(u);
        //y zie u
        v = 1.0 - new Interval(0,1).clamp(v);

        //Doe de punten * de hoogte of breedte
        int i = (int)(u * image.width());
        int j = (int)(v * image.height());
        //Geef het punt mee en vraagt om de data van dat punt in klasse image
        int[] pixel = image.pixelData(i,j);

        //Verandert de Rgb scale naar de waarde die Utility snapt(0/1) ipv (0/255)
        double colorScale = 1.0 / 255.0;
        //Geeft alle rgb waarden terug in de andere waarde
        return new Vector(colorScale * pixel[0], colorScale*pixel[1], colorScale*pixel[2]);

    }
}
