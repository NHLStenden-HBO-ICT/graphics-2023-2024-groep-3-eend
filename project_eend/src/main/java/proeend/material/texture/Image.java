package proeend.material.texture;

import proeend.material.Material;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;



/**
 * Deze klasse is voor afbeeldingen en functionaliteiten voor de gegevens van die afbeeldingen
 */
public class Image extends Material {
    private byte[] data;
    private int imageWidth;
    private int imageHeight;
    private int bytesPerScanLine;
    private final int bytesPerPixel = 3;

    /**
     *
     * @param imageFilename De naam van het afbeeldingsbestand om te laden.
     * @throws IOException Als er een fout optreed bij t laden van de foto.
     */
    public Image(String imageFilename){
        try{
            // Laden van afbeeldingsgegevens uit het opgegeven bestand
            byte[] imageBytes = Files.readAllBytes(Path.of(imageFilename));

            imageWidth = 3; //Zet breedte op basis van geladen gegevens //Voorbeeld: Breedte is 3
            data = imageBytes; //De afbeeldinggegevens worden direct gekopieert
            bytesPerScanLine = imageWidth * bytesPerPixel;
        }catch (IOException e){
            System.out.println("ERROR: Kon afbeelding niet laden: " + imageFilename);
        }
    }

    /**
     * Geeft de breedte van de afbeelding terug.
     * @Return De breedte van de afbeelding.
     */
    public int width(){  return (data == null) ? 0 : imageWidth; }

    /**
     * Geeft de hoogte van de afbeelding terug.
     * @return De hoogte van de afbeelding.
     */
    public int height(){  return (data == null) ? 0 : imageWidth; }

    /**
     * Geeft de pixelgegevens van specifieke locatie in de afbeelding terug.
     *
     * @param x De x-coordinaat van de pixel.
     * @param y De y-coordinaat van de pixel.
     * @return Een array van bytes dat de kleurgegevens van de pixel bevat in RGB.(Uiteindelijk in 0 t/m 1)
     */
    public byte[] pixelData(int x, int y){
        if (data == null){ return new byte[] { (byte) 255, 0, (byte) 255};}

        x = clamp(x, 0, imageWidth);
        y = clamp(y, 0, imageHeight);

        int pixelIndex = y * bytesPerScanLine + x * bytesPerPixel;
        byte[] pixel = new byte[3];
        for (int i = 0; i < bytesPerScanLine; i++){
            pixel[i] = data[pixelIndex + i];
        }
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
    private int clamp(int xy ,int low ,int high){
        if (xy<low){
            return low;
        } else if (xy<high) {
            return xy;
        }else {return high -1;}
    }














}
