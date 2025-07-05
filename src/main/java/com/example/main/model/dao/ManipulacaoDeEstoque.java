package com.example.main.model.dao;

import com.example.main.model.vo.Produto;

import java.math.BigDecimal;
import java.util.List;

public interface ManipulacaoDeEstoque {
    void cadastrar (Produto produto);
    void editar (Produto produto);
    void atualizarDescricao (String idProduto, String descricao);
    void atualizarQuant (String idProduto, int quantidade);
    void desativar (String idProduto, boolean ativo);
    List<Produto> listarProdutos(); // Obviamente apenas os ativos
    List<Produto> listarProdutoBE();
    List<Produto> pesquisar(String busca);
    BigDecimal valorEstoque();
}
