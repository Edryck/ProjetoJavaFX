package com.example.main.controller;

import com.example.main.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.kordamp.ikonli.javafx.FontIcon;

public class AjustarEstoqueController {

    @FXML
    private Button buttonCadastro;

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

    }

    @FXML
    void handleVoltar() {
        HelloApplication.changeScreen("estoqueView.fxml");
    }

}
