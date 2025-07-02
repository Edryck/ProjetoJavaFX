package com.example.main.controller;

import com.example.main.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.File;

public class CadastroProdutoController {
    @FXML
    private Button buttonAjustarEstoque;

    @FXML
    private Button buttonCadastro;

    @FXML
    private FontIcon buttonVoltarTela;

    @FXML
    private ChoiceBox<?> categoriaProduto;

    @FXML
    private TextField codigoField;

    @FXML
    private TextField descricaoField;

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

    @FXML
    void handleButtonCadastro() {
        
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
