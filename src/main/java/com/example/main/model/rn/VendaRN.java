package com.example.main.model.rn;

import com.example.main.enums.TipoAlerta;
import com.example.main.exceptions.RNException;
import com.example.main.model.dao.ItemVendaDAO;
import com.example.main.model.dao.VendaDAO;
import com.example.main.model.vo.ItemVenda;
import com.example.main.model.vo.Produto;
import com.example.main.model.vo.Venda;
import com.example.main.util.Alerta;

import java.util.List;

public class VendaRN {
    private final ProdutoRN produtoRN = new ProdutoRN();
    private final Produto produto = new Produto();
    private final VendaDAO vendaDAO = new VendaDAO();
    private final ItemVendaDAO itemVendaDAO = new ItemVendaDAO();

    public Venda buscarVendaCompleta(int id) {
        Venda venda = vendaDAO.buscarPorId(id);
        if (venda != null) {
            List<ItemVenda> itens = itemVendaDAO.buscarItensPorVendaId(id);
            venda.setItens(itens);
        }
        return venda;
    }

    /**
     * Validação e conversão de quantidade de itens. Converte para inteiro,
     * verificar se é menor ou igual a zero, se não, lança uma NumberFormatException,
     * próxima verificação é se a quantidade informada é maior que a quantidade em estoque,
     * se for maior, lança uma RNException por quantidade em estoque insuficiente.
     * @param quantidadeField Texto capturado na 'view'.
     * @return Retorna a quantidade informada em inteiros.
     */
    public int validarQuant(String quantidadeField, Produto produto) {
        int quantidade;
        try {
            quantidade = Integer.parseInt(quantidadeField);
            if (quantidade <= 0) {
                throw new NumberFormatException();
            }
            if (produto.getQuantidade() < quantidade) {
                Alerta.mostrarAlerta(TipoAlerta.ATENCAO, "Estoque Insuficiente", "Não há estoque suficiente para este produto. Restam apenas " + produto.getQuantidade() + " unidades.");
                throw new RNException("Estoque Insuficiente. Validação de venda.");
            }
        } catch (NumberFormatException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO, "Quantidade Inválida", "Por favor, insira uma quantidade numérica maior que zero.");
            throw new RNException("Quantidade Inválida. Validação da venda.");
        }
        return quantidade;
    }
}
