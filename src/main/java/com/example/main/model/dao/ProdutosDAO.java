package com.example.main.model.dao;

import com.example.main.connection.ConnectionFactory;
import com.example.main.model.vo.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProdutosDAO /*implements ManipulacaoDeEstoque*/ {
    // @Override
    public void cadastrar (Produto produto) {
        String sql = "INSERT INTO produto (idProduto, marca, descricao, quantidade, categoria, precoCusto, precoVenda, prodImagem, ativo)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, produto.getIdCodigo());
            ps.setString(2, produto.getMarcaProduto());
            ps.setString(3, produto.getDescricaoProduto());
            ps.setInt(4, produto.getQuantidade());
            ps.setString(5, produto.getCategoriaProduto());
            ps.setDouble(6, produto.getPrecoCusto());
            ps.setDouble(7, produto.getPrecoVenda());
            ps.setString(8, produto.getImagem());
            ps.setBoolean(9, true);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar roduto: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void atualizar (Produto produto){}
    public void atualizarEstoque (int idProduto, int quantidade, String motivo){}
    public void desativar (int idProduto){}
    public int buscarId (int idProduto){
        return idProduto;
    }
    // public List<Produto> listarProdutos(){} // Obviamente apenas os ativos

    public boolean codigoJaExiste (String codigo){
        String sql = "SELECT * FROM produto WHERE idProduto = ?";
        boolean codigoExiste = false;

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    codigoExiste = true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar o login: " + e.getMessage());
            e.printStackTrace();
        }
        return codigoExiste;
    }
}