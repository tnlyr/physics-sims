package edu.vanier.physicsimulations.views;

import edu.vanier.physicsimulations.controllers.MainAppController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.media.AudioClip;

public class MainApp extends Application {
    /**
     * Starts the application by loading the MainApp.fxml file and setting it as the scene for the primary stage.
     * Sets the controller for the FXMLLoader to a new instance of MainAppController.
     * @param primaryStage The primary stage for this application.
     * @throws Exception if the FXML file cannot be loaded or the scene cannot be set.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainApp.fxml"));
        loader.setController(new MainAppController());
        BorderPane root = loader.load();
        root.toFront();
        Scene mainScene = new Scene(root);
        mainScene.getRoot().setStyle("-fx-background-image: url('img/wp3500351.png'); -fx-background-repeat: no-repeat; -fx-background-size: cover;");

        Pane rightPaneUp = new Pane();
        rightPaneUp.setPrefSize(100, 100);
        Pane rightPaneDown = new Pane();
        rightPaneDown.setPrefSize(100, 100);
        rightPaneDown.setStyle("-fx-background-image: url('img/tebboune.jpg'); -fx-background-repeat: no-repeat;" +
                "-fx-background-size: contain; -fx-background-position: bottom;");
        rightPaneDown.setPrefWidth(350);
        rightPaneUp.setStyle("-fx-background-image: url('img/bouteflika.jpg'); -fx-background-repeat: no-repeat;" +
                "-fx-background-size: contain; -fx-background-position: top;");
        rightPaneUp.setPrefWidth(350);
        SplitPane splitPane = new SplitPane();
        splitPane.setOrientation(javafx.geometry.Orientation.VERTICAL);
        splitPane.setDividerPositions(0.5);
        splitPane.setStyle("-fx-background-color: transparent;");
        Node divider = splitPane.lookup(".split-pane-divider");
        if (divider!=null){
            divider.setStyle("-fx-background-color: transparent;");
        }
        root.setRight(splitPane);
        splitPane.getItems().addAll(rightPaneUp, rightPaneDown);

        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Physics Simulations");
        primaryStage.sizeToScene();
        primaryStage.show();
    }

    /**
     * The main entry point for the application.
     * Launches the JavaFX application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}