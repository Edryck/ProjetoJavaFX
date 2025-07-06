package com.example.main.model.dao;

import com.example.main.connection.ConnectionFactory;
import com.example.main.enums.TipoAlerta;
import com.example.main.interfaces.UsuarioInterface;
import com.example.main.model.vo.Usuario;
import com.example.main.util.Alerta;
import javafx.application.Platform;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO implements UsuarioInterface {
    @Override
    public boolean emailJaExiste (String email){
        String sql = "SELECT * FROM usuarios WHERE emailUsuario = ?";
        boolean emailExiste = false;
        Connection connection = ConnectionFactory.getConnection();
        if(connection == null) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Conexão com o Banco de Dados", "Erro inesperado: Não foi possível conectar com o banco de dados. Tente novamente.");
            return false;
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    emailExiste = true;
                }
            }
        } catch (SQLException e) {
            Platform.runLater(() -> Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso ao dados", "Erro inesperado: Não foi possível acessar os dados. Tente novamente."));
            System.err.println("Erro ao verificar o login: " + e.getMessage());
        }
        return emailExiste;
    }

    @Override
    public void cadastrar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nomeUsuario, emailUsuario, senhaUsuario) VALUES (?, ?, ?)";

        Connection connection = ConnectionFactory.getConnection();
        if(connection == null) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Conexão com o Banco de Dados", "Erro inesperado: Não foi possível conectar com o banco de dados. Tente novamente.");
            return;
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, usuario.getNomeUsuario());
            ps.setString(2, usuario.getemailUsuario());
            ps.setString(3, usuario.getSenhaUsuario());

            ps.executeUpdate();
        } catch (SQLException e) {
            Platform.runLater(() -> Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso ao dados", "Erro inesperado: Não foi possível acessar os dados. Tente novamente."));
            System.err.println("Erro ao verificar o login: " + e.getMessage());
        }
    }

    @Override
    public Usuario login(String email) {
        String sql = "SELECT * FROM usuarios WHERE emailUsuario = ?";
        Usuario usuario = null;

        Connection connection = ConnectionFactory.getConnection();
        if(connection == null) {
            Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Conexão com o Banco de Dados", "Erro inesperado: Não foi possível conectar com o banco de dados. Tente novamente.");
            return null;
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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
            Platform.runLater(() -> Alerta.mostrarAlerta(TipoAlerta.ERRO_BD, "Erro no acesso ao dados", "Erro inesperado: Não foi possível acessar os dados. Tente novamente."));
            System.err.println("Erro ao verificar o login: " + e.getMessage());
        }
        return usuario;
    }

}
