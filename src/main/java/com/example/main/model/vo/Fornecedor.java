package com.example.main.model.vo;

import com.example.main.enums.StatusFornecedor;

public class Fornecedor {
    private int idFornecedor;
    private String nome;
    private String email;
    private String telefone;
    private StatusFornecedor status;

    public Fornecedor() {
    }

    public Fornecedor(int idFornecedor, String nome, String email, String telefone, StatusFornecedor status) {
        this.idFornecedor = idFornecedor;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.status = status;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public StatusFornecedor getStatus() {
        return status;
    }

    public void setStatus(StatusFornecedor status) {
        this.status = status;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(int idFornecedor) {
        this.idFornecedor = idFornecedor;
    }
}
