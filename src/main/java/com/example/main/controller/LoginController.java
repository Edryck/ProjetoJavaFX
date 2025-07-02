package com.example.main.controller;

import com.example.main.HelloApplication;
import com.example.main.enums.TipoAlerta;
import com.example.main.model.rn.UsuarioRN;
import com.example.main.util.Alerta;
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

    private final UsuarioRN cadastroRN = new UsuarioRN();

    @FXML
    public void handleButtonLogin() {
        String email = emailField.getText();
        String senha = senhaField.getText();

        if (cadastroRN.autenticao(email, senha)) {
            HelloApplication.changeScreen("visaoGeral.fxml");
        } else {
            Alerta.mostrarAlerta(TipoAlerta.ERRO, "Erro ao realizar login!", "Email ou senha incorretos! Tente Novamente.");
            System.out.println("Login falhou!"); // Provisório
        }
    }

    @FXML
    void handleTelaCadastro() {
        HelloApplication.changeScreen("cadastroView.fxml");
    }
}