package com.example.main.model.rn;

import com.example.main.enums.TipoAlerta;
import com.example.main.exceptions.DAOException;
import com.example.main.exceptions.RNException;
import com.example.main.model.dao.ProdutoDAO;
import com.example.main.model.vo.Produto;
import com.example.main.util.Alerta;

import java.math.BigDecimal;
import java.util.List;

public class ProdutoRN {
    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    public void cadastrar(Produto produto) {
        if (produtoDAO.codigoJaExiste(produto.getIdProduto())) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO, "Produto já cadastrado", "Este código informado já tem cadastro no sistema!");
            throw new RNException("Este código de produto já está cadastrado!");
        }
        if (produto.getQuantidade() <= 0) {
            throw new RNException("A quantidade deve ser maior que zero!");
        }
        if (produto.getPrecoCusto().compareTo(produto.getPrecoVenda()) >= 0 || produto.getPrecoCusto() == null) {
            throw new RNException("O preço de venda deve ser maior que o preço de custo!");
        }
        if (produto.getMarca() == null || produto.getMarca().isBlank() ||
            produto.getCategoria() == null || produto.getCategoria().isBlank() ||
            produto.getFornecedor() == null || produto.getFornecedor().isBlank() ||
            produto.getDescricao() == null || produto.getDescricao().isBlank()) {
            throw new RNException("Dados incompletos.");
        }
        produtoDAO.cadastrar(produto);
    }

    public void atualizarProd(Produto produto) {

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

    public BigDecimal validarPrecos(String preco) {
        BigDecimal precoValidado;

        try {
            precoValidado = new BigDecimal(preco.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new RNException("Formato de preço de venda inválido, validação de preços ProdRN");
        }
        return precoValidado;
    }

    public int validarQuant(String quant) {
        int quantidade;

        try {
            quantidade = Integer.parseInt(quant);
            if (quantidade <= 0) {
                Alerta.mostrarAlerta(TipoAlerta.ATENCAO, "Quantidade inválida", "Quantidade informado deve ser positiva.");
                throw new RNException("Quantidade inválida");
            }
        } catch (NumberFormatException e) {
            throw new RNException("Quantidade informada inválida, validação de quantidade  ProdRN");
        }
        return quantidade;
    }

    public Produto pesquisarCod(String codigo) {
        try {
            produtoDAO.pesquisarCod(codigo);
        } catch (DAOException e) {
            System.err.println("Não foi possível realizar pesquisa!");
            e.printStackTrace();
            throw new RNException("Erro ao acessar o banco de dados: pesquisar produto por código.");
        }
        return produtoDAO.pesquisarCod(codigo);
    }
}
