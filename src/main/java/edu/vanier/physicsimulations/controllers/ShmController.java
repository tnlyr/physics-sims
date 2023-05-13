package edu.vanier.physicsimulations.controllers;

import edu.vanier.physicsimulations.engines.ShmEngine;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class ShmController implements Initializable {

    @FXML
    private Spinner<Double> lengthSpinner;
    @FXML
    private Spinner<Double> angleSpinner;
    @FXML
    private Spinner<Double> massSpinner;
    @FXML
    private Spinner<Double> gravitySpinner;
     @FXML
    private Button playBtn;
    @FXML
    private Button resetBtn;
    @FXML
    private Pane pane;
    @FXML
    private Text period;
    @FXML
    private Text totalEnergy;
    @FXML
    private Text potentialEnergy;
    @FXML
    private Text kineticEnergy;
    @FXML
    private Text currentVelocity;




int dummy =0;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lengthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(50, 500, 200));
        lengthSpinner.setEditable(true);
        angleSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 90, 30));
        angleSpinner.setEditable(true);
        massSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 50, 10 ));
        massSpinner.setEditable(true);
        gravitySpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(5, 30, 9.8));
        gravitySpinner.setEditable(true);






        Line pendulumArm = new Line();
        pendulumArm.setStartX(520);
        pendulumArm.setStartY(50);
        pendulumArm.setEndX(pendulumArm.getStartX() - lengthSpinner.getValue()*Math.sin(Math.toDegrees(angleSpinner.getValue())));
        pendulumArm.setEndY(pendulumArm.getStartY() - lengthSpinner.getValue()*Math.cos(Math.toDegrees(angleSpinner.getValue())));
        pendulumArm.setStroke(Color.BLACK);

        Circle pendulumBob = new Circle(pendulumArm.getEndX(), pendulumArm.getEndY(), massSpinner.getValue());
        pendulumBob.setFill(Color.RED);

        Group group = new Group();
        group.getChildren().addAll(pendulumArm, pendulumBob);


        angleSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            pendulumArm.setEndX(pendulumArm.getStartX() + lengthSpinner.getValue() * Math.sin(Math.toRadians(newValue)));
            pendulumArm.setEndY(pendulumArm.getStartY() + lengthSpinner.getValue() * Math.cos(Math.toRadians(newValue)));
            pendulumBob.setCenterX(pendulumArm.getEndX());
            pendulumBob.setCenterY(pendulumArm.getEndY());
            //animation.setByAngle(Math.toDegrees(newValue));
        });

        lengthSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            pendulumArm.setEndX(pendulumArm.getStartX() - newValue * Math.sin(Math.toDegrees(angleSpinner.getValue())));
            pendulumArm.setEndY(pendulumArm.getStartY() - newValue * Math.cos(Math.toDegrees(angleSpinner.getValue())));
            pendulumBob.setCenterX(pendulumArm.getEndX());
            pendulumBob.setCenterY(pendulumArm.getEndY());
        });



        massSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            pendulumBob.setRadius(newValue);
        });

        pane.getChildren().addAll(group);



        playBtn.setOnAction((event -> {

            animation(angleSpinner.getValue(), pendulumArm, group, resetBtn);

            ShmEngine pe =new ShmEngine(angleSpinner.getValue(), lengthSpinner.getValue(), massSpinner.getValue(), gravitySpinner.getValue());

            /*
            pe.setPeriod();
            period.setText(Double.toString(Math.round(pe.getPeriod())) + " s");
            pe.setHeight();
            pe.setTotalEnergy();
            totalEnergy.setText(Double.toString(Math.round(pe.getTotalEnergy())) + " J");
            */
        }));




















    }


    /*
    private void rotateLineTo(double newAngle,double oldAngle) {
        //tl.stop();
        Rotate rot = new Rotate();
        rot.setPivotX(string.getStartX());
        rot.setPivotY(string.getStartY());
        rot.setAngle(-(newAngle-oldAngle));

        string.getTransforms().add(rot);

      }

     */

      private void animation(double angle, Line string, Group group, Button reset) {

          Rotate rotate = new Rotate();


          rotate.setPivotX(string.getStartX());
          rotate.setPivotY(string.getStartY());

          group.getTransforms().add(rotate);

          Timeline timeline = new Timeline();
          timeline.setAutoReverse(true);
          timeline.setCycleCount(Timeline.INDEFINITE);

          KeyValue kv = new KeyValue(rotate.angleProperty(), angle*2, Interpolator.EASE_BOTH);
          KeyFrame kf = new KeyFrame(Duration.millis(1000), kv);

          KeyValue kv1 = new KeyValue(rotate.angleProperty(), 0);
          KeyFrame kf1 = new KeyFrame(Duration.ZERO, kv1);


          timeline.getKeyFrames().addAll(kf1, kf);
          timeline.playFromStart();

          reset.setOnAction((event) -> {

              timeline.jumpTo(Duration.ZERO);
              timeline.stop();

          });

          ShmEngine pe =new ShmEngine(angleSpinner.getValue(), lengthSpinner.getValue(), massSpinner.getValue(), gravitySpinner.getValue());

          period.setText(Double.toString(Math.round(pe.getPeriod())));
          totalEnergy.setText(Double.toString(Math.round(pe.getTotalEnergy())));








      }






}
