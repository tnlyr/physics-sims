package edu.vanier.physicsimulations.engines;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class ProjectileMotionEngine {
    private static ProjectileMotionEngine instance;
    private Pane pane = new Pane();
    private double initialPosX, initialPosY;
    private double currentPosX, currentPosY;
    private double nextPosX, nextPosY;
    private double initialVelocity;
    private double initialVelocityX, initialVelocityY;
    private double currentVelocityX, currentVelocityY;
    private double angle;
    private double gravity;
    private Line netVector, vectorX, vectorY;
    private double centerX, centerY;
    private double radius;
    private Canvas canvas = new Canvas(1500, 1500);
    private AnimationTimer timer;
    private double time;
    private double timeStep = 0.1;
    private GraphicsContext graphicsContext;

    public ProjectileMotionEngine(double s, double a, Canvas c) {
        setCanvas(canvas);
        setGraphicsContext(graphicsContext);

        this.time = 1;
        this.initialPosX = 0;
        this.angle = angle;
        this.gravity = gravity;
        this.graphicsContext = this.canvas.getGraphicsContext2D();

        this.currentVelocityX = initialVelocity * Math.cos(Math.toRadians(angle));
        this.currentVelocityY = initialVelocity * Math.sin(Math.toRadians(angle));
        
        this.initialVelocityY = initialVelocity * Math.sin(Math.toRadians(angle));
        
        this.initialPosX = 0;
        this.initialPosY = this.canvas.getHeight() - radius;

        this.timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };

    }
    private void update() {
        updateCoordinates();
        updateVectors();
        if (currentPosY >= canvas.getHeight() - radius) {
            timer.stop();
        }
        if (currentPosY < 0) {
            currentPosY = 0;
        }
        if (currentPosX >= canvas.getWidth() - radius) {
            currentPosX = canvas.getWidth() - radius;
        }
        if (currentPosX < 0) {
            currentPosX = 0;
        }
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        drawVectors(graphicsContext);
    }

    private double motionX(double time, double currentPosX, double currentVelocityX) {
        double nextPosX = currentPosX + currentVelocityX * time;
        return nextPosX;
    }

    private double motionY(double time, double currentPosY, double currentVelocityY, double gravity) {
        double nextPosY = currentPosY;
        if (this.initialVelocityY == 0) {
            return nextPosY;
        }
        else {
            nextPosY = currentPosY + currentVelocityY * time + 0.5 * gravity * Math.pow(time, 2);
            return nextPosY;
        }
    }

    private double getNextVectorX() {
        this.nextPosX = motionX(this.time, centerX, currentVelocityX);
        return this.nextPosX;
    }

    private double getNextVectorY() {
        this.nextPosY = motionY(this.time, centerY, currentVelocityY, gravity);
        return this.nextPosY;
    }

    public void showCanva() {
        this.currentPosX = this.initialPosX;
        this.currentPosY = this.initialPosY;

        this.centerX = currentPosX + radius;
        this.centerY = currentPosY + radius;
        drawVectors(this.graphicsContext);
    }

    public void drawVectors(GraphicsContext graphicsContext) {
        this.graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillOval(currentPosX, currentPosY, radius, radius);

        this.vectorX = new Line(centerX, centerY, centerX + getNextVectorX(), centerY);
        this.vectorX.setStrokeWidth(3);
        this.vectorX.setStroke(Color.RED);

        this.vectorY = new Line(centerX, centerY, centerX, centerY + getNextVectorY());
        this.vectorY.setStrokeWidth(3);
        this.vectorY.setStroke(Color.GREEN);

        this.netVector = new Line(centerX, centerY, centerX + getNextVectorX(), centerY + getNextVectorY());
        this.netVector.setStrokeWidth(2);
        this.netVector.setStroke(Color.BLUE);

        graphicsContext.strokeLine(vectorX.getStartX(), vectorX.getStartY(), vectorX.getEndX(), vectorX.getEndY());
        this.pane.getChildren().add(this.vectorX);
        this.pane.getChildren().add(this.vectorY);
        this.pane.getChildren().add(this.netVector);
    }

    public void updateCoordinates() {
        this.currentPosX = motionX(this.time, this.currentPosX, this.currentVelocityX);
        this.nextPosX = this.currentPosX;
        this.currentPosY = motionY(this.time, this.currentPosY, this.currentVelocityY, this.gravity);
        this.centerY = this.currentPosY + radius;
    }

    private void updateVectors() {
        this.pane.getChildren().remove(0, 3);
    }

    public void startAnimation() {
        this.timer.start();
    }

    public void pauseAnimation() {
        this.timer.stop();
    }

    public void resetAnimation() {
        this.timer.stop();
        this.time = 1;
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        getPane().getChildren().clear();
        this.currentPosX = this.initialPosX;
        this.currentPosY = this.initialPosY;
        this.currentVelocityX = initialVelocity * Math.cos(Math.toRadians(angle));
        this.currentVelocityY = initialVelocity * Math.sin(Math.toRadians(angle));
        this.centerX = currentPosX + radius;
        this.centerY = currentPosY + radius;
        this.pane.getChildren().remove(0, 3);
        drawVectors(graphicsContext);
    }

    // Getters and Setters
    public static ProjectileMotionEngine getInstance() {
        return instance;
    }

    public static void setInstance(ProjectileMotionEngine instance) {
        ProjectileMotionEngine.instance = instance;
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

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public Line getNetVector() {
        return netVector;
    }

    public void setNetVector(Line netVector) {
        this.netVector = netVector;
    }

    public Line getVectorX() {
        return vectorX;
    }

    public void setVectorX(Line vectorX) {
        this.vectorX = vectorX;
    }

    public Line getVectorY() {
        return vectorY;
    }

    public void setVectorY(Line vectorY) {
        this.vectorY = vectorY;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public AnimationTimer getTimer() {
        return timer;
    }

    public void setTimer(AnimationTimer timer) {
        this.timer = timer;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public double getTimeStep() {
        return timeStep;
    }

    public void setTimeStep(double timeStep) {
        this.timeStep = timeStep;
    }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public void setGraphicsContext(GraphicsContext graphicsContext) {
        this.graphicsContext = graphicsContext;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public double getInitialVelocity() {
        return initialVelocity;
    }

    public void setInitialVelocity(double initialVelocity) {
        this.initialVelocity = initialVelocity;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
