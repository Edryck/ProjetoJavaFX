package com.example.main.model.rn;


import com.example.main.model.dao.ProdutoDAO;
import com.example.main.model.dao.VendaDAO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

public class VisaoGeralRN {
    private final VendaRN vendaRN = new VendaRN();
    private final VendaDAO vendaDAO = new VendaDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    /**
     * Calcula o Ticket Médio de todas as vendas finalizadas.
     * @return O valor do ticket médio, ou BigDecimal.ZERO se não houver vendas.
     */
    public BigDecimal getTicketMedio() {
        // 1. Pega os totais da camada de negócio de Vendas
        int totalDeVendas = vendaRN.totalVendas();
        BigDecimal valorTotalVendas = vendaRN.valorTotalVendas();

        // 2. VERIFICAÇÃO CRÍTICA: Se não houver vendas, retorna 0 para evitar divisão por zero.
        if (totalDeVendas == 0) {
            return BigDecimal.ZERO;
        }

        // 3. Se houver vendas, calcula a divisão de forma segura.
        // Usamos 2 casas decimais e um modo de arredondamento padrão.
        return valorTotalVendas.divide(new BigDecimal(totalDeVendas), 2, RoundingMode.HALF_UP);
    }

    /**
     * Busca o resumo de vendas do dia atual.
     * (Este método estava no seu controller, mas o lugar ideal para ele é aqui)
     * @return Um mapa com a quantidade e o valor total das vendas de hoje.
     */
    public Map<String, Object> getResumoVendasHoje() {
        // Supondo que você criou o método getResumoVendasHoje() no seu VendaDAO
        return vendaDAO.getResumoVendasDeHoje(); // Você precisará criar VendaDAO e este método nele
    }

    /**
     * Busca a contagem de produtos com estoque baixo.
     * @return O número de produtos com estoque baixo.
     */
    public int getContagemEstoqueBaixo() {
        // Supondo que você criou o método contarEstoqueBaixo() no seu ProdutoDAO
        return produtoDAO.listarProdutoBE().size(); // Você precisará criar ProdutoDAO e este método nele
    }
}
