package edu.vanier.physicsimulations.controllers;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public  class ShmController {

    @FXML
    private Spinner lengthSpinner;
    @FXML
    private Spinner angleSpinner;
    @FXML
    private Spinner massSpinner;
    @FXML
    private Spinner gravitySpinner;
    @FXML
    private Slider playbackSlider;
    @FXML
    private Circle topCircle;
    @FXML
    private Line string;
    @FXML
    private Rectangle rectangle;
    @FXML
    private Button playBtn;
    @FXML
    Button resetBtn;





    @FXML
     void initialize() {

        rectangle.setFill(Color.BEIGE);





        /*
        angleSpinner.setOnMousePressed ((event) -> {
            double angle = (double) angleSpinner.getValue();
            string.setRotate(angle);
            string.setTranslateX(45);
            System.out.println("sewy");

        });;
        */
}

    public ShmController() {
        initialize();
    }


}
