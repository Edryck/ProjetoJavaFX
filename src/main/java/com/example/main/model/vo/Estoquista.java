package com.example.main.model.vo;

public class Estoquista extends Usuario {
    public Estoquista(Integer idUsuario, String nomeUsuario, String email, String senhaUsuario) {
        super(idUsuario, nomeUsuario, email, senhaUsuario);
    }

    public void adicionarProduto (Produto produto) {
    }
}
