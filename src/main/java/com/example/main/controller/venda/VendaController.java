package com.example.main.controller.venda;

import com.example.main.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class VendaController {

    @FXML
    private Pane buttonConfig;

    @FXML
    private Pane buttonEstoque;

    @FXML
    private Button buttonFilterFinalizados;

    @FXML
    private Button buttonFilterPendentes;

    @FXML
    private Button buttonFilterTodos;

    @FXML
    private Pane buttonFornecedores;

    @FXML
    private Button buttonRegistrarVenda;

    @FXML
    private Pane buttonRelatorios;

    @FXML
    private Pane buttonVendas;

    @FXML
    private Pane buttonVisaoGeral;

    @FXML
    private TableColumn<?, ?> colCliente;

    @FXML
    private TableColumn<?, ?> colData;

    @FXML
    private TableColumn<?, ?> colFormaPag;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private TableColumn<?, ?> colValorTotal;

    @FXML
    private TextField pesquisar;

    @FXML
    private Label quantVendas;

    @FXML
    private Label valorTotalVendas;

    @FXML
    private Label vendasFinalizadas;

    @FXML
    private Label vendasPendentes;

    @FXML
    void handleButtonEstoque(MouseEvent event) {
        HelloApplication.changeScreen("estoqueView.fxml");
    }

    @FXML
    void handleConfig(MouseEvent event) {

    }

    @FXML
    void handleFilterFinalizados(ActionEvent event) {

    }

    @FXML
    void handleFilterPendentes(ActionEvent event) {

    }

    @FXML
    void handleFilterTodos(ActionEvent event) {

    }

    @FXML
    void handleFornecedores(MouseEvent event) {

    }

    @FXML
    void handlePesquisar(ActionEvent event) {

    }

    @FXML
    void handleRegistrarVenda(ActionEvent event) {
        HelloApplication.changeScreen("registrarVendaView.fxml");
    }

    @FXML
    void handleRelatorios(MouseEvent event) {

    }

    @FXML
    void handleVisaoGeral(MouseEvent event) {
        HelloApplication.changeScreen("visaoGeral.fxml");
    }
}
