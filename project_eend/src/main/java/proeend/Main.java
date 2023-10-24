package proeend;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import proeend.math.Vector;
import proeend.misc.*;

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
     * @param stage Het venster waarin het programma zich afspeelt.
     */
    @Override
    public void start(Stage stage) {
        updateFrame();
        setupUI(stage);
        setupAnimation();
        EventHandler eventHandler = new EventHandler();
        eventHandler.setupEventHandlers(stage.getScene(), camera, world, lights);
        stage.setTitle("RayTracer");
        stage.show();

        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth) {
                double newHeight = newWidth.doubleValue() * (1.0 / camera.getAspectRatio());

                frame.setFitWidth(newWidth.doubleValue());
                frame.setFitHeight(newHeight);
                camera.setAspectRatio(newWidth.intValue() / stage.getHeight());
                camera.setHasMovedSinceLastFrame(true);
            }
        });

        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight) {
                double newWidth = stage.getWidth();

                frame.setFitWidth(newWidth);
                frame.setFitHeight(newHeight.doubleValue());
                camera.setAspectRatio(newWidth / newHeight.intValue());
                camera.setHasMovedSinceLastFrame(true);
            }
        });



    }
    private void setupUI(Stage stage) {
        Scene scene = new Scene(stackPane, camera.getImageWidth(), camera.getHeight());
        stackPane.getChildren().add(frame);
        StackPane.setAlignment(frame, Pos.CENTER);
        stackPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        stage.setScene(scene);

    }

    private void setupAnimation() {
        StackPane.setAlignment(frame, Pos.CENTER);
        Duration interval = Duration.seconds(INITIAL_FRAME_RATE);
        KeyFrame keyFrame = new KeyFrame(interval, actionEvent -> updateFrame());
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateFrame() {

        WritableImage newImage = Renderer.render(camera, false, world, lights);

        if(previousImage == null){
            previousImage = newImage;
            return;
        }

        if (!camera.isMoving() && !camera.hasMovedSinceLastFrame()) {
            // Blend the previous image with the new image
            newImage = ImageBlender.blendImages(previousImage, newImage);
        }
        previousImage = newImage;
        frame.setImage(newImage);
        camera.setHasMovedSinceLastFrame(false);
    }



    /**
     * Initialiseert het programma.
     * @param args Argumenten die aan het programma meegegeven kunnen worden.
     */
    public static void main(String[] args) {

        //Lambertian white = new Lambertian(new Vector(1, .5, .5));
        //Emitter white = new Emitter(new Vector(1,1,1));
        /*PolygonMesh duck = null;
        PolygonMesh icoSphere = null;
        PolygonMesh uvSphere = null;

        try {
            duck = ObjectLoader.loadObj("project_eend/Models/uploads_files_4534682_Duck.obj", white);
        } catch (IOException e) {
            System.out.println("load failed");
        }
        try {
            icoSphere = ObjectLoader.loadObj("project_eend/Models/icotest.obj", white);
        } catch (IOException e) {
            System.out.println("load failed");
        }
        try {
            uvSphere = ObjectLoader.loadObj("project_eend/Models/uvSphere.obj", white);
        } catch (IOException e) {
            System.out.println("load failed");
        }
*/
        //uvSphere.ConvertToTriangles();
        //world.add(uvSphere);

        camera.setBackground(new Vector(.5,.5,.5));
        camera.setImageWidth(400);
        //camera.setCameraCenter(camOrigin);
        camera.setCameraCenter(new Vector(0,0,2));

        camera.setSamplesPerPixel(1);
        camera.setMaxDepth(3);

        //cam1.cameraCenter = new Vector(-.5,20,40);
        //cam1.lookat = new Vector(0,20,39);

        Utility.loadWorld(world, lights, 0);
        world = new HittableList(new BBNode(world));

        /*var startTime = System.currentTimeMillis();
        System.out.println(LocalDateTime.now());
        //cam1.render(true, world, lights);
        //cam1.multiRender(true, world, lights);
        //cam1.multiRenderLines(true, world, lights);
        var endTime = System.currentTimeMillis() - startTime;
        var minutes = endTime/60_000.0;
        var hours = minutes/60.0;
        System.out.print("seconds:\t\t");
        System.out.println(endTime/1000.0);
        System.out.print("minutes:\t\t");
        System.out.println(minutes);
        System.out.println("hours:\t\t\t" + hours);*/
        //Renderer.render(camera, true, world, lights);

        launch(args);
    }
}