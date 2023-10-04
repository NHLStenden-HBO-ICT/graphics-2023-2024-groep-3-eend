package proeend.misc;

import java.util.HashSet;
import java.util.Set;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import proeend.ScatterRecord;
import proeend.hittable.Hittable;
import proeend.hittable.HittableList;
import proeend.material.Normal;
import proeend.material.pdf.CosPDF;
import proeend.material.pdf.HittablePDF;
import proeend.material.pdf.MixturePDF;
import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Deze klasse vertegenwoordigt een camera voor het renderen van beelden.
 */
public class Camera {

    public boolean block;
    static int frames = 0;
    private Vector u,v,w;
    public Vector cameraCenter = new Vector(0,0,0);
    public Vector lookat = new Vector(0,0,-1);
    public Vector up = new Vector(0,1,0);
    public double verticalFOV = Math.PI/2; //in radialen
    public int samplesPerPixel = 100;
    private int rootSPP = (int) Math.sqrt(samplesPerPixel);
    public int maxDepth = 50;
    public double aspectRatio = 16.0/9.0;
    public int imageWidth = 1920;
    public Vector background = new Vector();
    public int imageHeight = (int)(imageWidth /aspectRatio);
    public double focalLength = 1.0;
    private double viewportHeight;
    public double viewportWidth = viewportHeight * (double) imageWidth /(double) imageHeight;
    public Vector viewportU = new Vector(viewportWidth,0,0);
    public Vector viewportV = new Vector(0,-viewportHeight,0);
    public Vector pixelDeltaU = Vector.scale((1.0/ imageWidth), viewportU);
    public Vector pixelDeltaV = Vector.scale((1.0/ imageHeight), viewportV);
    public Vector viewportUpperleft = Vector.add(Vector.add(Vector.add(cameraCenter, Vector.inverse(new Vector(0,0,focalLength))),
            Vector.inverse(Vector.scale(1.0/2.0,viewportU))), Vector.inverse(Vector.scale(1.0/2.0,viewportV)));
    public Vector pixel00 = Vector.add(viewportUpperleft, Vector.scale(1.0/2.0, Vector.add(pixelDeltaU,pixelDeltaV)));

    /**
     * Geeft de hoogte van het beeld terug.
     *
     * @return De hoogte van het beeld.
     */

    public double getHeight() {
        return imageHeight;
    }
    /**
     * Initialiseert de camera-instellingen.
     */
    private void init() {
        rootSPP = (int) Math.sqrt(samplesPerPixel);
        focalLength = Vector.length(Vector.add(cameraCenter, Vector.inverse(lookat)));
        imageHeight = (int)(imageWidth /aspectRatio);
        imageHeight = imageHeight < 1 ? 1 : imageHeight;
        double h = Math.tan(verticalFOV/2);
        w = Vector.unitVector(Vector.add(cameraCenter, Vector.inverse(lookat)));
        u = Vector.unitVector(Vector.cross(up, w));
        v = Vector.cross(w,u);
        viewportHeight = 2*h*focalLength;
        viewportWidth = viewportHeight * (double) imageWidth /(double) imageHeight;
        viewportU = Vector.scale(viewportWidth, u);
        viewportV = Vector.scale(viewportHeight, Vector.inverse(v));
        pixelDeltaU = Vector.scale((1.0/ imageWidth), viewportU);
        pixelDeltaV = Vector.scale((1.0/ imageHeight), viewportV);
        viewportUpperleft = Vector.add(Vector.add(Vector.add(cameraCenter, Vector.inverse(Vector.scale(focalLength, w))),
                Vector.inverse(Vector.scale(1.0/2.0,viewportU))), Vector.inverse(Vector.scale(1.0/2.0,viewportV)));
        pixel00 = Vector.add(viewportUpperleft, Vector.scale(1.0/2.0, Vector.add(pixelDeltaU,pixelDeltaV)));
    }

    /**
     * Slaat het gegenereerde beeld op als een PNG-bestand.
     *
     * @param image Het te opslaan beeld.
     * @throws IOException Als er een fout optreedt bij het opslaan van het beeld.
     */
    public static void saveImage(WritableImage image) throws IOException{
        BufferedImage bufferedImage = new BufferedImage((int)image.getWidth(), (int)image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < (int) image.getWidth(); x++) {
            for (int y = 0; y < (int) image.getHeight(); y++) {
                int argb = image.getPixelReader().getArgb(x, y);
                bufferedImage.setRGB(x, y, argb);
            }
        }
        File output = new File("render.png");
        ImageIO.write(bufferedImage, "png", output);
        System.out.println("opgeslagen");
    }

