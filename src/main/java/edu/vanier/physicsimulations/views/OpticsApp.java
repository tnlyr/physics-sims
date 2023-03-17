package edu.vanier.physicsimulations.views;

import edu.vanier.physicsimulations.controllers.OpticsController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class OpticsApp extends Stage {
    public OpticsApp() {
        this.setTitle("Optics Simulation");
        this.initModality(Modality.APPLICATION_MODAL);
        try {
            start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void start() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Optics.fxml"));
        loader.setController(new OpticsController());
        Pane root = loader.load();
        this.setScene(new Scene(root));
        this.sizeToScene();
        this.show();
    }
}
