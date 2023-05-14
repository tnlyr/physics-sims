package edu.vanier.physicsimulations.controllers;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class GraphController {
    final int WINDOW_SIZE = 10;
    static ScheduledExecutorService executorService;
    Chrono timer = new Chrono();
    double time;
    double theta, v, g;

    /**
     * Constructs a new GraphController with the given initial angle, velocity, and gravitational acceleration.
     *
     * @param theta the initial angle of the projectile in degrees
     * @param v the initial velocity of the projectile in meters per second
     * @param g the gravitational acceleration in meters per second squared
     */
    public GraphController(double theta, double v, double g) {
        this.theta = theta;
        this.v = v;
        this.g = g;
        startGraph();
    }

    /**
     * Starts the graph display of the projectile motion simulation.
     * A new stage is created to display the graph, which updates every second
     * with the current position data of the projectile. The axes are defined
     * and a line chart is created to display the data. The data is added to
     * the series and old data is removed if the series gets too long.
     * A scheduled executor is used to periodically update the chart.
     */
    public void startGraph() {
        // create a new stage
        Stage primaryStage = new Stage();

        // start the timer
        timer.startTimer();

        // set the title of the stage
        primaryStage.setTitle("Graph");

        // define the axes for the chart
        final CategoryAxis xAxis = new CategoryAxis(); // we are plotting against time
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Time (s)");
        xAxis.setAnimated(false);
        yAxis.setLabel("Position Y");
        yAxis.setAnimated(false);

        // create the line chart with the axes defined above
        final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Projectile Motion Graph");
        lineChart.setAnimated(false); // disable animations

        // define a series to display data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data");

        // add the series to the chart
        lineChart.getData().add(series);

        // setup the scene with the chart
        Scene scene = new Scene(lineChart, 800, 600);
        primaryStage.setScene(scene);

        // show the stage
        primaryStage.show();

        // setup a scheduled executor to periodically update the chart
        executorService = Executors.newSingleThreadScheduledExecutor();

        // put data onto graph every second
        executorService.scheduleAtFixedRate(() -> {
            // get the position data for the current time
            double positionY = posCalculations();

            // get the current time as a string
            String time = String.valueOf(this.time);

            // add the data to the series and remove old data if the series is too long
            Platform.runLater(() -> {
                series.getData().add(new XYChart.Data<>(time, positionY));
                if (series.getData().size() > WINDOW_SIZE) {
                    series.getData().remove(0);
                }
            });
        }, 0, 1, TimeUnit.SECONDS);
    }

    /**
     * Stops the graph by shutting down the executor service and stopping the timer.
     */
    public void stopGraph() {
        executorService.shutdownNow();
        timer.stopTimer();
    }

    /**
     * Calculates the current vertical position of a projectile based on time, initial
     * velocity, and acceleration due to gravity.
     *
     * @return The vertical position of the projectile at the current time
     */
    private double posCalculations() {
        time = timer.getTime();
        return v * time - (0.5 * g * (time * time));
    }

}
