package com.example.main.model.dao;

import com.example.main.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CadastroUsuarioDAO {

    public void cadastrar(String nome, String email, String senha) {
        String sql = "INSERT INTO usuarios (nomeUsuario, emailUsuario, senhaUsuario) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, nome);
            ps.setString(1, email);
            ps.setString(3, senha);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
