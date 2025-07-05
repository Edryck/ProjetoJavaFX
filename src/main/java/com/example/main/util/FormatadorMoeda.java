package com.example.main.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatadorMoeda {
    public String formatador (BigDecimal texto) {
        Locale locale = new Locale("pt", "BR");
        NumberFormat formatadorM = NumberFormat.getCurrencyInstance(locale);
        String valorFormatado = formatadorM.format(texto);
        return valorFormatado;
    }
}
