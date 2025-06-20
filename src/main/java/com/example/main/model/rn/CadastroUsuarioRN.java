package com.example.main.model.rn;

import com.example.main.connection.ConnectionFactory;
import com.example.main.controller.CadastroUsuarioController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CadastroUsuarioRN {

    public boolean emailJaExiste (String emailUsuario){
        String sql = "SELECT * FROM usuarios WHERE emailUsuario = ?";
        boolean emailExiste = false;

        try (Connection connection = ConnectionFactory.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, emailUsuario);

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

    public boolean confirmaSenha (String senha, String confirmacao) {
        return senha.equals(confirmacao);
    }

}
