package com.example.main.controller;

import com.example.main.HelloApplication;
import com.example.main.model.rn.CadastroUsuarioRN;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Label buttonCadastrar;

    @FXML
    private Label buttonEsqueceuSenha;

    @FXML
    private Button buttonLogin;

    @FXML
    private Button buttonTelaCadastro;

    @FXML
    private TextField emailField;

    @FXML
    private Label emailNaoExisteMsg;

    @FXML
    private TextField senhaField;

    @FXML
    private Label senhaIncorretaMsg;

    private final CadastroUsuarioRN cadastroRN = new CadastroUsuarioRN();

    @FXML
    public void handleButtonLogin() {
        String email = emailField.getText();
        String senha = senhaField.getText();

        if (cadastroRN.autenticao(email, senha)) {
            HelloApplication.changeScreen("visaoGeral.fxml");
        } else {
            System.out.println("Login falhou!");
        }
    }

    @FXML
    void handleTelaCadastro() {
        HelloApplication.changeScreen("cadastroView.fxml");
    }
}