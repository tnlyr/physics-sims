module edu.vanier.physicsimulations {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    exports edu.vanier.physicsimulations.views;
    exports edu.vanier.physicsimulations.engines to javafx.fxml;
    opens edu.vanier.physicsimulations.controllers to javafx.fxml;
}