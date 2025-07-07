package com.example.main.model.dao;

import com.example.main.connection.ConnectionFactory;
import com.example.main.enums.StatusCompra;
import com.example.main.exceptions.DAOException;
import com.example.main.interfaces.PedidoCompraInterface;
import com.example.main.model.vo.Fornecedor;
import com.example.main.model.vo.PedidoCompra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoCompraDAO implements PedidoCompraInterface {
    private final FornecedorDAO fornecedorDAO = new FornecedorDAO();

    @Override
    public int salvar(PedidoCompra pedido) {
        String sql = "INSERT INTO pedidos_compra (idFornecedor, dataPedido, dataEntregPrevista, valorTotalNota, status) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, pedido.getFornecedor().getIdFornecedor());
            ps.setDate(2, Date.valueOf(pedido.getDataPedido()));
            ps.setDate(3, Date.valueOf(pedido.getDataEntregaPrevista()));
            ps.setBigDecimal(4, pedido.getValorTotalNota());
            ps.setString(5, pedido.getStatus().toString());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Falha ao salvar pedido, nenhum ID foi gerado.");
        }
        return pedido.getIdPedido();
    }

    @Override
    public void atualizar(PedidoCompra pedido) {
        String sql = "UPDATE pedidos_compra SET fornecedor_id = ?, data_pedido = ?, data_entrega_prevista = ?, valor_total_nota = ?, status = ? WHERE id_pedido = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pedido.getFornecedor().getIdFornecedor());
            ps.setDate(2, Date.valueOf(pedido.getDataPedido()));
            ps.setDate(3, Date.valueOf(pedido.getDataEntregaPrevista()));
            ps.setBigDecimal(4, pedido.getValorTotalNota());
            ps.setString(5, pedido.getStatus().toString());
            ps.setInt(6, pedido.getIdPedido()); // O ID vai no WHERE

            ps.executeUpdate();
            System.out.println("Pedido ID " + pedido.getIdPedido() + " atualizado com sucesso.");

        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar o pedido de compra.");
        }
    }

    @Override
    public void atualizarStatus(int idPedido, StatusCompra novoStatus) {
        String sql = "UPDATE pedidos_compra SET statusPedido = ? WHERE id_pedido = ?";

        try (Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, novoStatus.toString());
            ps.setInt(2, idPedido);
            ps.executeUpdate();

            System.out.println("Status do pedido ID " + idPedido + " alterado para: " + novoStatus);
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar status do pedido.");
        }
    }

    @Override
    public PedidoCompra buscarPorId(int idPedido) {
        String sql = "SELECT * FROM pedidos_compra WHERE id_pedido = ?";

        try (Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSetParaPedido(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar pedido por ID.");
        }
        return null;
    }

    @Override
    public List<PedidoCompra> listarTodos() {
        String sql = "SELECT * FROM pedidos_compra ORDER BY dataPedido DESC";
        return executarQueryDeListagem(sql, null);
    }

    @Override
    public List<PedidoCompra> listarPorStatus(String status) {
        String sql = "SELECT * FROM pedidos_compra WHERE status = ? ORDER BY dataPedido DESC";
        return executarQueryDeListagem(sql, status);
    }

    @Override
    public List<PedidoCompra> listarPorFornecedor(int idFornecedor) {
        String sql = "SELECT * FROM pedidos_compra WHERE idFornecedor = ? ORDER BY dataPedido DESC";
        List<PedidoCompra> pedidos = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idFornecedor);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(mapearResultSetParaPedido(rs));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao listar pedidos por fornecedor.");
        }
        return pedidos;
    }

    /**
     * Executa uma query de listagem genérica.
     * @param sql A query a ser executada.
     * @param parametro O parâmetro a ser definido na query (pode ser nulo).
     * @return Uma lista de PedidoCompra.
     */
    private List<PedidoCompra> executarQueryDeListagem(String sql, String parametro) {
        List<PedidoCompra> pedidos = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            if (parametro != null) {
                ps.setString(1, parametro);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(mapearResultSetParaPedido(rs));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao listar pedidos.");
        }
        return pedidos;
    }

    /**
     * Mapeia uma linha do ResultSet para um objeto PedidoCompra.
     * Evita repetição de código.
     */
    private PedidoCompra mapearResultSetParaPedido(ResultSet rs) throws SQLException {
        PedidoCompra pedido = new PedidoCompra();
        pedido.setIdPedido(rs.getInt("id_pedido"));

        // Busca o objeto Fornecedor completo usando o FornecedorDAO
        int fornecedorId = rs.getInt("idFornecedor");
        Fornecedor fornecedor = fornecedorDAO.buscarPorId(fornecedorId);
        pedido.setFornecedor(fornecedor);

        pedido.setDataPedido(rs.getDate("dataPedido").toLocalDate());

        Date dataEntrega = rs.getDate("dataEntregPrevista");
        if (dataEntrega != null) {
            pedido.setDataEntregaPrevista(dataEntrega.toLocalDate());
        }

        pedido.setValorTotalNota(rs.getBigDecimal("valorTotalNota"));
        pedido.setStatus(StatusCompra.valueOf(rs.getString("statusPedido")));

        return pedido;
    }
}