package com.smartstore.model;

public enum TypeCategoria {
    ELETRONICOS("ELETRONICOS"),
    ALIMENTOS("ALIMENTOS"),
    ROUPAS("ROUPAS"),
    REMEDIOS("REMEDIOS"),
    ELETRODOMESTICOS("ELETRODOMESTICOS");

    private final String valorBanco;

    TypeCategoria(String valorBanco) {
        this.valorBanco = valorBanco;
    }

    public String getValorBanco() {
        return valorBanco;
    }
}
