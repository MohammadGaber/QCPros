module com.example.AppViewer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires json.simple;
    requires rest.assured;
    requires json.path;
    requires org.jetbrains.annotations;

    opens com.example.AppViewers to javafx.fxml;
    exports com.example.AppViewers;
    exports Models;
    opens Models to javafx.fxml;
    exports Controllers;
    opens Controllers to javafx.fxml;
}