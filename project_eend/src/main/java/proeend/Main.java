package proeend;

import javafx.concurrent.Task;
import javafx.scene.control.Label;
import proeend.hittable.BBNode;
import proeend.hittable.Sphere;
import proeend.material.Lambertian;
import proeend.misc.Camera;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import proeend.hittable.HittableList;
import proeend.math.Vector;
import proeend.misc.Utility;



import java.io.IOException;

public class Main extends Application {

    static HittableList world = new HittableList();
    static HittableList lights = new HittableList();
    static Camera cam1 = new Camera();
    static Camera cam2 = new Camera();
    private boolean isCameraRotating = true;
    static double frameRate = 1.0/10.0; //hertz
    static double aspectRatio = 16.0/9.0;
    static Vector camOrigin = new Vector(0,0,2);

    ImageView frame = new ImageView();
    StackPane root = new StackPane();

    Label coordX = new Label(Double.toString(camOrigin.x()));
    Label coordY = new Label(Double.toString(camOrigin.y()));
    Label coordZ = new Label(Double.toString(camOrigin.z()));
    @Override
    public void start(Stage stage) throws IOException {


        Runnable renderTask = () -> {

            System.out.println("starting capture...");

            cam1.render(true, world, lights);
        };
        Scene scene = new Scene(root, cam1.imageWidth, cam1.getHeight());
        root.setAlignment(coordX, Pos.TOP_LEFT);
        root.setAlignment(coordY, Pos.TOP_CENTER);
        root.setAlignment(coordZ, Pos.TOP_RIGHT);


        //animatie
        Duration interval = Duration.seconds(frameRate);
        KeyFrame keyFrame = new KeyFrame(interval, actionEvent -> {
            update();
        });
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


        StackPane.setAlignment(frame, Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.BLACK,null, null)));
        root.getChildren().add(frame);
        root.getChildren().add(coordX);
        root.getChildren().add(coordY);
        root.getChildren().add(coordZ);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            int shiftMult = 1;
            @Override
            public void handle(KeyEvent event) {
                if (event.isShiftDown()) {shiftMult=10;}
                else shiftMult =1;
                switch (event.getCode()) {
                    case EQUALS:
                        isCameraRotating = true;
                        cam1.verticalFOV-=Math.PI/360*shiftMult;
                        break;
                    case MINUS:
                        isCameraRotating = true;
                        cam1.verticalFOV+=Math.PI/360*shiftMult;
                        break;
                    case Q:
                        isCameraRotating = true;
                        cam1.lookat.rotateY(-Math.PI/400*shiftMult);
                        break;
                    case E:
                        isCameraRotating = true;
                        cam1.lookat.rotateY(Math.PI/400*shiftMult);
                        break;
                    case UP:
                        isCameraRotating = true;
                        coordZ.setText(Double.toString( Double.parseDouble(coordZ.getText())-0.04*shiftMult));
                        cam1.cameraCenter = Vector.add(cam1.cameraCenter, new Vector(0,0,-0.04*shiftMult));
                        cam1.lookat = Vector.add(cam1.lookat, new Vector(0,0,-0.04*shiftMult));
                        break;
                    case LEFT:
                        isCameraRotating = true;
                        //camOrigin = new Vector(camOrigin.x()-0.02,camOrigin.y(),camOrigin.z());
                        coordX.setText(Double.toString( Double.parseDouble(coordX.getText())-0.02*shiftMult));
                        cam1.cameraCenter = Vector.add(cam1.cameraCenter, new Vector(-0.02*shiftMult,0,0));
                        break;
                    case RIGHT:
                        isCameraRotating = true;
                        coordX.setText(Double.toString( Double.parseDouble(coordX.getText())+0.02*shiftMult));
                        cam1.cameraCenter = Vector.add(cam1.cameraCenter, new Vector(0.02*shiftMult,0,0));
                        break;
                    case DOWN:
                        isCameraRotating = true;
                        coordZ.setText(Double.toString( Double.parseDouble(coordZ.getText())+0.04*shiftMult));
                        cam1.cameraCenter = Vector.add(cam1.cameraCenter, new Vector(0,0,0.04*shiftMult));
                        cam1.lookat = Vector.add(cam1.lookat, new Vector(0,0,+0.04*shiftMult));
                        break;
                    case SPACE:
                        isCameraRotating = true;
                        coordY.setText(Double.toString( Double.parseDouble(coordY.getText())+0.1*shiftMult));
                        cam1.cameraCenter = Vector.add(cam1.cameraCenter, new Vector(0,.1*shiftMult,0));
                        break;
                    case Z:
                        isCameraRotating = true;
                        coordY.setText(Double.toString( Double.parseDouble(coordY.getText())-0.1*shiftMult));
                        cam1.cameraCenter = Vector.add(cam1.cameraCenter, new Vector(0,-.1*shiftMult,0));
                        break;
                    case C:
                        cam1.samplesPerPixel = 100;
                        cam1.maxDepth = 30;
                        cam1.imageWidth = 800;
                        Thread thread = new Thread(renderTask);
                       // thread.setDaemon(true);
                        thread.start();
                        break;
                }
                if (event.getCode() == KeyCode.ESCAPE) {
                    System.out.println("Escape key pressed");
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case Q:
                    case E:
                    case Z:
                    case UP:
                    case LEFT:
                    case RIGHT:
                    case DOWN:
                    case SPACE:
                        isCameraRotating = false;
                        break;
                    // Handle other cases as before
                }
            }});

        stage.setTitle("Project Eend");
        stage.setScene(scene);
        stage.show();
    }

    private void update() {
        if (!cam1.block && isCameraRotating)
            frame.setImage(cam1.render (world,lights));

    }

    public static void main(String[] args) {
        Utility.loadWorld(world,lights,1);
        world = new HittableList(new BBNode(world));
        cam1.imageWidth = 400;
        cam1.cameraCenter = camOrigin;
        cam1.background = new Vector(.2,.2,.2);
        //cam1.render(true, world); //TODO vervang door capture

        cam1.samplesPerPixel = 1;
        cam1.maxDepth = 3;
        launch(args);



    }
}