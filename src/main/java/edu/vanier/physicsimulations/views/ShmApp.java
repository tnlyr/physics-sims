package edu.vanier.physicsimulations.views;

import edu.vanier.physicsimulations.controllers.ShmController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SHM.fxml"));
        loader.setController(new ShmController());
        this.setScene(new Scene(loader.load()));
        this.sizeToScene();
        this.show();
    }
}
