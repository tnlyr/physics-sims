package edu.vanier.physicsimulations.engines;
import java.lang.Math;
import java.util.Objects;

public class OpticsEngine {
    private static double objectDistance;
    private double objectHeight;
    private String lensType;
    private double focalLength;

    public OpticsEngine() {
    }

    public OpticsEngine(double objectDistance, double objectHeight, String lensType, double focalLength) {
        this.objectDistance = objectDistance;
        this.objectHeight = objectHeight;
        this.lensType = lensType;
        this.focalLength = focalLength;
    }

    public OpticsEngine(double objectDistance, String lensType, double focalLength) {
        this.objectDistance = objectDistance;
        this.lensType = lensType;
        this.focalLength = focalLength;
    }

    public static double getObjectDistance() {
        return objectDistance;
    }

    public void setObjectDistance(double objectDistance) {
        this.objectDistance = objectDistance;
    }

    public double getObjectHeight() {
        return objectHeight;
    }

    public void setObjectHeight(double objectHeight) {
        this.objectHeight = objectHeight;
    }

    public String getLensType() {
        return lensType;
    }

    public void setLensType(String lensType) {
        this.lensType = lensType;
    }

    public double getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(double focalLength) {
        this.focalLength = focalLength;
    }

    /**
     * Takes into account the focal length, object distance and lens type to get the image distance
     * @param objectDistance
     * @param focalLength
     * @param lensType
     * @return
     */
    public static double lensEquation(double objectDistance, double focalLength, String lensType) {
        if (Objects.equals(lensType, "Divergent")) {

            double image = 1 / (-1 * focalLength) - 1 / objectDistance;
            double exponent = -1;
            double imageDistance = Math.pow(image, exponent);
            return imageDistance;
        } else {
            double image = 1 / focalLength - 1 / objectDistance;
            double exposant = -1;
            double imageDistance = Math.pow(image, exposant);
            return imageDistance;
        }

    }

}

