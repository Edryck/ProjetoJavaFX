package com.example.main.interfaces;

import com.example.main.model.vo.Venda;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface VendaInterface {
    /**
     * Salva o cabeçalho de uma nova venda no banco, numa transação.
     * @param venda      O objeto Venda a ser salvo.
     * @return O 'ID' (chave primária) da venda recém-criada.
     */
    int salvar(Venda venda);

    /**
     * Busca uma única venda pelo seu 'ID'. Util para ver os detalhes de uma venda específica.
     * @param id O 'ID' da venda a ser buscada.
     * @return Um objeto Venda, ou null se não for encontrado.
     */
    Venda buscarPorId(int id);

    /**
     * Lista todas as vendas já realizadas.
     * Essencial para a tela de "Histórico de Vendas".
     * @return Uma lista de todas as vendas.
     */
    List<Venda> listarTodasVendas();

    /**
     * Busca todas as vendas num período de datas.
     * Perfeito para relatórios (ex: "Vendas deste mês").
     * @param inicio A data inicial.
     * @param fim A data final.
     * @return Uma lista de vendas dentro do período.
     */
    List<Venda> buscarPorPeriodo(LocalDate inicio, LocalDate fim);

    /**
     * Atualiza o status de uma venda (ex: para "CANCELADA").
     * Melhor do que deletar o registro.
     * @param id O 'ID' da venda a ser atualizada.
     * @param novoStatus O novo status (ex: "FINALIZADA", "CANCELADA").
     */
    void atualizarStatus(int id, String novoStatus);
    /**
     * NOVO: Calcula e retorna a soma do valorTotal de todas as vendas finalizadas.
     * @return Um BigDecimal com o valor total.
     */
    BigDecimal getValorTotalVendas();

    /**
     * NOVO: Conta o número total de vendas finalizadas.
     * @return Um int com a quantidade de vendas.
     */
    int getQuantidadeTotalDeVendas();

    /**
     * NOVO: Busca um resumo das vendas (quantidade e valor total) realizadas no dia de hoje.
     * @return Um Mapa contendo as chaves "total" (BigDecimal) e "quantidade" (int).
     */
    Map<String, Object> getResumoVendasDeHoje();
}