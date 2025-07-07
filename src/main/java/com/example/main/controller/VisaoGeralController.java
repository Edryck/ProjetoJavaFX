package com.example.main.controller;

import com.example.main.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class VisaoGeralController {

    @FXML
    private Pane buttonConfig;

    @FXML
    private Pane buttonFornecedores;

    @FXML
    private Pane buttonRelatorios;

    @FXML
    private Pane buttonVendas;

    @FXML
    private Pane buttonEstoque;

    @FXML
    void handleButtonEstoque() {
        HelloApplication.changeScreen("estoqueView.fxml");
    }

    @FXML
    void handleButtonVendas() { HelloApplication.changeScreen("vendaView.fxml");}

    @FXML
    void handleButtonFornecedores() {
        HelloApplication.changeScreen("fornecedoresView.fxml");
    }
}