    /**
     * rendert plaatje met voorwaarden vanuit het camera object
     * @param save
     * of het plaatje moet worden opgeslagen
     * @param world
     * de wereld die gerenderd wordt
     * @return
     * een WritableImage, voor een ImageView in de UI of om opgeslagen te worden
     */
    public WritableImage render(boolean save, Hittable world, Hittable lights){

        init();
        block = true;
        WritableImage writableImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        //Color color;
        //double[] colorVec = new Vec(Math.random(),Math.random(),Math.random()).toColor();
        for (int y = 0; y < imageHeight; ++y){
            if (save)
                System.out.println(Integer.toString(imageHeight - y)+ " lines to go");

             for (int x = 0; x < imageWidth; ++x) {
                 Vector colorVec = new Vector();

                 //nieuwe AA, een stuk minder snell, maar wel beter
                 for (int sy = 0; sy < rootSPP; ++sy) {
                     for (int sx = 0; sx < rootSPP; ++sx) {
                         Ray ray = getRay(x,y,sx,sy);
                         colorVec = Vector.add(colorVec, rayColor(ray, maxDepth, world, lights));
                     }
                 }

                 /* oude AA
                 for (int sample = 0; sample < samplesPerPixel; ++sample) {
                     Ray ray = getRay(x, y);
                     //colorVec = new Vec();
                     colorVec = Vector.add(colorVec, rayColor(ray, maxDepth, world));
                 }*/

                 int[] colors = colorVec.toColor(samplesPerPixel, save);
                 pixelWriter.setColor(x, y, Color.rgb(colors[0], colors[1], colors[2]));

             }
        }
        System.out.println("frame " + frames);
        frames++;
        block = false;
        if (save) {
            try {
                saveImage(writableImage);
            } catch (IOException e) {e.printStackTrace();}
        }
        return writableImage;
    }

    public int[] calculateStartAndEnd(int numberOfThreads, int threadCounter, int imageHeight) {
        int startPixel = imageHeight / numberOfThreads * threadCounter;
        int endPixel = imageHeight / numberOfThreads * (threadCounter + 1);

        return new int[]{startPixel, endPixel};
    }

