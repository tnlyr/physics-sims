package edu.vanier.physicsimulations.controllers;

import edu.vanier.physicsimulations.views.OpticsApp;
import edu.vanier.physicsimulations.views.ProjectileApp;
import edu.vanier.physicsimulations.views.ShmApp;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainAppController {
    @FXML
    Button projectileBtn, shmBtn, opticsBtn;
    @FXML
    private void initialize() {
        projectileBtn.setOnAction(e -> {
            new ProjectileApp();
        });
        opticsBtn.setOnAction(e -> {
            new OpticsApp();
        });
        shmBtn.setOnAction(e -> {
            new ShmApp();
        });
    }

}
