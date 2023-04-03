package edu.vanier.physicsimulations.controllers;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

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


    playBtn.setOnAction((event -> {
        double angleret = angleSpinner.getValue();

        Rotate rotate = new Rotate();

        rotate.setPivotX(string.getStartX());
        rotate.setPivotY(string.getStartY());


        string.getTransforms().add(rotate);

        Timeline timeline = new Timeline();

        timeline.setCycleCount(Timeline.INDEFINITE);



        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.1), new EventHandler<ActionEvent>() {
            double angle =0;
            @Override
            public void handle(ActionEvent event) {
                angle += 1;
                rotate.setAngle(angle);
            }
        });

        timeline.getKeyFrames().add(keyFrame);

        timeline.play();


    }));



/*
        angleSpinner.setOnMouseClicked ((event) -> {
            double angle = angleSpinner.getValue();

            Rotate rotate = new Rotate();

            rotate.setPivotX(string.getStartX());
            rotate.setPivotY(string.getStartY());

            rotate.setAngle(angle);
            string.getTransforms().add(rotate);


        });

        angleSpinner.setOnMouseReleased((event -> {

        }));
*/





    }
}