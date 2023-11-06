package proeend;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
import proeend.math.Vector;
import proeend.misc.*;
import proeend.windows.StartScreen;

/**
 * De `Main` klasse vertegenwoordigt de hoofdklasse van het RayTracer-programma.
 */
public class Main extends Application {

    private static final double INITIAL_FRAME_RATE = 0.1;
    private static final Camera camera = new Camera();
    private static HittableList world = new HittableList();
    private static final HittableList lights = new HittableList();
    private static final ImageView frame = new ImageView();
    private StackPane stackPane = new StackPane();
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

    public static Timeline getTimeLine(){
        return timeline;
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
        //currentStage.setFullScreen(true);

        currentStage.setTitle("RayTracer");

        showStartScreen();
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

        Scene scene = new Scene(stackPane);

        updateFrame(false);
        stackPane.getChildren().add(frame);

        startScreenVisible = false;

        stage.setScene(scene);



    }
    /**
     * Toont het startscherm van de applicatie en configureert de weergave-instellingen.
     */
    private void showStartScreen() {

        currentStage.setFullScreenExitHint("");

        if(timeline != null){
            timeline.stop();
        }

        stackPane.getChildren().remove(frame);

        BackgroundFill backgroundFill = new BackgroundFill(Color.LIGHTSTEELBLUE, null, null);
        Background background = new Background(backgroundFill);

        VBox root = new VBox(0);
        root.setBackground(background);

        if (startScreen == null) {
            startScreen = new StartScreen(this);
        }

        root.getChildren().add(startScreen);
        Scene scene = new Scene(root);

        currentStage.setScene(scene);
        currentStage.setFullScreen(true);

        startScreenVisible = true;

    }
    /**
     * Configureert de animatie voor het bijwerken van het frame.
     */
    private void setupAnimation(Stage stage) {

        EventHandler eventHandler = new EventHandler();
        eventHandler.setupEventHandlers(stage, frame, camera, world, lights);

        if(timeline == null){
            Duration interval = Duration.seconds(INITIAL_FRAME_RATE);
            KeyFrame keyFrame = new KeyFrame(interval, actionEvent -> handleKeyFrame(stage));

            timeline = new Timeline(keyFrame);
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            return;
        }

        timeline.play();

    }

    private void handleKeyFrame(Stage stage){
        updateFrame(false);
        if(startScreenVisible){
            showStartScreen();
        }
    }

    /**
     * Update het weergegeven frame met de nieuwste gerenderde afbeelding.
     */
    public static void updateFrame(boolean save) {

        WritableImage newImage = Renderer.render(camera, save, world, lights);

       // newImage = ImageBlender.applyACESAndTonemap(newImage);
        //TODO: (OPTIONEEL) Tonemapping om afbeelding lichter / meer gesatureerd te maken werkt niet na behoren.

        if(previousImage == null){
            previousImage = newImage;
            return;
        }

        if (camera.isMoving() || camera.hasMovedSinceLastFrame() || save) {
            previousImage = newImage;
            timeline.pause();
            camera.setSamplesPerPixel(1);
        } else {
            newImage = ImageBlender.blendImages(previousImage, newImage, 0.035, 3);
            previousImage = newImage;
        }

        if (!save) timeline.play();

        frame.setImage(newImage);
        camera.setHasMovedSinceLastFrame(false);
    }

    /**
     * Initialiseert het programma en start de JavaFX-toepassing.
     *
     * @param args Argumenten die aan het programma kunnen worden meegegeven.
     */
    public static void main(String[] args) {

        new Thread(Utility::loadImages).start();
        launch(args);

    }

    /**
     * Render de badeend en configureer de weergave-instellingen voor de weergave van de badeend.
     */
    public void renderDuck(){
        stackPane = new StackPane();
        stackPane.getChildren().removeAll();

        Utility.loadWorld(world, lights, caseSelector);
        world = new HittableList(new BBNode(world));

        camera.setBackground(Color.LIGHTPINK);
        camera.setImageWidth(400);
        camera.setCameraCenter(new Vector(0, 0, 2));

        camera.setSamplesPerPixel(5);

        //WritableImage image = Renderer.render(camera, true, world, lights);

        Scene scene = new Scene(stackPane);

        updateFrame(true);
        stackPane.getChildren().add(frame);

        startScreenVisible = false;

        camera.setSamplesPerPixel(1);


        currentStage.setScene(scene);


    }
    /**
     * Verwerkt het evenement wanneer een van de cases wordt geselecteerd door de gebruiker.
     * Het configureert de weergave en animatie voor de geselecteerde case / wereld.
     */
    public void caseButtonClicked(int caseSelector){

        stackPane = new StackPane();
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

        updateFrame(true);

        setupUI(currentStage);
        setupAnimation(currentStage);

        currentStage.setFullScreenExitHint("Press backspace to return to home screen");
        currentStage.setFullScreen(true);


    }

}
