package com.example.main.model.vo;

public class Produto {
    private String idCodigo;
    private String marcaProduto;
    private String descricaoProduto;
    private Integer quantidade;
    private String categoriaProduto;
    private String fornecedorProduto;
    private Double precoCusto;
    private Double precoVenda;
    private Boolean ativo;
    private String imagem;

    public Produto() {}

    public Produto(String idCodigo, String marcaProduto, String descricaoProduto, Integer quantidade, String categoriaProduto, String fornecedorProduto, Double precoCusto, Double precoVenda, String imagem) {
        this.idCodigo = idCodigo;
        this.marcaProduto = marcaProduto;
        this.descricaoProduto = descricaoProduto;
        this.quantidade = quantidade;
        this.categoriaProduto = categoriaProduto;
        this.fornecedorProduto = fornecedorProduto;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
        this.imagem = imagem;
    }

    public String getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(String idCodigo) {
        this.idCodigo = idCodigo;
    }

    public String getMarcaProduto() {
        return marcaProduto;
    }

    public void setMarcaProduto(String marcaProduto) {
        this.marcaProduto = marcaProduto;
    }

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public String getCategoriaProduto() {
        return categoriaProduto;
    }

    public void setCategoriaProduto(String categoriaProduto) {
        this.categoriaProduto = categoriaProduto;
    }

    public String getFornecedorProduto() {
        return fornecedorProduto;
    }

    public void setFornecedorProduto(String fornecedorProduto) {
        this.fornecedorProduto = fornecedorProduto;
    }

    public Double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(Double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public Double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
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