package com.example.main.controller;

import com.example.main.HelloApplication;
import com.example.main.model.dao.CadastroUsuarioDAO;
import com.example.main.model.rn.CadastroUsuarioRN;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CadastroUsuarioController {

    @FXML
    private Button buttonCadastro;

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

    private final CadastroUsuarioRN cadastroRN = new CadastroUsuarioRN();
    private final CadastroUsuarioDAO cadastroDAO = new CadastroUsuarioDAO();

    @FXML
    void handleButtonCadastro(ActionEvent event) {
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

        cadastroDAO.cadastrar(nome, email, senha);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso");
        alert.setHeaderText("Cadastro Realizado");
        alert.setContentText("Sua conta foi criada com sucesso!");
        alert.showAndWait();

        HelloApplication.changeScreen("login.fxml");
    }

    @FXML
    void handleVoltarTelaLogin() {
        HelloApplication.changeScreen("login.fxml");
    }
}
