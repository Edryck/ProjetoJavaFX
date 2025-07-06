package com.example.main.model.dao;

import com.example.main.connection.ConnectionFactory;
import com.example.main.enums.TipoAlerta;
import com.example.main.exceptions.DAOException;
import com.example.main.interfaces.EstoqueInterface;
import com.example.main.model.vo.Produto;
import com.example.main.util.Alerta;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO implements EstoqueInterface {
    @Override
    public void cadastrar(Produto produto) {
        String sql = "INSERT INTO produto (idProduto, marca, descricao, categoria, quantidade, precoCusto, precoVenda, imagem, fornecedor, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, produto.getIdProduto());
            ps.setString(2, produto.getMarca());
            ps.setString(3, produto.getDescricao());
            ps.setString(4, produto.getCategoria());
            ps.setInt(5, produto.getQuantidade());
            ps.setBigDecimal(6, produto.getPrecoCusto());
            ps.setBigDecimal(7, produto.getPrecoVenda());
            ps.setString(8, produto.getImagem());
            ps.setString(9, produto.getFornecedor());
            ps.setBoolean(10, produto.getAtivo());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro: Cadastro de produtos. " + e.getMessage());
            throw new DAOException("Não foi possível cadastrar o produto no banco de dados.");
        }
    }

    @Override
    public void editar (Produto produto){
    }

    public void atualizarDescricao (String idProduto, String descricao) {
        String sql = "UPDATE produto SET descricao = ? WHERE idProduto = ?";

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, descricao);
            ps.setString(2, idProduto);

            ps.executeUpdate();
        } catch (SQLException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso ao dados", "Erro inesperado: Não foi possível alterar a descrição do produto.");
            System.err.println("Erro: Atualizar descrição do produto. " + e.getMessage());
            throw new DAOException("Não foi possível atualizar a descrição do produto.");
        }
    }

    /**
     * Este método é utilizado para atualizar a quantidade do produto em estoque, seja ele adicionado após uma compra
     * ou subtraído após uma venda.
     * @param idProduto Código do produto.
     * @param quantidade Quantidade que será mudada.
     */
    public void atualizarQuant (String idProduto, int quantidade){
        String sql = "UPDATE produto SET quantidade = quantidade + ? WHERE idProduto = ?";

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quantidade);
            ps.setString(2, idProduto);

            ps.executeUpdate();
        } catch (SQLException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso ao dados", "Erro inesperado: Não foi possível atualizar a quantidade do produto. Tente novamente.");
            System.err.println("Erro: Atualizar quantidade do produto. " + e.getMessage());
            throw new DAOException("Não foi possível atualizar a quantidade do produto.");
        }
    }

    @Override
    public void desativar (String idProduto, boolean ativo){
        String sql = "UPDATE produto SET ativo = ? WHERE idProduto = ?";

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, ativo);
            ps.setString(2, idProduto);

            ps.executeUpdate();
        } catch (SQLException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso ao dados", "Erro inesperado: Não foi possível desativar o produto. Tente novamente.");
            System.err.println("Erro: Desativar o produto. " + e.getMessage());
            throw new DAOException("Não foi possível desativar o produto.");
        }
    }

    @Override
    public List<Produto> pesquisar(String busca){
        String sql = "SELECT * FROM produto WHERE ativo = true AND (idProduto LIKE ? OR " +
                "marca LIKE ? OR descricao LIKE ? OR " +
                "categoria LIKE ? OR fornecedor LIKE ?)";

        List<Produto> encontrado = new ArrayList<>();
        String encontradoFormatado = "%" + busca + "%";

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, encontradoFormatado);
            ps.setString(2, encontradoFormatado);
            ps.setString(3, encontradoFormatado);
            ps.setString(4, encontradoFormatado);
            ps.setString(5, encontradoFormatado);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Produto produto = new Produto();
                    produto.setIdProduto(rs.getString("idProduto"));
                    produto.setMarca(rs.getString("marca"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setQuantidade(rs.getInt("quantidade"));
                    produto.setCategoria(rs.getString("categoria"));
                    produto.setPrecoCusto(rs.getBigDecimal("precoCusto"));
                    produto.setPrecoVenda(rs.getBigDecimal("precoVenda"));
                    produto.setAtivo(rs.getBoolean("ativo"));

                    encontrado.add(produto);
                }
            }
        } catch (SQLException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso aos dados", "Erro inesperado: Não foi possível realizar a pesquisa. Tente novamente.");
            System.err.println("Erro: Pesquisa de produtos" + e.getMessage());
            throw new DAOException("Não foi possível realizar pesquisa de produtos.");
        }
        return encontrado;
    }

    public Produto pesquisarCod(String codigo) {
        String sql = "SELECT * FROM produto WHERE ativo = true AND idProduto = ?";
        Produto produto = null;

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto();
                    produto.setIdProduto(rs.getString("idProduto"));
                    produto.setMarca(rs.getString("marca"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setQuantidade(rs.getInt("quantidade"));
                    produto.setCategoria(rs.getString("categoria"));
                    produto.setPrecoCusto(rs.getBigDecimal("precoCusto"));
                    produto.setPrecoVenda(rs.getBigDecimal("precoVenda"));
                    produto.setAtivo(rs.getBoolean("ativo"));
                }
            }
        } catch (SQLException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso aos dados", "Erro inesperado: Não foi possível realizar a pesquisa. Tente novamente.");
            System.err.println("Erro: Pesquisa de produtos por código. " + e.getMessage());
            throw new DAOException("Não foi possível realizar pesquisa de produtos.");
        }
        return produto;
    }

    @Override
    public List<Produto> listarProdutos(){
        String sql = "SELECT * FROM produto WHERE ativo = true";
        List<Produto> lista = new ArrayList<>();

        try(Connection connection = ConnectionFactory.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    Produto produto = new Produto();

                    produto.setIdProduto(rs.getString("idProduto"));
                    produto.setMarca(rs.getString("marca"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setQuantidade(rs.getInt("quantidade"));
                    produto.setCategoria(rs.getString("categoria"));
                    produto.setPrecoCusto(rs.getBigDecimal("precoCusto"));
                    produto.setPrecoVenda(rs.getBigDecimal("precoVenda"));
                    produto.setAtivo(rs.getBoolean("ativo"));

                    lista.add(produto);
                }
        } catch (SQLException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso aos dados", "Não foi possível listar os produtos. Tente novamente.");
            System.err.println("Erro: Listar produtos. " + e.getMessage());
            throw new DAOException("Não foi possível listar os produtos.");
        }
        return lista;
    }

    @Override
    public List<Produto> listarProdutoBE(){
        String sql = "SELECT * FROM produto WHERE ativo = true AND quantidade < 5";
        List<Produto> lista = new ArrayList<>();

        try(Connection connection = ConnectionFactory.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Produto produto = new Produto();

                produto.setIdProduto(rs.getString("idProduto"));
                produto.setMarca(rs.getString("marca"));
                produto.setDescricao(rs.getString("descricao"));
                produto.setQuantidade(rs.getInt("quantidade"));
                produto.setCategoria(rs.getString("categoria"));
                produto.setPrecoCusto(rs.getBigDecimal("precoCusto"));
                produto.setPrecoVenda(rs.getBigDecimal("precoVenda"));
                produto.setAtivo(rs.getBoolean("ativo"));

                lista.add(produto);
            }
        } catch (SQLException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso ao dados", "Não foi possível listar os produtos com baixo estoque. Tente novamente.");
            System.err.println("Erro: Não foi possível listar os produtos com baixo estoque. " + e.getMessage());
            throw new DAOException("Não foi possível listar os produto com baixo estoque.");
        }
        return lista;
    }

    @Override
    public BigDecimal valorEstoque() {
        String sql = "SELECT SUM(precoCusto * quantidade) FROM produto WHERE ativo = true";
        BigDecimal valorTotal = BigDecimal.ZERO;

        try (Connection connection = ConnectionFactory.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                BigDecimal totalDoBanco = rs.getBigDecimal(1);
                if (totalDoBanco != null) {
                    valorTotal = totalDoBanco;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao calcular o valor total em estoque: " + e.getMessage());
            throw new DAOException("Não foi possível calcular o valor total do estoque.");
        }
        return valorTotal;
    }

    @Override
    public Integer quantProdutosEst() {
        String sql = "SELECT SUM(quantidade) AS total_de_itens FROM produto WHERE ativo = true";
        int quantProd = 0;

        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                quantProd = rs.getInt("total_de_itens");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao calcular o total de itens em estoque: " + e.getMessage());
            throw new DAOException("Não foi possível calcular a quantidade de produtos em estoque.");
        }

        return quantProd;
    }

    @Override
    public boolean codigoJaExiste (String codigo){
        String sql = "SELECT * FROM produto WHERE idProduto = ?";
        boolean codigoExiste = false;

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    codigoExiste = true;
                }
            }
        } catch (SQLException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso ao dados", "Erro inesperado: Não foi possível conferir se o código já foi cadastrado no sistema. Tente novamente.");
            System.err.println("Erro: Conferir se o código já foi cadastrado. " + e.getMessage());
            throw new DAOException("Falha ao validar o código digitado.");
        }
        return codigoExiste;
    }
}