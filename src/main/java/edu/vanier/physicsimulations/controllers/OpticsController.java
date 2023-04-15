package edu.vanier.physicsimulations.controllers;

import edu.vanier.physicsimulations.engines.OpticsEngine;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class OpticsController implements Initializable {

    @FXML
    Spinner<Double> objectDistance;

    @FXML
    Spinner<Double> objectHeightY;

    @FXML
    Spinner<Double> focalLength;

    @FXML
    Rectangle object;

    @FXML
    ChoiceBox<String> lensTypeDrag;

    private String[] lenses = {"Convergent", "Divergent"};

    @FXML
    Ellipse lens;

    @FXML
    Button playBtn;

    @FXML
    Pane opticsContainer;

    double currentValueX;
    double currentValueY;
    double currentValueFocalLength;
    int counter = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Double> valueFactoryX = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 500);

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

        SpinnerValueFactory<Double> valueFactoryY = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 108);
        valueFactoryY.setValue(108.00);
        objectHeightY.setValueFactory(valueFactoryY);
        currentValueY = objectHeightY.getValue();
        object.setHeight(currentValueY);
        objectHeightY.valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
                double previousValueY = object.getHeight();
                currentValueY = objectHeightY.getValue();
                object.setHeight(currentValueY);
                if (currentValueY > previousValueY) {
                    object.setY(object.getY() - object.getHeight() / (95 - 0.8 * counter));
                    counter++;
                } else {
                    counter = 1;
                    object.setY(object.getY() + object.getHeight() / (95 - 0.8 * counter));
                    counter++;
                }

            }

        });
        SpinnerValueFactory<Double> valueFactoryFocalLength = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 108);
        valueFactoryFocalLength.setValue(10.00);
        focalLength.setValueFactory(valueFactoryFocalLength);
        currentValueFocalLength = focalLength.getValue();

        focalLength.valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
                currentValueFocalLength = focalLength.getValue();
            }
        });

        lensTypeDrag.getItems().addAll(lenses);
        lensTypeDrag.setOnAction(this::getLenses);

        playBtn.setOnAction(e -> {
            Line lineR1 = new Line();
            Line lineR2 = new Line();
            Line lineR3 = new Line();
            Line lineR4 = new Line();
            Line lineR5 = new Line();
            Line lineR6 = new Line();
            Line lineR7 = new Line();
            Rectangle rectangle = new Rectangle(15, 107, Color.BLACK);
            rectangle.setY(375);
            opticsContainer.getChildren().add(rectangle);


            double x = OpticsEngine.lensEquation(valueFactoryX.getValue(), focalLength.getValue(), lensTypeDrag.getValue());
            System.out.println(x);
            System.out.println(rectangle.getTranslateX());
            rectangle.setX(lens.getLayoutX() + (10 * x));

            Ellipse focalLengthIndicator = new Ellipse();
            focalLengthIndicator.setCenterX(lens.getLayoutX() + 7 * focalLength.getValue()); // Set center X coordinate
            focalLengthIndicator.setCenterY(375); // Set center Y coordinate
            focalLengthIndicator.setRadiusX(5); // Set horizontal radius
            focalLengthIndicator.setRadiusY(5); // Set vertical radius
            focalLengthIndicator.setFill(Color.YELLOW); // Set fill color
            focalLengthIndicator.setStroke(Color.BLACK); // Set stroke color

            opticsContainer.getChildren().add(focalLengthIndicator);


            lineR1.setStartX(object.getLayoutX()+ object.getX());
            lineR1.setStartY(object.getLayoutY());
            lineR2.setStartX(object.getLayoutX() + object.getX());
            lineR2.setStartY(object.getLayoutY() + object.getY());
            lineR3.setStartX(object.getLayoutX() + object.getX());
            lineR3.setStartY(object.getLayoutY() + object.getY());

            lineR1.setEndX(lens.getLayoutX());
            lineR1.setEndY(lens.getLayoutY() - lens.getRadiusY() +12);
            lineR1.setStroke(Color.BLUE);
            lineR2.setEndX(lens.getLayoutX());
            lineR2.setEndY(lens.getLayoutY() + lens.getRadiusY());
            lineR2.setStroke(Color.RED);
            lineR3.setEndX(lens.getLayoutX());
            lineR3.setEndY(lens.getLayoutY());
            lineR3.setStroke(Color.GREEN);

            lineR4.setStartX(lens.getLayoutX());
            lineR4.setStartY(lens.getLayoutY() - lens.getRadiusY() +13);
            lineR4.setStroke(Color.BLUE);
            lineR5.setStartX(lens.getLayoutX());
            lineR5.setStartY(lens.getLayoutY() + lens.getRadiusY());
            lineR5.setStroke(Color.RED);
            lineR6.setStartX(lens.getLayoutX());
            lineR6.setStartY(lens.getLayoutY());
            lineR6.setStroke(Color.GREEN);
            //lineR7.setStartX(focalLengthIndicator.getCenterX());
           // lineR7.setStartY(focalLengthIndicator.getCenterY());
           // lineR7.setStroke(Color.BLUE);

            lineR4.setEndX(rectangle.getLayoutX() + rectangle.getX());
            lineR4.setEndY(rectangle.getHeight() + rectangle.getY());
            //lineR4.setEndX(focalLengthIndicator.getCenterX());
            //lineR4.setEndY(focalLengthIndicator.getCenterY());
            lineR5.setEndX(rectangle.getLayoutX() + rectangle.getX());
            lineR5.setEndY(rectangle.getHeight() + rectangle.getY());
            lineR6.setEndX(rectangle.getLayoutX() + rectangle.getX());
            lineR6.setEndY(rectangle.getHeight() + rectangle.getY());
           // lineR7.setEndX(rectangle.getLayoutX() + rectangle.getX());
           // lineR7.setEndY(rectangle.getHeight() + rectangle.getY());

            opticsContainer.getChildren().add(lineR1);
            opticsContainer.getChildren().add(lineR2);
            opticsContainer.getChildren().add(lineR3);
            opticsContainer.getChildren().add(lineR4);
            opticsContainer.getChildren().add(lineR5);
            opticsContainer.getChildren().add(lineR6);
            opticsContainer.getChildren().add(lineR7);


        });

    }

    public void getLenses(ActionEvent event) {

        String lenses = lensTypeDrag.getValue();
        if (lenses.equals("Convergent")) {
            lens.setFill(Color.RED);
            lens.setRadiusX(9);

        } else {
            lens.setFill(Color.GREEN);
            lens.setRadiusX(15);
        }
    }

}
