package com.example.main.model.rn;

import com.example.main.exceptions.RNException;
import com.example.main.model.dao.RelatorioDAO;

import java.time.LocalDate;
import java.util.Map;

public class RelatorioRN {
    private final RelatorioDAO relatorioDAO = new RelatorioDAO();

    /**
     * Valida as datas e busca os dados de vendas agrupados por categoria.
     *
     * @param inicio A data inicial do período.
     * @param fim A data final do período.
     * @return Um Mapa com Categoria (String) e o Valor Total Vendido (Double).
     * @throws RNException se as datas forem inválidas.
     */
    public Map<String, Double> buscarVendasPorCategoria(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null) {
            throw new RNException("As datas de início e fim são obrigatórias.");
        }
        if (inicio.isAfter(fim)) {
            throw new RNException("A data de início não pode ser posterior à data de fim.");
        }

        return relatorioDAO.buscarVendasPorCategoria(inicio, fim);
    }
}
