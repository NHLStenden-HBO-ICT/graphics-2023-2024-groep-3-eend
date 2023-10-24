package proeend.misc;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import proeend.hittable.Hittable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * De `ThreadController` klasse beheert multi-threading voor de renderings processen.
 */
public class ThreadController {
    private long startTime;
    final int numberOfThreads;
    private ExecutorService executorService;
    private final int blockSize;
    private final Camera camera;
    private final Hittable world;
    private final Hittable lights;
    private final WritableImage writableImage;
    private final PixelWriter pixelWriter;

    /**
     * Initialiseert een nieuw `ThreadController` object.
     *
     * @param blockSize  De grootte van het blok voor multi-threading.
     * @param camera     De camera-instellingen voor de rendering.
     * @param world      Het 3D-wereld object dat moet worden weergegeven.
     * @param lights     Het object dat lichtbronnen definieert.
     */

    public ThreadController(int blockSize, Camera camera, Hittable world, Hittable lights) {
        int maxNumberOfThreads = Runtime.getRuntime().availableProcessors(); // Aantal beschikbare CPU-cores;
        this.numberOfThreads = maxNumberOfThreads;
        this.blockSize = blockSize;
        this.camera = camera;
        this.world = world;
        this.lights = lights;
        writableImage = new WritableImage(camera.getImageWidth(), camera.getHeight());
        pixelWriter = writableImage.getPixelWriter();
        this.executorService = Executors.newFixedThreadPool(numberOfThreads);
    }

    /**
     * Start het render proces en slaat de afbeelding op als dat nodig is.
     *
     * @param save  Geeft aan of de afbeelding moet worden opgeslagen.
     * @return Een `WritableImage` die de gerenderde afbeelding bevat.
     */
    public WritableImage renderAndSave(boolean save) {
        if(save){
            startTime = System.nanoTime();
            executorService = Executors.newFixedThreadPool(numberOfThreads - 4);
        }

        AtomicInteger completedLines = new AtomicInteger(0); // Voortgang bijhouden

        for (int i = 0; i < camera.getHeight(); i += blockSize) {
            final int lineStart = i;
            final int lineEnd = Math.min(i + blockSize, camera.getHeight());

            executorService.submit(() -> {
                RenderTask task = new RenderTask(camera, world, lights, lineStart, lineEnd, save, pixelWriter, completedLines, () -> {
                    double progress = (completedLines.get() / (double) camera.getHeight()) * 100.0;
                    System.out.printf("Render progress: %.2f%%\r", progress);
                });
                task.run();
            });
        }

        shutdown();

        if(save) {
            ImageSaver.saveImage(writableImage, camera.getSamplesPerPixel());
            long endTime = System.nanoTime();

            long elapsedTime = endTime - startTime;
            long seconds = TimeUnit.NANOSECONDS.toSeconds(elapsedTime);
            long millis = TimeUnit.NANOSECONDS.toMillis(elapsedTime) - TimeUnit.SECONDS.toMillis(seconds);

            System.out.println("Rendering completed in " + seconds + " seconds and " + millis + " milliseconds.");
            camera.setSamplesPerPixel(1);
        }


        return writableImage;
    }

    /**
     * Sluit de executor service af en wacht tot alle threads zijn voltooid.
     */
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                logError("Thread execution did not complete in time.");
            }
        } catch (InterruptedException e) {
            logError("Thread execution was interrupted: " + e.getMessage());
        }
    }

    private void logError(String message) {
        System.err.println("Error: " + message);
    }

}
