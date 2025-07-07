package com.example.main.controller.venda;

import com.example.main.HelloApplication;
import com.example.main.enums.StatusVenda;
import com.example.main.enums.TipoAlerta;
import com.example.main.exceptions.RNException;
import com.example.main.model.rn.VendaRN;
import com.example.main.model.vo.ItemVenda;
import com.example.main.model.vo.Venda;
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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegistrarVendaController implements Initializable {
    private final VendaRN vendaRN = new VendaRN();

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
    private TextField quantParcelas;

    @FXML
    private ToggleGroup pagamentoToggleGroup;

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

    public void atualizarTotais() throws NumberFormatException {
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
            throw new RNException("Número inválido, atualização de totais, Registrar Venda. ");
        }
        BigDecimal valorDesconto = subtotal.multiply(descontoPercentual.divide(new BigDecimal(100)));
        BigDecimal totalFinal = subtotal.subtract(valorDesconto);

        subTotalLabel.setText(String.format("R$ %.2f", subtotal));
        descontoLabel.setText(String.format("- R$ %.2f", valorDesconto));
        totalLabel.setText(String.format("R$ %.2f", totalFinal));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quantParcelas.setVisible(false);

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

        pagamentoToggleGroup.selectedToggleProperty().addListener((observable, oldToggle, newToggle) -> {
            if (newToggle == null) {
                quantParcelas.setVisible(false);
                return;
            }
            quantParcelas.setVisible(newToggle == formaPagCredito);
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
        if (carrinhoItens.isEmpty()) {
            Alerta.mostrarAlerta(TipoAlerta.ATENCAO, "Carrinho vazio!", "Não foi possível completar o registro, carrinho vazio.");
        }
        Toggle formaPagSelecionado = pagamentoToggleGroup.getSelectedToggle();

        if (formaPagSelecionado == null) {
            Alerta.mostrarAlerta(TipoAlerta.ATENCAO, "Forma de Pagamento", "Selecione uma forma de pagamento.");
            return;
        }

        ToggleButton botaoSelecionado = (ToggleButton) formaPagSelecionado;
        String formaPagamento = botaoSelecionado.getText();
        Integer quantidadeParcelas = null;

        if (formaPagamento.equals("Crédito")) {
            try {
                quantidadeParcelas = Integer.parseInt(quantParcelas.getText());
                if (quantidadeParcelas <= 0) {
                    Alerta.mostrarAlerta(TipoAlerta.ERRO, "Quantidade inválida!", "Qunatidade deve ser maior que zero.");
                    throw new RNException("O número de parcelas deve ser maior que zero.");
                }
            } catch (NumberFormatException e) {
                Alerta.mostrarAlerta(TipoAlerta.ERRO, "Número de parcelas inválido.", "Número de parcelas devem ser maior que zero.");
                throw new RNException("Número de parcelas inválido.");
            }
        }
        try {
            String nomeC = nomeCliente.getText();
            BigDecimal valorTotal = new BigDecimal(totalLabel.getText().replace("R$ ", "").replace(",", "."));
            ToggleButton selectedToggle = (ToggleButton) pagamentoToggleGroup.getSelectedToggle();
            String formaPag = selectedToggle.getText();

            Venda novaVenda = new Venda();
            novaVenda.setNomeCliente(nomeC);
            novaVenda.setDataVenda(LocalDateTime.now());
            novaVenda.setValorTotal(valorTotal);
            novaVenda.setFormaPagamento(formaPag);
            novaVenda.setQuantidadeParcelas(quantidadeParcelas);
            if (formaPag.equals("Cartão de Crédito")) {
                novaVenda.setStatus(StatusVenda.PENDENTE);
            } else {
                novaVenda.setStatus(StatusVenda.FINALIZADA);
            }
            novaVenda.setItens(carrinhoItens);

            vendaRN.registrarVenda(novaVenda);
            Alerta.mostrarAlerta(TipoAlerta.VENDA_REG, "Venda registrada!", "Nova venda registrada com sucesso!");
        } catch (RNException | SQLException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO, "Falha ao registrar venda", "Não foi possível registrar venda. Tente novamente.");
        }
    }

    @FXML
    void handleVoltar() {
        HelloApplication.changeScreen("vendaView.fxml");
    }
}