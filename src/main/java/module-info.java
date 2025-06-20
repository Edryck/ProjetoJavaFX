module com.example.main.application {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    opens com.example.main.controller to javafx.fxml;
    exports com.example.main.controller to javafx.fxml;
    exports com.example.main;
    opens com.example.main to javafx.fxml;
}