package com.example.main.model.rn;

import com.example.main.exceptions.RNException;
import com.example.main.model.dao.ProdutosDAO;
import com.example.main.model.vo.Produto;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoRN {
    private final ProdutosDAO produtoDAO = new ProdutosDAO();

    public boolean precoRN(BigDecimal precoCusto, BigDecimal precoVenda) {
        return precoVenda.compareTo(precoCusto) > 0;
    }

    public boolean quantInvalida(int quantidade) {
        return quantidade > 0;
    }

    public boolean codigoJaExiste(String codigo) {
        ProdutosDAO produtoDAO = new ProdutosDAO();
        return produtoDAO.codigoJaExiste(codigo);
    }

    public void cadastrar(Produto produto) {
        if (produtoDAO.codigoJaExiste(produto.getIdProduto())) {
            throw new RNException("Este código de produto já está cadastrado!");
        }
        if (produto.getQuantidade() <= 0) {
            throw new RNException("A quantidade deve ser maior que zero!");
        }
        if (produto.getPrecoCusto().compareTo(produto.getPrecoVenda()) >= 0) {
            throw new RNException("O preço de venda deve ser maior que o preço de custo!");
        }
        if (produto.getMarca() == null || produto.getMarca().isBlank()) {
            throw new RNException("A marca do produto é obrigatória.");
        }
        produtoDAO.cadastrar(produto);
    }

    public List<Produto> pesquisar(String termo) {
        if (termo == null || termo.isBlank()) {
            return produtoDAO.listarProdutos();
        }
        return produtoDAO.pesquisar(termo);
    }

    public List<Produto> listarProdutos() {
        return produtoDAO.listarProdutos();
    }

    public List<Produto> listarProdutoBE() { return produtoDAO.listarProdutoBE();}

    public BigDecimal valorEstoque() { return produtoDAO.valorEstoque();}

    public Integer quantProdutos() { return produtoDAO.quantProdutosEst(); }
}
