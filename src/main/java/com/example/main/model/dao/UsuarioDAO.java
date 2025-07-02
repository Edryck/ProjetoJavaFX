package com.example.main.model.dao;

import com.example.main.connection.ConnectionFactory;
import main.model.vo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO implements ManipulacaoUsuario {
    @Override
    public boolean emailJaExiste (String email){
        String sql = "SELECT * FROM usuarios WHERE emailUsuario = ?";
        boolean emailExiste = false;

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emailExiste = true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao verificar o login: " + e.getMessage());
            e.printStackTrace();
        }
        return emailExiste;
    }

    @Override
    public void cadastrar(String nome, String email, String senha) {
        String sql = "INSERT INTO usuarios (nomeUsuario, emailUsuario, senhaUsuario) VALUES (?, ?, ?)";

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setString(3, senha);

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Usuario login(String email) {
        String sql = "SELECT * FROM usuarios WHERE emailUsuario = ?";
        main.model.vo.Usuario usuario = null;

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("usuarioId");
                    String nome = rs.getString("nomeUsuario");
                    String emailDoBanco = rs.getString("emailUsuario");
                    String senhaDoBanco = rs.getString("senhaUsuario");

                    usuario = new Usuario(id, nome, emailDoBanco, senhaDoBanco);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usuario;
    }

}
