package com.example.main.controller.venda;

import com.example.main.enums.TipoAlerta;
import com.example.main.model.rn.ProdutoRN;
import com.example.main.model.rn.VendaRN;
import com.example.main.model.vo.ItemVenda;
import com.example.main.model.vo.Produto;
import com.example.main.util.Alerta;
import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

public class AddItemVendaController {
    private final ProdutoRN produtoRN = new ProdutoRN();
    private final VendaRN vendaRN = new VendaRN();

    private Dialog<ItemVenda> dialog;

    public void setDialog(Dialog<ItemVenda> dialog) {
        this.dialog = dialog;
    }

    @FXML
    private FontIcon buttonVoltarTela;

    @FXML
    private TextField codigoField;

    @FXML
    private TextField descricaoField;

    @FXML
    private TextField precoField;

    @FXML
    private TextField quantidadeField;

    @FXML
    void handleBuscarProdutoId() {
        String codigo = codigoField.getText();
        if (codigo == null || codigo.isBlank()) {
            return;
        }

        Produto produto = (Produto) produtoRN.pesquisar(codigo);
        if (produto != null) {
            descricaoField.setText(produto.getDescricao());
            precoField.setText(produto.getPrecoVenda().toPlainString().replace('.', ',')); // Formata para o usuário ver

            quantidadeField.setText("1");
            quantidadeField.requestFocus();
            quantidadeField.selectAll();
        } else {
            descricaoField.clear();
            precoField.clear();
            quantidadeField.clear();
            Alerta.mostrarAlerta(TipoAlerta.INFO, "Não Encontrado", "Nenhum produto encontrado com este código.");
        }
    }

    @FXML
    void handleButtonAdd() {
        Produto produto = produtoRN.pesquisarCod(codigoField.getText());
        if (produto != null) {
            descricaoField.setText(produto.getDescricao());
            precoField.setText(produto.getPrecoVenda().toPlainString().replace('.', ','));
        } else {
            // Limpa os campos e avisa que não encontrou
            descricaoField.clear();
            precoField.clear();
            quantidadeField.clear();
            Alerta.mostrarAlerta(TipoAlerta.INFO, "Não Encontrado", "Nenhum produto encontrado com este código.");
        }
        int quantidade = vendaRN.validarQuant(quantidadeField.getText(), produto);

        ItemVenda novoItem = new ItemVenda();
        novoItem.setProduto(produto);
        novoItem.setQuantidade(quantidade);
        novoItem.setPrecoUnitario(produto.getPrecoVenda());
        if (dialog != null) {
            dialog.setResult(novoItem);
            dialog.close();
        }
    }

    @FXML
    void handleVoltar() {
        if (dialog == null) {
            dialog.close();
        } else {
            Stage stage = (Stage) buttonVoltarTela.getScene().getWindow();
            stage.close();
        }
    }

}
