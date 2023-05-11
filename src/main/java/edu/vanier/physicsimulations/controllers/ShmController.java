package edu.vanier.physicsimulations.controllers;

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













    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lengthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(50, 500, 200));
        angleSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Math.PI, Math.PI/4));
        massSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 50, 10 ));

        Line pendulumArm = new Line();
        pendulumArm.setStartX(520);
        pendulumArm.setStartY(50);
        pendulumArm.setEndX(pendulumArm.getStartX() + lengthSpinner.getValue()*Math.sin(angleSpinner.getValue()));
        pendulumArm.setEndY(pendulumArm.getStartY() + lengthSpinner.getValue()*Math.cos(angleSpinner.getValue()));
        pendulumArm.setStroke(Color.BLACK);

        Circle pendulumBob = new Circle();
        pendulumBob.setCenterX(pendulumArm.getEndX());
        pendulumBob.setCenterY(pendulumArm.getEndY());
        pendulumBob.setRadius(massSpinner.getValue());
        pendulumBob.setFill(Color.RED);

        Group group = new Group();
        group.getChildren().addAll(pendulumArm, pendulumBob);

        /*
        RotateTransition animation = new RotateTransition(Duration.seconds(1), group);
        animation.setByAngle(Math.toDegrees(angleSpinner.getValue()));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setAutoReverse(true);
        animation.play();
/*
        /*

        lengthSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            pendulumArm.setEndX(pendulumArm.getStartX() + newValue * Math.sin(angleSpinner.getValue()));
            pendulumArm.setEndY(pendulumArm.getStartY() + newValue * Math.cos(angleSpinner.getValue()));
            pendulumBob.setCenterX(pendulumArm.getEndX());
            pendulumBob.setCenterY(pendulumArm.getEndY());
        });

        angleSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            pendulumArm.setEndX(pendulumArm.getStartX() + lengthSpinner.getValue() * Math.sin(newValue));
            pendulumArm.setEndY(pendulumArm.getStartY() + lengthSpinner.getValue() * Math.cos(newValue));
            pendulumBob.setCenterX(pendulumArm.getEndX());
            pendulumBob.setCenterY(pendulumArm.getEndY());
            animation.setByAngle(Math.toDegrees(newValue));
        });

        massSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            pendulumBob.setRadius(newValue);
        });
*/
        pane.getChildren().addAll(group);
















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

      private void animation(double angle) {

          Rotate rotate = new Rotate();

          rotate.setPivotX(string.getStartX());
          rotate.setPivotY(string.getStartY());

          string.getTransforms().add(rotate);

          Timeline timeline = new Timeline();

          timeline.setCycleCount((int)(angle*2));




          KeyFrame keyFrame = new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
              double angle =0;
              @Override
              public void handle(ActionEvent event) {
                  angle += 1;
                  rotate.setAngle(angle);
              }
          });

          timeline.getKeyFrames().addAll(keyFrame);

          timeline.play();
          timeline.setOnFinished((event -> {

              Timeline tl1 = new Timeline();
              tl1.setCycleCount((int)angle*2);

              KeyFrame keyFrame1 = new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
                  double angle = rotate.getAngle();
                  @Override
                  public void handle(ActionEvent event) {
                      angle -= 1;
                      rotate.setAngle(angle);
                  }
              });

              tl1.getKeyFrames().add(keyFrame1);
              tl1.play();
          }));

      }

*/
}
