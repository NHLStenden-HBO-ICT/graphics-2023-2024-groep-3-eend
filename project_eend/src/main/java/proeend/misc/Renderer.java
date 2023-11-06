package proeend.misc;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import proeend.hittable.Hittable;
import proeend.material.Normal;
import proeend.material.pdf.CosPDF;
import proeend.material.pdf.HittablePDF;
import proeend.material.pdf.MixturePDF;
import proeend.math.*;
import proeend.records.HitRecord;
import proeend.records.ScatterRecord;
/**
 * Deze klasse is verantwoordelijk voor het renderen van scènes en het genereren van afbeeldingen.
 */
public class Renderer {
    /**
     * Render een scène met behulp van een camera en retourneert een afbeelding.
     *
     * @param camera De camera die wordt gebruikt om de scène te bekijken.
     * @param save Geeft aan of de afbeelding moet worden opgeslagen.
     * @param world Het 3D-wereldobject dat moet worden weergegeven.
     * @param lights De lichtbronnen in de scène.
     * @return Een WritableImage dat de weergave van de scène bevat.
     */
    public static WritableImage render(Camera camera, boolean save, Hittable world, Hittable lights) {
        if(!save && camera.hasMovedSinceLastFrame()){
            camera.setSamplesPerPixel(1);
            camera.setImageWidth(400);
        }

        camera.init();
        ThreadController threadController = new ThreadController(1, camera, world, lights);
        return threadController.renderAndSave(save);
    }
    /**
     * Render een horizontale lijn van pixels in de afbeelding.
     *
     * @param camera De camera die wordt gebruikt om de scène te bekijken.
     * @param save Geeft aan of de afbeelding moet worden opgeslagen als bestand.
     * @param world De lijst met objecten die moet worden weergegeven.
     * @param lights De lijst met lichtbronnen in de scène.
     * @param y De y-coördinaat van de lijn om te renderen.
     * @param pixelWriter De PixelWriter voor het schrijven van pixelgegevens.
     */
    public static void renderHorizontalLine(Camera camera, boolean save, Hittable world, Hittable lights, int y, PixelWriter pixelWriter) {
        int imageWidth = camera.getImageWidth();
        Vector[] lineBuffer = new Vector[imageWidth];

        for (int x = 0; x < imageWidth; ++x) {
            Vector colorVec = new Vector();
            colorVec = renderPixel(camera, world, lights, y, x, colorVec);
            lineBuffer[x] = colorVec;
        }

        synchronized (pixelWriter) {
            for (int x = 0; x < imageWidth; ++x) {
                Vector colorVec = lineBuffer[x];
                Color color = ColorParser.toColor(camera.getSamplesPerPixel(), colorVec, save);
                pixelWriter.setColor(x, y, color);
            }
        }
    }
    /**
     * Render een pixel en bereken de kleur door middel van meerdere samples per pixel.
     *
     * @param camera De camera die wordt gebruikt voor het renderen.
     * @param world De lijst met objecten die moet worden weergegeven.
     * @param lights De lichtbronnen in de scène.
     * @param y De y-coördinaat van de pixel.
     * @param x De x-coördinaat van de pixel.
     * @param color De huidige kleur van de pixel.
     * @return Een Vector die de uiteindelijke kleur van de pixel vertegenwoordigt.
     */
    private static Vector renderPixel(Camera camera, Hittable world, Hittable lights, int y, int x, Vector color) {
        for (int sy = 0; sy < camera.getRootSPP(); ++sy) {
            for (int sx = 0; sx < camera.getRootSPP(); ++sx) {
                Ray ray = getRay(camera, x, y, sx, sy);
                color = color.add(rayColor(camera, ray, camera.getMaxDepth(), world, lights));
            }
        }
        return color;
    }
    /**
     * Haalt de straal die geschoten wordt op.
     *
     * @param x  De x-as coördinaat.
     * @param y  De y-as coördinaat.
     * @param sx De sample op de x-as, coördinaat.
     * @param sy De sample op de y-as, coördinaat.
     * @return Geeft een straal terug.
     */
    private static Ray getRay(Camera camera, int x, int y, int sx, int sy) {
        Vector pixelCenter = camera.getTopLeftPixel()
                .add(camera.getPixelDeltaU().scale(x))
                .add((camera.getPixelDeltaV().scale(y)));
        Vector pixelSample = pixelCenter.add(pixelSampleSquare(camera, sx, sy));
        Vector rayOrigin = camera.getCameraCenter();
        Vector direction = pixelSample.add(rayOrigin.invert());
        return new Ray(rayOrigin, direction.toUnitVector());
    }
    /**
     * Schaalt een vector aan de hand van het aantal samples per pixel.
     *
     * @param sx De sample op de x-as, coördinaat.
     * @param sy De sample op de y-as, coördinaat.
     * @return Geeft een geschaalde vector terug.
     */

