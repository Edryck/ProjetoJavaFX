package com.example.main.controller;

import com.example.main.HelloApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LoginController {

    @FXML
    private Label buttonCadastrar;

    @FXML
    private Label buttonEsqueceuSenha;

    @FXML
    private Button buttonLogin;

    @FXML
    private Label emailNaoExisteMsg;

    @FXML
    private Label senhaIncorretaMsg;

    @FXML
    public void handleButtonLogin() {
        HelloApplication.changeScreen("visaoGeral.fxml");
    }
}