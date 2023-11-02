package proeend.material.texture;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.awt.image.Raster;
import java.util.Optional;
import java.lang.Exception.*;

import static org.junit.jupiter.api.Assertions.*;

class ImageTest{
    private BufferedImage image;

    @Test
    void loadImageBytes() {
        @Testable
        Image image = new Image();
        String placeholder1 = "1.png";



    }

    /**
     * Test
     * Test of de methode werkt op de manier zoals het hoort
     *
     */
    @Test
    void loadWorldImageWithWrongImageType(){
        Image image = new Image();
        String placeholder2 = "../project_eend/ModelTextureImages/world.png";
        var verwachting2 = false;
        assertEquals(verwachting2, image.load(placeholder2));
    }

    @Test
    void loadWorldImageWithoutPath(){
        Image image = new Image();
        String placeholder2 = "world.jpeg";
        var verwachting2 = false;
        assertEquals(verwachting2, image.load(placeholder2));
    }
    @Test
    void loadZonImageWithCorrectPath(){
        Image image = new Image();
        String placeholder = "../project_eend/ModelTextureImages/zon.jpg";

        var verwachting = true;

        assertEquals(verwachting,image.load(placeholder));
    }
    @Test
    void loadZonImageWithFaultyPath(){
        Image image = new Image();
        String placeholder = "project_eend/Images/zon.jpg";
        var verwachting= false;
        assertEquals(verwachting,image.load(placeholder));
    }

    @Test
    void imageEqualsNullSetsDataNullAndThrowsNullPointerException(){
        Image image = new Image();
        String placeholder = null;

        assertThrows(java.lang.NullPointerException.class,
                () -> {
                    image.load(placeholder);
                });
    }


    /**
     * PixelData reken tests
     */


    /*
    @Test
    void ifThereIsNoImageGivenThenReturnsArrayWasNull() { //Krijg deze niet voor mekaar
        Image image = new Image();
        image.pixelData(20,20);
        int[] pixel = new int[3];
        for (int i = 0; i < 3;i++){
        pixel[i] = 0;
        }
        //assertEquals(null ,image.pixelData(20,20));
        assertArrayEquals(null,image.pixelData(20,20));
        /*assertThrows(org.opentest4j.AssertionFailedError.class,
                () -> {
                    image.pixelData(20,20);
                });*//*
        assertEquals(0,image.pixelData(20,20));

    }
    @Test
    void giveDoubleCoordinatesAndReturnNull() {
        Image image = new Image();
        image.load("../project_eend/ModelTextureImages/zon.jpg");
        image.pixelData((int)20.2,(int)20.3);
        //assertEquals(null ,image.pixelData(20,20));
        assertArrayEquals(null,image.pixelData(20,20));
    }*/

    /**
     * Clamp Tests
     * kan niet want is private
     */
    @Test //Nuttige Test
    void highIsHigherThanLowReturnsFaulty_xy(){ //Was private maar voor de test even verandert
        Image image = new Image();
        int high = 1;
        int low = 10;
        int xy = 4;
        //assertEquals(low,image.clamp(xy,low,high));//Orginele Uitslag
        //Dit willen we niet want dat is verkeerde waarde

        //assertEquals(xy,image.clamp(xy,low,high));//Uitslag na eerste aangepaste code

        assertEquals(0,image.clamp(xy,low,high));//Uitslag na tweede aangepaste code

    }

    @Test
    void xyIsntInTheRangeOfHighLow(){ //Was private maar voor de test even verandert
        Image image = new Image();
        int high = 10;
        int low = 1;
        int xy = 11;
        assertEquals(high - 1,image.clamp(xy,low,high));
        //Dit willen we niet want dat is verkeerde waarde

    }





}