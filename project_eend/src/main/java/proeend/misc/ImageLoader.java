package proeend.misc;

import javafx.scene.image.Image;

import java.util.concurrent.CompletableFuture;

public class ImageLoader {
    public static CompletableFuture<Image> loadAsync(String imagePath) {
        CompletableFuture<Image> future = new CompletableFuture<>();


        Thread imageLoadThread = new Thread(() -> {
            try {
                // Assuming the image is in the classpath, use getClass().getResourceAsStream()
                //InputStream inputStream = ImageLoader.class.getResourceAsStream(imagePath);

                // if(inputStream != null){

                    // Create a white Image
                    Image whiteImage = new Image(imagePath);

                    future.complete(whiteImage);
                //}

            }catch (Exception e) {
                // Handle the error appropriately
                future.completeExceptionally(e);
            }
        });

        imageLoadThread.start();

        return future;
    }

    public interface Callback<T> {
        void onSuccess(T result);
        void onFailure(Exception e);
    }
}
