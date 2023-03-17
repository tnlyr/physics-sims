module edu.vanier.physicsimulations {
    requires javafx.controls;
    requires javafx.fxml;

    exports edu.vanier.physicsimulations.views;
    exports edu.vanier.physicsimulations.engines to javafx.fxml;
    opens edu.vanier.physicsimulations.controllers to javafx.fxml;
}