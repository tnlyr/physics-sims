package edu.vanier.physicsimulations.controllers;

import edu.vanier.physicsimulations.engines.OpticsEngine;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class OpticsController implements Initializable {

    @FXML
    Spinner<Double> objectDistance;

    @FXML
    Spinner<Double> objectHeightY;

    @FXML
    Spinner<Double> focalLength;


    @FXML
    ChoiceBox<String> lensTypeDrag;

    private String[] lenses = {"Convergent", "Divergent"};

    @FXML
    Ellipse lens;

    @FXML
    Button playBtn, resetBtn;

    @FXML
    Pane opticsContainer;


    @FXML
    Pane obj;

    @FXML
    Rectangle obj1;

    @FXML
    Text Magnification, focalLengthIndice, imageHeight, postionImage;

    @FXML
    MenuItem aboutBtn;


    double currentValueX;
    double currentValueY;
    double currentValueFocalLength;
    int lineHeight = 376;
    Rectangle rectangle = new Rectangle();

    Line lineR1 = new Line();
    Line lineR2 = new Line();
    Line lineR3 = new Line();
    Line lineR4 = new Line();
    Line lineR5 = new Line();
    Line lineR6 = new Line();

    Polygon triangleConvergentDown = new Polygon();
    Polygon triangleConvergentUp = new Polygon();
    Polygon triangleDivergentDown = new Polygon();
    Polygon triangleDivergentUp = new Polygon();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        SpinnerValueFactory<Double> valueFactoryX = new SpinnerValueFactory.DoubleSpinnerValueFactory(-500, 1000);

        valueFactoryX.setValue(0.00);
        objectDistance.setValueFactory(valueFactoryX);
        // currentValueX = objectDistance.getValue();
        // object.setX(currentValueX);
        obj.setLayoutX((lens.getLayoutX() - 10 - currentValueX));
        objectDistance.valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
                currentValueX = objectDistance.getValue();
                //object.setX(currentValueX);

                obj.setLayoutX((lens.getLayoutX() - 10 - currentValueX));

            }

        });

        SpinnerValueFactory<Double> valueFactoryY = new SpinnerValueFactory.DoubleSpinnerValueFactory(-200, 200);
        valueFactoryY.setValue(0.0);
        objectHeightY.setValueFactory(valueFactoryY);
        currentValueY = objectHeightY.getValue();
        //object.setHeight(currentValueY);
        obj1.setHeight(currentValueY);


        objectHeightY.valueProperty().addListener(new ChangeListener<Double>() {
            @Override
            public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {


                currentValueY = objectHeightY.getValue();


                if (obj1.getLayoutY() < lineHeight) {
                    obj1.setLayoutY(-1 * currentValueY);
                    //  obj.setPrefHeight((lineHeight - ( currentValueY)));
                    obj1.setHeight(currentValueY);
                    if (currentValueY < 0) {
                        obj1.setLayoutY(0);
                        obj1.setHeight(obj.getPrefHeight() - currentValueY);


                    }
                } else {
                    obj1.setLayoutY(lineHeight);
                    obj1.setHeight(currentValueY - lineHeight);
                }

            }
        });
        SpinnerValueFactory<Double> valueFactoryFocalLength = new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 300);
        valueFactoryFocalLength.setValue(50.00);
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

        /*PreferenceBtn.setOnAction(e -> {
            onPreference();
        });*/
        aboutBtn.setOnAction(e -> {
            onHelp();
        });

        resetBtn.setOnAction(e -> {
            opticsContainer.getChildren().remove(lineR1);
            opticsContainer.getChildren().remove(lineR2);
            opticsContainer.getChildren().remove(lineR3);
            opticsContainer.getChildren().remove(lineR4);
            opticsContainer.getChildren().remove(lineR5);
            opticsContainer.getChildren().remove(lineR6);
            opticsContainer.getChildren().remove(rectangle);


            valueFactoryFocalLength.setValue(50.0);
            valueFactoryX.setValue(0.0);
            valueFactoryY.setValue(0.0);
            obj1.setHeight(0);
            obj1.setX(0);

            Magnification.setText(String.valueOf(0));
            focalLengthIndice.setText(String.valueOf(0));
            postionImage.setText(String.valueOf(0));
            imageHeight.setText(String.valueOf(0));


        });
        playBtn.setOnAction(e -> {

            //opticsContainer.getChildren().add(triangleConvergentUp);
           // opticsContainer.getChildren().add(triangleConvergentDown);
           // opticsContainer.getChildren().add(triangleDivergentDown);
           // opticsContainer.getChildren().add(triangleDivergentUp);

            opticsContainer.getChildren().add(lineR1);
            opticsContainer.getChildren().add(lineR2);
            opticsContainer.getChildren().add(lineR3);
            opticsContainer.getChildren().add(lineR4);
            opticsContainer.getChildren().add(lineR5);
            opticsContainer.getChildren().add(lineR6);

            rectangle.setWidth(21);
            rectangle.setHeight(obj1.getHeight());
            rectangle.setFill(Color.BLACK);
            opticsContainer.getChildren().add(rectangle);


            String lenses = lensTypeDrag.getValue();
            if (lenses.equals("Convergent") && Math.abs(valueFactoryX.getValue()) < currentValueFocalLength) {
                // Upright, virtual, enlarged
                double imageDistance = OpticsEngine.lensEquation(valueFactoryX.getValue(), focalLength.getValue(), lensTypeDrag.getValue());
                Double rectangleCurrentHeight = rectangle.getHeight();
                Double rectangleCurrentWidth = rectangle.getWidth();
                if (valueFactoryX.getValue() > 0) {
                    rectangle.setX(lens.getLayoutX() + (imageDistance));
                } else if (valueFactoryX.getValue() < 0) {
                    rectangle.setX(lens.getLayoutX() + (Math.abs(imageDistance)));

                }
                rectangle.setHeight(rectangleCurrentHeight * (Math.abs(imageDistance / valueFactoryX.getValue())));
                rectangle.setWidth(rectangleCurrentWidth * (Math.abs(imageDistance / valueFactoryX.getValue())));
                if (valueFactoryY.getValue() > 0) {
                    rectangle.setY(lineHeight - rectangle.getHeight());
                } else if (valueFactoryY.getValue() < 0) {
                    rectangle.setY(lineHeight);
                }

            } else if (lenses.equals("Convergent") && valueFactoryX.getValue() == currentValueFocalLength) {
                double imageDistance = OpticsEngine.lensEquation(valueFactoryX.getValue(), focalLength.getValue(), lensTypeDrag.getValue());
                rectangle.setX(lens.getLayoutX() + (imageDistance));

            } else if (lenses.equals("Convergent") && Math.abs(valueFactoryX.getValue()) > currentValueFocalLength) {
                double imageDistance = OpticsEngine.lensEquation(valueFactoryX.getValue(), focalLength.getValue(), lensTypeDrag.getValue());
                Double rectangleCurrentHeight = rectangle.getHeight();
                Double rectangleCurrentWidth = rectangle.getWidth();
                rectangle.setWidth(rectangleCurrentWidth * (Math.abs(imageDistance / valueFactoryX.getValue())));
                if (valueFactoryX.getValue() > 0) {
                    rectangle.setX(lens.getLayoutX() + (imageDistance));
                } else if (valueFactoryX.getValue() < 0) {
                    rectangle.setX(lens.getLayoutX() - (Math.abs(imageDistance)));
                }
                rectangle.setHeight(rectangleCurrentHeight * (Math.abs(imageDistance / valueFactoryX.getValue())));
                if (valueFactoryY.getValue() < 0) {
                    rectangle.setY(lineHeight - rectangle.getHeight());
                } else if (valueFactoryY.getValue() > 0) {
                    rectangle.setY(lineHeight);
                }
            }

            if (lenses.equals("Divergent")) {
                double imageDistance = OpticsEngine.lensEquation(valueFactoryX.getValue(), focalLength.getValue(), lensTypeDrag.getValue());
                Double rectangleCurrentHeight = rectangle.getHeight();
                Double rectangleCurrentWidth = rectangle.getWidth();
                if (valueFactoryX.getValue() > 0) {
                    rectangle.setX(lens.getLayoutX() + (imageDistance));
                } else if (valueFactoryX.getValue() < 0) {
                    rectangle.setX(lens.getLayoutX() + (Math.abs(imageDistance)));

                }
                rectangle.setHeight(rectangleCurrentHeight * (Math.abs(imageDistance / valueFactoryX.getValue())));
                rectangle.setWidth(rectangleCurrentWidth * (Math.abs(imageDistance / valueFactoryX.getValue())));
                rectangle.setY(lineHeight - rectangle.getHeight());


            }


            if (currentValueY > -1) {

                lineR1.setStartX(lens.getLayoutX() - currentValueX - 10);
                lineR1.setStartY(obj.getLayoutY() - obj1.getHeight());
                lineR2.setStartX(lens.getLayoutX() - currentValueX - 10);
                lineR2.setStartY(obj.getLayoutY() - obj1.getHeight());
                lineR3.setStartX(lens.getLayoutX() - currentValueX - 10);
                lineR3.setStartY(obj.getLayoutY() - obj1.getHeight());
            } else {
                lineR1.setStartX(lens.getLayoutX() - currentValueX - 10);
                lineR1.setStartY(obj.getLayoutY() + obj1.getHeight());
                lineR2.setStartX(lens.getLayoutX() - currentValueX - 10);
                lineR2.setStartY(obj.getLayoutY() + obj1.getHeight());
                lineR3.setStartX(lens.getLayoutX() - currentValueX - 10);
                lineR3.setStartY(obj.getLayoutY() + obj1.getHeight());
            }


            lineR1.setEndX(lens.getLayoutX());
            lineR1.setEndY(lens.getLayoutY() - lens.getRadiusY() + 12);
            lineR1.setStroke(Color.BLUE);
            lineR2.setEndX(lens.getLayoutX());
            lineR2.setEndY(lens.getLayoutY() + lens.getRadiusY());
            lineR2.setStroke(Color.RED);
            lineR3.setEndX(lens.getLayoutX());
            lineR3.setEndY(lens.getLayoutY());
            lineR3.setStroke(Color.GREEN);

            lineR4.setStartX(lens.getLayoutX());
            lineR4.setStartY(lens.getLayoutY() - lens.getRadiusY() + 13);
            lineR4.setStroke(Color.BLUE);
            lineR5.setStartX(lens.getLayoutX());
            lineR5.setStartY(lens.getLayoutY() + lens.getRadiusY());
            lineR5.setStroke(Color.RED);
            //lineR6.setStartX(lens.getLayoutX());
            // lineR6.setStartY(lens.getLayoutY());
            lineR6.setStroke(Color.GREEN);
            if (lenses.equals("Divergent")) {
                lineR4.setEndX(rectangle.getLayoutX() + rectangle.getX());
                lineR4.setEndY(lineHeight - rectangle.getHeight());
                lineR5.setEndX(rectangle.getLayoutX() + rectangle.getX());
                lineR5.setEndY(lineHeight - rectangle.getHeight());
                lineR3.setEndX(rectangle.getLayoutX() + rectangle.getX());
                lineR3.setEndY(lineHeight - rectangle.getHeight());


            } else if (lenses.equals("Convergent") && Math.abs(valueFactoryX.getValue()) < currentValueFocalLength && valueFactoryY.getValue() < 0) {
                lineR4.setEndX(rectangle.getLayoutX() + rectangle.getX());
                lineR4.setEndY(lineHeight + rectangle.getHeight());
                lineR5.setEndX(rectangle.getLayoutX() + rectangle.getX());
                lineR5.setEndY(lineHeight + rectangle.getHeight());
                lineR3.setEndX(rectangle.getLayoutX() + rectangle.getX());
                lineR3.setEndY(lineHeight + rectangle.getHeight());
            } else if (lenses.equals("Convergent") && Math.abs(valueFactoryX.getValue()) < currentValueFocalLength || valueFactoryY.getValue() < 0 || valueFactoryX.getValue() == currentValueFocalLength) {
                lineR4.setEndX(rectangle.getLayoutX() + rectangle.getX());
                lineR4.setEndY(rectangle.getLayoutY() + rectangle.getY());
                lineR5.setEndX(rectangle.getLayoutX() + rectangle.getX());
                lineR5.setEndY(rectangle.getLayoutY() + rectangle.getY());
                lineR3.setEndX(rectangle.getLayoutX() + rectangle.getX());
                lineR3.setEndY(rectangle.getLayoutY() + rectangle.getY());
            } else if (lenses.equals("Convergent") && Math.abs(valueFactoryX.getValue()) > currentValueFocalLength || valueFactoryY.getValue() > 0) {
                lineR4.setEndX(rectangle.getLayoutX() + rectangle.getX());
                lineR4.setEndY(lineHeight + rectangle.getHeight());
                lineR5.setEndX(rectangle.getLayoutX() + rectangle.getX());
                lineR5.setEndY(lineHeight + rectangle.getHeight());
                lineR3.setEndX(rectangle.getLayoutX() + rectangle.getX());
                lineR3.setEndY(lineHeight + rectangle.getHeight());
            }


            // Text Magnification,focalLengthIndice,imageHeight,postionImage;
            ;
            Double imageDistance = OpticsEngine.lensEquation(valueFactoryX.getValue(), focalLength.getValue(), lensTypeDrag.getValue());
            double magnificaitonValue = Math.abs(imageDistance / valueFactoryX.getValue());
            Magnification.setText(String.valueOf(magnificaitonValue));
            focalLengthIndice.setText(String.valueOf(valueFactoryFocalLength.getValue()));

            postionImage.setText(String.valueOf(imageDistance));
            if (obj1.getHeight() < 0) {
                imageHeight.setText(String.valueOf(magnificaitonValue * rectangle.getHeight()));
            } else if (obj1.getHeight() > 0) {
                imageHeight.setText(String.valueOf(-1 * (magnificaitonValue * rectangle.getHeight())));
            }

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(lineR1.opacityProperty(), 0.0)),
                    new KeyFrame(Duration.seconds(5), new KeyValue(lineR1.opacityProperty(), 1.0))
            );
            timeline.play(); // Start the animation for lineR1

            // Repeat for all other lines
            timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(lineR2.opacityProperty(), 0.0)),
                    new KeyFrame(Duration.seconds(5), new KeyValue(lineR2.opacityProperty(), 1.0))
            );
            timeline.play();

            timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(lineR3.opacityProperty(), 0.0)),
                    new KeyFrame(Duration.seconds(5), new KeyValue(lineR3.opacityProperty(), 1.0))
            );
            timeline.play();

            timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(lineR4.opacityProperty(), 0.0)),
                    new KeyFrame(Duration.seconds(5), new KeyValue(lineR4.opacityProperty(), 1.0))
            );
            timeline.play();

            timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(lineR5.opacityProperty(), 0.0)),
                    new KeyFrame(Duration.seconds(5), new KeyValue(lineR5.opacityProperty(), 1.0))
            );
            timeline.play();

            timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(lineR6.opacityProperty(), 0.0)),
                    new KeyFrame(Duration.seconds(5), new KeyValue(lineR6.opacityProperty(), 1.0))
            );
            timeline.play();

            timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(rectangle.opacityProperty(), 0.0)),
                    new KeyFrame(Duration.seconds(5), new KeyValue(rectangle.opacityProperty(), 1.0))
            );
            timeline.play();
        });


    }
       // public void convergentAnalysis(){

       // }


            public void onHelp () {
                Stage stage = new Stage();
                TextArea textArea = new TextArea();
                textArea.setWrapText(true);
                textArea.setEditable(false);
                textArea.setText("""
                          File button
                                 
                                  -Import Simulation
                                 
                                 
                                 allows you to import any saved simulations from your computer\s
                                 
                                 
                                  - Export Simulation
                                 
                                 
                                 allows you to save your current simulation on your computer.
                                 
                                 
                                  - Help button
                                 
                                  The Help button gives the user access to the documentation to understand how the simulation work
                                 
                        Optics Setting
                                 
                                 - Object distance
                                 
                                 Allows the user to select the object's distance in meters 500 meters by either using the up and down arrows or manually entering the number.
                                 
                                 
                                 - Object Height
                                 
                                 Allows the user to select the object's height in meters 200 meters by either using the up and down arrows or manually entering the number.
                                 
                                 - Focal Length
                                 
                                 Allows the user to select the lens’ height in meters 300 meters by either using the up and down arrows or manually entering the number.
                                 
                                 
                                 - Lens Type
                                 
                                 Allows the user to select the lens type between Convergent and Divergent by clicking on the menu drop-down and selecting the lens type manually.
                                 
                                 
                         Simulation control
                                 
                                 -Play Button
                                 
                                  The play button allows the user to start the simulation; once it is clicked and the object’s image  is created, and the 3 primary rays are displayed.
                                 
                                 - Rest Button
                                 
                                 The Reset Button allows users to reset the simulation by removing the previous object’s image and 3 primary rays.
                                 
                                 
                                 - Foot Bar
                                 
                                  The toggle bar displays the simulation's current image position, Image Height, Focal Length and Magnification.
                        """);
                Scene scene = new Scene(textArea, 800, 800);
                stage.setTitle("Help Window");
                stage.setScene(scene);
                stage.show();
            }


    public void getLenses(ActionEvent event) {

        String lenses = lensTypeDrag.getValue();
        if (lenses.equals("Convergent")) {
            lens.setStroke(Color.VIOLET);
            lens.setRadiusX(1);

            triangleConvergentDown.getPoints().addAll(
                    0.0, 0.0,
                    5.0, 10.0,
                    10.0, 0.0
            );
            triangleConvergentDown.setFill(Color.BLACK);
            triangleConvergentDown.setLayoutX(649);
            triangleConvergentDown.setLayoutY(lens.getLayoutY() + lens.getRadiusY() );

            triangleConvergentUp.getPoints().addAll(new Double[]{
                    10.0, 5.0,
                    5.0, 15.0,
                    15.0, 15.0 });
            // Set the fill and stroke color of the polygon
            triangleConvergentUp.setFill(Color.BLACK);
            triangleConvergentUp.setLayoutX(644);
            triangleConvergentUp.setLayoutY(lens.getLayoutY() - lens.getRadiusY() - 5);


            opticsContainer.getChildren().add(triangleConvergentUp);
            opticsContainer.getChildren().add(triangleConvergentDown);
            opticsContainer.getChildren().remove(triangleDivergentDown);
            opticsContainer.getChildren().remove(triangleDivergentUp);

        } else {
            lens.setStroke(Color.VIOLET);
            lens.setRadiusX(1);

            // Create a new polygon

            // Add vertices to the polygon
            triangleDivergentDown.getPoints().addAll(
                    0.0, 0.0,
                    5.0, 10.0,
                    10.0, 0.0
            );
            triangleDivergentDown.setFill(Color.BLACK);
            triangleDivergentDown.setLayoutX(649);
            triangleDivergentDown.setLayoutY(lens.getLayoutY() - lens.getRadiusY() - 4);


            // Add three points to the polygon
            triangleDivergentUp.getPoints().addAll(new Double[]{
                    10.0, 5.0,
                    5.0, 15.0,
                    15.0, 15.0 });
            // Set the fill and stroke color of the polygon
            triangleDivergentUp.setFill(Color.BLACK);
            triangleDivergentUp.setLayoutX(644);
            triangleDivergentUp.setLayoutY(lens.getLayoutY() + lens.getRadiusY() - 4);

            // Create a new pane and add the triangle to it

            opticsContainer.getChildren().add(triangleDivergentDown);
            opticsContainer.getChildren().add(triangleDivergentUp);
            opticsContainer.getChildren().remove(triangleConvergentDown);
            opticsContainer.getChildren().remove(triangleConvergentUp);


        }


    }


    public double convert(double value) {
        value = value * 0.002;
        return value;
    }


}


