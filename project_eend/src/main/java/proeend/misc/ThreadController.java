package proeend.misc;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import proeend.hittable.Hittable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadController {
    private long startTime;
    private long endTime;
    private int[] activeThreadCount  = {0};
    int numberOfThreads = 12;
    private final ExecutorService executorService;
    private int blockSize;
    private final Camera camera;
    private final Hittable world;
    private final Hittable lights;
    private final WritableImage writableImage;
    private final PixelWriter pixelWriter;

    public ThreadController(int blockSize, Camera camera, Hittable world, Hittable lights) {
        int maxNumberOfThreads = Runtime.getRuntime().availableProcessors(); // Aantal beschikbare CPU-cores;
        this.numberOfThreads = Math.max((camera.getHeight() / blockSize), maxNumberOfThreads);
        this.blockSize = blockSize;
        this.camera = camera;
        this.world = world;
        this.lights = lights;
        writableImage = new WritableImage(camera.getImageWidth(), camera.getHeight());
        pixelWriter = writableImage.getPixelWriter();
        this.executorService = Executors.newFixedThreadPool(numberOfThreads);
    }

    public WritableImage renderAndSave(boolean save) {
        if(save){
            startTime = System.nanoTime();
        }

        AtomicInteger completedLines = new AtomicInteger(0); // Voortgang bijhouden

        for (int i = 0; i < camera.getHeight(); i += blockSize) {
            final int lineStart = i;
            final int lineEnd = Math.min(i + blockSize, camera.getHeight());

            executorService.submit(() -> {
                RenderTask task = new RenderTask(camera, world, lights, lineStart, lineEnd, save, pixelWriter, completedLines, () -> {
                    double progress = (completedLines.get() / (double) camera.getHeight()) * 100.0;
                    System.out.printf("Progress: %.2f%%\r", progress);
                });
                task.run();
            });
        }

        shutdown();

        if(save) {
            ImageSaver.saveImage(writableImage, camera.getSamplesPerPixel());
            endTime = System.nanoTime();

            long elapsedTime = endTime - startTime;
            long seconds = TimeUnit.NANOSECONDS.toSeconds(elapsedTime);
            long millis = TimeUnit.NANOSECONDS.toMillis(elapsedTime) - TimeUnit.SECONDS.toMillis(seconds);

            System.out.println("Rendering completed in " + seconds + " seconds and " + millis + " milliseconds.");
        }


        return writableImage;
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                System.err.println("Thread execution did not complete in time.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
