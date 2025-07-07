package com.example.main.controller.venda;

import com.example.main.HelloApplication;
import com.example.main.model.rn.VendaRN;
import com.example.main.model.vo.Produto;
import com.example.main.model.vo.Venda;
import com.example.main.util.FormatadorMoeda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class VendaController implements Initializable {
    private final VendaRN vendaRN = new VendaRN();

    @FXML
    private Pane buttonConfig;

    @FXML
    private Pane buttonEstoque;

    @FXML
    private ToggleButton buttonFilterFinalizados;

    @FXML
    private Toggle buttonFilterPendentes;

    @FXML
    private ToggleButton buttonFilterTodos;

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
    private TableView<Venda> tabelaVendas;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FormatadorMoeda f = new FormatadorMoeda();

        quantVendas.setText(String.valueOf(vendaRN.totalVendas()));
        vendasFinalizadas.setText(String.valueOf(vendaRN.totalVendasFinalizadas()));
        vendasPendentes.setText(String.valueOf(vendaRN.totalVendasPendentes()));
        valorTotalVendas.setText(f.formatador(vendaRN.valorTotalVendas()));

        colCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colData.setCellValueFactory(new PropertyValueFactory<>("dataVenda"));
        colFormaPag.setCellValueFactory(new PropertyValueFactory<>("formaPagamento"));
        colValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        carregarTabela();
    }

    private void carregarTabela() {

        List<Venda> listaVenda = vendaRN.listarTodasVendas();
        ObservableList<Venda> venda = FXCollections.observableArrayList(listaVenda);
        tabelaVendas.setItems(venda);
    }

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
    void handleFornecedores() {
        HelloApplication.changeScreen("fornecedoresView.fxml");
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
