package com.example.main.controller.fornecedor;

import com.example.main.HelloApplication;
import com.example.main.enums.StatusFornecedor;
import com.example.main.enums.TipoAlerta;
import com.example.main.model.rn.FornecedorRN;
import com.example.main.model.vo.Fornecedor;
import com.example.main.util.Alerta;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class FornecedoresController implements Initializable {
    private final FornecedorRN fornecedorRN = new FornecedorRN();

    @FXML
    private Button buttonCadastroFornecedor;

    @FXML
    private Button buttonCadastroProduto1;

    @FXML
    private Pane buttonConfig;

    @FXML
    private Pane buttonEstoque;

    @FXML
    private Pane buttonFornecedores;

    @FXML
    private Pane buttonRelatorios;

    @FXML
    private Pane buttonVendas;

    @FXML
    private Pane buttonVisaoGeral;

    @FXML
    private TableColumn<?, ?> codFornecedor;

    @FXML
    private TableColumn<Fornecedor, String> codFornecedorDados;

    @FXML
    private TableColumn<?, ?> colDataPedido;

    @FXML
    private TableColumn<Fornecedor, String> colEmail;

    @FXML
    private TableColumn<Fornecedor, StatusFornecedor> colStatusFornecedor;

    @FXML
    private TableColumn<?, ?> colStatusPedido;

    @FXML
    private TableColumn<Fornecedor, String> colTelefone;

    @FXML
    private TableColumn<?, ?> colValorNota;

    @FXML
    private TableColumn<Fornecedor, Void> colAcoes;

    @FXML
    private TableColumn<?, ?> colAcoesPedidos;

    @FXML
    private TableView<Fornecedor> listaFornecedor;

    @FXML
    private TableView<?> listaPedidos;

    @FXML
    private TextField pesquisarFornecedor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configura as colunas da tabela para buscar os dados dos getters da classe Fornecedor
        codFornecedorDados.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colStatusFornecedor.setCellValueFactory(new PropertyValueFactory<>("status"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));

        configurarColunaAcoes();
        carregarDadosDaTabela();

        pesquisarFornecedor.textProperty().addListener((obs, oldText, newText) -> {
            handlePesquisarFornecedor(newText);
        });
    }

    private void carregarDadosDaTabela() {
        try {
            List<Fornecedor> fornecedoresDoBanco = fornecedorRN.listarTodos();
            atualizarTabela(fornecedoresDoBanco);
        } catch (Exception e) {
            System.err.println("Não foi possível carregar os dados da tabela: " + e.getMessage());
        }
    }

    private void configurarColunaAcoes() {
        colAcoes.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button("", new FontIcon("mdi2p-pencil"));
            private final Button deleteButton = new Button("", new FontIcon("mdi2d-delete"));
            private final HBox pane = new HBox(editButton, deleteButton);

            {
                pane.setAlignment(Pos.CENTER);
                pane.setSpacing(5);
                editButton.setStyle("-fx-background-color: transparent;");
                deleteButton.setStyle("-fx-background-color: transparent;");

                editButton.setOnAction(event -> {
                    Fornecedor fornecedor = getTableView().getItems().get(getIndex());
                    handleEditar(fornecedor);
                });

                deleteButton.setOnAction(event -> {
                    Fornecedor fornecedor = getTableView().getItems().get(getIndex());
                    handleDesativar(fornecedor);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(pane);
                }
            }
        });
    }

    private Optional<Fornecedor> mostrarFormularioFornecedor(Fornecedor fornecedor) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("/com/example/main/application/auth/assets/view/formularioFornecedor.fxml"));
            AnchorPane customPane = loader.load();

            FormularioFornecedorController formController = loader.getController();
            Dialog<Fornecedor> dialog = new Dialog<>();
            dialog.getDialogPane().setContent(customPane);
            dialog.setTitle(fornecedor == null ? "Cadastrar Novo Fornecedor" : "Editar Fornecedor");
            formController.setDialog(dialog);

            if (fornecedor != null) {
                formController.carregarDadosFornecedor(fornecedor);
            }

            return dialog.showAndWait();
        } catch (IOException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO, "Erro gerenciar fornecedor", "Não foi possível abrir a tela.");
            System.err.println("Não foi possível abrir a tela de gerenciar fornecedor: " + e.getMessage());
            return Optional.empty();
        }
    }

    @FXML
    void handeButtonConfig() {

    }

    @FXML
    void handleButtonEstoque() {
        HelloApplication.changeScreen("estoqueView.fxml");
    }

    @FXML
    void handleButtonPrincipal() {
        HelloApplication.changeScreen("visaoGeral.fxml");
    }

    @FXML
    void handleButtonRelatorios() {

    }

    @FXML
    void handleButtonVendas() {
        HelloApplication.changeScreen("vendaView.fxml");
    }

    @FXML
    void handleCadastroFornecedor() {
        Optional<Fornecedor> resultado = mostrarFormularioFornecedor(null); // Passa null porque é um novo
        resultado.ifPresent(novoFornecedor -> {
            fornecedorRN.salvar(novoFornecedor);
            carregarDadosDaTabela(); // Atualiza a tabela com o novo registro
        });
    }

    @FXML
    void handleGerenciarPedidos() {

    }

    @FXML
    void handlePesquisarFornecedor() {

    }

    private void handleEditar(Fornecedor fornecedor) {
        Optional<Fornecedor> resultado = mostrarFormularioFornecedor(fornecedor); // Passa o fornecedor a ser editado
        resultado.ifPresent(fornecedorEditado -> {
            fornecedorRN.salvar(fornecedorEditado);
            carregarDadosDaTabela(); // Atualiza a tabela com os dados modificados
        });
    }

    private void handleDesativar(Fornecedor fornecedor) {
        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION,
                "Tem certeza que deseja desativar o fornecedor '" + fornecedor.getNome() + "'?",
                ButtonType.YES, ButtonType.NO);

        confirmacao.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                fornecedorRN.desativar(fornecedor);
                carregarDadosDaTabela();
            }
        });
    }
    
    private void handlePesquisarFornecedor(String termo) {
        List<Fornecedor> resultadoDaBusca = fornecedorRN.pesquisar(termo);
        atualizarTabela(resultadoDaBusca);
    }

    private void atualizarTabela(List<Fornecedor> fornecedores) {
        listaFornecedor.setItems(FXCollections.observableArrayList(fornecedores));
    }
}