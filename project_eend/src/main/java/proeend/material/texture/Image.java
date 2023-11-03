package proeend.material.texture;

import proeend.material.Material;

import java.awt.image.Raster;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.file.Paths;


/**
 * Deze klasse is voor afbeeldingen en functionaliteiten voor de gegevens van die afbeeldingen
 */
public class Image extends Material {

    private byte[] data;
    private BufferedImage image;
    private int imageWidth;
    private int imageHeight;
    private int bytesPerScanLine;
    private final int bytesPerPixel = 3;
    private Raster raster;

    public Image(){
        data = null;
    }

    public Image(String imageFilename){
        this();
        load(imageFilename);
    }

    /** Laad alle gegevens van de gegeven foto van type jpg of jpeg
     *
     * @param imageFilename De naam van het afbeeldingsbestand om te laden.
     * @throws IOException Als er een fout optreed bij t laden van de foto.
     *
     */
    public boolean load(String imageFilename){
        try{

            // Laden van afbeeldingsgegevens uit het opgegeven bestand
            byte[] imageBytes = Files.readAllBytes(Path.of(imageFilename));
            //Vang het op en stop het in een array
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
            //Geef de info aan een BufferedImage zodat die data wel ingelezen kan worden (reden hiervoor is omdat jpg en jpeg gecomprimeerde bestanden zijn)
            image = ImageIO.read(byteArrayInputStream);
            //Lees die info en stop dat in een raster
            raster = image.getData();

            //Als er geen foto gegeven word is data null
            //Ook als de foto van het verkeerde type is word er null terug gegeven
            if (image == null){
                data = null;
                return false;
            }
            //imageWidth wordt de groote van de gebruikte foto
            imageWidth = image.getWidth();
            //Zie imageWidth
            imageHeight = image.getHeight();
            //Word verdeeld in Bytes per line zodat alle data in 1 lange Array kan gezet worden
            bytesPerScanLine = imageWidth * bytesPerPixel;

        }catch (IOException e){
            System.out.println("ERROR: Kon afbeelding niet laden: " + imageFilename);
        }
        return image != null;
    }


    /**
     * Geeft de breedte van de afbeelding terug.
     * @Return De breedte van de afbeelding.
     */
    public int width(){  return imageWidth; }

    /**
     * Geeft de hoogte van de afbeelding terug.
     * @return De hoogte van de afbeelding.
     */
    public int height(){  return imageHeight; }

    /**
     * Geeft de pixelgegevens van specifieke x y locatie in de 2d afbeelding terug.
     *
     * @param x De x-coordinaat van de pixel in de afbeelding.
     * @param y De y-coordinaat van de pixel in de afbeelding.
     * @return Een array van bytes dat de kleurgegevens van de pixel bevat in RGB.(0/255)(0/255)(0/255)
     */
    public int[] pixelData(int x, int y){
        //Als er geen foto is of de verkeerde bestandstype dan word er een standaard kleur teruggegeven. In dit geval magenta.
        if (image == null){
            return new int[] { 255, 0, 255};
        }


        //De array die de RGB waarde opslaat van de gekozen pixel
        int[] pixel = new int[3];

        //getPixel haalt de data op van de gekozen pixel
        //x,y is de gekozen pixel en pixel wordt de RGB waarden
        raster.getPixel(x,y, pixel);

        //Stuur de RGB waarden terug die je hebt opgehaald
        return pixel;
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
