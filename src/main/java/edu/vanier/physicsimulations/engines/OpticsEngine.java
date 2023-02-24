package edu.vanier.physicsimulations.engines;

public class OpticsEngine {
    private double sourcePos, sourceHeight;
    private double objectPos, objectHeight;
    private double imagePos, imageHeight;
    private double focalLength;
    private double objectDistance, imageDistance;
    private double objectDistanceToImageDistanceRatio;

    public OpticsEngine(double sourcePos, double sourceHeight, double objectPos, double objectHeight, double imagePos, double imageHeight, double focalLength) {

    }

    public void update(double time) {

    }

    public double getSourcePos() {
        return sourcePos;
    }

    public void setSourcePos(double sourcePos) {
        this.sourcePos = sourcePos;
    }

    public double getSourceHeight() {
        return sourceHeight;
    }

    public void setSourceHeight(double sourceHeight) {
        this.sourceHeight = sourceHeight;
    }

    public double getObjectPos() {
        return objectPos;
    }

    public void setObjectPos(double objectPos) {
        this.objectPos = objectPos;
    }

    public double getObjectHeight() {
        return objectHeight;
    }

    public void setObjectHeight(double objectHeight) {
        this.objectHeight = objectHeight;
    }

    public double getImagePos() {
        return imagePos;
    }

    public void setImagePos(double imagePos) {
        this.imagePos = imagePos;
    }

    public double getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(double imageHeight) {
        this.imageHeight = imageHeight;
    }

    public double getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(double focalLength) {
        this.focalLength = focalLength;
    }

    public double getObjectDistance() {
        return objectDistance;
    }

    public void setObjectDistance(double objectDistance) {
        this.objectDistance = objectDistance;
    }

    public double getImageDistance() {
        return imageDistance;
    }

    public void setImageDistance(double imageDistance) {
        this.imageDistance = imageDistance;
    }

    public double getObjectDistanceToImageDistanceRatio() {
        return objectDistanceToImageDistanceRatio;
    }

    public void setObjectDistanceToImageDistanceRatio(double objectDistanceToImageDistanceRatio) {
        this.objectDistanceToImageDistanceRatio = objectDistanceToImageDistanceRatio;
    }
}
