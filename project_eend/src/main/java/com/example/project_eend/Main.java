package com.example.project_eend;

import classes.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.awt.*;
import java.io.IOException;

public class Main extends Application {

    static HittableList world = new HittableList();

    static Camera cam1 = new Camera();
    static double frameRate = 1.0/10.0; //hertz
    static double aspectRatio = 16.0/9.0;
    ImageView frame = new ImageView();
    StackPane root = new StackPane();
    @Override
    public void start(Stage stage) throws IOException {


        Scene scene = new Scene(root, cam1.image_width, cam1.getHeight());

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
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                        cam1.cameraCenter = Vec.add(cam1.cameraCenter, new Vec (0,0,-1));

                        break;
                    case LEFT:
                        cam1.cameraCenter = Vec.add(cam1.cameraCenter, new Vec (1,0,0));
                        break;
                    case RIGHT:
                        cam1.cameraCenter = Vec.add(cam1.cameraCenter, new Vec (-1,0,0));
                        break;
                    case DOWN:
                        cam1.cameraCenter = Vec.add(cam1.cameraCenter, new Vec (0,0,1));
                        break;
                    case C:
                        cam1.maxDepth = 50;
                        cam1.samplesPerPixel = 100;
                        System.out.println("starting capture...");
                        cam1.render(true, world);
                        cam1.maxDepth = 3;
                        cam1.samplesPerPixel = 1;

                }
                if (event.getCode() == KeyCode.ESCAPE) {
                    System.out.println("Escape key pressed");
                }
            }
        });

        stage.setTitle("Project Eend");
        stage.setScene(scene);
        stage.show();
    }

    private void update() {
        frame.setImage(cam1.render(world));
    }

    public static void main(String[] args) {

        Lambertian greyLambertian = new Lambertian(new Vec(.5,.5,.5));
        Mirror redMirror = new Mirror(new Vec(1,.5,.5), .3);
        world.add(new Sphere(new Vec(0,0,-1),0.5, redMirror));
        world.add(new Sphere(new Vec(0,-100.5,-1), 100, greyLambertian));
        world.add(new Sphere(new Vec(-1,0,-1),.5,greyLambertian));
        //cam1.render(true, world); //TODO vervang door capture

        cam1.samplesPerPixel = 1;
        cam1.maxDepth = 3;
        launch();


    }
}