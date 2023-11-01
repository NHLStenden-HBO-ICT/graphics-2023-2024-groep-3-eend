package proeend;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import proeend.hittable.BBNode;
import proeend.hittable.HittableList;
import proeend.hittable.ObjectLoader;
import proeend.hittable.PolygonMesh;
import proeend.math.Vector;
import proeend.material.*;
import proeend.misc.*;
import proeend.windows.StartScreen;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * De `Main` klasse vertegenwoordigt de hoofdklasse van het RayTracer-programma.
 */
public class Main extends Application {

    private static final double INITIAL_FRAME_RATE = 0.1;
    private static final Camera camera = new Camera();
    private static HittableList world = new HittableList();
    private static final HittableList lights = new HittableList();
    final ImageView frame = new ImageView();
     StackPane stackPane = new StackPane();
    WritableImage previousImage;
    public static StartScreen startScreen;
    public static int caseSelector;

    public static void setButtonClicked(int i){
        caseSelector = i;
    }


    /**
     * Start het programma en configureert de besturingselementen.
     *
     * @param stage Het venster waarin het programma wordt weergegeven.
     */
    @Override
    public void start(Stage stage) {

/*
     stage.setOnCloseRequest(event -> {

            startScreen = new StartScreen(this);

        });
*/

        if(stackPane.getChildren().contains(startScreen)){
          startScreen.setInfoLabel("Loading...");
        }

        if (world.getObjects().isEmpty()) {

            VBox root = new VBox(10);
            BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, null, null);
            Background background = new Background(backgroundFill);

            root.setBackground(background);

            StartScreen startScreen = new StartScreen(this);
            root.getChildren().add(startScreen);
            Scene scene = new Scene(root, 600, 700);

            stage.setScene(scene);
        }
        else {
        camera.setBackground(Color.LIGHTPINK);
        camera.setImageWidth(400);
        camera.setCameraCenter(new Vector(0, 0, 2));

        camera.setSamplesPerPixel(1);
        camera.setMaxDepth(3);

        updateFrame();
        setupUI(stage);
        setupAnimation(stage);
        }


        stage.setTitle("RayTracer");
        stage.show();

        if(stackPane.getChildren().contains(startScreen)){
            startScreen.setInfoLabel("");
        }
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
        launch(args);
    }

    public void renderDuck(){

        Lambertian white = new Lambertian(new Vector(1, .5, .5));
        Emitter white2 = new Emitter(new Vector(1,1,1));
        PolygonMesh duck = null;
        PolygonMesh icoSphere = null;
        PolygonMesh uvSphere = null;

        try {
            duck = ObjectLoader.loadObj("project_eend/Models/uploads_files_4534682_Duck.obj", white);
        } catch (IOException e) {
            Main.startScreen.setInfoLabel("load failed");
        }
        try {
            icoSphere = ObjectLoader.loadObj("project_eend/Models/icotest.obj", white);
        } catch (IOException e) {
            Main.startScreen.setInfoLabel("load failed");
        }
        try {
            uvSphere = ObjectLoader.loadObj("project_eend/Models/uvSphere.obj", white);
        } catch (IOException e) {
            Main.startScreen.setInfoLabel("load failed");
        }

        Utility.loadWorld(world, lights, caseSelector);

        uvSphere.ConvertToTriangles();
        world.add(uvSphere);

        start(new Stage());

    }

    public void caseButtonClicked(){
        stackPane = new StackPane();
        stackPane.getChildren().removeAll();
        Utility.loadWorld(world, lights, caseSelector);
        world = new HittableList(new BBNode(world));

        camera.setSamplesPerPixel(1);
        camera.setMaxDepth(5);



        start(new Stage());

    }

    @Override
    public void stop(){
    }
}
