package edu.vanier.physicsimulations.controllers;

import edu.vanier.physicsimulations.engines.ProjectileMotionEngine;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.text.DecimalFormat;

public class ProjectileMotionController extends VBox {
    private Label angleLabel, velocityLabel, heightLabel, gravityLabel;
    private TextField angleInput, velocityInput, heightInput, gravityInput;
    private Label warningLabel;
    private static Label distanceLabel, timeLabel, maxHeightLabel, accelerationLabel;
    private ProjectileMotionEngine engine = new ProjectileMotionEngine();
    Button runButton = new Button("Run");
    Button pauseButton = new Button("Pause");
    Button stopButton = new Button("Stop");
    Button graphButton = new Button("Start Graph");
    Button helpButton = new Button("Help");
    MenuButton backgroundButton = new MenuButton("Default Background");
    MenuItem background1 = new MenuItem("Grass");
    MenuItem background2 = new MenuItem("Desert");
    MenuItem background3 = new MenuItem("Moon");
    MenuItem background4 = new MenuItem("Default");

    /**
     * A controller class for a projectile motion simulation application.
     * This class creates a user interface with input fields for the angle, velocity, and height of a projectile,
     * as well as a button to run the simulation and other controls to pause, stop, and graph the simulation,
     * and to change the background of the simulation environment.
     */
    public ProjectileMotionController() {
        setPadding(new Insets(5, 5, 5, 5));
        setSpacing(10);

        HBox angleBox = new HBox();
        angleLabel = new Label("Angle (deg): ");
        angleInput = new TextField("10");
        angleInput.setPrefWidth(50);
        angleBox.getChildren().addAll(angleLabel, angleInput);

        HBox velocityBox = new HBox();
        velocityLabel = new Label("Velocity (m/s): ");
        velocityInput = new TextField("5");
        velocityInput.setPrefWidth(50);
        velocityBox.getChildren().addAll(velocityLabel, velocityInput);

        HBox heightBox = new HBox();
        heightLabel = new Label("Height (m): ");
        heightInput = new TextField("5");
        heightInput.setPrefWidth(50);
        heightBox.getChildren().addAll(heightLabel, heightInput);

        HBox gravityBox = new HBox();
        gravityLabel = new Label("Gravity (m/s^2): ");
        gravityInput = new TextField("9.81");
        gravityInput.setEditable(false);
        gravityInput.setPrefWidth(50);
        gravityBox.getChildren().addAll(gravityLabel, gravityInput);

        HBox boxesCombined = new HBox();
        boxesCombined.setSpacing(10);
        boxesCombined.getChildren().addAll(angleBox, velocityBox, heightBox, gravityBox);
        boxesCombined.setAlignment(Pos.CENTER);

        warningLabel = new Label();
        warningLabel.setTextFill(Color.BROWN);
        warningLabel.setText("Please enter your values above and click Run to start the simulation.");
        warningLabel.setVisible(true);

        HBox buttonBox = new HBox();
        buttonBox.setSpacing(10);
        pauseButton.setDisable(true);
        stopButton.setDisable(true);
        buttonBox.setAlignment(Pos.CENTER);
        backgroundButton.getItems().addAll(background1, background2, background3, background4);
        buttonBox.getChildren().addAll(runButton, pauseButton, stopButton, graphButton, helpButton, backgroundButton);

        runButton.setOnAction(e -> {
            String warning = validateInput();

            if (warning == null) {
                warningLabel.setVisible(true);
                warningLabel.setTextFill(Color.GREEN);
                warningLabel.setText("Simulation running...");

                double angle = Double.parseDouble(angleInput.getText());
                double velocity = Double.parseDouble(velocityInput.getText());
                double height = Double.parseDouble(heightInput.getText());
                double gravity = Double.parseDouble(gravityInput.getText());

                engine.startSimulation(velocity, height, angle, gravity);
                boolean b = true;
                runButton.setText("Restart");
                runButton.setDisable(true);
                pauseButton.setDisable(false);
                stopButton.setDisable(false);
                graphButton.setDisable(true);
            } else {
                warningLabel.setVisible(true);
                warningLabel.setTextFill(Color.RED);
                warningLabel.setText(warning);
            }
        });

        pauseButton.setOnAction(e -> {
            if (engine.isRunning()) {
                engine.pauseSimulation();
                warningLabel.setVisible(true);
                warningLabel.setTextFill(Color.ORANGE);
                warningLabel.setText("Simulation paused.");
                pauseButton.setText("Resume");
            }
            else if (!engine.isRunning()) {
                engine.resumeSimulation();
                warningLabel.setVisible(true);
                warningLabel.setTextFill(Color.GREEN);
                warningLabel.setText("Simulation running...");
                pauseButton.setText("Pause");
            }
        });

        stopButton.setOnAction(e -> {
            engine.stopSimulation();
            warningLabel.setVisible(true);
            warningLabel.setTextFill(Color.RED);
            warningLabel.setText("Simulation stopped.");
            runButton.setText("Run");
            runButton.setDisable(false);
            pauseButton.setDisable(true);
            stopButton.setDisable(true);
            graphButton.setDisable(false);
        });

        graphButton.setOnAction(e -> {
            engine.graphSimulation();
        });

        background1.setOnAction(e -> {
            String imageUrl = "img/GRASS.jpg";
            BackgroundImage backgroundImage = new BackgroundImage(new Image(imageUrl),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(1.0, 1.0, true, true, false, false));
            engine.setBackground(new Background(backgroundImage));

            backgroundButton.setText("Grass");
        });

        background2.setOnAction(e -> {
            String imageUrl = "img/DESERT.jpg";
            BackgroundImage backgroundImage = new BackgroundImage(new Image(imageUrl),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(1.0, 1.0, true, true, false, false));
            engine.setBackground(new Background(backgroundImage));

            backgroundButton.setText("Desert");
        });

        background3.setOnAction(e -> {
            String imageUrl = "img/MOON.jpg";
            BackgroundImage backgroundImage = new BackgroundImage(new Image(imageUrl),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.CENTER,
                    new BackgroundSize(1.0, 1.0, true, true, false, false));
            engine.setBackground(new Background(backgroundImage));

            backgroundButton.setText("Moon");
        });

        background4.setOnAction(e -> {
            engine.setBackground(new Background(new BackgroundFill(Color.rgb(30, 144, 255), CornerRadii.EMPTY, Insets.EMPTY)));
            backgroundButton.setText("Default");
        });

        helpButton.setOnAction(e -> {
            Stage helpStage = new Stage();
            TextArea helpText = new TextArea();
            helpText.setEditable(false);
            helpText.setWrapText(true);
            helpText.setText("""
                    Welcome to the Projectile Motion Simulator!
                    This program simulates the motion of a projectile in a 2D environment.
                    To use this program, enter the angle, velocity, and height of the projectile. (The gravity is fixed at 9.81 m/s^2)
                    Then, click Run to start the simulation.
                    You can pause the simulation at any time by clicking Pause.
                    You can resume the simulation by clicking Resume.
                    You can stop the simulation at any time by clicking Stop.
                    You can graph the simulation by clicking Graph. You must open the graph view before starting the simulation.
                    You can change the background by clicking on the Background Menu Button.
                    You can view this help menu by clicking Help.
                    """);
            Scene helpScene = new Scene(helpText, 500, 300);
            helpStage.setScene(helpScene);
            helpStage.setTitle("Help Menu");
            helpStage.show();
        });

        HBox infoBox = new HBox();
        infoBox.setAlignment(Pos.CENTER);
        infoBox.setSpacing(10);
        distanceLabel = new Label("Distance Traveled: N/A");
        timeLabel = new Label("Total Air Time: N/A");
        accelerationLabel = new Label("Acceleration (g): N/A");
        maxHeightLabel = new Label("Max Height: N/A");
        infoBox.getChildren().addAll(distanceLabel, timeLabel, maxHeightLabel, accelerationLabel);

        VBox container = new VBox();
        container.setSpacing(10);
        container.setAlignment(Pos.CENTER);
        container.getChildren().addAll(boxesCombined, warningLabel, buttonBox, infoBox);

        getChildren().add(container);
    }

