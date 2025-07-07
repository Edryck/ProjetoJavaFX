package com.example.main.controller;

import com.example.main.HelloApplication;
import com.example.main.model.rn.ProdutoRN;
import com.example.main.model.rn.RelatorioRN;
import com.example.main.model.rn.VisaoGeralRN;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.util.Map;
import java.util.ResourceBundle;

public class VisaoGeralController implements Initializable {
    // --- Camadas de Regra de Negócio ---
    private final VisaoGeralRN visaoGeralRN = new VisaoGeralRN();
    private final ProdutoRN produtoRN = new ProdutoRN();
    private final RelatorioRN relatorioRN = new RelatorioRN();

    // --- Componentes FXML do Dashboard ---
    @FXML private Label vendasHojeValorLabel;
    @FXML private Label vendasHojeQtdLabel;
    @FXML private Label estoqueBaixoLabel;
    @FXML private Label ticketMedioLabel;
    @FXML private PieChart graficoCategorias;

    // --- Componentes FXML do Menu de Navegação ---
    @FXML private Pane buttonConfig;
    @FXML private Pane buttonFornecedores;
    @FXML private Pane buttonRelatorios;
    @FXML private Pane buttonVendas;
    @FXML private Pane buttonEstoque;
    @FXML private Pane buttonVisaoGeral; // Adicionado para consistência

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Carrega os dados do dashboard assim que a tela é aberta.
        carregarDadosDashboard();
    }

    private void carregarDadosDashboard() {
        // Cria uma tarefa para rodar em segundo plano e não travar a tela
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                // Busca os dados em segundo plano usando as camadas de RN
                final Map<String, Object> resumoVendas = visaoGeralRN.getResumoVendasHoje();
                // CORREÇÃO: Usando o método correto que retorna a contagem de estoque baixo
                final int qtdEstoqueBaixo = produtoRN.listarProdutoBE().size();
                final Map<String, Double> dadosGrafico = relatorioRN.buscarVendasPorCategoria(
                        LocalDate.now().minusDays(30),
                        LocalDate.now()
                );

                // Quando os dados estiverem prontos, atualiza a interface gráfica
                Platform.runLater(() -> {
                    atualizarKpisVendas(resumoVendas);
                    estoqueBaixoLabel.setText(String.valueOf(qtdEstoqueBaixo));

                    // Popula o gráfico de pizza
                    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
                    for (Map.Entry<String, Double> entry : dadosGrafico.entrySet()) {
                        pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
                    }
                    graficoCategorias.setData(pieChartData);
                });
                return null;
            }
        };
        // Inicia a tarefa em uma nova thread
        new Thread(task).start();
    }

    private void atualizarKpisVendas(Map<String, Object> resumoVendas) {
        BigDecimal totalVendido = (BigDecimal) resumoVendas.get("total");
        int qtdVendas = (int) resumoVendas.get("quantidade");

        vendasHojeValorLabel.setText(String.format("R$ %.2f", totalVendido));
        vendasHojeQtdLabel.setText(String.valueOf(qtdVendas));

        // Calcula o Ticket Médio
        if (qtdVendas > 0) {
            BigDecimal ticketMedioCalculado = totalVendido.divide(new BigDecimal(qtdVendas), 2, RoundingMode.HALF_UP);
            ticketMedioLabel.setText(String.format("R$ %.2f", ticketMedioCalculado));
        } else {
            ticketMedioLabel.setText("R$ 0,00");
        }
    }

    // --- Métodos de Navegação ---
    @FXML void handleButtonEstoque() { HelloApplication.changeScreen("estoqueView.fxml"); }
    @FXML void handleButtonVendas() { HelloApplication.changeScreen("vendaView.fxml"); }
    @FXML void handleButtonFornecedores() { HelloApplication.changeScreen("fornecedoresView.fxml"); }
    @FXML void handleButtonRelatorio() { HelloApplication.changeScreen("relatorioView.fxml"); }
}