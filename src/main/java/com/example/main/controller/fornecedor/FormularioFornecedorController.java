package com.example.main.controller.fornecedor;

import com.example.main.enums.StatusFornecedor;
import com.example.main.enums.TipoAlerta;
import com.example.main.exceptions.RNException;
import com.example.main.model.vo.Fornecedor;
import com.example.main.util.Alerta;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

public class FormularioFornecedorController {
    private Dialog<Fornecedor> dialog;
    private Fornecedor fornecedorParaEditar;

    public void setDialog(Dialog<Fornecedor> dialog) {
        this.dialog = dialog;
    }

    @FXML
    private Button buttonSalvar;

    @FXML
    private FontIcon buttonVoltarTela;

    @FXML
    private TextField emailFornecedorField;

    @FXML
    private Button buttonDeletar;

    @FXML
    private TextField nomeFornecedorField;

    @FXML
    private TextField telefoneFornecedorField;

    public void carregarDadosFornecedor(Fornecedor fornecedor) {
        this.fornecedorParaEditar = fornecedor;

        nomeFornecedorField.setText(fornecedor.getNome());
        emailFornecedorField.setText(fornecedor.getEmail());
        telefoneFornecedorField.setText(fornecedor.getTelefone());

        buttonDeletar.setVisible(true);
    }

    @FXML
    void handleButtonSalvar() {
        try {
            if (nomeFornecedorField.getText() == null || nomeFornecedorField.getText().isBlank()) {
                Alerta.mostrarAlerta(TipoAlerta.ATENCAO, "Nome inválido!", "Deve informar o nome do fornecedor!");
                throw new RNException("O nome do fornecedor é obrigatório.");
            }

            if (this.fornecedorParaEditar == null) {
                this.fornecedorParaEditar = new Fornecedor();
                this.fornecedorParaEditar.setStatus(StatusFornecedor.ATIVO); // Novo fornecedor sempre é ativo
            }

            fornecedorParaEditar.setNome(nomeFornecedorField.getText());
            fornecedorParaEditar.setEmail(emailFornecedorField.getText());
            fornecedorParaEditar.setTelefone(telefoneFornecedorField.getText());

            dialog.setResult(fornecedorParaEditar);
            dialog.close();

        } catch (RNException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO, "Erro de Validação", e.getMessage());
        }
    }

    @FXML
    void handleButtonDeletar() {

    }

    @FXML
    void handleVoltar() {
        if (dialog != null) {
            dialog.close();
        } else {
            Stage stage = (Stage) nomeFornecedorField.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void initialize() {
        buttonDeletar.setVisible(false);
    }
}
