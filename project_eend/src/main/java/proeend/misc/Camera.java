package proeend.misc;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import proeend.hittable.Hittable;
import proeend.material.Normal;
import proeend.material.pdf.CosPDF;
import proeend.material.pdf.HittablePDF;
import proeend.material.pdf.MixturePDF;
import proeend.math.ColorParser;
import proeend.math.Interval;
import proeend.math.Ray;
import proeend.math.Vector;
import proeend.records.HitRecord;
import proeend.records.ScatterRecord;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Deze klasse vertegenwoordigt een camera voor het renderen van beelden.
 */
public class Camera {

    // Toolkit is een klasse voor het beheren van systeembronnen
    Toolkit toolkit = Toolkit.getDefaultToolkit();

    // De dimensie van het scherm ophalen
    Dimension screenSize = toolkit.getScreenSize();

    // De schermbreedte ophalen
    int screenWidth = screenSize.width;
    public boolean block;
    static int frames = 0;
    private Vector u,v,w;
    private Vector cameraCenter = new Vector(0,0,0);
    private Vector lookat = new Vector(0,0,-1);
    private Vector up = new Vector(0,1,0);
    private double verticalFOV = Math.PI/2; //in radialen
    private int samplesPerPixel = 100;
    private int rootSPP = (int) Math.sqrt(samplesPerPixel);
    private int maxDepth = 50;
    private double aspectRatio = 16.0/9.0;
    private int imageWidth = 800;
    private int imageHeight = (int)(imageWidth /aspectRatio);
    private double focalLength = 1.0;
    private double viewportHeight;
    private double viewportWidth = viewportHeight * (double) imageWidth /(double) imageHeight;
    private Vector viewportU = new Vector(viewportWidth,0,0);
    private Vector viewportV = new Vector(0,-viewportHeight,0);
    private Vector pixelDeltaU = Vector.scale((1.0/ imageWidth), viewportU);
    private Vector pixelDeltaV = Vector.scale((1.0/ imageHeight), viewportV);
    private Vector viewportUpperleft = Vector.add(Vector.add(Vector.add(cameraCenter, Vector.negate(new Vector(0,0,focalLength))),
            Vector.negate(Vector.scale(1.0/2.0,viewportU))), Vector.negate(Vector.scale(1.0/2.0,viewportV)));
    private Vector pixel00 = Vector.add(viewportUpperleft, Vector.scale(1.0/2.0, Vector.add(pixelDeltaU,pixelDeltaV)));
    private Vector background = new Vector();

    public Vector getCameraCenter() {
        return cameraCenter;
    }

    public void setCameraCenter(Vector cameraCenter) {
        this.cameraCenter = cameraCenter;
    }

    public Vector getLookat() {
        return lookat;
    }

    public void setLookat(Vector lookat) {
        this.lookat = lookat;
    }

    public Vector getUp() {
        return up;
    }

    public void setUp(Vector up) {
        this.up = up;
    }

    public double getVerticalFOV() {
        return verticalFOV;
    }

    public void updateVerticalFOV(double difference) {
        this.verticalFOV += difference;
    }

    public int getSamplesPerPixel() {
        return samplesPerPixel;
    }

