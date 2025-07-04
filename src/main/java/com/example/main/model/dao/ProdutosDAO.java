package com.example.main.model.dao;

import com.example.main.connection.ConnectionFactory;
import com.example.main.enums.TipoAlerta;
import com.example.main.model.vo.Produto;
import com.example.main.util.Alerta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO /*implements ManipulacaoDeEstoque*/ {
    // @Override
    /**
     * Este método é utilizado para cadastrar produtos no banco de dados, caso seja o cadastro deste primeiro
     * deverá ser usado está função para cadastrar, caso contrário, deverá utilizar o método para atualizar produto.
     * @param produto Produto instânciado.
     */
    public void cadastrar (Produto produto) {
        String sql = "INSERT INTO produto (idProduto, marca, descricao, quantidade, categoria, precoCusto, precoVenda, prodImagem, ativo)" +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
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
            Alerta.mostrarAlerta(TipoAlerta.ERRO, "Erro no cadastro", "Não foi possível realizar o cadastro do produto!");
            System.err.println("Erro ao cadastrar produto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Este método atualizar as informações de um produto já cadastrado no banco de dados, não será mudado
     * o id do produto, apenas outras informações como descrição, preço (Custo e Venda), marca, etc.
     * @param produto Produto que será atualizado.
     */
    public void atualizar (Produto produto){
    }
    
    public void atualizarDescricao (String idProduto, String descricao) {
        String sql = "UPDATE produto SET descricao = ? WHERE idProduto = ?";
        
        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, descricao);
            ps.setString(2, idProduto);

            ps.executeUpdate();
        } catch (SQLException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO, "Erro ao atualizar descrição", "Não foi possível atualizar a descrição do produto!");
            System.err.println("Erro ao atualizar a descrição do produto: " + e.getMessage());
            throw new RuntimeException(e);
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
            Alerta.mostrarAlerta(TipoAlerta.ERRO, "Erro ao atualizar estoque", "Não foi possível atualizar estoque!");
            System.err.println("Erro ao ajustar estoque do produto: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Este método "exclui" um produto, como nada deve ser apagado do banco de dados, ele apenas atualizar o estado
     * produto para inativo.
     * @param idProduto O código do produto.
     * @param ativo Boolean do produto para saber se ele está desativado (0) ou ativado (1).
     */
    public void desativar (String idProduto, boolean ativo){
        String sql = "UPDATE produto SET ativo = ? WHERE idProduto = ?";

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, ativo);
            ps.setString(2, idProduto);

            ps.executeUpdate();
        } catch (SQLException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO, "Erro!", "Não foi possível atualizar estoque!");
            System.err.println("Erro ao desativar produto: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca o produto no banco de dados pelo id do produto.
     * @param idProduto Código do produto.
     * @return true no caso do produto ser encontrado e false caso contrário.
     */
    public Produto buscarProduto (String idProduto){
        String sql = "SELECT * FROM produto WHERE idProduto = ?";
        Produto produto = null;

        try(Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, idProduto);

            try(ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto();
                    produto.setIdCodigo(rs.getString("idProduto"));
                    produto.setMarcaProduto(rs.getString("marca"));
                    produto.setDescricaoProduto(rs.getString("descricao"));
                    produto.setQuantidade(rs.getInt("quantidade"));
                    produto.setCategoriaProduto(rs.getString("categoria"));
                    produto.setPrecoCusto(rs.getDouble("precoCusto"));
                    produto.setPrecoVenda(rs.getDouble("precoVenda"));
                    produto.setAtivo(rs.getBoolean("ativo"));
                }
            }
        } catch (SQLException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO, "Não encontrado!", "Talvez este produto não esteja cadastrado no sistema!");
            e.printStackTrace();
        }
        return produto;
    }

    /**
     * Lista todos os produtos cadastrados no estoque.
     * @return Retorna a lista de produtos cadastrados.
     */
    public List<Produto> listarProdutos(){
        String sql = "SELECT * FROM produtos WHERE ativo = true";
        List<Produto> lista = new ArrayList<>();

        try(Connection connection = ConnectionFactory.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    Produto produto = new Produto();

                    produto.setIdCodigo(rs.getString("idProduto"));
                    produto.setMarcaProduto(rs.getString("marca"));
                    produto.setDescricaoProduto(rs.getString("descricao"));
                    produto.setQuantidade(rs.getInt("quantidade"));
                    produto.setCategoriaProduto(rs.getString("categoria"));
                    produto.setPrecoCusto(rs.getDouble("precoCusto"));
                    produto.setPrecoVenda(rs.getDouble("precoVenda"));
                    produto.setAtivo(rs.getBoolean("ativo"));

                    lista.add(produto);
                }
        } catch (SQLException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO, "Erro ao listar produtos", "Não foi possível listar os produtos cadastrados no estoque.");
            System.err.println("Erro ao listar produtos: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    public boolean codigoJaExiste (String codigo){
        String sql = "SELECT * FROM produto WHERE idProduto = ?";
        boolean codigoExiste = false;

        try (Connection connection = ConnectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    codigoExiste = true;
                }
            }
        } catch (SQLException e) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO, "Erro inesperado", "Não foi possível verificar se o código do produto já existe!");
            System.err.println("Erro ao verificar se o código do produto existe: " + e.getMessage());
            e.printStackTrace();
        }
        return codigoExiste;
    }
}