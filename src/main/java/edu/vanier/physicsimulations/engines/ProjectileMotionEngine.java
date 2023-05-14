package edu.vanier.physicsimulations.engines;

import edu.vanier.physicsimulations.controllers.GraphController;
import edu.vanier.physicsimulations.controllers.ProjectileMotionController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Light;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Objects;

public class ProjectileMotionEngine extends StackPane {
    private static ProjectileMotionController controller = new ProjectileMotionController();
    public static double WIDTH = 1038 - controller.getWidth();
    private final double PANE_WIDTH = getWidth();
    private final double PANE_HEIGHT = getHeight();
    private static int GROUND_HEIGHT = 25;
    private static int WIDTH_PADDING = 30;
    private double velocity, height, angle, gravity;
    private boolean isPlaying = false;
    private ArrayList<Point2D> points = new ArrayList<Point2D>();
    private ArrayList<Light.Point> calculatedPoints = new ArrayList<Light.Point>();
    private Canvas cv;
    private int i = 0;
    DecimalFormat df = new DecimalFormat("#.##");
    double timestep;
    double duration = motionDuration();
    double widthSc, heightSc, scale;
    Timeline timer;
    private static GraphController graphController;

    /**
     * Constructs a new ProjectileMotionEngine object.
     * The default size of the engine is set to PANE_WIDTH by PANE_HEIGHT.
     * The engine's background is set to a blue color.
     * The engine contains a Canvas object that is added to the engine's children.
     */
    public ProjectileMotionEngine() {
        setPrefSize(PANE_WIDTH, PANE_HEIGHT);
        setBackground(new Background(new BackgroundFill(Color.rgb(30, 144, 255), CornerRadii.EMPTY, Insets.EMPTY)));
        cv = new Canvas(PANE_WIDTH, PANE_HEIGHT);
        getChildren().add(cv);
    }

    /**
     * Sets the {@link ProjectileMotionController} to be used by the class.
     * @param controller the {@link ProjectileMotionController} instance to be set
     */
    public void setController(ProjectileMotionController controller) {
        this.controller = controller;
    }

    /**
     * Overrides the layoutChildren method of the Parent class and sets the width and height of the canvas (cv)
     * To be equal to the width and height of the current simulation window.
     */
    @Override
    public void layoutChildren() {
        super.layoutChildren();
        cv.setWidth(getWidth());
        cv.setHeight(getHeight());
    }

    /**
     * Draws the projectile motion on the given Canvas based on the calculated points and settings.
     * The Canvas is cleared before drawing.
     * If the background is not imported from an image, the background is drawn as green ground.
     * The current state of the motion is drawn as a gray rectangle on the Canvas.
     * The highest point of the motion is displayed as an orange circle and its height is labeled as "Highest Point" near it.
     * The last point of the motion is labeled with its coordinates as "(x, y)".
     * The current position of the projectile is represented as a red circle.
     * The x-coordinate of the current position is labeled as "Distance" at the bottom right corner of the Canvas.
     * @param None.
     * @return void.
     */
    public void draw() {
        GraphicsContext g = cv.getGraphicsContext2D();
        g.clearRect(0, 0, getWidth(), getHeight());

        // Draw the background if not imported from image
        if (checkBackground()) {
            g.setFill(Color.rgb(50, 205, 50));
            g.fillRect(0, getHeight() - GROUND_HEIGHT, getWidth(), GROUND_HEIGHT);
        }

        if (!isPlaying) {
            return;
        }

        g.setFill(Color.GRAY);

        if (height > 0) {
            g.fillRect(WIDTH_PADDING, getHeight() - GROUND_HEIGHT - height * scale, 20, height * scale);
        }

        g.setFill(Color.BLACK);

        DecimalFormat fmt = new DecimalFormat("#.##");

        boolean displayedMaxY = false;

        // Draw each point on Canvas
        for (int j = 0; j <= i; j++) {
            g.fillOval(calculatedPoints.get(j).getX(), calculatedPoints.get(j).getY(), 4, 4);

            // If highest point
            if (!displayedMaxY && Math.abs(points.get(j).getY() - highestPoint()) < 0.001) {
                displayedMaxY = true;
                g.fillText("Highest Point: " + fmt.format(points.get(j).getY()) + "m",
                        calculatedPoints.get(j).getX() - 10, calculatedPoints.get(j).getY() - 10);
                g.setFill(Color.ORANGE);
                g.fillOval(calculatedPoints.get(j).getX(), calculatedPoints.get(j).getY() - 5, 15, 15);
                g.setFill(Color.BLACK);
            }

            // If last point
            if (j == i) {
                String x = fmt.format(points.get(j).getX());
                String y = fmt.format(points.get(j).getY());

                g.fillText("(" + x + ", " + y + ")", calculatedPoints.get(j).getX() - 5, calculatedPoints.get(j).getY() - 5);
            }
        }

        g.setFill(Color.RED);

        g.fillOval(WIDTH_PADDING + getX(duration) * scale,
                getHeight() - (GROUND_HEIGHT + getY(duration) * scale), 12, 12);

        g.setFill(Color.BLACK);

        String xText = "Distance: " + fmt.format(getX(duration)) + " m";

        g.setTextAlign(TextAlignment.RIGHT);
        g.setTextBaseline(VPos.BOTTOM);
        g.fillText(xText, getWidth() - 10 - 20, getHeight() - 10);
    }


