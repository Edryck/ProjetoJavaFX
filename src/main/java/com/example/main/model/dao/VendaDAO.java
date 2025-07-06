package com.example.main.model.dao;

import com.example.main.connection.ConnectionFactory;
import com.example.main.exceptions.DAOException;
import com.example.main.interfaces.VendaInterface;
import com.example.main.model.vo.ItemVenda;
import com.example.main.model.vo.Venda;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class VendaDAO implements VendaInterface {
    @Override
    public int salvar(Venda venda) {
        String sql = "INSERT INTO venda INTO (valorTotal, formaPagamento) VALUES (?, ?)";

        try (Connection connection = ConnectionFactory.getConnection()) {
            assert connection != null;
            try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

                ps.setBigDecimal(1, venda.getValorTotal());
                ps.setString(2, venda.getFormaPagamento());

                ps.executeUpdate();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    } else {
                        throw new SQLException("Falha ao obter o id da venda, nenhum id foi gerado.");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro: Salvar venda. " + e.getMessage());
            throw new DAOException("Falha ao salvar Venda.");
        }
    }

    @Override
    public Venda buscarPorId(int id) {
        String sql = "SELECT * FROM venda WHERE idVenda = ?";
        Venda venda = null;
        ItemVendaDAO itemVendaDAO = new ItemVendaDAO();

        try (Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

                ps.setInt(1, id);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        venda = new Venda();
                        venda.setIdVenda(rs.getInt("idVenda"));
                        Timestamp timestamp = rs.getTimestamp("data_venda");
                        if (timestamp != null) {
                            venda.setDataVenda(timestamp.toLocalDateTime());
                        }
                        venda.setValorTotal(rs.getBigDecimal("valorTotal"));
                        venda.setFormaPagamento(rs.getString("formaPagamento"));

                        List<ItemVenda> itens = itemVendaDAO.buscarItensPorVendaId(venda.getIdVenda());
                        venda.setItens(itens);
                    }
                }
            }catch (SQLException e) {
            System.err.println("Erro: Buscar venda por id. " + e.getMessage());
            throw new DAOException("Não foi possível realizar pesquisa de venda.");
        }
        return venda;
    }

    @Override
    public List<Venda> listarTodasVendas() {
        return List.of();
    }

    @Override
    public List<Venda> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return List.of();
    }

    @Override
    public void atualizarStatus(int id, String novoStatus) {}
}