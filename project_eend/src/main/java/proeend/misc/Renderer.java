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

public class Renderer {
    public static WritableImage render(Camera camera, boolean save, Hittable world, Hittable lights) {
        camera.init();
        camera.setLock(true);
            ThreadController threadController = new ThreadController(20, camera, world, lights);
            WritableImage image = threadController.renderAndSave(save);
            camera.setLock(false);
            return image;
    }
    public static void renderHorizontalLine(Camera camera, boolean save, Hittable world, Hittable lights, int y, PixelWriter pixelWriter) {
        for (int x = 0; x < camera.getImageWidth(); ++x) {
            Vector colorVec = new Vector();
            colorVec = renderPixel(camera, world, lights, y, x, colorVec);
            int[] colors = ColorParser.toColor(camera.getSamplesPerPixel(), save, colorVec);
            synchronized (pixelWriter) {
                pixelWriter.setColor(x, y, Color.rgb(colors[0], colors[1], colors[2]));
            }
        }
    }
    private static Vector renderPixel(Camera camera, Hittable world, Hittable lights, int y, int x, Vector color) {
        for (int sy = 0; sy < camera.getRootSPP(); ++sy) {
            for (int sx = 0; sx < camera.getRootSPP(); ++sx) {
                Ray ray = getRay(camera, x, y, sx, sy);
                color = Vector.add(color, rayColor(camera, ray, camera.getMaxDepth(), world, lights));
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
        Vector pixelCenter = Vector.add(Vector.add(camera.getTopLeftPixel(), Vector.scale(x, camera.getPixelDeltaU())), Vector.scale(y, camera.getPixelDeltaV()));
        Vector pixelSample = Vector.add(pixelCenter, pixelSampleSquare(camera, sx, sy));
        Vector rayOrigin = camera.getCameraCenter();
        Vector direction = Vector.add(pixelSample, Vector.negate(rayOrigin));
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
        return Vector.add(Vector.scale(px, camera.getPixelDeltaU()), Vector.scale(py, camera.getPixelDeltaV()));
    }
    /**
     * Berekent de kleur van een ray in de scene met een maximaal aantal reflecties.
     *
     * @param r     De ray om te traceren.
     * @param depth Het maximum aantal reflecties.
     * @param world Het 3D-wereldobject dat moet worden weergegeven.
     * @return Een Vector die de kleur van de ray vertegenwoordigt.
     */
    private static Vector rayColor(Camera camera, Ray r, int depth, Hittable world, Hittable lights) {
        if (world == null) {
            return Vector.randomVec();
        }

        // Controleer of het maximumaantal reflecties is bereikt
        if (depth <= 0) {
            return new Vector(0, 0, 0);
        }
        // Maak een HitRecord om gegevens over het getroffen object op te slaan
        HitRecord rec = new HitRecord();
        // r.direction = Vector.unitVector(r.direction);

        // Controleer of de ray een object in de wereld raakt
        // Materiaal wordt onder water ook ingesteld in de hit methode van een object zoals sphere.
        if (!world.hit(r, new Interval(0.00000001, Double.POSITIVE_INFINITY), rec)) {
            // Geen raakpunt
            return camera.getBackground();
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
            return Vector.multiply(scatterRecord.attenuation, rayColor(camera, scatterRecord.skipRay,
                    depth - 1, world, lights));
        }

        HittablePDF lightPDF = new HittablePDF(lights, rec.p);
        CosPDF surfacePDF = new CosPDF(rec.normal);
        MixturePDF mixPDF = new MixturePDF(lightPDF, surfacePDF);

        Ray scattered = new Ray(rec.p, mixPDF.generate());
        double pdfVal = mixPDF.value(scattered.getDirection());
        double scatteringPDF = rec.material.scatteringPDF(r, rec, scattered);

        Vector scatterColor = Vector.scale(1.0 / pdfVal,
                Vector.multiply(Vector.scale(scatteringPDF, scatterRecord.attenuation), rayColor(camera, scattered, depth - 1, world, lights)));

        return Vector.add(emissionColor, scatterColor);
    }
}
