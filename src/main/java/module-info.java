module com.example.main {
    // Módulos Nativos do JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;

    // Módulos de Terceiros
    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.fontawesome5;
    requires java.sql;
    requires org.kordamp.ikonli.materialdesign2;
    requires annotations;

    // Abre seus pacotes para o JavaFX
    opens com.example.main to javafx.fxml;
    exports com.example.main;
    opens com.example.main.controller to javafx.fxml;
    exports com.example.main.controller;
    opens com.example.main.model.vo to javafx.fxml, javafx.base;
    exports com.example.main.model.vo;
}