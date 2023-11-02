package proeend;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
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
    private static final int numberOfThreads = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads - 4);
    private static ThreadController threadController;
    private Scene startScene;
    private Scene renderScene;
    private Stage stage;
    private static EventHandler eventHandler;

    public static ExecutorService getExecutorService() {
        return executorService;
    }

    public static ThreadController getThreadController() {
        return threadController;
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
        this.stage = stage != null ? stage : new Stage();

        camera.init();
        threadController = new ThreadController(1, world, lights, executorService);

        createScenes();

        // Toon het startscherm
        stage.setScene(startScene);
        stage.setTitle("RayTracer");
        stage.show();

        if (stackPane.getChildren().contains(startScreen)) {
            startScreen.setInfoLabel("Loading...");

        }

    }

    private void createScenes() {
        // Creëer en configureer scènes voor startscherm en renderingsscherm
        startScene = createStartScene();
        renderScene = createRenderScene();

        stage.setScene(startScene);
    }

    private Scene createStartScene() {
        // Lay-out van het startscherm
        VBox startLayout = new VBox(10);
        startLayout.setAlignment(Pos.CENTER);

        StartScreen startScreen = new StartScreen(this);
        startLayout.getChildren().add(startScreen);

        Label titleLabel = new Label("Welcome to RayTracer");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: blue;");



        Button startRenderingButton = new Button("Start Rendering");
        startRenderingButton.setStyle("-fx-background-color: white; -fx-text-fill: blue; -fx-font-size: 16px;");
        startRenderingButton.setMinSize(200, 50);
        startRenderingButton.setOnAction(e -> showRenderScene());

        startLayout.getChildren().addAll(titleLabel, startRenderingButton);

        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, null, null);
        Background background = new Background(backgroundFill);
        startLayout.setBackground(background);

        return new Scene(startLayout, 600, 700);
    }

    private Scene createRenderScene() {
        // Lay-out van het renderingsscherm
        VBox renderLayout = new VBox(10);
        renderLayout.setAlignment(Pos.CENTER);

        /*Label renderLabel = new Label("Rendering Scene");
        renderLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: blue;");*/

        Button returnToStartButton = new Button("Terug naar Startscherm");
        returnToStartButton.setStyle("-fx-background-color: white; -fx-text-fill: blue; -fx-font-size: 16px;");
        returnToStartButton.setMinSize(200, 50);
        returnToStartButton.setOnAction(e -> {
            if (!executorService.isTerminated()) {
                threadController.cancelAllTasks();
            }
            showStartScene();
        });


        // Voeg het frame toe aan de renderLayout
        renderLayout.getChildren().addAll(/*renderLabel*/ returnToStartButton, frame);

        BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE, null, null);
        Background background = new Background(backgroundFill);
        renderLayout.setBackground(background);

        return new Scene(renderLayout, camera.getImageWidth(), camera.getHeight());
    }

    private void showStartScene() {
        stage.setScene(startScene);
    }

    private void showRenderScene() {
        stage.setScene(renderScene);
        setupAnimation();
    }

    /**
     * Configureert de animatie voor het bijwerken van het frame.
     */
    private void setupAnimation() {
        eventHandler = new EventHandler();
        eventHandler.setupEventHandlers(stage, frame, camera, world, lights);

        final long[] previousTime = {System.nanoTime()};
        double frameRate = 24; // Set your desired frame rate
        double frameTime = 1.0 / frameRate;
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - previousTime[0] > frameTime * 1e9) {
                    // Only update frame if necessary
                    updateFrame();
                    previousTime[0] = now;
                }
            }
        }.start();
    }

    /**
     * Update het weergegeven frame met de nieuwste gerenderde afbeelding.
     */
    private void updateFrame() {

        WritableImage newImage = Renderer.render(camera, threadController, false);

        if (!camera.isMoving() && !camera.hasMovedSinceLastFrame() && previousImage != null) {
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
        stackPane = new StackPane();
        stackPane.getChildren().removeAll();

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

        camera.setBackground(Color.LIGHTPINK);
        camera.setImageWidth(400);
        camera.setCameraCenter(new Vector(0, 0, 2));

        camera.setSamplesPerPixel(100);
        camera.setMaxDepth(3);

        Renderer.render(camera, threadController, true);

    }


    public void caseButtonClicked(){
        Utility.loadWorld(world, lights, caseSelector);
        world = new HittableList(new BBNode(world));

        camera.setBackground(Color.LIGHTPINK);
        camera.setImageWidth(400);
        camera.setCameraCenter(new Vector(0, 0, 2));

        setupAnimation();
        showRenderScene();


    }
}
