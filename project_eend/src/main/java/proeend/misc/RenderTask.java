package proeend.misc;

import javafx.scene.image.PixelWriter;
import proeend.hittable.Hittable;

public class RenderTask extends Renderer implements Runnable  {
    private final Hittable world;
    private final Hittable lights;
    private final int startLine;
    private final int endLine;
    private final boolean save;
    private final PixelWriter pixelWriter;
    private final Camera camera;

    public RenderTask(Camera camera, Hittable world, Hittable lights, int startLine, int endLine, boolean save, PixelWriter pixelWriter) {
        this.world = world;
        this.lights = lights;
        this.startLine = startLine;
        this.endLine = endLine;
        this.save = save;
        this.pixelWriter = pixelWriter;
        this.camera = camera;
    }

    @Override
    public void run() {
        for (int y = startLine; y < endLine; y++) {
            renderHorizontalLine(camera, save, world, lights, y, pixelWriter);
        }
            //decrementActiveThreads(activeThreadCount);
    }

    public void decrementActiveThreads(int[] activethreads) {
        synchronized (activethreads) {
            activethreads[0]--;
        }
    }
}