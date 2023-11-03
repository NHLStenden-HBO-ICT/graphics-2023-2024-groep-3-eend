package proeend;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import proeend.hittable.BBNode;
import proeend.hittable.HittableList;
import proeend.hittable.ObjectLoader;
import proeend.hittable.PolygonMesh;
import proeend.material.Lambertian;
import proeend.math.Vector;
import proeend.misc.*;

import java.io.IOException;

/**
 * De `Main` klasse vertegenwoordigt de hoofdklasse van het RayTracer-programma.
 */
public class Main extends Application {

    private static final double INITIAL_FRAME_RATE = 0.1;
    private static final Camera camera = new Camera();
    private static HittableList world = new HittableList();
    private static final HittableList lights = new HittableList();

    final ImageView frame = new ImageView();
    final StackPane stackPane = new StackPane();
    WritableImage previousImage;

    /**
     * Start het programma en configureert de besturingselementen.
     *
     * @param stage Het venster waarin het programma wordt weergegeven.
     */
    @Override
    public void start(Stage stage) {
        updateFrame();
        setupUI(stage);
        setupAnimation(stage);
        stage.setTitle("RayTracer");
        stage.show();
    }
    /**
     * Configureert de gebruikersinterface voor het hoofdvenster van het programma.
     *
     * @param stage Het JavaFX-venster voor de gebruikersinterface.
     */
    private void setupUI(Stage stage) {
        Scene scene = new Scene(stackPane, camera.getImageWidth(), camera.getHeight());
        stackPane.getChildren().add(frame);
        StackPane.setAlignment(frame, Pos.CENTER);
        stackPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        stage.setScene(scene);
    }

    /**
     * Configureert de animatie voor het bijwerken van het frame.
     */
    private void setupAnimation(Stage stage) {
        EventHandler eventHandler = new EventHandler();
        eventHandler.setupEventHandlers(stage, frame, camera, world, lights);
        StackPane.setAlignment(frame, Pos.CENTER);
        Duration interval = Duration.seconds(INITIAL_FRAME_RATE);
        KeyFrame keyFrame = new KeyFrame(interval, actionEvent -> updateFrame());
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Update het weergegeven frame met de nieuwste gerenderde afbeelding.
     */
    private void updateFrame() {

        WritableImage newImage = Renderer.render(camera, false, world, lights);

        if(previousImage == null){
            previousImage = newImage;
            return;
        }

        if (!camera.isMoving() && !camera.hasMovedSinceLastFrame()) {
            // Blend the previous image with the new image
            newImage = ImageBlender.blendImages(previousImage, newImage, .035, 3);
        }
        previousImage = newImage;
        frame.setImage(newImage);
        camera.setHasMovedSinceLastFrame(false);
    }



    /**
     * Initialiseert het programma en start de JavaFX-toepassing.
     *
     * @param args Argumenten die aan het programma kunnen worden meegegeven.
     */
    public static void main(String[] args) {



        camera.setBackground(Color.BLACK);
        camera.setImageWidth(400);
        //camera.setCameraCenter(camOrigin);
        camera.setCameraCenter(new Vector(0,0,4));

        camera.setSamplesPerPixel(64);
        camera.setMaxDepth(3);

        //camera.setCameraCenter(new Vector(-.5,20,40));
        //camera.setLookat(new Vector(0,20,39));

        Utility.loadWorld(world, lights, 9);
        world = new HittableList(new BBNode(world));

        /*var startTime = System.currentTimeMillis();
        System.out.println(LocalDateTime.now());
        //cam1.render(true, world, lights);
        //cam1.multiRender(true, world, lights);
        camera.multiRenderLines(true, world, lights);
        var endTime = System.currentTimeMillis() - startTime;
        var minutes = endTime/60_000.0;
        var hours = minutes/60.0;
        System.out.print("seconds:\t\t");
        System.out.println(endTime/1000.0);
        System.out.print("minutes:\t\t");
        System.out.println(minutes);
        System.out.println("hours:\t\t\t" + hours);*/
        Renderer.render(camera, true, world, lights);

       // launch(args);
    }
}