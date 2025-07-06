package com.example.main.controller.venda;

import com.example.main.HelloApplication;
import com.example.main.enums.TipoAlerta;
import com.example.main.model.rn.ProdutoRN;
import com.example.main.model.rn.VendaRN;
import com.example.main.model.vo.ItemVenda;
import com.example.main.util.Alerta;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegistrarVendaController implements Initializable {
    private final VendaRN vendaRN = new VendaRN();
    private final ProdutoRN produtoRN = new ProdutoRN();

    @FXML
    private Button buttonAddProduto;

    @FXML
    private Button buttonRegistrar;

    @FXML
    private FontIcon buttonVoltarTela;

    @FXML
    private TableView<ItemVenda> carrinhoTableView;

    @FXML
    private TableColumn<ItemVenda, String> colCodItem;

    @FXML
    private TableColumn<ItemVenda, String> colItem;

    @FXML
    private TableColumn<ItemVenda, BigDecimal> colPrecoUnit;

    @FXML
    private TableColumn<ItemVenda, Integer> colQuant;

    @FXML
    private TableColumn<ItemVenda, BigDecimal> colTotal;

    @FXML
    private Label descontoLabel;

    @FXML
    private TextField descontoVenda;

    @FXML
    private ToggleButton formaPagCredito;

    @FXML
    private ToggleButton formaPagDebito;

    @FXML
    private ToggleButton formaPagDinheiro;

    @FXML
    private ToggleButton formaPagPix;

    @FXML
    private TextField nomeCliente;

    @FXML
    private Label subTotalLabel;

    @FXML
    private Label totalLabel;

    private final ObservableList<ItemVenda> carrinhoItens = FXCollections.observableArrayList();

    public void atualizarTotais() {
        BigDecimal subtotal = BigDecimal.ZERO;
        for (ItemVenda item : carrinhoItens) {
            BigDecimal totalItem = item.getPrecoUnitario().multiply(new BigDecimal(item.getQuantidade()));
            subtotal = subtotal.add(totalItem);
        }

        BigDecimal descontoPercentual = BigDecimal.ZERO;
        try {
            if (!descontoVenda.getText().isBlank()) {
                descontoPercentual = new BigDecimal(descontoVenda.getText());
            }
        } catch (NumberFormatException e) {

        }
        BigDecimal valorDesconto = subtotal.multiply(descontoPercentual.divide(new BigDecimal(100)));
        BigDecimal totalFinal = subtotal.subtract(valorDesconto);

        subTotalLabel.setText(String.format("R$ %.2f", subtotal));
        descontoLabel.setText(String.format("- R$ %.2f", valorDesconto));
        totalLabel.setText(String.format("R$ %.2f", totalFinal));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        carrinhoTableView.setItems(carrinhoItens);
        colQuant.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colCodItem.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduto().getIdProduto()));
        colItem.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProduto().getDescricao()));
        colPrecoUnit.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getProduto().getPrecoVenda()));
        colTotal.setCellValueFactory(cellData -> {
                    ItemVenda item = cellData.getValue();
                    BigDecimal total = item.getPrecoUnitario().multiply(new BigDecimal(item.getQuantidade()));
                    return new SimpleObjectProperty<>(total);
                });

        descontoVenda.textProperty().addListener((obs, oldVal, newVal) -> atualizarTotais());
    }

    @FXML
    void handleAddProduto() {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/main/application/auth/assets/view/addItemVendaView.fxml"));
            AnchorPane customPane = loader.load();

            AddItemVendaController addItemController = loader.getController();
            Dialog<ItemVenda> dialog = new Dialog<>();
            dialog.getDialogPane().setContent(customPane);
            dialog.setTitle("Adicionar Item à Venda");
            addItemController.setDialog(dialog);
            Optional<ItemVenda> resultado = dialog.showAndWait();
            resultado.ifPresent(itemVenda -> {
                carrinhoItens.add(itemVenda);
                atualizarTotais();
            });
        } catch (IOException e) {
            e.printStackTrace();
            Alerta.mostrarAlerta(TipoAlerta.ERRO, "Erro de Interface", "Não foi possível abrir a tela para adicionar o item.");
        }

    }

    @FXML
    void handleButtonRegistrar() {
        String nomeC = nomeCliente.getText();
        BigDecimal desconto = produtoRN.validarPrecos(descontoVenda.getText());
    }

    @FXML
    void handleVoltar() {
        HelloApplication.changeScreen("vendaView.fxml");
    }
}