    private static Vector pixelSampleSquare(Camera camera, int sx, int sy) {
        double px = -.5 + 1.0 / camera.getRootSPP() * (sx + FastRandom.random());
        double py = -.5 + 1.0 / camera.getRootSPP() * (sy + FastRandom.random());

        Vector deltaU = camera.getPixelDeltaU().scale(px);
        Vector deltaV = camera.getPixelDeltaV().scale(py);

        return deltaU.add(deltaV);
    }

    /**
     * Berekent de kleur van een ray in de scene met een maximaal aantal reflecties.
     *
     * @param camera De camera die wordt gebruikt om de scène te bekijken.
     * @param ray     De ray om te traceren.
     * @param depth Het maximum aantal reflecties.
     * @param world De lijst met objecten die moet worden weergegeven.
     * @param lights De lichtbronnen in de scène.
     * @return Een Vector die de kleur van de ray vertegenwoordigt.
     */
    private static Vector rayColor(Camera camera, Ray ray, int depth, Hittable world, Hittable lights) {
        if (world == null) {
            return Vector.randomVec();
        }

        // Controleer of het maximumaantal reflecties is bereikt
        if (depth <= 0) {
            return new Vector();
        }
        // Maak een HitRecord om gegevens over het getroffen object op te slaan
        HitRecord rec = new HitRecord();

        // Controleer of de ray een object in de wereld raakt
        // Materiaal wordt onder water ook ingesteld in de hit methode van een object zoals sphere.
        if (!world.hit(ray, new Interval(0.00000001, Double.POSITIVE_INFINITY), rec)) {
            return camera.getBackground();
        }

        ScatterRecord scatterRecord = new ScatterRecord();
        Vector emissionColor = rec.material.emit(ray, rec, rec.u, rec.v, rec.p);
        if (!rec.material.scatter(ray, rec, scatterRecord)) {
            if (rec.material instanceof Normal) {
                return new Vector(rec.normal.getX() + 1, rec.normal.getY() + 1, rec.normal.getZ() + 1).scale(0.5);
            }

            return emissionColor;
        }

        if (scatterRecord.skipPDF) {
            return Vector.multiply(scatterRecord.attenuation, rayColor(camera, scatterRecord.skipRay,
                    depth - 1, world, lights));
        }

        HittablePDF lightPDF = new HittablePDF(lights, rec.p);
        CosPDF surfacePDF = new CosPDF(rec.normal);
        MixturePDF mixPDF = new MixturePDF(lightPDF, surfacePDF);

        Ray scattered = new Ray(rec.p, mixPDF.generate());
        double pdfVal = mixPDF.value(scattered.getDirection());
        double scatteringPDF = rec.material.scatteringPDF(ray, rec, scattered);

        Vector scatterColor = Vector.multiply(scatterRecord.attenuation.scale(scatteringPDF), rayColor(camera, scattered, depth - 1, world, lights)).scale(1.0 / pdfVal);

        return emissionColor.add(scatterColor);
    }
}
