package com.example.main.model.rn;

import com.example.main.model.dao.ProdutosDAO;

public class ProdutoRN {
    public boolean quantInvalida(int quantidade) {
        boolean result;
        if (quantidade <= 0){
            return result = false;
        }
        return result = true;
    }

    public boolean codigoJaExiste(String codigo) {
        ProdutosDAO produtoDAO = new ProdutosDAO();
        return produtoDAO.codigoJaExiste(codigo);
    }
}
