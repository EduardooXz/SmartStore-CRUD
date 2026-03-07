package com.smartstore.model;

public class Produto {

    private int id;
    private String nome;
    private double preco;
    private String categoria;
    private int estoque;

    public Produto(int id, String name, double preco, String categoria, int estoque) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        this.estoque = estoque;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void atualizarEstoque(int quantidade) {
        estoque += quantidade;
    }

    public String toString() {
        return "ID: " + id +
                "\nNome: " + nome +
                "\nPreço: " + preco +
                "\nCategoria: " + categoria +
                "\nEstoque: " + estoque;
    }
}
