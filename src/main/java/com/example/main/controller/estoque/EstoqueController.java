package com.example.main.controller.estoque;

import com.example.main.HelloApplication;
import com.example.main.model.rn.ProdutoRN;
import com.example.main.model.vo.Produto;
import com.example.main.util.FormatadorMoeda;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class EstoqueController implements Initializable {
    private final ProdutoRN produtoRN = new ProdutoRN();

    @FXML
    private TableColumn<Produto, Void> acao;

    @FXML
    private TableColumn<Produto, Void> acaoBE;

    @FXML
    private Pane buttonConfig;

    @FXML
    private Pane buttonFornecedores;

    @FXML
    private Pane buttonRelatorios;

    @FXML
    private Pane buttonVendas;

    @FXML
    private Pane buttonVisaoGeral;

    @FXML
    private TableView<Produto> listaProdutosCad;

    @FXML
    private TableView<Produto> listaProdutoBE;

    @FXML
    private TableColumn<Produto, String> codProduto;

    @FXML
    private TableColumn<Produto, String> colCodigoBE;

    @FXML
    private TableColumn<Produto, String> colDescricao;

    @FXML
    private TableColumn<Produto, String> colDescricaoBE;

    @FXML
    private TableColumn<Produto, BigDecimal> colPrecoCusto;

    @FXML
    private TableColumn<Produto, BigDecimal> colPrecoCustoBE;

    @FXML
    private TableColumn<Produto, BigDecimal> colPrecoVenda;

    @FXML
    private TableColumn<Produto, Integer> colQuantBE;

    @FXML
    private TableColumn<Produto, Integer> colQuantidade;

    @FXML
    private Label quantProdutosCadastrados;

    @FXML
    private Label produtoBaixoEstoque;

    @FXML
    private Label quantidadeProdutos;

    @FXML
    private Label valorEstoque;

    @FXML
    private TextField pesquisarProduto;

    @FXML
    void handleButtonVendas() {
        HelloApplication.changeScreen("vendaView.fxml");
    }

    @FXML
    void handleCadastroProduto() {
        HelloApplication.changeScreen("cadastroProdutoView.fxml");
    }

    @FXML
    void handleButtonVisaoGeral() {
        HelloApplication.changeScreen("visaoGeral.fxml");
    }

    @FXML
    void handlePesquisarProduto() {
        String termo = pesquisarProduto.getText();
        pesquisarProduto(termo);
    }

    @FXML
    void handleEditarProduto(Produto produto) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/main/application/auth/assets/view/editarProdutoView.fxml"));
            DialogPane dialogPane = loader.load();

            EditarProdutoController formController = loader.getController();

            formController.carregarProduto(produto);

            Dialog<Produto> dialog = new Dialog<>();
            dialog.setDialogPane(dialogPane);
            dialog.setTitle("Editar Produto");
            formController.setDialog(dialog);

            Optional<Produto> resultado = dialog.showAndWait();

            // Se o produto editado foi retornado, atualiza no banco
            resultado.ifPresent(produtoEditado -> {
                produtoRN.atualizarProd(produtoEditado); // Você precisará criar este método no seu DAO
                carregarDados();
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        codProduto.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colPrecoCusto.setCellValueFactory(new PropertyValueFactory<>("precoCusto"));
        colPrecoVenda.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));

        colCodigoBE.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        colDescricaoBE.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        colQuantBE.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colPrecoCustoBE.setCellValueFactory(new PropertyValueFactory<>("precoCusto"));

        configurarAcoes(acao);
        configurarAcoes(acaoBE);

        pesquisarProduto.textProperty().addListener((obs, oldText, newText) -> pesquisarProduto(newText));

        carregarDados();
    }

    private void configurarAcoes(TableColumn<Produto, Void> coluna) {
        coluna.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("", new FontIcon("mdi2p-pencil"));
            {
                editButton.setStyle("-fx-background-color: transparent;");
                editButton.setOnAction(event -> {
                    Produto produto = getTableView().getItems().get(getIndex());
                    handleEditarProduto(produto);
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(editButton);
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    public void infoEstoque() {
        List<Produto> prodBaixoEstoque = produtoRN.listarProdutoBE();
        List<Produto> produtos = produtoRN.listarProdutos();
        FormatadorMoeda f = new FormatadorMoeda();

        BigDecimal valorEst = produtoRN.valorEstoque();
        String totalProd = String.valueOf(produtos.size());
        String quantProdBE = String.valueOf(prodBaixoEstoque.size());
        String quantProdCad = String.valueOf(produtoRN.quantProdutos());

        valorEstoque.setText(f.formatador(valorEst));
        quantidadeProdutos.setText(quantProdCad);
        produtoBaixoEstoque.setText(quantProdBE);
        quantProdutosCadastrados.setText(totalProd);
    }

    private void carregarTabelaPrincipal(List<Produto> resultadoDaBusca) {
        List<Produto> listaDeProdutos = produtoRN.listarProdutos();
        ObservableList<Produto> produtos = FXCollections.observableArrayList(listaDeProdutos);
        listaProdutosCad.setItems(produtos);
    }

    private void carregarTabelaBaixoEstoque() {
        List<Produto> listaDeProdutosBE = produtoRN.listarProdutoBE();
        ObservableList<Produto> produtos = FXCollections.observableArrayList(listaDeProdutosBE);
        listaProdutoBE.setItems(produtos);
    }

    private void carregarDados() {
        List<Produto> todosOsProdutos = produtoRN.listarProdutos();
        List<Produto> produtosBaixoEstoque = produtoRN.listarProdutoBE();

        atualizarTabelaPrincipal(todosOsProdutos);
        atualizarTabelaBaixoEstoque(produtosBaixoEstoque);
        atualizarInfoEstoque(todosOsProdutos, produtosBaixoEstoque);
    }

    private void atualizarTabelaPrincipal(List<Produto> produtos) {
        listaProdutosCad.setItems(FXCollections.observableArrayList(produtos));
    }

    private void atualizarTabelaBaixoEstoque(List<Produto> produtos) {
        listaProdutoBE.setItems(FXCollections.observableArrayList(produtos));
    }

    private void atualizarInfoEstoque(List<Produto> todosProdutos, List<Produto> baixoEstoque) {
        FormatadorMoeda f = new FormatadorMoeda();

        BigDecimal valorTotal = todosProdutos.stream()
                .map(p -> p.getPrecoCusto().multiply(BigDecimal.valueOf(p.getQuantidade())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        valorEstoque.setText(f.formatador(valorTotal));
        quantidadeProdutos.setText(String.valueOf(todosProdutos.size()));
        produtoBaixoEstoque.setText(String.valueOf(baixoEstoque.size()));
        quantProdutosCadastrados.setText(String.valueOf(todosProdutos.size()));
    }

    public void pesquisarProduto(String termo) {
        List<Produto> resultadoDaBusca = produtoRN.pesquisar(termo);
        carregarTabelaPrincipal(resultadoDaBusca);
    }
}