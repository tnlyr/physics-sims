package edu.vanier.physicsimulations.views;

import edu.vanier.physicsimulations.controllers.ShmController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ShmApp extends Stage {

    public ShmApp() {
        this.setTitle("Simple Harmonic Motion Simulation");
        this.initModality(Modality.APPLICATION_MODAL);
        try {
            start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SHM1.fxml"));
        loader.setController(new ShmController());
        VBox root = loader.load();
        this.setScene(new Scene(root));
        this.sizeToScene();
        this.show();
    }
}
