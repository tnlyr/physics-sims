package edu.vanier.physicsimulations.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class Chrono {
    private double time;
    private Timeline timer;
    private KeyFrame keyframe;

    /**
     * EventHandler that calls the handleOnFinished method when an ActionEvent is triggered.
     */
    private EventHandler<ActionEvent> finshed = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            handleOnFinished(event);
        }
    };

    /**
     * Constructor for the Chrono class.
     * Initializes a keyframe with a duration of 1 second and an event handler that calls the handleOnFinished method.
     * Creates a timeline with the keyframe and sets the cycle count to INDEFINITE.
     */
    public Chrono() {
        keyframe = new KeyFrame(Duration.seconds(1), finshed);
        timer = new Timeline(keyframe);
        timer.setCycleCount(Animation.INDEFINITE);
    }

    /**
     * Starts the timer for the animation.
     */
    public void startTimer() {
        this.timer.play();
    }

    /**
     * Stops the timer for the animation.
     */
    public void stopTimer(){
        this.timer.stop();
    }

    /**
     * Returns the current time of the timer.
     * @return the current time in seconds
     */
    public double getTime() {
        return this.time;
    }

    /**
     * Increments the time by 1 second when the timer finishes.
     * @param event the event that triggers the method
     */
    private void handleOnFinished(ActionEvent event) {
        this.time++;
    }
}