package com.example.main.model.vo;

import java.math.BigDecimal;

public class ItemVenda {
    private int id;
    private int idVenda;
    private Produto produto; // O objeto Produto completo
    private int quantidade;
    private BigDecimal precoUnitario;

    public ItemVenda() {
    }

    public ItemVenda(int id, int idVenda, Produto produto, int quantidade, BigDecimal precoUnitario) {
        this.id = id;
        this.idVenda = idVenda;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(int idVenda) {
        this.idVenda = idVenda;
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

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }
}
