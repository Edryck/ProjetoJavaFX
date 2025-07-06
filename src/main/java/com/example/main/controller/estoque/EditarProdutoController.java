package com.example.main.controller.estoque;

import com.example.main.model.rn.ProdutoRN;
import com.example.main.model.vo.Produto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.math.BigDecimal;

public class EditarProdutoController {
    private Dialog<Produto> dialog;
    private Produto produtoParaEditar;
    private final ProdutoRN produtoRN = new ProdutoRN();

    @FXML
    private Button buttonAlterar;

    @FXML
    private FontIcon buttonVoltarTela;

    @FXML
    private TextField codigoField;

    @FXML
    private TextArea descricaoField;

    @FXML
    private TextField precoCustoField;

    @FXML
    private TextField precoVendaField;

    @FXML
    private TextField quantidadeField;

    public void setDialog(Dialog<Produto> dialog) {
        this.dialog = dialog;
    }

    public void carregarProduto(Produto produto) {
        this.produtoParaEditar = produto;

        // Preenche todos os campos da tela com os dados do produto existente
        codigoField.setText(produto.getIdProduto());
        descricaoField.setText(produto.getDescricao());
        precoVendaField.setText(produto.getPrecoVenda().toString());
        precoCustoField.setText(produto.getPrecoCusto().toString());
        quantidadeField.setText(String.valueOf(produto.getQuantidade()));
    }

    @FXML
    void handleButtonAlterar() {
        produtoParaEditar.setIdProduto(codigoField.getText());
        produtoParaEditar.setDescricao(descricaoField.getText());
        produtoParaEditar.setPrecoVenda(new BigDecimal(precoVendaField.getText().replace(",", ".")));
        produtoParaEditar.setPrecoCusto(new BigDecimal(precoCustoField.getText().replace(",", ".")));
        produtoParaEditar.setQuantidade(Integer.parseInt(quantidadeField.getText()));

        dialog.setResult(produtoParaEditar);
        dialog.close();
    }

    @FXML
    void handleVoltar() {
        if (dialog != null) {
            dialog.close();
        } else {
            Stage stage = (Stage) buttonVoltarTela.getScene().getWindow();
            stage.close();
        }
    }
}