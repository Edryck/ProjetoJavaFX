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

public class PedidoCompraRN {
    private final PedidoCompraDAO pedidoDAO = new PedidoCompraDAO();
    private final ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    /**
     * Salvamento de um novo pedido de compra completo, usando uma transação.
     */
    public void registrarNovoPedido(PedidoCompra pedido) {
        if (pedido.getFornecedor() == null) {
            throw new RNException("Um fornecedor deve ser selecionado para o pedido.");
        }
        if (pedido.getItens().isEmpty()) {
            throw new RNException("O pedido deve conter pelo menos um item.");
        }

        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();
            connection.setAutoCommit(false); // 1. INICIA A TRANSAÇÃO

            // Define o status inicial do pedido
            pedido.setStatus(StatusCompra.PENDENTE);

            // 2. Salva o "cabeçalho" do pedido e obtém o ID gerado
            int pedidoId = pedidoDAO.salvar(pedido);

            for (ItemPedido item : pedido.getItens()) {
                item.setIdPedido(pedidoId);
                itemPedidoDAO.salvar(item);
            }

            connection.commit();

        } catch (SQLException e) {
            try {
                if (connection != null) connection.rollback();
            } catch (SQLException ex) {
                System.err.println("Erro crítico ao tentar fazer rollback da transação.");
                ex.printStackTrace();
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
     * Recebimento de um pedido, atualizando o status do pedido e o estoque dos produtos.
     */
    public void receberPedido(PedidoCompra pedido) {
        if (pedido.getStatus() != StatusCompra.PENDENTE) {
            throw new RNException("Apenas pedidos com status 'PENDENTE' podem ser recebidos.");
        }

        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();
            connection.setAutoCommit(false);

            List<ItemPedido> itensDoPedido = itemPedidoDAO.buscarPorPedidoId(pedido.getIdPedido());

            for (ItemPedido item : itensDoPedido) {
                produtoDAO.atualizarQuant(item.getProduto().getIdProduto(), item.getQuantidade());
            }

            pedidoDAO.atualizarStatus(pedido.getIdPedido(), StatusCompra.FINALIZADO);

            connection.commit();

        } catch (SQLException e) {
            try { if (connection != null) connection.rollback(); } catch (SQLException ex) { e.printStackTrace(); }
            throw new DAOException("Erro ao dar entrada no estoque do pedido.");
        } finally {
            // Bloco finally para fechar a conexão
        }
    }

    // Demais métodos de listagem
    public List<PedidoCompra> listarTodosPedidos() {
        return pedidoDAO.listarTodos();
    }
}