    /**
     * Validates the input values for the projectile motion calculation.
     *
     * @return A string message indicating any errors with the input values, or null if there are no errors.
     *
     * The method parses the values entered in the text fields for angle, velocity, height, and gravity,
     * and checks that they meet the following criteria:
     *
     * - The angle must be greater than 0 and less than 90 degrees.
     * - The velocity must be greater than 0.
     * - The height cannot be negative.
     * - The gravity must be greater than 0.
     *
     * If any of the values fail to meet these criteria or cannot be parsed as doubles, a string error message is returned.
     * Otherwise, null is returned to indicate that the input values are valid.
     */

    public String validateInput() {
        try {
            double angle = Double.parseDouble(angleInput.getText());
            double velocity = Double.parseDouble(velocityInput.getText());
            double height = Double.parseDouble(heightInput.getText());
            double gravity = Double.parseDouble(gravityInput.getText());

            if (angle <= 0 || angle >= 90) {
                return "The angle must be greater than 0 and less than 90 degrees.";
            }

            if (velocity <= 0) {
                return "The velocity must be greater than 0.";
            }

            if (height < 0) {
                return "The height cannot be negative.";
            }
            if (gravity <= 0) {
                return "The gravity must be greater than 0.";
            }
            return null;
        } catch (Exception e) {
            return "The angle, velocity, height, and gravity must be numbers.";
        }
    }

    /**
     * Sets the projectile motion engine used by this simulator.
     *
     * @param engine the ProjectileMotionEngine instance to set
     */
    public void setEngine(ProjectileMotionEngine engine) {
        this.engine = engine;
    }

    /**
     * Sets the simulation information for the projectile motion.
     * @param time the total air time in seconds.
     * @param distance the distance traveled in meters.
     * @param maxHeight the maximum height reached in meters.
     */
    public void setSimulationInfo(double time, double distance, double maxHeight) {
        DecimalFormat format = new DecimalFormat("#.##");

        distanceLabel.setText("Distance Traveled: " + format.format(distance) + "m");
        timeLabel.setText("Total Air Time: " + format.format(time) + "s");
        maxHeightLabel.setText("Max Height: " + format.format(maxHeight) + "m");
        accelerationLabel.setText("Acceleration (g): " + format.format(9.81) + "m/s^2");
    }

    /**
     * Changes the visibility, text and disable status of various buttons and labels
     * after the end of the simulation.
     * Displays a warning message indicating that the simulation has ended.
     *
     * @return void
     */
    public void setButtonsAfterEndOfSimulation() {
            warningLabel.setVisible(true);
            warningLabel.setTextFill(Color.RED);
            warningLabel.setText("Simulation ended.");
            runButton.setText("Run");
            runButton.setDisable(false);
            pauseButton.setDisable(true);
            stopButton.setDisable(true);
            graphButton.setDisable(false);
    }
}