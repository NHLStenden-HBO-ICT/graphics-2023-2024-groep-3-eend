package com.example.project_eend;

import classes.Camera;
import classes.Vec;
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

    static Camera cam1 = new Camera();
    static double frameRate = 1.0/20.0; //hertz
    static double aspectRatio = 16.0/9.0;
    ImageView frame = new ImageView(cam1.render());
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
        frame.setImage(cam1.render());
    }

    public static void main(String[] args) {

        //cam1.image_width = 800; //zet maar niet te hoog
        System.out.println(cam1.pixelDeltaU.x());
        System.out.println(cam1.pixelDeltaV.y());
        cam1.render(true); //TODO vervang door capture
        launch();


    }
}