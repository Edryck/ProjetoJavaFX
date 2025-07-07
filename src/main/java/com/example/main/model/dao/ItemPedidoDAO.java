package com.example.main.model.dao;

import com.example.main.connection.ConnectionFactory;
import com.example.main.exceptions.DAOException;
import com.example.main.interfaces.ItemPedidoInterface;
import com.example.main.model.vo.ItemPedido;
import com.example.main.model.vo.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAO implements ItemPedidoInterface {
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    /**
     * Salva um item de pedido no banco. Ele DEVE receber a conexão da RN
     * para fazer parte da mesma transação da venda principal.
     */
    @Override
    public void salvar(ItemPedido item) {
        String sql = "INSERT INTO itensPedido (pedidoId, idProduto, quantidade, precoPedido) VALUES (?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, item.getIdPedido());
            ps.setString(2, item.getProduto().getIdProduto()); // Pega o ID do objeto Produto
            ps.setInt(3, item.getQuantidade());
            ps.setBigDecimal(4, item.getCustoUnitarioPedido());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao salvar o pedido!");
        }
    }

    /**
     * Busca todos os itens de um pedido específico, usando o ID do pedido.
     */
    @Override
    public List<ItemPedido> buscarPorPedidoId(int idPedido) {
        String sql = "SELECT * FROM itens_pedido WHERE pedidoId = ?";
        List<ItemPedido> itens = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPedido);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Mapeia a linha para um objeto ItemPedido
                    ItemPedido item = new ItemPedido();
                    item.setIdPedido(rs.getInt("pedidoId"));
                    item.setQuantidade(rs.getInt("quantidade"));
                    item.setCustoUnitarioPedido(rs.getBigDecimal("precoPedido"));

                    // Busca o objeto Produto completo usando o produtoDAO
                    String produtoId = rs.getString("idProduto");
                    Produto produto = produtoDAO.pesquisarCod(produtoId);
                    item.setProduto(produto);

                    itens.add(item);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar itens do pedido.");
        }
        return itens;
    }
}
