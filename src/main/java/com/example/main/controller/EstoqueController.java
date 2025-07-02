package com.example.main.controller;

import com.example.main.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

public class EstoqueController {
    @FXML
    private Button buttonAjustarEstoque;

    @FXML
    private Button buttonCadastroProduto;

    @FXML
    private Pane buttonConfig;

    @FXML
    private Pane buttonEstoque;

    @FXML
    private Pane buttonFornecedores;

    @FXML
    private Pane buttonRelatorios;

    @FXML
    private Pane buttonVendas;

    @FXML
    private Pane buttonVisaoGeral;

    @FXML
    private TableColumn<?, ?> descricao;

    @FXML
    private TableColumn<?, ?> código;

    @FXML
    private TableView<?> listaProdutosCad;

    @FXML
    private TableColumn<?, ?> quantidade;

    @FXML
    private Label pesquisarProdutos;

    @FXML
    private TableColumn<?, ?> precoCusto;

    @FXML
    private TableColumn<?, ?> precoVenda;

    @FXML
    void handleAjustarEstoque() {
        HelloApplication.changeScreen("ajustarEstoqueView.fxml");
    }

    @FXML
    void handleCadastroProduto() {
        HelloApplication.changeScreen("cadastroProdutoView.fxml");
    }

    @FXML
    void handleButtonVisaoGeral() {
        HelloApplication.changeScreen("visaoGeral.fxml");
    }

    @FXML
    void listaProdutos(ActionEvent event) {

    }

}
