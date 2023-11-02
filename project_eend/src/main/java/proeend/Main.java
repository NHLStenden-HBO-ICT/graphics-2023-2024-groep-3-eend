package proeend;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private static final Camera camera = new Camera();

    private static HittableList world = new HittableList();
    private static final HittableList lights = new HittableList();
    final static ImageView frame = new ImageView();
    StackPane stackPane = new StackPane();
    private static WritableImage previousImage;
    public static StartScreen startScreen;
    public static int caseSelector;
    private static final int numberOfThreads = Runtime.getRuntime().availableProcessors();
    private static final ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads - 4);
    private static ThreadController threadController;
    private Scene startScene;
    public Scene renderScene;
    private static Stage stage;
    private static long lastFrameTime = 0;
    private static final double TARGET_FRAME_RATE = 24;
    private AnimationTimer animationTimer;

    public static ImageView getFrame() {
        return frame;
    }

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
     * @param primaryStage Het venster waarin het programma wordt weergegeven.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setFullScreen(true);
        //primaryStage.setFullScreenExitHint("Exit");

        stage = primaryStage;

        threadController = new ThreadController(10, world, lights, executorService);
        createScenes();

        // Toon het startscherm
        if (stage != null) {
            stage.setScene(startScene);
            stage.setTitle("RayTracer");
            stage.show();
        }

        if (stackPane.getChildren().contains(startScreen)) {
            startScreen.setInfoLabel("Loading...");
        }

        EventHandler eventHandler = new EventHandler();
        eventHandler.setupEventHandlers(stage, frame, camera, world, lights);
    }

    private void createScenes() {
        // Creëer en configureer scènes voor startscherm en renderingsscherm

        startScene = createStartScene();
        renderScene = createRenderScene();
        stage.setScene(startScene);
    }

    public void setCamera(){
        camera.setImageWidth(400);
        camera.setCameraCenter(new Vector(0, 0, 2));
        camera.setSamplesPerPixel(5);
        camera.setMaxDepth(3);
    }

    private Scene createStartScene() {
        // Lay-out van het startscherm
        VBox startLayout = new VBox(10);
        startLayout.setAlignment(Pos.CENTER);

        StartScreen startScreen = new StartScreen(this);
        startLayout.getChildren().add(startScreen);

        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTBLUE, null, null);
        Background background = new Background(backgroundFill);
        startLayout.setBackground(background);

        return new Scene(startLayout);
    }

    public Scene createRenderScene() {

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
            stopAnimation();
            showStartScene();
        });

        setCamera();
        setupAnimation();
        frame.setImage(Renderer.render(camera, threadController, false));


        // Voeg het frame toe aan de renderLayout
        renderLayout.getChildren().addAll(returnToStartButton, frame);

        BackgroundFill backgroundFill = new BackgroundFill(Color.WHITE, null, null);
        Background background = new Background(backgroundFill);
        renderLayout.setBackground(background);

        return new Scene(renderLayout);
    }

    private void showStartScene() {
        stage.setScene(startScene);
        stopAnimation();
    }

    public void showRenderScene() {
        camera.init();
        stage.setScene(renderScene);
    }

    /**
     * Configureert de animatie voor het bijwerken van het frame.
     */
    public void setupAnimation() {


        lastFrameTime = System.nanoTime();
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Calculate the time passed since the last frame
                long elapsedNanos = now - lastFrameTime;
                double elapsedSeconds = elapsedNanos / 1e9;

                // Update the frame if enough time has passed for the target frame rate
                if (elapsedSeconds >= 1.0 / TARGET_FRAME_RATE) {
                    updateFrame();
                    lastFrameTime = now;
                }
            }
        };

        animationTimer.start();
    }

    private void stopAnimation() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
    }

    /**
     * Update het weergegeven frame met de nieuwste gerenderde afbeelding.
     */
    public static void updateFrame() {
        camera.init();

        WritableImage newImage = Renderer.render(camera, threadController, false);

        Platform.runLater(() -> {
            frame.setImage(newImage);
            camera.setHasMovedSinceLastFrame(false);
        });
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

        camera.setImageWidth(400);
        camera.setCameraCenter(new Vector(0, 0, 2));

        camera.setSamplesPerPixel(100);
        camera.setMaxDepth(3);

        camera.init();

        Renderer.render(camera, threadController, true);

    }


    public void caseButtonClicked(){

        Utility.loadWorld(world, lights, caseSelector);
        world = new HittableList(new BBNode(world));

        setCamera();

        setupAnimation();
        // Renderer.render(camera, threadController, true);

        showRenderScene();

    }


    /**
     * Initialiseert het programma en start de JavaFX-toepassing.
     *
     * @param args Argumenten die aan het programma kunnen worden meegegeven.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
