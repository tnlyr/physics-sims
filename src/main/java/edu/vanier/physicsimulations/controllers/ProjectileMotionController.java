package edu.vanier.physicsimulations.controllers;

import edu.vanier.physicsimulations.engines.ProjectileMotionEngine;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectileMotionController implements Initializable {
    @FXML
    Spinner<Double> velocitySpinner, angleSpinner, heightSpinner, gravitySpinner;
    @FXML
    Pane projectilePane;
    @FXML
    Slider playbackSlider;
    @FXML
    Button playBtn, resetBtn, graphBtn;

    static boolean isPlaying = false;
    static boolean isReset = false;
    Circle ball;
    Line ground;
    double time = 0;

    ProjectileMotionEngine engine = ProjectileMotionEngine.getInstance();
    
    public ProjectileMotionController() {
        initialize();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Double> velocitySpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 10, 1);
        velocitySpinner.setValueFactory(velocitySpinnerValue);

        SpinnerValueFactory<Double> angleSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 90, 25, 1);
        angleSpinner.setValueFactory(angleSpinnerValue);

        SpinnerValueFactory<Double> heightSpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 25, 1);
        heightSpinner.setValueFactory(heightSpinnerValue);

        SpinnerValueFactory<Double> gravitySpinnerValue = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 100, 9.8, 1);
        gravitySpinner.setValueFactory(gravitySpinnerValue);

        velocitySpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            // TODO : UPDATE VELOCITY
        });

        angleSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            // update the angle in the engine
            engine.setAngle(newValue.doubleValue());

            // update the ball position based on the new angle
            double x = engine.getCurrentVelocityX() * Math.cos(Math.toRadians(engine.getAngle())) * engine.getTime();
            double y = engine.getCurrentVelocityY() * Math.sin(Math.toRadians(engine.getAngle())) * engine.getTime() -
                    0.5 * engine.getGravity() * Math.pow(engine.getTime(), 2);
            ball.setCenterX(x);
            ball.setCenterY(engine.getInitialPosY() - y);
        });


        heightSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            // TODO : UPDATE HEIGHT
        });

        gravitySpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            // TODO : UPDATE GRAVITY
        });

        playbackSlider.valueProperty().addListener((obs, oldValue, newValue) -> {
            // TODO : UPDATE PLAYBACK
        });

        heightSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            double newHeight = newValue.doubleValue();
            moveBallHeight(newHeight);
        });


        playBtn.setOnAction(e -> {
            if (isPlaying) {
                onPause();
            } else {
                onPlay();
            }
        });
        
        resetBtn.setOnAction(e -> {
            onReset();
        });
        
        graphBtn.setOnAction(e -> {
            onGraph();
        });
    }

    private void animationTest() {
        ball = new Circle(10, Color.BLUE);
        ground = new Line(0, 400, 800, 400);
        projectilePane.getChildren().addAll(ball, ground);
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(16), e -> {
            double x = 20 * Math.cos(25) * time;
            double y = 20 * Math.sin(25) * time - 0.5 * gravitySpinner.getValue() * Math.pow(time, 2);

            ball.setCenterX(x);
            ball.setCenterY(200 - y);

            // check if ball hits the ground
            if (ball.getCenterY() >= 400) {
                timeline.stop();
            }

            time += 0.016;
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        // FIXME : Get values from spinners
    }

    private void onGraph() {
        // TODO : OPEN GRAPH
    }

    private void onReset() {
        disableButtons(false);
        playBtn.setText("Play");
        isPlaying = false;
        isReset = true;
        // TODO : RESET ANIMATION
    }

    private void onPause() {
        disableButtons(false);
        playBtn.setText("Play");
        isPlaying = false;
        // TODO : PAUSE ANIMATION
    }

    private void onPlay() {
        disableButtons(true);
        playBtn.setText("Pause");
        isPlaying = true;
        System.out.println("Gravity: " + gravitySpinner.getValue());
        // FIXME : START ANIMATION
        animationTest();
    }

    private void disableButtons(boolean b) {
        // TODO : DISABLE HBOX BUTTONS
    }

    private void initialize() {}

    // TEST FUNCTIONS
    private void animateProjectileThrown() {
        double velocity = velocitySpinner.getValue();
        double angle = angleSpinner.getValue();
        double height = heightSpinner.getValue();
        double gravity = gravitySpinner.getValue();

        double initialVelocityX = velocity * Math.cos(Math.toRadians(angle));
        double initialVelocityY = velocity * Math.sin(Math.toRadians(angle));

        double totalTime = (2 * initialVelocityY) / gravity;
        double maxDistance = initialVelocityX * totalTime;

        ball = new Circle(10, Color.BLUE);
        ball.setCenterX(0);
        ball.setCenterY(400 - height);

        ground = new Line(0, 400, maxDistance, 400);
        projectilePane.getChildren().addAll(ball, ground);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(16), e -> {
            double time = timeline.getCurrentTime().toMillis() / 1000.0;

            double x = initialVelocityX * time;
            double y = initialVelocityY * time - 0.5 * gravity * Math.pow(time, 2);

            ball.setCenterX(x);
            ball.setCenterY(400 - height - y);

            if (ball.getCenterY() >= 400) {
                timeline.stop();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private void animateProjectileDropped() {
        double height = heightSpinner.getValue();
        double gravity = gravitySpinner.getValue();

        ball = new Circle(10, Color.BLUE);
        ball.setCenterX(0);
        ball.setCenterY(400 - height);

        ground = new Line(0, 400, 800, 400);
        projectilePane.getChildren().addAll(ball, ground);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(16), e -> {
            double time = timeline.getCurrentTime().toMillis() / 1000.0;

            double x = 0;
            double y = 0.5 * gravity * Math.pow(time, 2);

            ball.setCenterX(x);
            ball.setCenterY(400 - height - y);

            if (ball.getCenterY() >= 400) {
                timeline.stop();
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private void moveBallHeight(double height) {
        double currentX = ball.getCenterX();
        double currentY = 200 - ball.getCenterY() + height;
        ball.setCenterY(200 - height);

        // Adjusting gnd pos if ball is set blw gnd
        if (ball.getCenterY() + ball.getRadius() >= ground.getStartY()) {
            double groundAdjustment = ball.getCenterY() + ball.getRadius() - ground.getStartY() + 5;
            ground.setStartY(ground.getStartY() + groundAdjustment);
            ground.setEndY(ground.getEndY() + groundAdjustment);
        }
    }

}
