package com.example.project_eend;

import classes.Camera;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;

public class Main extends Application {

    static Camera cam1 = new Camera();
    static double frameRate = 1.0/30.0; //hertz
    static double aspectRatio = 16.0/9.0;
    ImageView frame = new ImageView(cam1.render());
    AnchorPane root = new AnchorPane();
    @Override
    public void start(Stage stage) throws IOException {



        Scene scene = new Scene(root, cam1.image_width, cam1.getHeight());
        root.getChildren().add(frame);

        //animatie
        Duration interval = Duration.seconds(frameRate);
        KeyFrame keyFrame = new KeyFrame(interval, actionEvent -> {
            update();
        });
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();



        stage.setTitle("Project Eend");
        stage.setScene(scene);
        stage.show();
    }

    private void update() {
        frame.setImage(cam1.render());
    }

    public static void main(String[] args) {

        cam1.image_width = 800; //zet maar niet te hoog
        launch();
    }
}