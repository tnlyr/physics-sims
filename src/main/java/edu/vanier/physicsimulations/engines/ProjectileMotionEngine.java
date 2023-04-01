package edu.vanier.physicsimulations.engines;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

public class ProjectileMotionEngine {
    private static ProjectileMotionEngine instance;
    private double initialPosX, initialPosY;
    private double currentPosX, currentPosY;
    private double nextPosX, nextPosY;
    private double initialVelocityX, initialVelocityY;
    private double currentVelocityX, currentVelocityY;
    private double angle;
    private double gravity;

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public static void setInstance(ProjectileMotionEngine instance) {
        ProjectileMotionEngine.instance = instance;
    }

    public double getNextPosX() {
        return nextPosX;
    }

    public void setNextPosX(double nextPosX) {
        this.nextPosX = nextPosX;
    }

    public double getNextPosY() {
        return nextPosY;
    }

    public void setNextPosY(double nextPosY) {
        this.nextPosY = nextPosY;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    private double time;

    private void animationSettings() {
    }

    public void update(double time) {
    }

    public static ProjectileMotionEngine getInstance() {
        if (instance == null) {
            instance = new ProjectileMotionEngine();
        }
        return instance;
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
