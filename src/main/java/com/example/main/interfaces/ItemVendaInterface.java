package com.example.main.interfaces;

import com.example.main.model.vo.ItemVenda;

import java.sql.SQLException;
import java.util.List;

public interface ItemVendaInterface {
    /**
     * Salva um item de uma venda no banco, dentro de uma transação.
     * Este método será chamado várias vezes, uma para cada produto na venda.
     * @param item O objeto ItemVenda a ser salvo.
     * @throws SQLException Se ocorrer um erro no INSERT.
     */
    void salvar(ItemVenda item);

    /**
     * Busca todos os itens de uma venda específica.
     * Usado quando o utilizador clica para ver os detalhes de uma venda no histórico.
     * @param vendaId O 'ID' da venda da qual queremos os itens.
     * @return Uma lista de todos os itens daquela venda.
     */
    List<ItemVenda> buscarItensPorVendaId(int vendaId);
}
