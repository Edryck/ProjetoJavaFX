package com.example.main.model.dao;

import com.example.main.connection.ConnectionFactory;
import com.example.main.exceptions.DAOException;

import java.sql.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class RelatorioDAO {
    /**
     * Busca o valor total de vendas agrupado por categoria de produto em um determinado período.
     * @param inicio A data inicial do período.
     * @param fim A data final do período.
     * @return Um Mapa onde a chave é o nome da categoria (String) e o valor é o total vendido (Double).
     */
    public Map<String, Double> buscarVendasPorCategoria(LocalDate inicio, LocalDate fim) {
        // Esta query junta as tabelas de itens_venda e produtos para agrupar pela categoria.
        String sql = "SELECT p.categoria, SUM(iv.quantidade * iv.precoUnitario) as total_vendido " +
                "FROM itemvenda iv " +
                "JOIN produto p ON iv.idProduto = p.idProduto " +
                "JOIN venda v ON iv.idVenda = v.idVenda " +
                "WHERE v.dataVenda BETWEEN ? AND ? " +
                "GROUP BY p.categoria " +
                "ORDER BY total_vendido DESC";

        Map<String, Double> vendasPorCategoria = new HashMap<>();

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setTimestamp(1, Timestamp.valueOf(inicio.atStartOfDay()));
            ps.setTimestamp(2, Timestamp.valueOf(fim.plusDays(1).atStartOfDay()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String categoria = rs.getString("categoria");
                    double totalVendido = rs.getDouble("total_vendido");
                    vendasPorCategoria.put(categoria, totalVendido);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar vendas por categoria.");
        }
        return vendasPorCategoria;
    }
}
