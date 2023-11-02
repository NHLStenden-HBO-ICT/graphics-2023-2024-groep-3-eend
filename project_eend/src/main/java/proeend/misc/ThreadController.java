package proeend.misc;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import proeend.hittable.Hittable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * De `ThreadController` klasse beheert multi-threading voor het rendering proces.
 */
public class ThreadController {
    private long startTime;
    private final int blockSize;
    private final Hittable world;
    private final Hittable lights;
    private PixelWriter pixelWriter;
    private final ExecutorService executorService;
    private final List<Future<?>> futureList = new ArrayList<>();


    /**
     * Initialiseert een nieuw ThreadController object.
     *
     * @param blockSize  De grootte van het blok voor multi-threading.
     * @param world      Het 3D-wereldobject dat moet worden weergegeven.
     * @param lights     Het object dat lichtbronnen definieert.
     */

    public ThreadController(int blockSize, Hittable world, Hittable lights, ExecutorService executorService) {
        this.blockSize = blockSize;
        this.world = world;
        this.lights = lights;
        this.executorService = executorService;

    }


    /**
     * Start het render proces en slaat de afbeelding op als dat nodig is.
     *
     * @param save  Geeft aan of de afbeelding moet worden opgeslagen.
     * @return Een `WritableImage` die de gerenderde afbeelding bevat.
     */

    public WritableImage renderAndSave(boolean save, Camera camera) {
        CountDownLatch renderingLatch = new CountDownLatch(camera.getHeight() / blockSize);

        WritableImage writableImage = new WritableImage(camera.getImageWidth(), camera.getHeight());
        pixelWriter = writableImage.getPixelWriter();


        if(save){
            startTime = System.nanoTime();
        }

        AtomicInteger completedLines = new AtomicInteger(0); // Voortgang bijhouden

        for (int i = 0; i < camera.getHeight(); i += blockSize) {
            final int lineStart = i;
            final int lineEnd = Math.min(i + blockSize, camera.getHeight());
            Future<?> future = executorService.submit(() -> {
                RenderTask task = new RenderTask(camera, world, lights, lineStart, lineEnd, save, pixelWriter, completedLines, renderingLatch, () -> {
                    double progress = (completedLines.get() / (double) camera.getHeight()) * 100.0;
                    String info = String.format("Render progress: %.0f\r", progress) + "%";
                    //Main.startScreen.setInfoLabel(info);
                });
                task.run();
            });

            futureList.add(future);
        }

        try {
            // Wacht tot alle taken zijn voltooid
            renderingLatch.await();
        } catch (InterruptedException e) {
            logError("Error during rendering or saving: " + e.getMessage());
        }

        if (save) {


            ImageSaver.saveImage(writableImage, camera.getSamplesPerPixel());
            long endTime = System.nanoTime();
            long elapsedTime = endTime - startTime;
            long seconds = TimeUnit.NANOSECONDS.toSeconds(elapsedTime);
            long millis = TimeUnit.NANOSECONDS.toMillis(elapsedTime) - TimeUnit.SECONDS.toMillis(seconds);

            System.out.println("Rendering completed in " + seconds + " seconds and " + millis + " milliseconds.");
        }


        return writableImage;
    }

    /**
     * Sluit de executor service af en wacht tot alle threads zijn voltooid.
     */
    public void shutdown() {
        for (Future<?> future : futureList) {
            future.cancel(true); // Annuleer de taak
        }

        executorService.shutdown();

        // Wacht tot alle threads voltooid zijn
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                // Als threads niet binnen 60 seconden zijn voltooid, forceer het afsluiten
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            logError("Thread execution was interrupted: " + e.getMessage());
        }
    }

    private void logError(String message) {
        System.err.println("Error: " + message);
    }

    public void cancelAllTasks() {
        for (Future<?> future : futureList) {
            future.cancel(true); // Annuleer de taak
        }
    }
}
