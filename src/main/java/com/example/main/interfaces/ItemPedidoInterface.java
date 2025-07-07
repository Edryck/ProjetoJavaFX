package com.example.main.interfaces;

import com.example.main.model.vo.ItemPedido;

import java.util.List;

public interface ItemPedidoInterface {
    /**
     * Salva um item de um pedido, dentro de uma transação.
     */
    void salvar(ItemPedido item);

    /**
     * Busca todos os itens de um pedido específico.
     */
    List<ItemPedido> buscarPorPedidoId(int idPedido);
}
