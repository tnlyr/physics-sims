package edu.vanier.physicsimulations.engines;

public class ProjectileMotionEngine {
    private double initialPosX, initialPosY;
    private double currentPosX, currentPosY;
    private double initialVelocityX, initialVelocityY;
    private double currentVelocityX, currentVelocityY;

    public ProjectileMotionEngine(double initialPosX, double initialPosY, double initialVelocityX, double initialVelocityY) {
        this.initialPosX = initialPosX;
        this.initialPosY = initialPosY;
        this.initialVelocityX = initialVelocityX;
        this.initialVelocityY = initialVelocityY;
    }

    public void update(double time) {
    }

    public double getInitialPosX() {
        return initialPosX;
    }

    public void setInitialPosX(double initialPosX) {
        this.initialPosX = initialPosX;
    }

    public double getInitialPosY() {
        return initialPosY;
    }

    public void setInitialPosY(double initialPosY) {
        this.initialPosY = initialPosY;
    }

    public double getCurrentPosX() {
        return currentPosX;
    }

    public void setCurrentPosX(double currentPosX) {
        this.currentPosX = currentPosX;
    }

    public double getCurrentPosY() {
        return currentPosY;
    }

    public void setCurrentPosY(double currentPosY) {
        this.currentPosY = currentPosY;
    }

    public double getInitialVelocityX() {
        return initialVelocityX;
    }

    public void setInitialVelocityX(double initialVelocityX) {
        this.initialVelocityX = initialVelocityX;
    }

    public double getInitialVelocityY() {
        return initialVelocityY;
    }

    public void setInitialVelocityY(double initialVelocityY) {
        this.initialVelocityY = initialVelocityY;
    }

    public double getCurrentVelocityX() {
        return currentVelocityX;
    }

    public void setCurrentVelocityX(double currentVelocityX) {
        this.currentVelocityX = currentVelocityX;
    }

    public double getCurrentVelocityY() {
        return currentVelocityY;
    }

    public void setCurrentVelocityY(double currentVelocityY) {
        this.currentVelocityY = currentVelocityY;
    }
}
