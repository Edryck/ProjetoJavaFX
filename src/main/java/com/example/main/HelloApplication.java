// O package deve ser com.example.main agora
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

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL fxmlLocation = getClass().getResource("/com/example/main/application/auth/assets/view/login.fxml");
        Parent root = FXMLLoader.load(fxmlLocation);

        mainScene = new Scene(root); // Cria a cena e guarda na variável estática

        primaryStage.setTitle("FinStock");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    // MÉTODO MÁGICO PARA TROCAR DE TELA
    public static void changeScreen(String fxml) {
        try {
            URL fxmlLocation = HelloApplication.class.getResource("/com/example/main/view/" + fxml);
            if (fxmlLocation == null) {
                System.err.println("Não foi possível encontrar a tela: " + fxml);
                return;
            }
            Parent newScreen = FXMLLoader.load(fxmlLocation);
            mainScene.setRoot(newScreen); // Apenas troca o conteúdo da cena principal!
        } catch (IOException e) {
            System.err.println("Erro ao carregar a tela: " + fxml);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}