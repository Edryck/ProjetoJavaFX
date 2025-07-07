package com.example.main.model.rn;

import com.example.main.enums.StatusFornecedor;
import com.example.main.exceptions.RNException;
import com.example.main.model.dao.FornecedorDAO;
import com.example.main.model.vo.Fornecedor;

import java.util.List;

public class FornecedorRN {
    private final FornecedorDAO fornecedorDAO = new FornecedorDAO();

    /**
     * Valida e salva um fornecedor. Decide se deve cadastrar (novo) ou atualizar (existente).
     * @param fornecedor O objeto Fornecedor a ser salvo.
     */
    public void salvar(Fornecedor fornecedor) {
        if (fornecedor.getNome() == null || fornecedor.getNome().isBlank()) {
            throw new RNException("O nome do fornecedor não pode estar em branco.");
        }

        // Se o ID for 0, é um novo fornecedor. Se for diferente, é uma atualização.
        if (fornecedor.getIdFornecedor() == 0) {
            fornecedor.setStatus(StatusFornecedor.ATIVO); // Todo novo fornecedor começa como ATIVO
            fornecedorDAO.cadastrar(fornecedor);
        } else {
            fornecedorDAO.atualizar(fornecedor);
        }
    }

    /**
     * Busca todos os fornecedores ativos.
     * @return Uma lista de fornecedores ativos.
     */
    public List<Fornecedor> listarTodos() {
        return fornecedorDAO.listarTodos();
    }

    /**
     * Desativa um fornecedor.
     * @param fornecedor O fornecedor que vai ser desativado.
     */
    public void desativar(Fornecedor fornecedor) {
        if (fornecedor == null || fornecedor.getIdFornecedor() == 0) {
            throw new RNException("Fornecedor inválido para desativação.");
        }
        fornecedorDAO.setStatus(fornecedor.getIdFornecedor(), StatusFornecedor.INATIVO);
    }

    /**
     * Realiza a busca de fornecedores. Se o termo for vazio, lista todos.
     * @param termo O texto a ser pesquisado.
     * @return A lista de fornecedores encontrados.
     */
    public List<Fornecedor> pesquisar(String termo) {
        if (termo == null || termo.isBlank()) {
            return listarTodos();
        }
        return fornecedorDAO.pesquisar(termo);
    }

}
