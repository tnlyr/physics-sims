package edu.vanier.physicsimulations.views;

import edu.vanier.physicsimulations.controllers.ProjectileMotionController;

import edu.vanier.physicsimulations.engines.ProjectileMotionEngine;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ProjectileApp extends Stage {

    /**
     * Constructs a new ProjectileApp object.
     * Sets the title of the app to "Projectile Motion Simulation",
     * initializes the modality to Modality.APPLICATION_MODAL, and sets
     * the app to be non-resizable. Calls the start() method to start
     * the simulation.
     *
     * @throws RuntimeException if an exception occurs while calling
     * the start() method.
     */
    public ProjectileApp() {
        this.setTitle("Projectile Motion Simulation");
        this.initModality(Modality.APPLICATION_MODAL);
        this.setResizable(false);
        try {
            start(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Starts the Projectile Motion Simulation by setting up the stage, creating a ProjectileMotionController and a
     * ProjectileMotionEngine, and displaying them in a BorderPane. The scene is then set to the stage and the stage is shown.
     * @param secondaryStage the stage on which to display the simulation
     * @throws Exception if an error occurs while setting up the simulation
     */
    public void start(Stage secondaryStage) throws Exception {
        ProjectileMotionController controller = new ProjectileMotionController();
        ProjectileMotionEngine engine = new ProjectileMotionEngine();

        secondaryStage.setTitle("Projectile Motion Simulation - by Lounes");
        controller.setEngine(engine);
        engine.setController(controller);

        BorderPane root = new BorderPane();
        root.setCenter(engine);
        root.setBottom(controller);

        Scene sc = new Scene(root, 700, 700);
        secondaryStage.setScene(sc);
        secondaryStage.sizeToScene();
        secondaryStage.resizableProperty().setValue(Boolean.FALSE);
        secondaryStage.show();

        /* FXML file loading method
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Projectile.fxml"));
        loader.setController(new ProjectileMotionController());
        Pane root = loader.load();
        this.setScene(new Scene(root));
        this.sizeToScene();
        this.show();*/
    }
}
