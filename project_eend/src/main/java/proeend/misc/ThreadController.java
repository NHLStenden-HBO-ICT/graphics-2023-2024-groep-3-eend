package proeend.misc;

import javafx.scene.image.PixelWriter;
import proeend.hittable.Hittable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadController {

    public void pauseThread() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void shutdownExecutorService(ExecutorService executorService) {
        executorService.shutdown();
        try {
            executorService.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean canSpawnThread(int[] activeThreads, int numberOfThreads) {
        return activeThreads[0] < numberOfThreads;
    }

    public void spawnWorkerThread(ExecutorService executorService, int[] line, int[] activeThreads, Camera camera, Hittable world, Hittable lights, PixelWriter pixelWriter) {
        synchronized (activeThreads) {
            activeThreads[0]++;
        }
        executorService.submit(new WorkerThread(activeThreads, camera, world, lights, line[0], false, 0, pixelWriter));
        incrementLine(line);
    }

    private void incrementLine(int[] line) {
        synchronized (line) {
            line[0]++;
        }
    }

}
