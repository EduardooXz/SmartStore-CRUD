package com.smartstore.model;

public class Produto {

    private long id;
    private String nome;
    private double preco;
    private Categoria categoria;
    private int estoque;

    public Produto() {}

    public Produto(String nome, double preco, Categoria categoria, int estoque) {
        this.nome = nome;
        this.preco = preco;
        this.categoria = categoria;
        this.estoque = estoque;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getEstoque() {
        return estoque;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }

    public String toString() {
        return "ID: " + id +
                "\nNome: " + nome +
                "\nPreço: " + preco +
                "\nCategoria: " + categoria +
                "\nEstoque: " + estoque;
    }
}
