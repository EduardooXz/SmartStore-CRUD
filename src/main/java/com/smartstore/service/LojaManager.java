package com.smartstore.service;

import com.smartstore.database.Conexao;
import com.smartstore.model.Produto;
import com.smartstore.model.Categoria;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LojaManager {

    public static void cadastrarProduto(Produto produto) {
        String sql = "INSERT INTO produto (nome, preco, quantidade, idcategoria) values (?,?,?,?)";
        try(Connection conn = Conexao.conexaoBD()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getEstoque());
            stmt.setLong(4, produto.getCategoria().getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
}

    public static List<Produto> listarProdutos() {

        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT p.id, p.nome, p.preco, p.quantidade, c.id AS categoria_id, c.nome AS categoria_nome " +
                "FROM produto p JOIN categoria c ON p.idcategoria = c.id";

        try (Connection conn = Conexao.conexaoBD();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto produto = new Produto();
                produto.setId(rs.getLong("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setEstoque(rs.getInt("quantidade"));

                Categoria categoria = new Categoria();
                categoria.setId(rs.getLong("categoria_id"));
                categoria.setNome(rs.getString("categoria_nome"));

                produtos.add(produto);
                produto.setCategoria(categoria);

            }

        } catch (SQLException e) {
                e.printStackTrace();
        }

        return produtos;
    }
}