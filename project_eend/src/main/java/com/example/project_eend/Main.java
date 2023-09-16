package com.example.project_eend;

import classes.Camera;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
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
    static double frameRate = 1.0/30.0; //hertz
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
        stage.setTitle("Project Eend");
        stage.setScene(scene);
        stage.show();
    }

    private void update() {
        frame.setImage(cam1.render());
    }

    public static void main(String[] args) {

        cam1.image_width = 800; //zet maar niet te hoog
        cam1.render(true); //TODO vervang door capture
        launch();


    }
}