    /**
     * Checks whether the current background of the node is the expected background.
     * @return true if the current background matches the expected background, false otherwise.
     */
    private boolean checkBackground() {
        Background expectedBackground = new Background(new BackgroundFill(Color.rgb(30, 144, 255), CornerRadii.EMPTY, Insets.EMPTY));
        return Objects.equals(getBackground(), expectedBackground);
    }

    /**
     * Starts the simulation with the given initial parameters, updating the canvas every timestep.
     * @param v the initial velocity in m/s
     * @param h the initial height in m
     * @param a the launch angle in degrees
     * @param g the gravitational acceleration in m/s^2
     */
    public void startSimulation(double v, double h, double a, double g) {
        isPlaying = true;

        this.angle = Math.toRadians(a);
        this.velocity = v;
        this.height = h;
        this.gravity = g;

        duration = motionDuration();
        timestep = duration / 1000;

        heightSc = (getHeight() - GROUND_HEIGHT) / (highestPoint() * 1.1);
        widthSc = (getWidth() - 2 * WIDTH_PADDING) / (getX(duration));

        scale = Math.min(heightSc, widthSc);

        points.clear();
        calculatedPoints.clear();
        i = 0;

        for (double t = 0; t <= duration; t += timestep) {
            points.add(new Point2D(getX(t), getY(t)));
            calculatedPoints.add(new Light.Point(WIDTH_PADDING + getX(t) * scale,
                    (int) getHeight() - (GROUND_HEIGHT + getY(t) * scale), 0, Color.BLACK));
        }

        controller.setSimulationInfo(duration, getX(duration), highestPoint());

        if (timer != null) {
            timer.stop();
        }

        timer = new Timeline();

        KeyFrame kf = new KeyFrame(Duration.millis(timestep * 1000), event -> {
            cv.getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
            draw();

            i++;

            if (i == calculatedPoints.size() - 1) {
                timer.stop();
                timer = null;
                isPlaying = false;
                controller.setButtonsAfterEndOfSimulation();
                try {
                    graphController.stopGraph();
                } catch (Exception e) {
                    System.out.println("Graph is not running");
                }
            }
        });
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.getKeyFrames().add(kf);
        timer.play();
    }


    /**
     * Pauses the current simulation by pausing the timer and setting {@code isPlaying} to false.
     */
    public void pauseSimulation() {
        timer.pause();
        isPlaying = false;
    }

    /**
     * Resumes the current simulation by resuming the timer and setting {@code isPlaying} to true.
     */
    public void resumeSimulation() {
        timer.play();
        isPlaying = true;
    }

    /**
     * Returns whether the simulation is currently running.
     * @return true if the simulation is running, false otherwise
     */
    public boolean isRunning() {
        return isPlaying;
    }

    /**
     * Stops the simulation and sets isPlaying to false.
     */
    public void stopSimulation() {
        timer.stop();
        isPlaying = false;
    }

    /**
     * Creates a new graph controller for the simulation.
     */
    public void graphSimulation() {
        graphController = new GraphController(angle, velocity, gravity);
    }

    /**
     * Calculates the x-coordinate of the projectile at time t.
     *
     * @param t the time in seconds
     * @return the x-coordinate in meters
     */
    private double getX(double t){
        return velocity * Math.cos(angle) * t;
    }

    /**
     * Calculates the y-coordinate of the projectile at time t.
     *
     * @param t the time in seconds
     * @return the y-coordinate in meters
     */
    private double getY(double t){
        return height + velocity * Math.sin(angle) * t - 0.5 * gravity * Math.pow(t, 2);
    }

    /**
     * Calculates the maximum height reached by the projectile.
     *
     * @return the maximum height in meters
     */
    private double highestPoint() {
        return getY(velocity * Math.sin(angle) / gravity);
    }

    /**
     * Calculates the duration of the motion.
     *
     * @return the duration in seconds
     */
    private double motionDuration() {
        return (-velocity * Math.sin(angle) - Math.sqrt(Math.pow(velocity * Math.sin(angle), 2) + 2 * 9.81 * height)) / (-9.81);
    }
}