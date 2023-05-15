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
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.DoubleToIntFunction;

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
    @FXML
    private Text timer;







    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lengthSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 50, 1));
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
        pendulumArm.setEndX(pendulumArm.getStartX() - (150+lengthSpinner.getValue())*Math.sin(Math.toDegrees(angleSpinner.getValue())));
        pendulumArm.setEndY(pendulumArm.getStartY() - (150+lengthSpinner.getValue())*Math.cos(Math.toDegrees(angleSpinner.getValue())));
        pendulumArm.setStroke(Color.BLACK);

        Circle pendulumBob = new Circle(pendulumArm.getEndX(), pendulumArm.getEndY(), massSpinner.getValue());
        pendulumBob.setFill(Color.RED);

        Group group = new Group();
        group.getChildren().addAll(pendulumArm, pendulumBob);


        angleSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            pendulumArm.setEndX(pendulumArm.getStartX() + (150+lengthSpinner.getValue()) * Math.sin(Math.toRadians(newValue)));
            pendulumArm.setEndY(pendulumArm.getStartY() + (150+lengthSpinner.getValue()) * Math.cos(Math.toRadians(newValue)));
            pendulumBob.setCenterX(pendulumArm.getEndX());
            pendulumBob.setCenterY(pendulumArm.getEndY());
            //animation.setByAngle(Math.toDegrees(newValue));
        });

        lengthSpinner.valueProperty().addListener((observable, oldValue, newValue) -> {
            pendulumArm.setEndX(pendulumArm.getStartX() - (150 + newValue) * Math.sin(Math.toDegrees(angleSpinner.getValue())));
            pendulumArm.setEndY(pendulumArm.getStartY() - (150 + newValue) * Math.cos(Math.toDegrees(angleSpinner.getValue())));
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

          DecimalFormat decfor = new DecimalFormat("0.00");

          ShmEngine pe =new ShmEngine(angleSpinner.getValue(), lengthSpinner.getValue(), massSpinner.getValue(), gravitySpinner.getValue());

          Rotate rotate = new Rotate();


          rotate.setPivotX(string.getStartX());
          rotate.setPivotY(string.getStartY());

          group.getTransforms().add(rotate);

          Timeline timeline = new Timeline();
          timeline.setAutoReverse(true);
          timeline.setCycleCount(Timeline.INDEFINITE);

          KeyValue kv = new KeyValue(rotate.angleProperty(), angle*2, Interpolator.EASE_BOTH);
          KeyFrame kf = new KeyFrame(Duration.seconds(pe.getPeriod()/2), kv);




          timeline.getKeyFrames().addAll( kf);


          reset.setOnAction((event) -> {

              timeline.jumpTo(Duration.ZERO);
              timeline.stop();

          });



          period.setText(decfor.format(pe.getPeriod()) + " s");
          totalEnergy.setText(decfor.format(pe.getTotalEnergy()) + " J");
         // System.out.println(pe.velocityCalc(timeline.getCurrentTime().toSeconds()));
          //System.out.println(pe.velocityCalc(14));
          //System.out.println(pe.getAngularFreq());

          KeyFrame textUpdate = new KeyFrame(Duration.millis(150), new EventHandler<ActionEvent>() {
              double counter = 0;
              @Override
              public void handle(ActionEvent event) {
                  counter +=150;
                  double timeinsec = counter/1000;
                  timer.setText(decfor.format(timeinsec) + " s");
                  pe.setVelocity(pe.velocityCalc(timeinsec));
                  currentVelocity.setText(decfor.format(pe.getVelocity()) + "m/s");
                  pe.setKineticEnergy(pe.kineticCalc(pe.getVelocity()));
                  kineticEnergy.setText(decfor.format(pe.getKineticEnergy()) + " J");
                  potentialEnergy.setText(decfor.format(pe.getTotalEnergy()-pe.getKineticEnergy()) + " J");

                 // System.out.println(pe.getVelocity());
                //  kineticEnergy.setText(Double.toString(pe.getKineticEnergy()));
                 // potentialEnergy.setText(Double.toString(pe.getTotalEnergy()-pe.getKineticEnergy()));
              }

      });

          Timeline text = new Timeline();
        text.getKeyFrames().add(textUpdate);
        text.setCycleCount(Timeline.INDEFINITE);
        text.play();

          timeline.play();








      }






}
