package com.example.main.model.dao;

public interface Validacoes {
    boolean emailJaExiste (String email);
    void cadastrar (String nome, String email, String senha);
    main.model.vo.Usuario login (String email);
}
