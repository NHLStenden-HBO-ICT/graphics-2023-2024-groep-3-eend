package proeend.misc;

import javafx.scene.paint.Color;
import proeend.math.Vector;

/**
 * Deze klasse vertegenwoordigt een camera voor het renderen van beelden.
 */
public class Camera {

    private boolean cameraIsMoving = false;
    private boolean hasMovedSinceLastFrame = false;
    private Vector cameraCenter = new Vector(0, 0, 0);
    private Vector lookat = new Vector(0, 0, -1);
    private Vector up = new Vector(0, 1, 0);
    private double verticalFOV = Math.PI / 2;
    private int samplesPerPixel;
    private int rootSPP = (int) Math.sqrt(samplesPerPixel);
    private int maxDepth = 3;
    private double aspectRatio = 16.0 / 9.0;
    private int imageWidth = 400;
    private int imageHeight = (int) (imageWidth / aspectRatio);
    private double viewportHeight;
    private double viewportWidth = viewportHeight * (double) imageWidth / (double) imageHeight;
    private Vector viewportU = new Vector(viewportWidth, 0, 0);
    private Vector viewportV = new Vector(0, -viewportHeight, 0);
    private Vector pixelDeltaU = viewportU.scale(1.0 / imageWidth);
    private Vector pixelDeltaV = viewportV.scale(1.0 / imageHeight);
    private Vector topLeftPixel;
    private final Vector background =  colorToVector(Color.CORNFLOWERBLUE);

    public void setCameraMoving(boolean cameraIsMoving) {
        this.cameraIsMoving = cameraIsMoving;
    }

    public void setHasMovedSinceLastFrame(boolean hasMoved) {
        this.hasMovedSinceLastFrame = hasMoved;
    }

    public boolean hasMovedSinceLastFrame(){
        return hasMovedSinceLastFrame;
    }

    public boolean isMoving(){
        return cameraIsMoving;
    }

    public Vector getPixelDeltaU() {
        return pixelDeltaU;
    }

    public Vector getPixelDeltaV() {
        return pixelDeltaV;
    }


    public Vector getCameraCenter() {
        return cameraCenter;
    }

    public void setCameraCenter(Vector cameraCenter) {
        this.cameraCenter = cameraCenter;
    }

    public Vector getLookat() {
        return lookat;
    }

    public void setLookat(Vector lookat) {
        this.lookat = lookat;
    }

    public void setUp(Vector up) {
        this.up = up;
    }

    public void updateVerticalFOV(double difference) {
        this.verticalFOV += difference;
    }

    public int getSamplesPerPixel() {
        return samplesPerPixel;
    }

    public void setSamplesPerPixel(int samplesPerPixel) {
        this.samplesPerPixel = samplesPerPixel;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Vector getBackground() {
        return background;
    }

    public void setAspectRatio(double aspectRatio){
        this.aspectRatio = aspectRatio;
    }

    public Vector colorToVector(Color bg) {
        return new Vector(bg.getRed(), bg.getGreen(), bg.getBlue());
    }

    public int getHeight() {
        return imageHeight;
    }

    public int getRootSPP() {
        return rootSPP;
    }

    public Vector getTopLeftPixel() {
        return topLeftPixel;
    }

    public double getAspectRatio() {
        return aspectRatio;
    }

    public Camera(){
        init();
    }

    /**
     * Initialiseert de camera-instellingen, omdat deze door key-events kunnen zijn aangepast.
     */
    public void init() {
        Vector u, v, w;
        rootSPP = (int) Math.sqrt(samplesPerPixel);
        double focalLength = Vector.length(cameraCenter.add(lookat.invert()));
        imageHeight = (int) (imageWidth / aspectRatio);
        imageHeight = Math.max(1, imageHeight);
        double h = Math.tan(verticalFOV / 2);
        w = cameraCenter.add(lookat.invert()).toUnitVector();
        u = Vector.cross(up, w).toUnitVector();
        v = Vector.cross(w, u);
        viewportHeight = 2 * h * focalLength;
        viewportWidth = viewportHeight * (double) imageWidth / (double) imageHeight;
        viewportU = u.scale(viewportWidth);
        viewportV = v.invert().scale(viewportHeight);
        pixelDeltaU = viewportU.scale(1.0 / imageWidth);
        pixelDeltaV = viewportV.scale(1.0 / imageHeight);
        Vector viewportUpperleft = cameraCenter
                .add(w.scale(focalLength).invert())
                .add(viewportU.scale(0.5).invert())
                .add(viewportV.scale(0.5).invert());
        topLeftPixel = viewportUpperleft.add(pixelDeltaU.add(pixelDeltaV).scale(1.0 / 2.0));
    }
}
