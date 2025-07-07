package com.example.main.model.dao;

import com.example.main.connection.ConnectionFactory;
import com.example.main.enums.StatusVenda;
import com.example.main.enums.TipoAlerta;
import com.example.main.exceptions.DAOException;
import com.example.main.interfaces.VendaInterface;
import com.example.main.model.vo.ItemVenda;
import com.example.main.model.vo.Produto;
import com.example.main.model.vo.Venda;
import com.example.main.util.Alerta;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO implements VendaInterface {
    @Override
    public int salvar(Venda venda) {
        String sql = "INSERT INTO venda (nomeCliente, dataVenda, valorTotal, formaPagamento, statusVenda, quantidadeParcelas) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, venda.getNomeCliente());
            ps.setTimestamp(2, Timestamp.valueOf(venda.getDataVenda()));
            ps.setBigDecimal(3, venda.getValorTotal());
            ps.setString(4, venda.getFormaPagamento());
            ps.setString(5, venda.getStatus().toString());
            ps.setObject(6, venda.getQuantidadeParcelas()); // Para aceitar valor nulo

            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Falha ao obter o id da venda, nenhum id foi gerado.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao salvar venda, VendaDAO: " + e.getMessage());
            throw new DAOException("Erro ao salvar venda: VendaDAO");
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
        String sql = "SELECT * FROM venda";
        List<Venda> listaVendas = new ArrayList<>();

        try(Connection connection = ConnectionFactory.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Venda venda = new Venda();

                venda.setIdVenda(rs.getInt("idVenda"));
                venda.setNomeCliente(rs.getString("nomeCliente"));
                venda.setDataVenda(rs.getTimestamp("dataVenda").toLocalDateTime());
                venda.setFormaPagamento(rs.getString("formaPagamento"));
                venda.setValorTotal(rs.getBigDecimal("valorTotal"));
                venda.setStatus(StatusVenda.valueOf(rs.getString("statusVenda")));
                venda.setQuantidadeParcelas(rs.getInt("quantidadeParcelas"));

                listaVendas.add(venda);
            }
        } catch (SQLException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso aos dados", "Não foi possível listar os produtos. Tente novamente.");
            System.err.println("Erro: Listar produtos. " + e.getMessage());
            throw new DAOException("Não foi possível listar os produtos.");
        }
        return listaVendas;
    }

    public List<Venda> listarPendentes() {
        String sql = "SELECT * FROM venda WHERE statusVenda = 'PENDENTE'";
        List<Venda> listaVendas = new ArrayList<>();

        try(Connection connection = ConnectionFactory.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Venda venda = new Venda();

                venda.setIdVenda(rs.getInt("idVenda"));
                venda.setNomeCliente(rs.getString("nomeCliente"));
                venda.setDataVenda(rs.getTimestamp("dataVenda").toLocalDateTime());
                venda.setFormaPagamento(rs.getString("formaPagamento"));
                venda.setValorTotal(rs.getBigDecimal("valorTotal"));
                venda.setStatus(StatusVenda.valueOf(rs.getString("statusVenda")));
                venda.setQuantidadeParcelas(rs.getInt("quantidadeParcelas"));

                listaVendas.add(venda);
            }
        } catch (SQLException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso aos dados", "Não foi possível listar os produtos. Tente novamente.");
            System.err.println("Erro: Listar produtos. " + e.getMessage());
            throw new DAOException("Não foi possível listar os produtos.");
        }
        return listaVendas;
    }

    public List<Venda> listarFinalizadas() {
        String sql = "SELECT * FROM venda WHERE statusVenda = 'FINALIZADA'";
        List<Venda> listaVendas = new ArrayList<>();

        try(Connection connection = ConnectionFactory.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Venda venda = new Venda();

                venda.setIdVenda(rs.getInt("idVenda"));
                venda.setNomeCliente(rs.getString("nomeCliente"));
                venda.setDataVenda(rs.getTimestamp("dataVenda").toLocalDateTime());
                venda.setFormaPagamento(rs.getString("formaPagamento"));
                venda.setValorTotal(rs.getBigDecimal("valorTotal"));
                venda.setStatus(StatusVenda.valueOf(rs.getString("statusVenda")));
                venda.setQuantidadeParcelas(rs.getInt("quantidadeParcelas"));

                listaVendas.add(venda);
            }
        } catch (SQLException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso aos dados", "Não foi possível listar os produtos. Tente novamente.");
            System.err.println("Erro: Listar produtos. " + e.getMessage());
            throw new DAOException("Não foi possível listar os produtos.");
        }
        return listaVendas;
    }

    public BigDecimal valorTotalVendas() {
        String sql = "SELECT SUM(valorTotal) FROM venda WHERE statusVenda = 'FINALIZADA'";
        BigDecimal valorTotal = BigDecimal.ZERO;

        try (Connection connection = ConnectionFactory.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                BigDecimal totalDoBanco = rs.getBigDecimal(1);
                if (totalDoBanco != null) {
                    valorTotal = totalDoBanco;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao calcular o valor total de vendas: " + e.getMessage());
            throw new DAOException("Não foi possível calcular o valor total de vendas.");
        }
        return valorTotal;
    }

    @Override
    public List<Venda> buscarPorPeriodo(LocalDate inicio, LocalDate fim) {
        return List.of();
    }

    @Override
    public void atualizarStatus(int id, String novoStatus) {}
}