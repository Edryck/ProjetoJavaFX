package com.example.main.model.dao;

import main.model.vo.Usuario;

public interface ManipulacaoUsuario {
    boolean emailJaExiste (String email);
    void cadastrar (Usuario usuario);
    Usuario login (String email);
}
