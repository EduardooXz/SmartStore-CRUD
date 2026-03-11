package com.smartstore.dto;

import com.smartstore.model.Categoria;

public class RelatorioCategoria {
    private String categoria;
    private int totalProduto;
    private int totalEstoque;
    private double valorTotal;

    public RelatorioCategoria() {}

    public RelatorioCategoria(String categoria, int totalProduto, int totalEstoque, double valorTotal) {
        this.categoria = categoria;
        this.totalProduto = totalProduto;
        this.totalEstoque = totalEstoque;
        this.valorTotal = valorTotal;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public int getTotalProduto() {
        return totalProduto;
    }

    public void setTotalProduto(int totalProduto) {
        this.totalProduto = totalProduto;
    }

    public int getTotalEstoque() {
        return totalEstoque;
    }

    public void setTotalEstoque(int totalEstoque) {
        this.totalEstoque = totalEstoque;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
