package edu.vanier.physicsimulations.controllers;

import edu.vanier.physicsimulations.views.OpticsApp;
import edu.vanier.physicsimulations.views.ProjectileApp;
import edu.vanier.physicsimulations.views.ShmApp;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.media.AudioClip;

public class MainAppController {
    @FXML
    Button projectileBtn, shmBtn, opticsBtn, muteBtn;
    AudioClip audioClip;

    /**
     * Sets up the event handlers for each of the simulation buttons. When a button is clicked, it will launch the
     corresponding simulation.
     */
    @FXML
    private void initialize() {
        loopMusic();
        projectileBtn.setOnMouseEntered(e -> {
            projectileBtn.setStyle("-fx-background-color: #006633; -fx-text-fill: #ffffff;");
        });
        projectileBtn.setOnMouseExited(e -> {
            projectileBtn.setStyle("-fx-background-color: #E0E0E0; -fx-text-fill: #000000;");
        });
        opticsBtn.setOnMouseEntered(e -> {
            opticsBtn.setStyle("-fx-background-color: #006633; -fx-text-fill: #ffffff;");
        });
        opticsBtn.setOnMouseExited(e -> {
            opticsBtn.setStyle("-fx-background-color: #E0E0E0; -fx-text-fill: #000000;");
        });
        shmBtn.setOnMouseEntered(e -> {
            shmBtn.setStyle("-fx-background-color: #006633; -fx-text-fill: #000000;");
        });
        shmBtn.setOnMouseExited(e -> {
            shmBtn.setStyle("-fx-background-color: #E0E0E0; -fx-text-fill: #000000;");
        });
        projectileBtn.setOnAction(e -> {
            new ProjectileApp();
        });
        opticsBtn.setOnAction(e -> {
            new OpticsApp();
        });
        shmBtn.setOnAction(e -> {
            new ShmApp();
        });
        muteBtn.setOnAction(e -> {
            if (isMusicPlaying()){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Are you sure?");
                alert.setHeaderText("Are you sure you want to mute this beautiful song? I recommend you don't.");
                alert.setContentText("Press OK to mute the song, or cancel to keep listening to it.");
                alert.showAndWait();
                if (alert.getResult().getText().equals("OK")) {
                    Alert alert2 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert2.setTitle("Are you really sure?");
                    alert2.setHeaderText("Are you really sure you want to mute this beautiful song? I think you're not sure.");
                    alert2.setContentText("Press OK to mute the song, or cancel to keep listening to it.");
                    alert2.showAndWait();
                    if (alert2.getResult().getText().equals("OK")) {
                        Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                        confirmation.setTitle("I guess you're really stubborn. Fine.");
                        confirmation.setHeaderText("I guess you're really stubborn. Fine.");
                        confirmation.setContentText("Fine. I'll mute the song. But I'm not happy about it.");
                        confirmation.showAndWait();
                        audioClip.stop();
                        muteBtn.setText("Unmute for a masterpiece");
                    }
                }
                else {
                    Alert confirmation = new Alert(Alert.AlertType.INFORMATION);
                    confirmation.setTitle("Happy Dev!");
                    confirmation.setHeaderText("Good choice.");
                    confirmation.setContentText("I'm glad you decided to keep listening to this beautiful song.");
                    confirmation.showAndWait();
                }
            } else {
                audioClip.play();
                muteBtn.setText("Mute this beautiful song");
            }
        });
    }
    private void loopMusic() {
        audioClip = new AudioClip(getClass().getResource("/img/menuSound.wav").toString());
        audioClip.setCycleCount(AudioClip.INDEFINITE);
        audioClip.play();
        audioClip.setVolume(5.0);
    }

    private boolean isMusicPlaying() {
        return audioClip.isPlaying();
    }
}
