package com.example.main.model.vo;

import java.math.BigDecimal;

public class ItemPedido {
    private int idPedido;
    private Produto produto;
    private int quantidade;
    private BigDecimal custoUnitarioPedido;

    public ItemPedido() {
    }

    public ItemPedido(int idPedido, Produto produto, int quantidade, BigDecimal custoUnitarioPedido) {
        this.idPedido = idPedido;
        this.produto = produto;
        this.quantidade = quantidade;
        this.custoUnitarioPedido = custoUnitarioPedido;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
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
