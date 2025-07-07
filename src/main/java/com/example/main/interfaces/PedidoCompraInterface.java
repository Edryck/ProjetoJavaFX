package com.example.main.interfaces;

import com.example.main.model.vo.PedidoCompra;

import java.util.List;

public interface PedidoCompraInterface {
    int salvar(PedidoCompra pedido);
    void atualizar(PedidoCompra pedido);
    void atualizarStatus(int idPedido, String novoStatus);
    PedidoCompra buscarPorId(int idPedido);
    List<PedidoCompra> listarTodos();
    List<PedidoCompra> listarPorStatus(String status);
    List<PedidoCompra> listarPorFornecedor(int idFornecedor);
}
