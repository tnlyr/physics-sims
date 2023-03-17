package edu.vanier.physicsimulations.views;

import edu.vanier.physicsimulations.controllers.ProjectileMotionController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProjectileApp extends Stage {

    public ProjectileApp() {
        this.setTitle("Projectile Simulation");
        this.initModality(Modality.APPLICATION_MODAL);
        try {
            start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void start() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Projectile.fxml"));
        loader.setController(new ProjectileMotionController());
        Pane root = loader.load();
        this.setScene(new Scene(root));
        this.sizeToScene();
        this.show();
    }
}
