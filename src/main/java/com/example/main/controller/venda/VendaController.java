package com.example.main.controller.venda;

import com.example.main.HelloApplication;
import com.example.main.model.vo.Venda;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VendaController implements Initializable {

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
    private TableColumn<Venda, String> colCliente;

    @FXML
    private TableColumn<Venda, LocalDateTime> colData;

    @FXML
    private TableColumn<Venda, String> colFormaPag;

    @FXML
    private TableColumn<Venda, String> colStatus;

    @FXML
    private TableColumn<Venda, BigDecimal> colValorTotal;

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
    void handleButtonEstoque() {
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
