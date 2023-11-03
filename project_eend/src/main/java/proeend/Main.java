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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import proeend.hittable.BBNode;
import proeend.hittable.HittableList;
import proeend.hittable.ObjectLoader;
import proeend.hittable.PolygonMesh;
import proeend.material.Emitter;
import proeend.material.Lambertian;
import proeend.math.Vector;
import proeend.misc.*;
import proeend.windows.StartScreen;

import java.io.IOException;
import java.io.PrintStream;

/**
 * De `Main` klasse vertegenwoordigt de hoofdklasse van het RayTracer-programma.
 */
public class Main extends Application {

    private static final double INITIAL_FRAME_RATE = 0.1;
    private static Camera camera = new Camera();
    private static HittableList world = new HittableList();
    private static final HittableList lights = new HittableList();
    private static final ImageView frame = new ImageView();
     StackPane stackPane = new StackPane();
    private static WritableImage previousImage;
    public static StartScreen startScreen;
    public static int caseSelector;
    private static Timeline timeline;
    private Stage currentStage;
    private static boolean startScreenVisible;

    public static boolean isStartScreenVisible(){
        return startScreenVisible;
    }
    public static void setStartScreenVisible(boolean value){
        startScreenVisible = value;
    }
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


        this.currentStage = stage;

        currentStage.setFullScreenExitHint("Press escape to exit full screen model");
        currentStage.setFullScreen(true);
        stage.setFullScreen(true);

        if (world.getObjects().isEmpty()) {
            showStartScreen();
        }

        currentStage.setTitle("RayTracer");
        currentStage.show();

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

        stackPane.getChildren().removeAll();

        startScreenVisible = false;

        Scene scene = new Scene(stackPane);
        updateFrame(false);

        stage.setFullScreen(true);

        StackPane.setAlignment(frame, Pos.CENTER);

        stackPane.getChildren().add(frame);

        stage.setScene(scene);
    }
    /**
     * Toont het startscherm van de applicatie en configureert de weergave-instellingen.
     */
    private void showStartScreen() {

        currentStage.setFullScreenExitHint("");
        currentStage.setFullScreen(true);
        startScreenVisible = true;


        if(timeline != null){
            timeline.stop();
        }
        stackPane.getChildren().remove(frame);
        camera = new Camera();

        VBox root = new VBox(10);
        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, null, null);
        Background background = new Background(backgroundFill);

        root.setBackground(background);

        startScreen = new StartScreen(this);
        root.getChildren().addAll(startScreen);
        Scene scene = new Scene(root, 600, 700);

        currentStage.setScene(scene);
    }
    /**
     * Configureert de animatie voor het bijwerken van het frame.
     */
    private void setupAnimation(Stage stage) {
        EventHandler eventHandler = new EventHandler();
        eventHandler.setupEventHandlers(stage, frame, camera, world, lights);
        StackPane.setAlignment(frame, Pos.CENTER);
        Duration interval = Duration.seconds(INITIAL_FRAME_RATE);
        KeyFrame keyFrame = new KeyFrame(interval, actionEvent -> {
            updateFrame(false);
            if(startScreenVisible){
                showStartScreen();
                currentStage.setFullScreen(true);
                stage.setFullScreen(true);
            }
        });
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Update het weergegeven frame met de nieuwste gerenderde afbeelding.
     */
    public static void updateFrame(boolean save) {

        WritableImage newImage = Renderer.render(camera, save, world, lights);

        if(previousImage == null){
            previousImage = newImage;
            return;
        }

        if (!camera.isMoving() && !camera.hasMovedSinceLastFrame() && !save) {
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

    /**
     * Render de badeend en configureer de weergave-instellingen voor de weergave van de badeend.
     */
    public void renderDuck(){
        stackPane = new StackPane();
        stackPane.getChildren().removeAll();

        Utility.loadWorld(world, lights, caseSelector);

        camera.setBackground(Color.LIGHTPINK);
        camera.setImageWidth(400);
        camera.setCameraCenter(new Vector(0, 0, 2));

        camera.setSamplesPerPixel(10);
        camera.setMaxDepth(3);

        Renderer.render(camera, true, world, lights);

    }
    /**
     * Verwerkt het evenement wanneer een van de cases wordt geselecteerd door de gebruiker.
     * Het configureert de weergave en animatie voor de geselecteerde case / wereld.
     */
    public void caseButtonClicked(){

        stackPane = new StackPane();
        stackPane.getChildren().removeAll();
        Utility.loadWorld(world, lights, caseSelector);
        world = new HittableList(new BBNode(world));

        camera.setSamplesPerPixel(1);
        camera.setMaxDepth(5);
        if(caseSelector == 2){
            camera.setBackground(Color.BLACK);
        } else {
            camera.setBackground(Color.LIGHTBLUE);
        }
        camera.setImageWidth(400);
        camera.setCameraCenter(new Vector(0, 0, 2));

        setupUI(currentStage);
        setupAnimation(currentStage);

        currentStage.setFullScreenExitHint("Press backspace to return to home screen");

        currentStage.setFullScreen(true);
        launch();

    }

}