    WritableImage writableImage = new WritableImage(imageWidth, imageHeight);// /threads
    PixelWriter pixelWriter = writableImage.getPixelWriter();
    public WritableImage multiThreadRender(HittableList world, Hittable lights) {
        init();
        int beschikbareProcessors = Runtime.getRuntime().availableProcessors();
        System.out.println("Aantal beschikbare processors: " + beschikbareProcessors);
        // Bereken het aantal threads dat je wilt maken (bijv. helft van beschikbare processors)
        int numberOfThreads = beschikbareProcessors / 2;

        int[] threadCounter = {0}; // Use an array to store the counter


        Runnable taak = () -> {
            synchronized (threadCounter) {
                threadCounter[0]++;
            }
            WritableImage image;
            image = multiTaak(true,world,numberOfThreads,threadCounter[0],lights);

            try {
                Camera.saveImage(image);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        for (int i = 0; i < numberOfThreads - 1; i++) {
            Thread thread = new Thread(taak);
            thread.start();
            //launch();
        }
        return writableImage;
    }

public WritableImage multiTaak(boolean save, Hittable world, int threads, int threadCounter, Hittable lights){
    int[] startAndEnd = calculateStartAndEnd(threads, threadCounter, imageHeight);
    int startPixelY = startAndEnd[0];
    int endPixelY = startAndEnd[1];

    for (int y = startPixelY; y < endPixelY; ++y) {
        System.out.println(Integer.toString(endPixelY - y) + " lines to go op Thread: " + threadCounter);

        for (int x = 0; x < imageWidth; ++x) {
            Vector colorVec = new Vector();

            //nieuwe AA, een stuk minder snell, maar wel beter
            for (int sy = 0; sy < rootSPP; ++sy) {
                for (int sx = 0; sx < rootSPP; ++sx) {
                    Ray ray = getRay(x, y, sx, sy);
                    colorVec = Vector.add(colorVec, rayColor(ray, maxDepth, world, lights));
                }
            }
            int[] colors = colorVec.toColor(samplesPerPixel, true);
            try{
                synchronized (pixelWriter) {
                    pixelWriter.setColor(x, y, Color.rgb(colors[0], colors[1], colors[2]));
                }
            }catch (Exception ex){
                System.out.println(ex.toString());
            }


        }
    }
    return writableImage;
}



    private Ray getRay(int x, int y, int sx, int sy) {
        Vector pixelCenter = Vector.add(Vector.add(pixel00, Vector.scale(x, pixelDeltaU)), Vector.scale(y, pixelDeltaV));
        Vector pixelSample = Vector.add(pixelCenter, pixelSampleSquare(sx, sy));

        Vector rayOrigin = cameraCenter;
        Vector direction = Vector.add(pixelSample, Vector.inverse(rayOrigin));
        return new Ray(rayOrigin, direction);

    }
    private Vector pixelSampleSquare(int sx, int sy) {
        double px = -.5 + 1.0/rootSPP * (sx + Math.random());
        double py = -.5 + 1.0/rootSPP * (sy + Math.random());
        return Vector.add(Vector.scale(px, pixelDeltaU), Vector.scale(py, pixelDeltaV));
    }

    /**
     * Geeft een ray terug die door het opgegeven pixel gaat.
     *
     * @param x De x-coördinaat van het pixel.
     * @param y De y-coördinaat van het pixel.
     * @return Een Ray-object dat het pixel vertegenwoordigt.
     */
    private Ray getRay(int x, int y) {
        Vector pixelcenter = Vector.add(Vector.add(pixel00, Vector.scale(x, pixelDeltaU)), Vector.scale(y, pixelDeltaV));
        Vector pixelSample = Vector.add(pixelcenter, pixelSampleSquare());

        Vector rayOrigin = cameraCenter;
        Vector direction = Vector.add(pixelSample, Vector.inverse(rayOrigin));
        return new Ray(rayOrigin, direction);

    }

    /**
     * Genereert een willekeurige pixelverschuiving voor anti-aliasing.
     *
     * @return Een Vector die de pixelverschuiving vertegenwoordigt.
     */
    private Vector pixelSampleSquare() {
        double px = -.5 + Math.random();
        double py = -.5 + Math.random();
        return Vector.add(Vector.scale(px, pixelDeltaU), Vector.scale(py, pixelDeltaV));
    }
    public WritableImage render(Hittable world, Hittable lights) {
        return render(false, world, lights);
    }

    /**
     * Berekent de kleur van een ray in de scene met een maximaal aantal reflecties.
     *
     * @param r     De ray om te traceren.
     * @param depth Het maximum aantal reflecties.
     * @param world Het 3D-wereldobject dat moet worden weergegeven.
     * @return Een Vector die de kleur van de ray vertegenwoordigt.
     */
    private Vector rayColor(Ray r, int depth, Hittable world) {
        // Controleer of er geen wereld is (bijv. achtergrondkleur)
        return new Vector();
    }

    private Vector rayColor(Ray r, int depth, Hittable world, Hittable lights) {
        if (world == null) {
            return Vector.randomVec();
        }

        // Controleer of het maximumaantal reflecties is bereikt
        if (depth <= 0) {
            //System.out.println("hit");
            return new Vector(.0, .0, .0);
        }
            // Maak een HitRecord om gegevens over het getroffen object op te slaan
            HitRecord rec = new HitRecord();
            r.direction = Vector.unitVector(r.direction);
            if (!world.hit(r, new Interval(0.00000001, Double.POSITIVE_INFINITY), rec))

                // Controleer of de ray een object in de wereld raakt
                // Materiaal wordt onder water ook ingesteld in de hit methode van een object zoals sphere.
                if (!world.hit(r, new Interval(0.00000001, Double.POSITIVE_INFINITY), rec)) {

                    // TODO: In de world.hit functie wordt het materiaal gezet, dit moet even netjes.
                    // TODO: Render afstand in de interval kunnen aanpassen om render te versnellen.
                    // Geen raakpunt, retourneer de achtergrondkleur
                    return background;
                }

            ScatterRecord scatterRecord = new ScatterRecord();
            Vector emissionColor = rec.material.emit(r, rec, rec.u, rec.v, rec.p);
            if (!rec.material.scatter(r, rec, scatterRecord)) {
                if (rec.material instanceof Normal) {
                    return Vector.scale(.5, new Vector(rec.normal.x() + 1,
                            rec.normal.y() + 1, rec.normal.z() + 1));
                }
                return emissionColor;
            }
            if (scatterRecord.skipPDF) {
                return Vector.multiply(scatterRecord.attenuation, rayColor(scatterRecord.skipRay,
                        depth - 1, world, lights));
            }

            HittablePDF lightPDF = new HittablePDF(lights, rec.p);
            CosPDF surfacePDF = new CosPDF(rec.normal);
            MixturePDF mixPDF = new MixturePDF(lightPDF, surfacePDF);

            Ray scattered = new Ray(rec.p, mixPDF.generate());
            double pdfVal = mixPDF.value(scattered.direction());
            double scatteringPDF = rec.material.scatteringPDF(r, rec, scattered);
            //double scatteringPDF = rec.material.scatteringPDF(r, rec, scattered);
            //double pdf = scatteringPDF;
            //blijkbaar mag je floats(...) wel delen door nul...
            Vector scatterColor = Vector.scale(1.0 / pdfVal,
                    Vector.multiply(Vector.scale(scatteringPDF, scatterRecord.attenuation), rayColor(scattered, depth - 1, world, lights)));

            return Vector.add(emissionColor, scatterColor);
        }
        }
