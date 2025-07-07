package com.example.main.model.rn;

import com.example.main.connection.ConnectionFactory;
import com.example.main.enums.StatusCompra;
import com.example.main.exceptions.DAOException;
import com.example.main.exceptions.RNException;
import com.example.main.model.dao.ItemPedidoDAO;
import com.example.main.model.dao.PedidoCompraDAO;
import com.example.main.model.dao.ProdutoDAO;
import com.example.main.model.vo.ItemPedido;
import com.example.main.model.vo.PedidoCompra;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ItemPedidoRN {
    private final PedidoCompraDAO pedidoDAO = new PedidoCompraDAO();
    private final ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO(); // Precisaremos dele para dar entrada no estoque

    /**
     * Orquestra o salvamento de um novo pedido de compra completo, usando uma transação.
     */
    public void registrarNovoPedido(PedidoCompra pedido) {
        // --- VALIDAÇÕES DE NEGÓCIO ---
        if (pedido.getFornecedor() == null) {
            throw new RNException("Um fornecedor deve ser selecionado.");
        }
        if (pedido.getItens() == null || pedido.getItens().isEmpty()) {
            throw new RNException("O pedido deve conter pelo menos um item.");
        }
        // Outras validações...

        Connection connection = null;
        try {connection = ConnectionFactory.getConnection();
            connection.setAutoCommit(false); // Inicia a transação

            // Salva o "cabeçalho" do pedido e obtém o ID gerado
            int pedidoId = pedidoDAO.salvar(pedido);

            // Associa cada item ao ID do pedido e salva
            for (ItemPedido item : pedido.getItens()) {
                item.setIdPedido(pedidoId);
                itemPedidoDAO.salvar(item);
            }

            connection.commit(); // Confirma todas as operações no banco

        } catch (SQLException e) {
            try {
                if (connection != null) connection.rollback(); // Desfaz tudo em caso de erro
            } catch (SQLException ex) {
                e.printStackTrace(); // Erro ao tentar fazer o rollback
            }
            throw new DAOException("Erro ao salvar o pedido no banco de dados.");
        } finally {
            if (connection != null) {
                try {
                    connection.setAutoCommit(true);
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Orquestra o recebimento de um pedido, atualizando o status e o estoque.
     */
    public void receberPedido(PedidoCompra pedido) {
        if (pedido == null || pedido.getStatus() != StatusCompra.PENDENTE) {
            throw new RNException("Apenas pedidos pendentes podem ser recebidos.");
        }

        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();
            connection.setAutoCommit(false);

            // 1. Busca os itens do pedido no banco
            List<ItemPedido> itensRecebidos = itemPedidoDAO.buscarPorPedidoId(pedido.getIdPedido());

            // 2. Para cada item recebido, dá entrada no estoque
            for (ItemPedido item : itensRecebidos) {
                produtoDAO.atualizarQuant(item.getProduto().getIdProduto(), item.getQuantidade());
            }

            // 3. Atualiza o status do pedido para FINALIZADO
            pedidoDAO.atualizarStatus(pedido.getIdPedido(), StatusCompra.FINALIZADO);

            connection.commit();

        } catch (SQLException e) {
            try { if (connection != null) connection.rollback(); } catch (SQLException ex) { e.printStackTrace(); }
            throw new DAOException("Erro ao dar entrada no estoque.");
        } finally {

        }
    }

    // Métodos simples que apenas repassam a chamada para o DAO
    public List<PedidoCompra> listarTodos() {
        return pedidoDAO.listarTodos();
    }
}
