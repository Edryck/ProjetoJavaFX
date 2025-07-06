package com.example.main.model.vo;

import com.example.main.enums.StatusVenda;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    private Integer idVenda;
    private String nomeCliente;
    private LocalDateTime dataVenda;
    private BigDecimal valorTotal;
    private String formaPagamento;
    private StatusVenda status;
    private Integer quantidadeParcelas;
    private List<ItemVenda> itens = new ArrayList<>();

    public Venda() {
    }

    public Venda(Integer idVenda, LocalDateTime dataVenda, BigDecimal valorTotal, String formaPagamento, StatusVenda status, Integer quantidadeParcelas) {
        this.idVenda = idVenda;

        this.dataVenda = dataVenda;
        this.valorTotal = valorTotal;
        this.formaPagamento = formaPagamento;
        this.status = status;
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public Integer getIdVenda() {
        return idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public LocalDateTime getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(LocalDateTime dataVenda) {
        this.dataVenda = dataVenda;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public StatusVenda getStatus() {
        return status;
    }

    public void setStatus(StatusVenda status) {
        this.status = status;
    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }

    public List<ItemVenda> getItens() {
        return itens;
    }

    public void setItens(List<ItemVenda> itens) {
        this.itens = itens;
    }
}
