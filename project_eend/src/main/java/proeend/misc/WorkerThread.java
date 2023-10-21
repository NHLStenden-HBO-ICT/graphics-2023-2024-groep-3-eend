package proeend.misc;

import javafx.scene.image.PixelWriter;
import proeend.hittable.Hittable;

public class WorkerThread implements Runnable {
    private final Hittable world;
    private final Hittable lights;
    private final int line;
    private final boolean save;
    private final int numberOfThreads;
    private final int[] activeThreadCount;
    private final PixelWriter pixelWriter;
    private final Camera camera;

    public WorkerThread(int[] activeThreadCount, Camera camera, Hittable world, Hittable lights, int line, boolean save, int numberOfThreads, PixelWriter pixelWriter) {
        this.world = world;
        this.lights = lights;
        this.line = line;
        this.save = save;
        this.numberOfThreads = numberOfThreads;
        this.activeThreadCount = activeThreadCount;
        this.pixelWriter = pixelWriter;
        this.camera = camera;
    }

    @Override
    public void run() {
        Renderer.renderHorizontalLine(camera, save, world, lights, line, pixelWriter);
        decrementActiveThreads(activeThreadCount);
    }

    public void decrementActiveThreads(int[] activethreads) {
        synchronized (activethreads) {
            activethreads[0]--;
        }
    }
}