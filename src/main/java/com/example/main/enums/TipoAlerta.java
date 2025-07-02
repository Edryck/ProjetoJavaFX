package com.example.main.enums;

import javafx.scene.paint.Color;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.materialdesign2.MaterialDesignA;
import org.kordamp.ikonli.materialdesign2.MaterialDesignC;
import org.kordamp.ikonli.materialdesign2.MaterialDesignI;
import org.kordamp.ikonli.materialdesign2.MaterialDesignP;

public enum TipoAlerta {
    SUCESSO(MaterialDesignC.CHECK_CIRCLE, "#27ae60"), // Verde
    ERRO(MaterialDesignA.ALERT_CIRCLE, "#e74c3c"), // Vermelho
    INFO(MaterialDesignI.INFORMATION_OUTLINE, "#1e3231"), // Não sei, tá na minha paleta de cores
    P_CAD(MaterialDesignP.PACKAGE_VARIANT_PLUS, "#27ae60"); // Verde P_CAD = produto cadastrado

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