    public void setSamplesPerPixel(int samplesPerPixel) {
        this.samplesPerPixel = samplesPerPixel;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Vector getBackground() {
        return background;
    }

    public void setBackground(Vector background) {
        this.background = background;
    }

    public Camera(){
    }

    public double getHeight() {
        return imageHeight;
    }

    /**
     * Initialiseert de camera-instellingen.
     */
    private void init() {
        rootSPP = (int) Math.sqrt(samplesPerPixel);
        focalLength = Vector.length(Vector.add(cameraCenter, Vector.negate(lookat)));
        imageHeight = (int)(imageWidth /aspectRatio);
        imageHeight = Math.max(1, imageHeight);
        double h = Math.tan(verticalFOV/2);
        w = Vector.unitVector(Vector.add(cameraCenter, Vector.negate(lookat)));
        u = Vector.unitVector(Vector.cross(up, w));
        v = Vector.cross(w,u);
        viewportHeight = 2*h*focalLength;
        viewportWidth = viewportHeight * (double) imageWidth /(double) imageHeight;
        viewportU = Vector.scale(viewportWidth, u);
        viewportV = Vector.scale(viewportHeight, Vector.negate(v));
        pixelDeltaU = Vector.scale((1.0/ imageWidth), viewportU);
        pixelDeltaV = Vector.scale((1.0/ imageHeight), viewportV);
        viewportUpperleft = Vector.add(Vector.add(Vector.add(cameraCenter, Vector.negate(Vector.scale(focalLength, w))),
                Vector.negate(Vector.scale(1.0/2.0,viewportU))), Vector.negate(Vector.scale(1.0/2.0,viewportV)));
        pixel00 = Vector.add(viewportUpperleft, Vector.scale(1.0/2.0, Vector.add(pixelDeltaU,pixelDeltaV)));
    }

    /**
     * Slaat het gegenereerde beeld op als een PNG-bestand.
     * @param image Het te opslaan beeld.
     * @throws IOException Als er een fout optreedt bij het opslaan van het beeld.
     */
    public void saveImage(WritableImage image) throws IOException{
        BufferedImage bufferedImage = new BufferedImage((int)image.getWidth(), (int)image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < (int) image.getWidth(); x++) {
            for (int y = 0; y < (int) image.getHeight(); y++) {
                int argb = image.getPixelReader().getArgb(x, y);
                bufferedImage.setRGB(x, y, argb);
            }
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss");
        LocalDateTime now = LocalDateTime.now();
        File output = new File("renders/"+dtf.format(now)+"_"+imageWidth+"x"+imageHeight+"_s"+samplesPerPixel+".png");
        ImageIO.write(bufferedImage, "png", output);
        System.out.println("opgeslagen");
    }

    /**
     * Hetzelfde als (multi)render, maar dan wordt voor elke lijn een thread aangemaakt, met maximaal standaard 5 threads.
     * Lijkt de snelste render methode te zijn in de meeste situaties.
     */
    public void multiRenderLines(boolean save, final Hittable world, final Hittable lights) {
        init();
        block = true;
        WritableImage writableImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        //TODO bedenk of er een extra numberOfThreads parameter toegevoegd kan worden
        int numberOfThreads = 5;
        int[] activethreads = {0};
        ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
        class WorkerThread implements Runnable {
            private final int line;
            public WorkerThread(int line) {
                this.line = line;
            }
            @Override
            public void run() {
                int y = line;

                if (save) {
                    System.out.println(imageHeight - y + " lines left");
                }
                for (int x = 0; x < imageWidth; ++x) {
                    Vector colorVec = new Vector();
                    for (int sy = 0; sy < rootSPP; ++sy) {
                        for (int sx = 0; sx < rootSPP; ++sx) {
                            Ray ray = getRay(x,y,sx,sy);
                            colorVec = Vector.add(colorVec, rayColor(ray, maxDepth, world, lights));
                        }
                    }
                    int[] colors = ColorParser.toColor(samplesPerPixel, save, colorVec);
                    synchronized (pixelWriter) {
                        pixelWriter.setColor(x, y, Color.rgb(colors[0], colors[1], colors[2]));
                    }
                }
                synchronized (activethreads) {
                    activethreads[0]--;
                }
            }
        }

        int[] line = {0};
        while (line[0] < imageHeight) {
            if (activethreads[0] < numberOfThreads) {
                synchronized (activethreads) {
                    activethreads[0]++;
                }
                executorService.submit(new WorkerThread(line[0]));
                synchronized (line) {
                    line[0]++;
                }
            }
            //TODO vervang door notify/wait achtig iets
            //als er hier niks staat houdt de while loop op
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //////////////////////////////////////////////////////////////////////////////////////////
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(1,TimeUnit.DAYS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        if (save) {
            try {
                saveImage(writableImage);
            } catch (IOException e) {e.printStackTrace();}
        }
    }

    /**
     * Rendert plaatje met voorwaarden vanuit het camera object.
     * @param save Bepaald of het plaatje moet worden opgeslagen.
     * @param world De wereld die gerenderd wordt.
     * @return Een WritableImage, voor een ImageView in de UI of om opgeslagen te worden.
     */
    public WritableImage render(boolean save, Hittable world, Hittable lights){

        long startTime = System.currentTimeMillis();

        init();
        block = true;

        WritableImage writableImage = new WritableImage(imageWidth, imageHeight);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

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

                 int[] colors = ColorParser.toColor(samplesPerPixel, save, colorVec);
                 pixelWriter.setColor(x, y, Color.rgb(colors[0], colors[1], colors[2]));
             }
        }
        System.out.println("frame " + frames);
        frames++;
        block = false;
        if (save) {
            try {
                saveImage(writableImage);
                long totalTime = System.currentTimeMillis() - startTime;
                System.out.println( "Single thread gerendered in " + totalTime  + "ms" );
            } catch (IOException e) {e.printStackTrace();}
        }
        return writableImage;
    }




    /**
     * Haalt de straal die geschoten wordt op.
     * @param x De x-as coördinaat.
     * @param y De y-as coördinaat.
     * @param sx De sample op de x-as, coördinaat.
     * @param sy De sample op de y-as, coördinaat.
     * @return Geeft een straal terug.
     */
    private Ray getRay(int x, int y, int sx, int sy) {
        Vector pixelCenter = Vector.add(Vector.add(pixel00, Vector.scale(x, pixelDeltaU)), Vector.scale(y, pixelDeltaV));
        Vector pixelSample = Vector.add(pixelCenter, pixelSampleSquare(sx, sy));

        Vector rayOrigin = cameraCenter;
        Vector direction = Vector.add(pixelSample, Vector.negate(rayOrigin));
        return new Ray(rayOrigin, Vector.unitVector(direction));
    }

    /**
     * Schaalt een vector aan de hand van het aantal samples per pixel.
     * @param sx De sample op de x-as, coördinaat.
     * @param sy De sample op de y-as, coördinaat.
     * @return Geeft een geschaalde vector terug.
     */
    private Vector pixelSampleSquare(int sx, int sy) {
        double px = -.5 + 1.0/rootSPP * (sx + Math.random());
        double py = -.5 + 1.0/rootSPP * (sy + Math.random());
        return Vector.add(Vector.scale(px, pixelDeltaU), Vector.scale(py, pixelDeltaV));
    }


    public WritableImage render(Hittable world, Hittable lights) {
        return render(false, world, lights);
    }

    /**
     * Berekent de kleur van een ray in de scene met een maximaal aantal reflecties.
     * @param r     De ray om te traceren.
     * @param depth Het maximum aantal reflecties.
     * @param world Het 3D-wereldobject dat moet worden weergegeven.
     * @return Een Vector die de kleur van de ray vertegenwoordigt.
     */
    private Vector rayColor(Ray r, int depth, Hittable world, Hittable lights) {
        if (world == null) {
            return Vector.randomVec();
        }

        // Controleer of het maximumaantal reflecties is bereikt
        if (depth <= 0) {
            //System.out.println("hit");
            return new Vector(0, 0, 0);
        }
            // Maak een HitRecord om gegevens over het getroffen object op te slaan
            HitRecord rec = new HitRecord();
            // r.direction = Vector.unitVector(r.direction);

            // Controleer of de ray een object in de wereld raakt
            // Materiaal wordt onder water ook ingesteld in de hit methode van een object zoals sphere.
            if (!world.hit(r, new Interval(0.00000001, Double.POSITIVE_INFINITY), rec)) {
                // Geen raakpunt
                return background;
            }

            ScatterRecord scatterRecord = new ScatterRecord();
            Vector emissionColor = rec.material.emit(r, rec, rec.u, rec.v, rec.p);
            if (!rec.material.scatter(r, rec, scatterRecord)) {
                if (rec.material instanceof Normal) {
                    return Vector.scale(.5, new Vector(rec.normal.getX() + 1,
                            rec.normal.getY() + 1, rec.normal.getZ() + 1));
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
            double pdfVal = mixPDF.value(scattered.getDirection());
            double scatteringPDF = rec.material.scatteringPDF(r, rec, scattered);

            Vector scatterColor = Vector.scale(1.0 / pdfVal,
                    Vector.multiply(Vector.scale(scatteringPDF, scatterRecord.attenuation), rayColor(scattered, depth - 1, world, lights)));

        return Vector.add(emissionColor, scatterColor);
    }
}
