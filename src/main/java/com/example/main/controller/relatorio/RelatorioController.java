package com.example.main.controller.relatorio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Pane;

public class RelatorioController {

    @FXML
    private ToggleButton DRE;

    @FXML
    private Pane buttonConfig;

    @FXML
    private Pane buttonEstoque;

    @FXML
    private Button buttonExportar;

    @FXML
    private Pane buttonFornecedores;

    @FXML
    private Button buttonGerar;

    @FXML
    private Pane buttonRelatorios;

    @FXML
    private Pane buttonVendas;

    @FXML
    private ToggleButton curvaABCProduto;

    @FXML
    private ToggleButton fluxoCaixa;

    @FXML
    private ToggleButton historicoPedidoFornecedor;

    @FXML
    private ToggleButton inventarioAtualVal;

    @FXML
    private ToggleButton produtoBaixoEstoque;

    @FXML
    private ToggleButton produtoMaisVendido;

    @FXML
    private ToggleGroup relatoriosToggleGroup;

    @FXML
    private ToggleButton vendaCategoria;

    @FXML
    private ToggleButton vendaFormaPag;

    @FXML
    private ToggleButton vendaPeriodo;

    @FXML
    void handleButtonExportar(ActionEvent event) {

    }

    @FXML
    void handleButtonGerarRelatorio(ActionEvent event) {

    }
}
