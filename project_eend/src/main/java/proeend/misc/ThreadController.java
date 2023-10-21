package proeend.misc;

import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import proeend.hittable.Hittable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadController {
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
        int startLine = 0;
        int endLine = blockSize;
        if(save){ blockSize = 2;}

        for (int i = 0; i < numberOfThreads; i++) {
            if (i == numberOfThreads - 1) {
                endLine = camera.getHeight();
            }
            RenderTask task = new RenderTask(camera, world, lights, startLine, endLine, save, pixelWriter);
            executorService.submit(task);

            startLine = endLine;
            endLine += blockSize;
        }

        shutdown();

        if(save) {ImageSaver.saveImage(writableImage, camera.getSamplesPerPixel());}

        return writableImage;
    }

    public void pauseThread() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

  /*  public boolean canSpawnThread() {
        return activeThreadCount[0] < numberOfThreads;
    }

    public void spawnWorkerThread(int[] line, Camera camera, Hittable world, Hittable lights, PixelWriter pixelWriter) {
        synchronized (activeThreadCount) {
            activeThreadCount[0]++;
        }
        executorService.submit(new RenderTask(activeThreadCount, camera, world, lights, line[0], false, 0, pixelWriter));
    }*/



}
