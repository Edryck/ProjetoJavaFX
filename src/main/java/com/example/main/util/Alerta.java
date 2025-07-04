package com.example.main.util;

import com.example.main.controller.AlertaController;
import com.example.main.enums.TipoAlerta;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class Alerta {
    /**
     * Gera um alerta personalizado.
     * @param tipo Tipo de icone que será utilizado
     * @param titulo Titulo do alerta
     * @param mensagem Mensagem exibida no alerta
     * @code alerta.mostrarAlerta(TipoAlerta.SUCESSO, "Cadastro Realizado", "Conta criada com sucesso");
     */
    public static void mostrarAlerta(TipoAlerta tipo, String titulo,String mensagem) {
        try {
            Dialog<Void> dialog = new Dialog<>();

            FXMLLoader loader = new FXMLLoader(Alerta.class.getResource("/com/example/main/application/auth/assets/view/alerta.fxml"));
            Parent root = loader.load();

            AlertaController controller = loader.getController();
            // Cria o icone com as info do enum
            FontIcon icone = new FontIcon(tipo.getCodIcone());
            icone.setIconColor(tipo.getCor());

            controller.dadosAlerta(titulo, mensagem, icone);

            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.setContent(root);
            dialogPane.setStyle("-fx-background-color: transparent;");

            dialog.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}