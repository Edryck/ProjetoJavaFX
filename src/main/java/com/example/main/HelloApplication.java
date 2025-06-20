package com.example.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL; // Importe a classe URL

public class HelloApplication extends Application {
    private static Scene mainScene; // Guarda a cena principal
    // Armazena o caminho da view
    private static final String VIEW_PATH = "/com/example/main/application/auth/assets/view/";

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL fxmlLocation = getClass().getResource(VIEW_PATH + "login.fxml");
        Parent root = FXMLLoader.load(fxmlLocation);

        mainScene = new Scene(root); // Cria a cena e guarda na variável estática

        primaryStage.setTitle("FinStock");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    // Método pra trocar de tela
    public static void changeScreen(String fxmlFileName) {
        try {
            URL fxmlLocation = HelloApplication.class.getResource(VIEW_PATH + fxmlFileName);
            Parent newScreen = FXMLLoader.load(fxmlLocation);
            mainScene.setRoot(newScreen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}