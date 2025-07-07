package com.example.main.model.vo;

import com.example.main.enums.StatusCompra;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoCompra {
    private int idPedido;
    private Fornecedor fornecedor; // Em vez de um int, guardamos o objeto Fornecedor completo!
    private LocalDate dataPedido;
    private LocalDate dataEntregaPrevista;
    private BigDecimal valorTotalNota;
    private StatusCompra status;

    private List<ItemPedido> itens = new ArrayList<>();

    public PedidoCompra() {
    }

    public PedidoCompra(int idPedido, Fornecedor fornecedor, LocalDate dataPedido, LocalDate dataEntregaPrevista, BigDecimal valorTotalNota, StatusCompra status, List<ItemPedido> itens) {
        this.idPedido = idPedido;
        this.fornecedor = fornecedor;
        this.dataPedido = dataPedido;
        this.dataEntregaPrevista = dataEntregaPrevista;
        this.valorTotalNota = valorTotalNota;
        this.status = status;
        this.itens = itens;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Fornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public LocalDate getDataEntregaPrevista() {
        return dataEntregaPrevista;
    }

    public void setDataEntregaPrevista(LocalDate dataEntregaPrevista) {
        this.dataEntregaPrevista = dataEntregaPrevista;
    }

    public BigDecimal getValorTotalNota() {
        return valorTotalNota;
    }

    public void setValorTotalNota(BigDecimal valorTotalNota) {
        this.valorTotalNota = valorTotalNota;
    }

    public StatusCompra getStatus() {
        return status;
    }

    public void setStatus(StatusCompra status) {
        this.status = status;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }
}
