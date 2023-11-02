package proeend.misc;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import proeend.Main;
import proeend.hittable.Hittable;
import proeend.math.Vector;

/**
 * Deze klasse beheert gebeurtenissen voor interactie met de gebruiker, zoals toetsenbord input en venster aanpassingen.
 */
public class EventHandler {

    private static final Vector INITIAL_CAMERA_POSITION = new Vector(0, 0, 2);
    private static final double ROTATION_UNIT = Math.PI / 360;
    private final Label coordX;
    private final Label coordY;
    private final Label coordZ;
    public static boolean ExitProgram;

    public EventHandler() {
        coordX = new Label(Double.toString(INITIAL_CAMERA_POSITION.getX()));
        coordY = new Label(Double.toString(INITIAL_CAMERA_POSITION.getY()));
        coordZ = new Label(Double.toString(INITIAL_CAMERA_POSITION.getZ()));
        setLabels();
    }

    /**
     * Stelt de labels in de juiste positie in voor de camera coördinaten.
     */
    private void setLabels() {
        //Todo: Labels verwijderen of zichtbaar maken.
        // Stel je zou stackPane megeven vanuit main, krijg je een error.
        // Maar zoals nu static property aanpassen is ook niet good practise en werkt ook niet.

        StackPane.setAlignment(coordX, Pos.TOP_LEFT);
        StackPane.setAlignment(coordY, Pos.TOP_CENTER);
        StackPane.setAlignment(coordZ, Pos.TOP_RIGHT);
    }

    /**
     * Stelt event handlers in voor het toetsenbord en venstergrootte.
     *
     * @param stage  Het JavaFX-venster
     * @param frame  Het afbeeldingsframe
     * @param camera De camera met instellingen
     * @param world  Het 3D-wereldobject met verschillende objecten
     * @param lights De lichtbronnen in de wereld
     */
    public void setupEventHandlers(Stage stage, ImageView frame, Camera camera, Hittable world, Hittable lights) {
        setKeyReleasedEventHandler(stage, camera);
        setKeyPressedEventHandler(stage, camera, world, lights);
        setResizeListeners(stage, frame, camera);
    }


    /**
     * Stelt de key released event handler in.
     *
     * @param stage  Het JavaFX-venster
     * @param camera De camera
     */
    private void setKeyPressedEventHandler(Stage stage, Camera camera, Hittable world, Hittable lights) {
        Scene scene = stage.getScene();
        scene.setOnKeyPressed(new javafx.event.EventHandler<>() {
            int shiftMult = 1;


            /**
             * Behandelt toetsindrukken en past de camera aan op basis van de toetsen.
             * @param event Het toetsenbord event.
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
                        Main.getThreadController().cancelAllTasks();
                        camera.setSamplesPerPixel(100);
                        //WritableImage image = Main.getFrame().snapshot(new SnapshotParameters(), null);
                        //ImageSaver.saveImage(image, camera.getSamplesPerPixel());
                        Renderer.render(camera, Main.getThreadController(), true);

                    }
                    case ESCAPE -> {
                        ExitProgram = true;
                        Platform.exit();
                    }
                }
            }
        });

    }





    /**
     * Stelt resize listeners in voor het aanpassen van het afbeeldingsframe bij wijzigingen in venstergrootte.
     *
     * @param stage  Het JavaFX-venster
     * @param frame  Het afbeeldingsframe
     * @param camera De camera
     */
    private void setResizeListeners(Stage stage, ImageView frame, Camera camera) {
        ChangeListener<Number> resizeListener = (observableValue, oldValue, newValue) -> {
            double newWidth = stage.getWidth();
            double newHeight = (newValue.doubleValue() == stage.widthProperty().doubleValue()) ? newWidth * (1.0 / camera.getAspectRatio()) : stage.getHeight();
            frame.setFitWidth(newWidth);
            frame.setFitHeight(newHeight);
            camera.setAspectRatio(newWidth / newHeight);
            camera.setHasMovedSinceLastFrame(true);
        };

        stage.widthProperty().addListener(resizeListener);
        stage.heightProperty().addListener(resizeListener);
    }

    /**
     * Stelt de key released event handler in voor het regelen van camera- en bewegingsstatus.
     *
     * @param stage  Het JavaFX-venster
     * @param camera De camera
     */
    private void setKeyReleasedEventHandler(Stage stage, Camera camera) {
        Scene scene = stage.getScene();
        scene.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case EQUALS, MINUS, Q, E, UP, LEFT, RIGHT, DOWN, SPACE, Z -> {
                    camera.setCameraMoving(false);
                    camera.setHasMovedSinceLastFrame(true);
                }
            }
        });
    }
}
