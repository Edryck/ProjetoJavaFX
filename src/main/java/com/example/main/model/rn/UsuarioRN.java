package com.example.main.model.rn;

import com.example.main.model.dao.UsuarioDAO;

public class UsuarioRN {
    private final UsuarioDAO cadastroDAO = new UsuarioDAO();

    public boolean emailJaExiste (String email) {
        UsuarioDAO cadastroDAO = new UsuarioDAO();
        return cadastroDAO.emailJaExiste(email);
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
