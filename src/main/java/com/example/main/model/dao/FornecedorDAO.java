package com.example.main.model.dao;

import com.example.main.connection.ConnectionFactory;
import com.example.main.enums.StatusFornecedor;
import com.example.main.enums.TipoAlerta;
import com.example.main.exceptions.DAOException;
import com.example.main.interfaces.FornecedorInterface;
import com.example.main.model.vo.Fornecedor;
import com.example.main.util.Alerta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FornecedorDAO implements FornecedorInterface {
    @Override
    public void cadastrar(Fornecedor fornecedor) {
        String sql = "INSERT INTO fornecedor (nome, email, telefone, statusForn) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fornecedor.getNome());
            ps.setString(2, fornecedor.getEmail());
            ps.setString(3, fornecedor.getTelefone());
            ps.setString(4, fornecedor.getStatus().toString()); // Converte o Enum para String

            ps.executeUpdate();
            System.out.println("Fornecedor '" + fornecedor.getNome() + "' cadastrado com sucesso.");

        } catch (SQLException e) {
            throw new DAOException("Erro ao cadastrar fornecedor.");
        }
    }

    @Override
    public void atualizar(Fornecedor fornecedor) {
        String sql = "UPDATE fornecedor SET nome = ?, email = ?, telefone = ?, statusForn = ? WHERE idFornecedor = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, fornecedor.getNome());
            ps.setString(2, fornecedor.getEmail());
            ps.setString(3, fornecedor.getTelefone());
            ps.setString(4, fornecedor.getStatus().toString());
            ps.setInt(5, fornecedor.getIdFornecedor());

            ps.executeUpdate();
            System.out.println("Fornecedor ID " + fornecedor.getIdFornecedor() + " atualizado com sucesso.");
            Alerta.mostrarAlerta(TipoAlerta.SUCESSO, "Fornecedor atualizado!", "Fornecedor ID " + "atualizado com sucesso.");

        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar fornecedor.");
        }
    }

    @Override
    public Fornecedor buscarPorId(int idFornecedor) {
        String sql = "SELECT * FROM fornecedor WHERE idFornecedor = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idFornecedor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearResultSet(rs);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar fornecedor por ID.");
        }
        return null; // Retorna null se não encontrar
    }

    @Override
    public List<Fornecedor> listarTodos() {
        String sql = "SELECT * FROM fornecedor WHERE statusForn = 'ATIVO' ORDER BY nome";
        List<Fornecedor> fornecedores = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                fornecedores.add(mapearResultSet(rs));
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao listar todos os fornecedores.");
        }
        return fornecedores;
    }

    @Override
    public void setStatus(int idFornecedor, StatusFornecedor novoStatus) {
        String sql = "UPDATE fornecedor SET statusForn = ? WHERE idFornecedor = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, novoStatus.toString());
            ps.setInt(2, idFornecedor);
            ps.executeUpdate();

            System.out.println("Status do fornecedor ID " + idFornecedor + " alterado para: " + novoStatus);

        } catch (SQLException e) {
            throw new DAOException("Erro ao alterar status do fornecedor.");
        }
    }

    @Override
    public List<Fornecedor> pesquisar(String termo) {
        String sql = "SELECT * FROM fornecedores WHERE status = 'ATIVO' AND " +
                "(nome LIKE ? OR email LIKE ? OR telefone LIKE ? OR pessoa_contato LIKE ?)";

        List<Fornecedor> fornecedoresEncontrados = new ArrayList<>();

        String termoBuscaFormatado = "%" + termo + "%";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Define o mesmo termo de busca para todos os '?' da query
            ps.setString(1, termoBuscaFormatado);
            ps.setString(2, termoBuscaFormatado);
            ps.setString(3, termoBuscaFormatado);
            ps.setString(4, termoBuscaFormatado);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    fornecedoresEncontrados.add(mapearResultSet(rs));
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao pesquisar fornecedores.");
        }
        return fornecedoresEncontrados;
    }

    /**
     * Método para mapear uma linha do ResultSet para um objeto Fornecedor.
     * Evita a repetição do mesmo bloco de código nos métodos de busca.
     */
    private Fornecedor mapearResultSet(ResultSet rs) throws SQLException {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setIdFornecedor(rs.getInt("idFornecedor"));
        fornecedor.setNome(rs.getString("nome"));
        fornecedor.setEmail(rs.getString("email"));
        fornecedor.setTelefone(rs.getString("telefone"));
        // Converte a String do banco de volta para o Enum
        fornecedor.setStatus(StatusFornecedor.valueOf(rs.getString("statusForn")));
        return fornecedor;
    }
}