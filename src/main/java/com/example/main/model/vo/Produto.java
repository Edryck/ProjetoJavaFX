package com.example.main.model.vo;

import java.math.BigDecimal;

public class Produto {
    private String idProduto;
    private String marca;
    private String descricao;
    private int quantidade;
    private String categoria;
    private String fornecedor;
    private BigDecimal precoCusto;
    private BigDecimal precoVenda;
    private Boolean ativo;
    private String imagem;

    public Produto() {}

    public Produto(String idCodigo, String marca, String descricaoProduto, int quantidade, String categoriaProduto, String fornecedorProduto, BigDecimal precoCusto, BigDecimal precoVenda, String imagem) {
        this.idProduto = idCodigo;
        this.marca = marca;
        this.descricao = descricaoProduto;
        this.quantidade = quantidade;
        this.categoria = categoriaProduto;
        this.fornecedor = fornecedorProduto;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.imagem = imagem;
    }

    public String getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(String idProduto) {
        this.idProduto = idProduto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
    }

    public BigDecimal getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(BigDecimal precoCusto) {
        this.precoCusto = precoCusto;
    }

    public BigDecimal getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(BigDecimal precoVenda) {
        this.precoVenda = precoVenda;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

}