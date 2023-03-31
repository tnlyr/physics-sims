package edu.vanier.physicsimulations.controllers;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class OpticsController implements Initializable {

    @FXML
    Spinner<Double> objectDistance;

    @FXML
    Spinner<Double> objectHeightY;

   // @FXML
  // Spinner<Double> objectHeight;

    @FXML
   Rectangle object;

    @FXML
    ChoiceBox<String> lensTypeDrag;

    private String[] lenses = {"Convergent", "Divergent"};

    @FXML
    Ellipse lens;

    @FXML
    Button playBtn;

    double currentValueX;
    double currentValueY;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Double> valueFactoryX = new SpinnerValueFactory.DoubleSpinnerValueFactory(1,500);

        valueFactoryX.setValue(50.0);
        objectDistance.setValueFactory(valueFactoryX);
         currentValueX = objectDistance.getValue();
        object.setX(currentValueX);
        objectDistance.valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
                currentValueX = objectDistance.getValue();
                object.setX(currentValueX);
            }
        });
        SpinnerValueFactory<Double> valueFactoryY = new SpinnerValueFactory.DoubleSpinnerValueFactory(1,108);
        valueFactoryY.setValue(108.00);
        objectHeightY.setValueFactory(valueFactoryY);
        currentValueY = objectHeightY.getValue();
        object.setHeight(currentValueY);
        objectHeightY.valueProperty().addListener(new ChangeListener<Double>(){
            @Override
            public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
                currentValueY = objectHeightY.getValue();
                object.setHeight(currentValueY);
            }
        });

        lensTypeDrag.getItems().addAll(lenses);
        lensTypeDrag.setOnAction(this::getLenses);

        playBtn.setOnAction(e ->{
            Line lineR1 = new Line();
            lineR1.setStartX(object.getX());
            lineR1.setStartY(object.getY());
            lineR1.setEndX(lens.getCenterX());
            lineR1.setStartX(lens.getRadiusY()*2);


        });

    }

    public void getLenses(ActionEvent event){
        String lenses = lensTypeDrag.getValue();
        if(lenses == "Convergent"){
            lens.setFill(Color.RED);
            lens.setRadiusX(9);

        }else{
            lens.setFill(Color.GREEN);
            lens.setRadiusX(15);
        }
    }

}
