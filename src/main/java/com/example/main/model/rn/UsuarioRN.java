package com.example.main.model.rn;

import com.example.main.model.dao.UsuarioDAO;
import com.example.main.model.vo.Usuario;

public class UsuarioRN {
    private final UsuarioDAO cadastroDAO = new UsuarioDAO();

    public boolean emailJaExiste (String email) {
        return cadastroDAO.emailJaExiste(email);
    }

    public boolean confirmaSenha (String senha, String confirmacao) {
        return senha.equals(confirmacao);
    }

    public boolean autenticao (String email, String senha) {
        Usuario usuario = cadastroDAO.login(email);

        if (usuario == null) {
            return false;
        }
        return confirmaSenha(senha, usuario.getSenhaUsuario());
    }

    public void cadastro(String nome, String email, String senha) {
        Usuario usuario = new Usuario();

        usuario.setNomeUsuario(nome);
        usuario.setemailUsuario(email);
        usuario.setSenhaUsuario(senha);

        cadastroDAO.cadastrar(usuario);
    }
}
