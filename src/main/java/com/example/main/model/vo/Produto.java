package main.model.vo;

public class Produto {
    private Integer idCodigo;
    private String marcaProduto;
    private String descricaoProduto;
    private Integer quantidade;
    private String categoriaProduto;
    private String FornecedorProduto;
    private Double precoCusto;
    private Double precoVenda;

    public Produto(Integer idCodigo, String marcaProduto, String descricaoProduto, String categoriaProduto, Integer quant, String fornecedorProduto, Double precoCusto, Double precoVenda) {
        this.idCodigo = idCodigo;
        this.marcaProduto = marcaProduto;
        this.descricaoProduto = descricaoProduto;
        this.quantidade = quant;
        this.categoriaProduto = categoriaProduto;
        this.FornecedorProduto = fornecedorProduto;
        this.precoCusto = precoCusto;
        this.precoVenda = precoVenda;
    }

    public int getIdCodigo() {
        return idCodigo;
    }

    public void setIdCodigo(Integer idCodigo) {
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

    public int getQuantidade() {
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
        return FornecedorProduto;
    }

    public void setFornecedorProduto(String fornecedorProduto) {
        FornecedorProduto = fornecedorProduto;
    }

    public double getPrecoCusto() {
        return precoCusto;
    }

    public void setPrecoCusto(Double precoCusto) {
        this.precoCusto = precoCusto;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(Double precoVenda) {
        this.precoVenda = precoVenda;
    }
}
