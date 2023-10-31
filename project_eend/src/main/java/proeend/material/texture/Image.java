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

    /**
     *
     * @param imageFilename De naam van het afbeeldingsbestand om te laden.
     * @throws IOException Als er een fout optreed bij t laden van de foto.
     */
    public boolean load(String imageFilename){
        try{

            // Laden van afbeeldingsgegevens uit het opgegeven bestand
            byte[] imageBytes = Files.readAllBytes(Path.of(imageFilename));
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
            image = ImageIO.read(byteArrayInputStream);
            raster = image.getData();

            if (image == null){
                data = null;
                return false;
            }

            imageWidth = image.getWidth();
            imageHeight = image.getHeight();

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
     * Geeft de pixelgegevens van specifieke locatie in de afbeelding terug.
     *
     * @param x De x-coordinaat van de pixel.
     * @param y De y-coordinaat van de pixel.
     * @return Een array van bytes dat de kleurgegevens van de pixel bevat in RGB.(Uiteindelijk in 0 t/m 1)
     */
    public int[] pixelData(int x, int y){
        if (image == null){
            return new int[] { 255, 0, 255};
        }

        //int cx = clamp(x, 0, imageWidth);
        //int cy = clamp(y, 0, imageHeight);

        //int pixelIndex = (cy * bytesPerScanLine) + (cx);


        int numBands = raster.getNumBands();
        byte[] pixelData = new byte[numBands];
        
        int[] pixel = new int[3];

       raster.getPixel(x,y, pixel);


        return pixel;
    }

    /**
     * Beperkt een waarde tot het opgegeven bereik.
     *
     * @param xy De waarde om te beperken.
     * @param low De ondergrens van het bereik.
     * @param high de Bovengrens van het bereik.
     * @return De waarde, beperkt tot het opgegeven bereik.
     */
    private int clamp(int xy ,int low , int high){
        if (xy < low){
            return low;
        } else if (xy < high) {
            return xy;
        } else {
            return high - 1;
        }
    }














}
