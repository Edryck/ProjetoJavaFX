package com.example.main.interfaces;

import com.example.main.enums.StatusFornecedor;
import com.example.main.model.vo.Fornecedor;

import java.util.List;

public interface FornecedorInterface {
        /**
         * Cadastra fornecedores no sistema.
         * @param fornecedor Fornecedor instânciado.
         */
        void cadastrar(Fornecedor fornecedor);

        /**
         * Atualiza as informações do fornecedor.
         * @param fornecedor Fornecedor instânciado.
         */
        void atualizar(Fornecedor fornecedor);

        /**
         * Seta o status do fornecedor quando for "deletado" ou cadastrado novamente.
         * @param idFornecedor Código do fornecedor.
         * @param novoStatus Status novo do fornecedor (Ativo/Inativo).
         */
        void setStatus(int idFornecedor, StatusFornecedor novoStatus); // Para ativar/desativar

        /**
         * Busca o fornecedor no banco de dados pelo código dele.
         * @param idFornecedor Código do fornecedor.
         * @return Retorna 'null' caso não seja encontrado, caso contrário retorna o fornecedor.
         */
        Fornecedor buscarPorId(int idFornecedor);

        /**
         * Lista todos os fornecedores cadastrados no sistema, por ordem alfabética.
         * @return Lista de todos os fornecedores cadastrados.
         */
        List<Fornecedor> listarTodos();

        /**
         * Pesquisa fornecedores por um termo em várias colunas.
         * @param termo O texto a ser pesquisado.
         * @return Uma lista de fornecedores que correspondem ao termo.
         */
        List<Fornecedor> pesquisar(String termo);
}
