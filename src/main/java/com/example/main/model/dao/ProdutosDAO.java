package com.example.main.model.dao;

import com.example.main.connection.ConnectionFactory;
import com.example.main.enums.TipoAlerta;
import com.example.main.model.vo.Produto;
import com.example.main.util.Alerta;
import javafx.application.Platform;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutosDAO implements ManipulacaoDeEstoque {
    /**
     * Este método é utilizado para cadastrar produtos no banco de dados, caso seja o cadastro deste primeiro
     * deverá ser usado está função para cadastrar, caso contrário, deverá utilizar o método para atualizar produto.
     * @param produto Produto instânciado.
     */
    public void cadastrar(Produto produto){
        String sql = "INSERT INTO produto (idProduto, marca, descricao, categoria, quantidade, precoCusto, precoVenda, imagem, fornecedor, ativo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = ConnectionFactory.getConnection();
        if(connection == null) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Conexão com o Banco de Dados", "Erro inesperado: Não foi possível conectar com o banco de dados. Tente novamente.");
            return;
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

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
            Platform.runLater(() -> Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso ao dados", "Não foi possível acessar os dados."));
            System.err.println("Erro ao acessar o banco de dados: " + e.getMessage());
        }
    }

    /**
     * Este método atualizar as informações de um produto já cadastrado no banco de dados, não será mudado
     * o id do produto, apenas outras informações como descrição, preço (Custo e Venda), marca, etc.
     * @param produto Produto que será atualizado.
     */
    public void editar (Produto produto){
    }
    
    public void atualizarDescricao (String idProduto, String descricao) {
        String sql = "UPDATE produto SET descricao = ? WHERE idProduto = ?";

        Connection connection = ConnectionFactory.getConnection();
        if(connection == null) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Conexão com o Banco de Dados", "Erro inesperado: Não foi possível conectar com o banco de dados. Tente novamente.");
            return;
        }
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, descricao);
            ps.setString(2, idProduto);

            ps.executeUpdate();
        } catch (SQLException e) {
            Platform.runLater(() -> Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso ao dados", "Erro inesperado: Não foi possível acessar os dados. Tente novamente."));
            System.err.println("Erro ao acessar o banco de dados: " + e.getMessage());
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

        Connection connection = ConnectionFactory.getConnection();
        if(connection == null) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Conexão com o Banco de Dados", "Erro inesperado: Não foi possível conectar com o banco de dados. Tente novamente.");
            return;
        }
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quantidade);
            ps.setString(2, idProduto);

            ps.executeUpdate();
        } catch (SQLException e) {
            Platform.runLater(() -> Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso ao dados", "Erro inesperado: Não foi possível acessar os dados. Tente novamente."));
            System.err.println("Erro ao acessar o banco de dados: " + e.getMessage());
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

        Connection connection = ConnectionFactory.getConnection();
        if(connection == null) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Conexão com o Banco de Dados", "Erro inesperado: Não foi possível conectar com o banco de dados. Tente novamente.");
            return;
        }
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setBoolean(1, ativo);
            ps.setString(2, idProduto);

            ps.executeUpdate();
        } catch (SQLException e) {
            Platform.runLater(() -> Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso ao dados", "Erro inesperado: Não foi possível acessar os dados. Tente novamente."));
            System.err.println("Erro ao acessar o banco de dados: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Busca o produto no banco de dados. O input pode ser código do produto, marca, categoria, descrição e fornecedor, o método vai
     * buscar todos que tiverem algo como o termo da pesquisa.
     *
     * @param busca Termo utilizado na pesquisa geral.
     */
    public List<Produto> pesquisar(String busca){
        String sql = "SELECT * FROM produto WHERE ativo = true AND (idProduto LIKE ? OR " +
                "marca LIKE ? OR descricao LIKE ? OR " +
                "categoria LIKE ? OR fornecedor LIKE ?)";

        List<Produto> encontrado = new ArrayList<>();
        String encontradoFormatado = "%" + busca + "%";

        Connection connection = ConnectionFactory.getConnection();
        if(connection == null) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Conexão com o Banco de Dados", "Erro inesperado: Não foi possível conectar com o banco de dados. Tente novamente.");
            return null;
        }
        try(PreparedStatement ps = connection.prepareStatement(sql)) {
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
            Platform.runLater(() -> Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso ao dados", "Erro inesperado: Não foi possível acessar os dados. Tente novamente."));
            System.err.println("Erro ao acessar o banco de dados: " + e.getMessage());
        }
        return encontrado;
    }

    /**
     * Lista todos os produtos cadastrados no estoque.
     */
    public List<Produto> listarProdutos(){
        String sql = "SELECT * FROM produto WHERE ativo = true";
        List<Produto> lista = new ArrayList<>();

        Connection connection = ConnectionFactory.getConnection();
        if(connection == null) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Conexão com o Banco de Dados", "Não foi possível conectar com o banco de dados.");
            return null;
        }
        try(Statement st = connection.createStatement();
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
            Platform.runLater(() -> Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso ao dados", "Não foi possível acessar os dados."));
            System.err.println("Erro ao acessar o banco de dados: " + e.getMessage());
        }
        return lista;
    }

    public List<Produto> listarProdutoBE(){
        String sql = "SELECT * FROM produto WHERE ativo = true AND quantidade < 5";
        List<Produto> lista = new ArrayList<>();

        Connection connection = ConnectionFactory.getConnection();
        if(connection == null) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Conexão com o Banco de Dados", "Não foi possível conectar com o banco de dados.");
            return null;
        }
        try(Statement st = connection.createStatement();
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
            Platform.runLater(() -> Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso ao dados", "Não foi possível acessar os dados."));
            System.err.println("Erro ao acessar o banco de dados: " + e.getMessage());
        }
        return lista;
    }

    public BigDecimal valorEstoque() {
        String sql = "SELECT SUM(precoCusto * quantidade) FROM produto WHERE ativo = true";
        BigDecimal valorTotal = BigDecimal.ZERO;

        Connection connection = ConnectionFactory.getConnection();
        if(connection == null) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Conexão com o Banco de Dados", "Erro inesperado: Não foi possível conectar com o banco de dados. Tente novamente.");
            return valorTotal;
        }
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                BigDecimal totalDoBanco = rs.getBigDecimal(1);
                if (totalDoBanco != null) {
                    valorTotal = totalDoBanco;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao calcular o valor total em estoque: " + e.getMessage());
        }
        return valorTotal;
    }

    public Integer quantProdutosEst() {
        String sql = "SELECT SUM(quantidade) AS total_de_itens FROM produto WHERE ativo = true";
        int quantProd = 0;

        Connection connection = ConnectionFactory.getConnection();
        if(connection == null) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Conexão com o Banco de Dados", "Não foi possível conectar com o banco de dados.");
            return null;
        }
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                quantProd = rs.getInt("total_de_itens");
            }

        } catch (SQLException e) {
            System.err.println("Erro ao calcular o total de itens em estoque: " + e.getMessage());
            e.printStackTrace();
        }

        return quantProd;
    }

    public boolean codigoJaExiste (String codigo){
        String sql = "SELECT * FROM produto WHERE idProduto = ?";
        boolean codigoExiste = false;

        Connection connection = ConnectionFactory.getConnection();
        if(connection == null) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Conexão com o Banco de Dados", "Erro inesperado: Não foi possível conectar com o banco de dados. Tente novamente.");
            return false;
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    codigoExiste = true;
                }
            }
        } catch (SQLException e) {
            Platform.runLater(() -> Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso ao dados", "Erro inesperado: Não foi possível acessar os dados. Tente novamente."));
            System.err.println("Erro ao acessar o banco de dados: " + e.getMessage());
        }
        return codigoExiste;
    }
}