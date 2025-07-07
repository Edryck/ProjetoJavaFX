package com.example.main.model.rn;

import com.example.main.connection.ConnectionFactory;
import com.example.main.enums.TipoAlerta;
import com.example.main.exceptions.DAOException;
import com.example.main.exceptions.RNException;
import com.example.main.model.dao.ItemVendaDAO;
import com.example.main.model.dao.ProdutoDAO;
import com.example.main.model.dao.VendaDAO;
import com.example.main.model.vo.ItemVenda;
import com.example.main.model.vo.Produto;
import com.example.main.model.vo.Venda;
import com.example.main.util.Alerta;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class VendaRN {
    private final ProdutoRN produtoRN = new ProdutoRN();
    private final ProdutoDAO produtoDAO = new ProdutoDAO();
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

    public void registrarVenda(Venda venda) throws SQLException, RNException {
        Connection connection = null;
        try {
            connection = ConnectionFactory.getConnection();
            connection.setAutoCommit(false);

            for (ItemVenda item : venda.getItens()) {
                // Verificação para não ser quantidade não ser maior que a quantidade em estoque e a não se <= 0
            }
            int idVenda = vendaDAO.salvar(venda);
            for (ItemVenda item : venda.getItens()) {
                item.setIdVenda(idVenda);
                itemVendaDAO.salvar(item);
                produtoDAO.atualizarQuant(item.getProduto().getIdProduto(), -item.getQuantidade());
            }
            connection.commit();
        } catch (DAOException e) {
            if (connection != null) connection.rollback();
            throw new RNException("Não foi possível registrar venda: registrar venda, VendaRN.");
        } finally {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
    }

    public List<Venda> listarTodasVendas() {
        return vendaDAO.listarTodasVendas();
    }

   public int totalVendasPendentes() {
        return vendaDAO.listarPendentes().size();
   }

    public int totalVendasFinalizadas() {
        return vendaDAO.listarFinalizadas().size();
    }

    public int totalVendas() {
        return vendaDAO.listarTodasVendas().size();
    }

    public BigDecimal valorTotalVendas() {
        return vendaDAO.getValorTotalVendas();
    }
}
