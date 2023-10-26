package proeend.misc;

import javafx.scene.image.PixelWriter;
import proeend.hittable.Hittable;

import java.util.concurrent.atomic.AtomicInteger;

public class RenderTask implements Runnable  {
    private final Hittable world;
    private final Hittable lights;
    private final int startLine;
    private final int endLine;
    private final boolean save;
    private final PixelWriter pixelWriter;
    private final Camera camera;
    private final AtomicInteger completedLines;
    private final Runnable updateProgress;

    public RenderTask(Camera camera, Hittable world, Hittable lights, int startLine, int endLine, boolean save, PixelWriter pixelWriter, AtomicInteger completedLines, Runnable updateProgress) {
        this.world = world;
        this.lights = lights;
        this.startLine = startLine;
        this.endLine = endLine;
        this.save = save;
        this.pixelWriter = pixelWriter;
        this.camera = camera;
        this.completedLines = completedLines;
        this.updateProgress = updateProgress;
    }

    @Override
    public void run() {
        if(EventHandler.ExitProgram){
            return;
        }
        for (int y = startLine; y < endLine; y++) {
            Renderer.renderHorizontalLine(camera, save, world, lights, y, pixelWriter);
            int lines = completedLines.incrementAndGet();
            if (save && updateProgress != null) {
                if (lines % 5 == 0) { // Update progress every 5 lines
                    updateProgress.run();
                }
            }}
    }
}