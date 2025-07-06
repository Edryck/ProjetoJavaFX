package com.example.main.interfaces;

import com.example.main.model.vo.Usuario;

public interface UsuarioInterface {
    boolean emailJaExiste (String email);
    void cadastrar (Usuario usuario);
    Usuario login (String email);
}
