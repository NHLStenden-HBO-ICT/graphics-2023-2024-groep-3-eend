package com.example.project_eend;

import classes.Camera;
import classes.Vec;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        Camera.render();
        /* //testcode
        Vec vector = new Vec(1.1,2.2,3.3);
        System.out.println(Double.toString(vector.x()));
        Vec inverse = vector.inverse();
        System.out.println(Double.toString(inverse.x()));
        double[] darray = inverse.getAll();
        for (double d:darray) {
            System.out.println(Double.toString(d));
        }
         */
        //launch();
    }
}