package com.example.main.model.rn;

import com.example.main.connection.ConnectionFactory;
import com.example.main.model.dao.CadastroUsuarioDAO;
import com.example.main.model.dao.Validacoes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CadastroUsuarioRN {
    private final CadastroUsuarioDAO cadastroDAO = new CadastroUsuarioDAO();

    public boolean emailJaExiste (String email) {
        CadastroUsuarioDAO cadastroDAO = new CadastroUsuarioDAO();
        boolean existe = cadastroDAO.emailJaExiste(email);
        return existe;
    }

    public boolean confirmaSenha (String senha, String confirmacao) {
        return senha.equals(confirmacao);
    }

    public boolean autenticao (String email, String senha) {
        main.model.vo.Usuario usuario = cadastroDAO.login(email);

        if (usuario == null) {
            return false;
        }
        if (confirmaSenha(senha, usuario.getSenhaUsuario())) {
            return true;
        }
        return false;
    }
}
