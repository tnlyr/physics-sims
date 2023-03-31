package edu.vanier.physicsimulations.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class ShmController implements Initializable {

    @FXML
    private Spinner lengthSpinner;
    @FXML
    private Spinner<Double> angleSpinner;
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
    private Button resetBtn;










    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 45);
        valueFactory.setValue((double)1);

        angleSpinner.setValueFactory(valueFactory);
        AtomicInteger counter = new AtomicInteger();



        playBtn.setOnAction((event -> {
            System.out.println(angleSpinner.getValue());
        }));


        angleSpinner.setOnMouseClicked ((event) -> {
            double angle = (double) angleSpinner.getValue();

            Rotate rotate = new Rotate();

            rotate.setPivotX(string.getStartX());
            rotate.setPivotY(string.getStartY());

            rotate.setAngle(angle);















            string.getTransforms().add(rotate);
        });

        angleSpinner.setOnMouseReleased((event -> {
            Rotate rotate = new Rotate();
        }));
    }
}