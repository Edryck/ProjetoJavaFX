package com.example.main.controller;

import com.example.main.HelloApplication;
import com.example.main.enums.TipoAlerta;
import com.example.main.model.rn.UsuarioRN;
import com.example.main.util.Alerta;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class UsuarioController {

    @FXML
    private TextField confirmaSenhaFIeld;

    @FXML
    private TextField emailField;

    @FXML
    private Label emailJaCadastradoMsg;

    @FXML
    private TextField nomeField;

    @FXML
    private TextField senhaField;

    @FXML
    private Label senhaNaoConfereMsg;

    private final UsuarioRN cadastroRN = new UsuarioRN();

    @FXML
    void handleButtonCadastro() {
        senhaNaoConfereMsg.setVisible(false);
        emailJaCadastradoMsg.setVisible(false);

        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = senhaField.getText();
        String confirmacao = confirmaSenhaFIeld.getText();

        if(cadastroRN.emailJaExiste(email)) {
            emailJaCadastradoMsg.setVisible(true);
            return;
        }

        if(!cadastroRN.confirmaSenha(senha, confirmacao)) {
            senhaNaoConfereMsg.setVisible(true);
            return;
        }

        cadastroRN.cadastro(nome, email, senha);
        Alerta.mostrarAlerta(TipoAlerta.SUCESSO, "Cadastro Realizado!", "Sua conta foi criada com suceso!");

        HelloApplication.changeScreen("login.fxml");
    }

    @FXML
    void handleVoltarTelaLogin() {
        HelloApplication.changeScreen("login.fxml");
    }
}
