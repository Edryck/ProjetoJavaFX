package com.example.main.model.vo;

import java.math.BigDecimal;

public class ItemPedido {
    private int idItemPedido;
    private Produto produto;
    private int quantidade;
    private BigDecimal custoUnitarioPedido;

    public ItemPedido() {
    }

    public ItemPedido(int idItemPedido, Produto produto, int quantidade, BigDecimal custoUnitarioPedido) {
        this.idItemPedido = idItemPedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.custoUnitarioPedido = custoUnitarioPedido;
    }

    public int getIdItemPedido() {
        return idItemPedido;
    }

    public void setIdItemPedido(int idItemPedido) {
        this.idItemPedido = idItemPedido;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getCustoUnitarioPedido() {
        return custoUnitarioPedido;
    }

    public void setCustoUnitarioPedido(BigDecimal custoUnitarioPedido) {
        this.custoUnitarioPedido = custoUnitarioPedido;
    }

    public BigDecimal getSubtotal() {
        if (custoUnitarioPedido == null || quantidade <= 0) {
            return BigDecimal.ZERO;
        }
        return custoUnitarioPedido.multiply(new BigDecimal(quantidade));
    }
}
