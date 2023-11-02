package proeend.misc;

import javafx.scene.image.PixelWriter;
import proeend.hittable.Hittable;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Deze klasse vertegenwoordigt een taak voor het renderen van een deel van een afbeelding.
 */
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
    private final CountDownLatch renderingLatch;

    /**
     * Initialiseert een RenderTask met de opgegeven parameters.
     *
     * @param camera De camera die wordt gebruikt voor het renderen.
     * @param world Het 3D-wereldobject dat moet worden weergegeven.
     * @param lights De lichtbronnen in de scène.
     * @param startLine De startlijn van de te renderen pixels.
     * @param endLine De eindlijn van de te renderen pixels.
     * @param save Geeft aan of de afbeelding moet worden opgeslagen.
     * @param pixelWriter De PixelWriter voor het schrijven van pixelgegevens.
     * @param completedLines Een AtomicInteger om het aantal voltooide lijnen bij te houden.
     * @param updateProgress Een Runnable om de voortgang bij te werken.
     */
    public RenderTask(Camera camera, Hittable world, Hittable lights, int startLine, int endLine, boolean save, PixelWriter pixelWriter, AtomicInteger completedLines, CountDownLatch renderingLatch, Runnable updateProgress) {
        this.world = world;
        this.lights = lights;
        this.startLine = startLine;
        this.endLine = endLine;
        this.save = save;
        this.pixelWriter = pixelWriter;
        this.camera = camera;
        this.completedLines = completedLines;
        this.updateProgress = updateProgress;
        this.renderingLatch = renderingLatch;
    }

    /**
     * Voert de rendering bewerking uit voor het toegewezen deel van de afbeelding.
     */
    @Override
    public void run() {
        if(EventHandler.ExitProgram){
            return;
        }
        for (int y = startLine; y < endLine; y++) {
            Renderer.renderHorizontalLine(camera, save, world, lights, y, pixelWriter);
            int lines = completedLines.incrementAndGet();
            if (save && updateProgress != null) {
                    updateProgress.run();
            }}

        // Notify completion and count down the latch
        renderingLatch.countDown();

    }
}