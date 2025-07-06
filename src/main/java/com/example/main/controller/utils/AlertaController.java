package com.example.main.controller.utils;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

public class AlertaController {

    @FXML
    private FontIcon iconeAlerta;

    @FXML
    private Label mensagemLabel;

    @FXML
    private Button botaoOk;

    @FXML
    private Label tituloLabel;

    public void dadosAlerta(String titulo, String mensagem, FontIcon icone) {
        tituloLabel.setText(titulo);
        mensagemLabel.setText(mensagem);

        iconeAlerta.setIconLiteral(icone.getIconLiteral());
        iconeAlerta.setIconColor(icone.getIconColor());
    }

    @FXML
    private void fechar() {
        Stage stage = (Stage) botaoOk.getScene().getWindow();
        stage.close();
    }
}
