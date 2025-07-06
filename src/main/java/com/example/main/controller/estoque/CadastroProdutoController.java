package com.example.main.controller.estoque;

import com.example.main.HelloApplication;
import com.example.main.enums.TipoAlerta;
import com.example.main.exceptions.RNException;
import com.example.main.model.rn.ProdutoRN;
import com.example.main.model.vo.Produto;
import com.example.main.util.Alerta;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;

public class CadastroProdutoController implements Initializable {
    private final ObservableList<String> categorias = FXCollections.observableArrayList("Suitãs", "Calcinhas", "Cuecas", "Conjuntos", "Outro");
    private final ProdutoRN produtoRN = new ProdutoRN();

    @FXML
    private ChoiceBox<String> categoriaProduto;

    @FXML
    private TextField codigoField;

    @FXML
    private TextField descricaoField;

    @FXML
    public Label formatoInvalidoPCMsg;

    @FXML
    public Label formatoInvalidoPVMsg;

    @FXML
    public Label formatoInvalidoQMsg;

    @FXML
    private TextField fornecedorField;

    @FXML
    private ImageView imagePreview;

    @FXML
    private TextField marcaField;

    @FXML
    private TextField precoCustoFIeld;

    @FXML
    private TextField precoVendaFIeld;

    @FXML
    private TextField quantidadeField;

    @FXML
    private Label caminhoImg;

    @FXML
    private Button selecionarImg;

    public void initialize(URL location, ResourceBundle resources) {
        categoriaProduto.setItems(categorias);
        categoriaProduto.setValue("Outro");
    }

    @FXML
    void handleButtonCadastro() {
        formatoInvalidoPVMsg.setVisible(false);
        formatoInvalidoPCMsg.setVisible(false);
        formatoInvalidoQMsg.setVisible(false);

        try {
            int quantidade;
            BigDecimal precoV, precoC;
            try{
                precoC = produtoRN.validarPrecos(precoCustoFIeld.getText());
            } catch (RNException e) {
                formatoInvalidoPCMsg.setVisible(true);
                return;
            }
            try{
                precoV = produtoRN.validarPrecos(precoVendaFIeld.getText());
            } catch (RNException e) {
                formatoInvalidoPCMsg.setVisible(true);
                return;
            }
            try{
                quantidade = produtoRN.validarQuant(quantidadeField.getText());
            } catch (RNException e) {
                formatoInvalidoQMsg.setVisible(true);
                return;
            }

            Produto produto = getProduto(precoV, precoC, quantidade);

            produtoRN.cadastrar(produto);
            Alerta.mostrarAlerta(TipoAlerta.P_CAD, "Produto cadastrado!", "O produto foi cadastrado com sucesso!");

            HelloApplication.changeScreen("estoqueView.fxml");
        } catch(NumberFormatException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO, "Formato inválido!", "Preços e quantidade devem conter apenas números");
        } catch(RNException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO, "Erro de validação!", e.getMessage());
        }
    }

    @NotNull
    private Produto getProduto(BigDecimal precoV, BigDecimal precoC, int quantidade) {
        Produto produto = new Produto();
        produto.setIdProduto(codigoField.getText());
        produto.setMarca(marcaField.getText());
        produto.setDescricao(descricaoField.getText());
        produto.setCategoria(categoriaProduto.getValue());
        produto.setFornecedor(fornecedorField.getText());
        produto.setPrecoVenda(precoV);
        produto.setPrecoCusto(precoC);
        produto.setQuantidade(quantidade);
        produto.setAtivo(true);
        produto.setImagem(caminhoImg.getText());
        return produto;
    }

    @FXML
    void handleSelecionarImg() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione uma Imagem para o Produto");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivos de Imagem (*.png, *.jpg, *.gif)", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        Stage stage = (Stage) selecionarImg.getScene().getWindow();
        File arquivo = fileChooser.showOpenDialog(stage);

        if (arquivo != null) {
            Image imagem = new Image(arquivo.toURI().toString());
            imagePreview.setImage(imagem);
            caminhoImg.setText(arquivo.getAbsolutePath());
        }
    }

    @FXML
    void handleVoltar() {
        HelloApplication.changeScreen("estoqueView.fxml");
    }

}
