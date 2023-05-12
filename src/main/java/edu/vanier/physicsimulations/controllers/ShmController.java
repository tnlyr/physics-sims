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







int dummy =0;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lengthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(50, 500, 200));
        lengthSpinner.setEditable(true);
        angleSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 90, 30));
        angleSpinner.setEditable(true);
        massSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 50, 10 ));
        massSpinner.setEditable(true);

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

        /*
        RotateTransition animation = new RotateTransition(Duration.seconds(1), group);
        animation.setByAngle(Math.toDegrees(angleSpinner.getValue()));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setAutoReverse(true);
        animation.play();

         */

        lengthSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            pendulumArm.setEndX(pendulumArm.getStartX() - newValue * Math.sin(Math.toDegrees(angleSpinner.getValue())));
            pendulumArm.setEndY(pendulumArm.getStartY() - newValue * Math.cos(Math.toDegrees(angleSpinner.getValue())));
            pendulumBob.setCenterX(pendulumArm.getEndX());
            pendulumBob.setCenterY(pendulumArm.getEndY());
        });

        angleSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            pendulumArm.setEndX(pendulumArm.getStartX() + lengthSpinner.getValue() * Math.sin(Math.toRadians(newValue)));
            pendulumArm.setEndY(pendulumArm.getStartY() + lengthSpinner.getValue() * Math.cos(Math.toRadians(newValue)));
            pendulumBob.setCenterX(pendulumArm.getEndX());
            pendulumBob.setCenterY(pendulumArm.getEndY());
            //animation.setByAngle(Math.toDegrees(newValue));
        });

        massSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            pendulumBob.setRadius(newValue);
        });

        pane.getChildren().addAll(group);



        playBtn.setOnAction((event -> {

            animation(angleSpinner.getValue(), pendulumArm, group, resetBtn);
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


          timeline.getKeyFrames().addAll(kf);
          timeline.play();




          /*

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

          Timeline tl1 = new Timeline();
          timeline.setOnFinished((event -> {


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
              tl1.setOnFinished((loop) -> {
                  animation(angle, string, group, reset);
              });


          }));

          reset.setOnAction((stop) -> {
              timeline.stop();
              tl1.stop();




          });

*/

      }




}
