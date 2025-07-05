package com.example.main.controller;

import com.example.main.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.kordamp.ikonli.javafx.FontIcon;

public class EditarProdutoController {

    @FXML
    private Button buttonAjustar;

    @FXML
    private FontIcon buttonVoltarTela;

    @FXML
    private TextField codigoField;

    @FXML
    private TextField descricaoField;

    @FXML
    private TextField precoCustoFIeld;

    @FXML
    private TextField precoVendaFIeld;

    @FXML
    private TextField quantidadeField;

    @FXML
    void handleButtonAjustar() {
        String codigo = codigoField.getText();

    }

    @FXML
    void handleVoltar() {
        HelloApplication.changeScreen("estoqueView.fxml");
    }
}
