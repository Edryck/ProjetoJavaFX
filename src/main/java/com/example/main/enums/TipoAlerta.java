package com.example.main.enums;

import javafx.scene.paint.Color;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.*;

public enum TipoAlerta {
    SUCESSO(MaterialDesignC.CHECK_CIRCLE, "#27ae60"), // Verde, ícone para sucesso
    ERRO(MaterialDesignA.ALERT_CIRCLE, "#e74c3c"), // Vermelho, ícone para erro
    ATENCAO(MaterialDesignA.ALERT, "#f39c12"), // Laranja, ícone para aviso
    ERRO_BD(MaterialDesignD.DATABASE_ALERT, "#e74c3c"), // Vermelho, ícone para erro na conexão com o banco de dados
    INFO(MaterialDesignI.INFORMATION, "#1e3231"), // Não sei qual cor é, mas tá na minha paleta de cores, ícone para informações
    VENDA_REG(MaterialDesignC.CART_CHECK, "#27ae60"), // Verde, ícone para aviso de sucesso ao registrar venda
    P_CAD(MaterialDesignP.PACKAGE_VARIANT_PLUS, "#27ae60"); // Verde, ícone de sucesso ao cadastrar produto

    private final Ikon codIcone;
    private final Color cor;

    TipoAlerta(Ikon codIcone, String cor) {
        this.codIcone = codIcone;
        this.cor = Color.web(cor);
    }

    public Ikon getCodIcone() {
        return codIcone;
    }

    public Color getCor() {
        return cor;
    }
}
