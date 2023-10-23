package proeend.misc;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import proeend.hittable.Hittable;
import proeend.math.Vector;
import javafx.scene.control.Label;


public class EventHandler {

    private static final Vector INITIAL_CAMERA_POSITION = new Vector(0, 0, 2);
    private final Label coordX;
    private final Label coordY;
    private final Label coordZ;

    public EventHandler() {
        coordX = new Label(Double.toString(INITIAL_CAMERA_POSITION.getX()));
        coordY = new Label(Double.toString(INITIAL_CAMERA_POSITION.getY()));
        coordZ = new Label(Double.toString(INITIAL_CAMERA_POSITION.getZ()));
        setLabels();
    }

    private void setLabels() {
        StackPane.setAlignment(coordX, Pos.TOP_LEFT);
        StackPane.setAlignment(coordY, Pos.TOP_CENTER);
        StackPane.setAlignment(coordZ, Pos.TOP_RIGHT);
    }

    public void setupEventHandlers(Scene scene, Camera camera, Hittable world, Hittable lights) {
        double ROTATION_UNIT = Math.PI / 360;

        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case EQUALS, MINUS, Q, E, UP, LEFT, RIGHT, DOWN, SPACE, Z -> {
                    camera.setCameraMoving(false);
                    camera.setHasMovedSinceLastFrame(true);
                }
            }
        });


        scene.setOnKeyPressed(new javafx.event.EventHandler<>() {
            int shiftMult = 1;

            /**
             * Zorgt ervoor dat het programma reageert als er een toets wordt ingedrukt.
             *
             * @param event Geeft aan wat er gebeurt.
             */
            @Override
            public void handle(KeyEvent event) {

                if (event.isShiftDown()) {
                    shiftMult = 10;
                } else shiftMult = 1;
                switch (event.getCode()) {
                    case EQUALS -> {
                        camera.setCameraMoving(true);
                        camera.setHasMovedSinceLastFrame(true);
                        camera.updateVerticalFOV(ROTATION_UNIT * shiftMult);
                    }
                    case MINUS -> {
                        camera.setCameraMoving(true);
                        camera.setHasMovedSinceLastFrame(true);
                        camera.updateVerticalFOV(-ROTATION_UNIT * shiftMult);
                    }
                    case Q -> {
                        camera.setCameraMoving(true);
                        camera.setHasMovedSinceLastFrame(true);
                        camera.setLookat(camera.getLookat().rotateY(-ROTATION_UNIT * shiftMult));
                    }
                    case E -> {
                        camera.setCameraMoving(true);
                        camera.setHasMovedSinceLastFrame(true);
                        camera.setLookat(camera.getLookat().rotateY(ROTATION_UNIT * shiftMult));
                    }
                    case UP -> {
                        camera.setCameraMoving(true);
                        camera.setHasMovedSinceLastFrame(true);
                        coordZ.setText(Double.toString(Double.parseDouble(coordZ.getText()) - 0.04 * shiftMult));
                        camera.setCameraCenter(camera.getCameraCenter().add(new Vector(0, 0, -0.04 * shiftMult)));
                        camera.setLookat(camera.getLookat().rotateY(-ROTATION_UNIT * shiftMult));
                    }
                    case LEFT -> {
                        camera.setCameraMoving(true);
                        camera.setHasMovedSinceLastFrame(true);
                        coordX.setText(Double.toString(Double.parseDouble(coordX.getText()) - 0.02 * shiftMult));
                        camera.setCameraCenter(camera.getCameraCenter().add(new Vector(-0.02 * shiftMult, 0, 0)));
                    }
                    case RIGHT -> {
                        camera.setCameraMoving(true);
                        camera.setHasMovedSinceLastFrame(true);
                        coordX.setText(Double.toString(Double.parseDouble(coordX.getText()) + 0.02 * shiftMult));
                        camera.setCameraCenter(camera.getCameraCenter().add(new Vector(0.02 * shiftMult, 0, 0)));
                    }
                    case DOWN -> {
                        camera.setCameraMoving(true);
                        camera.setHasMovedSinceLastFrame(true);
                        coordZ.setText(Double.toString(Double.parseDouble(coordZ.getText()) + 0.04 * shiftMult));
                        camera.setCameraCenter(camera.getCameraCenter().add(new Vector(0, 0, 0.04 * shiftMult)));
                        camera.setLookat(camera.getLookat().add(new Vector(0, 0, 0.04 * shiftMult)));
                    }
                    case SPACE -> {
                        camera.setCameraMoving(true);
                        camera.setHasMovedSinceLastFrame(true);
                        coordY.setText(Double.toString(Double.parseDouble(coordY.getText()) + 0.1 * shiftMult));
                        camera.setCameraCenter(camera.getCameraCenter().add(new Vector(0, .1 * shiftMult, 0)));
                    }
                    case Z -> {
                        camera.setCameraMoving(true);
                        camera.setHasMovedSinceLastFrame(true);
                        coordY.setText(Double.toString(Double.parseDouble(coordY.getText()) - 0.1 * shiftMult));
                        camera.setCameraCenter(camera.getCameraCenter().add(new Vector(0, -.1 * shiftMult, 0)));
                    }
                    case C -> {
                        camera.setSamplesPerPixel(50);
                        camera.setMaxDepth(10);
                        camera.setImageWidth(400);
                        Renderer.render(camera, true, world, lights);
                    }
                }


                if (event.getCode() == KeyCode.ESCAPE) {
                    System.out.println("Escape key pressed");
                }
            }
        });
    }
}
