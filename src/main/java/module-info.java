module main.project {
    requires javafx.controls;
    requires javafx.fxml;


    opens main.project to javafx.fxml;
    opens model to java.fxml;

    exports model;
    exports main.project;

    exports controller;
    opens controller to javafx.fxml;
}