package com.example.main;

import com.example.main.enums.TipoAlerta;
import com.example.main.util.Alerta;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {
    private static Scene mainScene; // Guarda a cena principal
    // Armazena o caminho da view
    private static final String caminhoView = "/com/example/main/application/auth/assets/view/";

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL fxmlLocation = getClass().getResource(caminhoView + "visaoGeral.fxml"); // Alterar depois
        if (fxmlLocation == null) {
            return;
        }
        Parent root = FXMLLoader.load(fxmlLocation);

        mainScene = new Scene(root); // Cria a cena e guarda na variável estática

        primaryStage.setTitle("FinStock");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    // Método para trocar de tela
    public static void changeScreen(String fxmlFileName) {
        try {
            URL fxmlLocation = HelloApplication.class.getResource(caminhoView + fxmlFileName);
            if (fxmlLocation == null) {
                return;
            }
            Parent novaTela = FXMLLoader.load(fxmlLocation);
            mainScene.setRoot(novaTela);
        } catch (IOException e) {
            Platform.runLater(() -> Alerta.mostrarAlerta(TipoAlerta.ERRO, "Erro Inesperado", "Não foi possível carregar a tela."));
            System.err.println("Erro ao carregar a tela: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}