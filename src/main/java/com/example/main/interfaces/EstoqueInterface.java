package com.example.main.interfaces;

import com.example.main.model.vo.Produto;

import java.math.BigDecimal;
import java.util.List;

public interface EstoqueInterface {
    /**
     * Este método é utilizado para cadastrar produtos no banco de dados, caso seja o cadastro deste primeiro
     * deverá ser usado está função para cadastrar, caso contrário, deverá utilizar o método para atualizar produto.
     * @param produto Produto instânciado.
     */
    void cadastrar (Produto produto);

    /**
     * Este método atualizar as informações de um produto já cadastrado no banco de dados, não será mudado
     * o id do produto, apenas outras informações como descrição, preço (Custo e Venda), marca, etc.
     * @param produto Produto que será atualizado.
     */
    void editar (Produto produto);

    void atualizarDescricao (String idProduto, String descricao);

    void atualizarQuant (String idProduto, int quantidade);

    /**
     * Este método "exclui" um produto, como nada deve ser apagado do banco de dados, ele apenas atualizar o estado
     * produto para inativo.
     * @param idProduto O código do produto.
     * @param ativo Boolean do produto para saber se ele está desativado (0) ou ativado (1).
     */
    void desativar (String idProduto, boolean ativo);

    /**
     * Lista todos os produtos cadastrados no estoque.
     */
    List<Produto> listarProdutos(); // Obviamente apenas os ativos

    /**
     * Lista todos os produtos que estiverem com quantidade em estoque baixa.
     * @return Retorna lista de produtos com quantidade em estoque baixa.
     */
    List<Produto> listarProdutoBE();

    /**
     * Busca o produto no banco de dados. O input pode ser código do produto, marca, categoria, descrição e fornecedor, o método vai
     * buscar todos que tiverem algo como o termo da pesquisa.
     * @param busca Termo utilizado na pesquisa geral.
     */
    List<Produto> pesquisar(String busca);

    /**
     * Calcula o valor total de produtos em estoque. Basicamente calcula valor em estoque += (preço de custo do produto n * quantidade em estoque do produto n).
     * @return O valor total.
     */
    BigDecimal valorEstoque();

    /**
     * Calcula quantos produtos ativos estão cadastrados no estoque.
     * @return Quantidade de produtos cadastrados no estoque.
     */
    Integer quantProdutosEst();

    /**
     * Percorre todos os produtos cadastrados no banco de dados e verifica se o código
     * informado já está cadastrado no sistema.
     * @param codigo Código digitado pelo utilizador no momento do cadastro do produto.
     * @return false se o código não tiver cadastro no banco de dados e true caso contrário.
     */
    boolean codigoJaExiste (String codigo);

    /**
     * Procura no banco de dados o produto pelo código informado.
     * @param codigo Código do produto que está pesquisando.
     * @return Retorna o produto se for achado.
     */
    Produto pesquisarCod(String codigo);
}
