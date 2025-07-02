package com.example.main.model.dao;

import main.model.vo.Usuario;

public interface ManipulacaoUsuario {
    boolean emailJaExiste (String email);
    void cadastrar (String nome, String email, String senha);
    Usuario login (String email);
}
