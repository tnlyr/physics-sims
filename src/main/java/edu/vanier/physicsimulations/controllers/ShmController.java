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

    Timeline tl;










    @Override
    public void initialize(URL location, ResourceBundle resources) {

        SpinnerValueFactory<Double> valueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, 90);
        valueFactory.setValue((double)1);

        angleSpinner.setValueFactory(valueFactory);

        angleSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            rotateLineTo(newValue, oldValue);
        });





    playBtn.setOnAction((event -> {

        double angler = angleSpinner.getValue();

        animation(angler);


    }));







    }


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


}
