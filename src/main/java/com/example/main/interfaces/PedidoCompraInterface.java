package com.example.main.interfaces;

import com.example.main.enums.StatusCompra;
import com.example.main.model.vo.PedidoCompra;

import java.sql.SQLException;
import java.util.List;

public interface PedidoCompraInterface {
    /**
     * Salva um novo pedido de compra.
     * @param pedido Pedido que irá ser salvo.
     * @return Retorna o 'ID' do pedido.
     */
    int salvar(PedidoCompra pedido);

    /**
     * Atualiza um pedido com o status PENDENTE.
     * @param pedido Pedido que será atualizado.
     */
    void atualizar(PedidoCompra pedido);

    /**
     * Atualiza o status de um pedido.
     * @param idPedido Código do pedido.
     * @param novoStatus Novo status do pedido.
     */
    void atualizarStatus(int idPedido, StatusCompra novoStatus);

    /**
     * Busca um pedido pelo código dele.
     * @param idPedido Código que será buscado.
     * @return Se for encontrado retorna o pedido, caso contrário retorna nulo.
     */
    PedidoCompra buscarPorId(int idPedido);

    /**
     * Lista todos os pedidos.
     * @return Retorna uma lista contendo todos os pedidos.
     */
    List<PedidoCompra> listarTodos();

    /**
     * Lista pedidos pelo status específico.
     * @param status Status dos pedidos que quer listar.
     * @return Retorna uma lista dos pedidos com o status específico.
     */
    List<PedidoCompra> listarPorStatus(String status);

    /**
     * Lista pedidos pelo fornecedor.
     * @param idFornecedor Código do fornecedor.
     * @return Retorna uma lista de todos os pedidos feito para o fornecedor especificado.
     */
    List<PedidoCompra> listarPorFornecedor(int idFornecedor);
}
