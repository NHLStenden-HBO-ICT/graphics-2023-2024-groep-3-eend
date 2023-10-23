package proeend.misc;

import proeend.math.Vector;

/**
 * Deze klasse vertegenwoordigt een camera voor het renderen van beelden.
 */
public class Camera {

    private boolean settingsAreLocked;
    private boolean cameraIsMoving = false;
    private boolean hasMovedSinceLastFrame = false;
    private Vector cameraCenter = new Vector(0, 0, 0);
    private Vector lookat = new Vector(0, 0, -1);
    private Vector up = new Vector(0, 1, 0);
    private double verticalFOV = Math.PI / 2;
    private int samplesPerPixel = 1;
    private int rootSPP = (int) Math.sqrt(samplesPerPixel);
    private int maxDepth = 50;
    private final double aspectRatio = 16.0 / 9.0;
    private int imageWidth = 800;
    private int imageHeight = (int) (imageWidth / aspectRatio);
    private double focalLength = 1.0;
    private double viewportHeight;
    private double viewportWidth = viewportHeight * (double) imageWidth / (double) imageHeight;
    private Vector viewportU = new Vector(viewportWidth, 0, 0);
    private Vector viewportV = new Vector(0, -viewportHeight, 0);
    private Vector pixelDeltaU = Vector.scale((1.0 / imageWidth), viewportU);
    private Vector pixelDeltaV = Vector.scale((1.0 / imageHeight), viewportV);
    private Vector viewportUpperleft = Vector.add(Vector.add(Vector.add(cameraCenter, Vector.negate(new Vector(0, 0, focalLength))), Vector.negate(Vector.scale(1.0 / 2.0, viewportU))), Vector.negate(Vector.scale(1.0 / 2.0, viewportV)));
    private Vector topLeftPixel = Vector.add(viewportUpperleft, Vector.scale(1.0 / 2.0, Vector.add(pixelDeltaU, pixelDeltaV)));
    private Vector background = new Vector();

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

    public boolean isLocked() {
        return settingsAreLocked;
    }

    public void setLock(boolean locked) {
        settingsAreLocked = locked;
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

    public void setBackground(Vector background) {
        this.background = background;
    }

    public Camera() {
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

    /**
     * Initialiseert de camera-instellingen, omdat deze door key-events kunnen zijn aangepast.
     */
    public void init() {
        Vector u, v, w;
        rootSPP = (int) Math.sqrt(samplesPerPixel);
        focalLength = Vector.length(Vector.add(cameraCenter, Vector.negate(lookat)));
        imageHeight = (int) (imageWidth / aspectRatio);
        imageHeight = Math.max(1, imageHeight);
        double h = Math.tan(verticalFOV / 2);
        w = Vector.add(cameraCenter, Vector.negate(lookat)).toUnitVector();
        u = Vector.cross(up, w).toUnitVector();
        v = Vector.cross(w, u);
        viewportHeight = 2 * h * focalLength;
        viewportWidth = viewportHeight * (double) imageWidth / (double) imageHeight;
        viewportU = Vector.scale(viewportWidth, u);
        viewportV = Vector.scale(viewportHeight, Vector.negate(v));
        pixelDeltaU = Vector.scale((1.0 / imageWidth), viewportU);
        pixelDeltaV = Vector.scale((1.0 / imageHeight), viewportV);
        viewportUpperleft = Vector.add(Vector.add(Vector.add(cameraCenter, Vector.negate(Vector.scale(focalLength, w))), Vector.negate(Vector.scale(1.0 / 2.0, viewportU))), Vector.negate(Vector.scale(1.0 / 2.0, viewportV)));
        topLeftPixel = Vector.add(viewportUpperleft, Vector.scale(1.0 / 2.0, Vector.add(pixelDeltaU, pixelDeltaV)));
    }


}
