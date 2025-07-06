package com.example.main.model.dao;

import com.example.main.interfaces.ItemVendaInterface;
import com.example.main.model.vo.ItemVenda;

import java.util.List;

public class ItemVendaDAO implements ItemVendaInterface {
    @Override
    public void salvar(ItemVenda item) {}

    @Override
    public List<ItemVenda> buscarItensPorVendaId(int vendaId) {
        return List.of();
    }
}
