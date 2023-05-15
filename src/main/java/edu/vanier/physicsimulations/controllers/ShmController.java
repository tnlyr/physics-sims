package edu.vanier.physicsimulations.controllers;

import edu.vanier.physicsimulations.engines.ShmEngine;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
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
    @FXML
    private MenuItem aboutBtn;







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


            aboutBtn.setOnAction(e -> {
                onHelp();
            });



        }));




















    }

    public void onHelp() {
        Stage stage = new Stage();
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setEditable(false);
        textArea.setText("""
                  
                         
                Simple Harmonic Motion Setting
                         
                         - Length
                         
                         Allows the user to select the length distance in meters either using the up and down arrows or manually entering the number.
                         
                         
                         - Mass
                         
                         Allows the user to select the mass in Kg either using the up and down arrows or manually entering the number.
                         
                         - Angle
                         
                         Allows the user to select the angle of the pendulum swing in degrees by either using the up and down arrows or manually entering the number.
                         
                         
                         - Gravity
                         
                         Allows the user to select the gravity in Newtons by either using the up and down arrows or manually entering the number.
                         
                         
                         
                         
                         
                 Simulation control
                         
                         -Play Button
                         
                          The play button allows the user to start the simulation; once it is clicked, the pendulum animation (swing) starts.
                         
                         - Rest Button
                         
                         The Reset Button allows users to reset the simulation by restarting the pendulum to its initial position and allowing the user to change the parameters.
                         
                         
                         - Foot Bar
                         
                          The foot bar displays the simulation's current simulations timer, period, velocity, kinetic energy, potential energy, and total energy.
                """);
        Scene scene = new Scene(textArea, 800, 800);
        stage.setTitle("Help Window");
        stage.setScene(scene);
        stage.show();
    }




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
