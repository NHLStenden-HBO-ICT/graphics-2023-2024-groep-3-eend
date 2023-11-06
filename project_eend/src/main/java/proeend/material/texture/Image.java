package proeend.material.texture;

import javafx.scene.paint.Color;
import proeend.material.Material;
import proeend.math.Vector;


/**
 * Deze klasse is voor afbeeldingen en functionaliteiten voor de gegevens van die afbeeldingen
 */
public class Image extends Material {

    private final javafx.scene.image.Image image;

    public javafx.scene.image.Image getImage() {
        return image;
    }

    public Image(javafx.scene.image.Image image){
        this.image = image;
    }

    /**
     * Geeft de pixelgegevens van specifieke x y locatie in de 2d afbeelding terug.
     *
     * @param x De x-coordinaat van de pixel in de afbeelding.
     * @param y De y-coordinaat van de pixel in de afbeelding.
     * @return Een vector die de kleurgegevens van de pixel bevat in RGB.(0/255)(0/255)(0/255)
     */
    public Vector pixelData(int x, int y){
        //Als er geen foto is of de verkeerde bestandstype dan word er een standaard kleur teruggegeven. In dit geval magenta.
        if (image == null){
            return new Vector(255, 0, 255);
        }

        Color pixel = image.getPixelReader().getColor(x,y);

        return new Vector(pixel.getRed(), pixel.getGreen(), pixel.getBlue());
    }

    /**
     * Beperkt een waarde tot het opgegeven bereik.
     *
     * @param xy De waarde om te beperken. Dit kan óf x zijn óf y die meegegeven word.
     * @param low De ondergrens van het bereik.
     * @param high de Bovengrens van het bereik.
     * @return De waarde, beperkt tot het opgegeven bereik.
     */
    int clamp(int xy, int low, int high){

        if (high > low){
            if (xy < low){
                return low;
            } else if (xy < high) {
                return xy;
            } else {
                return high - 1;
            }
        }else {System.out.println("ERROR: De waardes die bij clamp zijn meegegeven zijn fout en verkeerd om: Low = "+ low + " ,High = " + high );
        return 0;}
    }














}
