package com.smartstore.service;

import com.smartstore.database.Conexao;
import com.smartstore.model.Produto;

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

    public static void listarProdutos() {

        String sql = "SELECT * FROM produto";

        try (Connection conn = Conexao.conexaoBD();
             PreparedStatement stmt = conn.prepareStatement(sql);
             java.sql.ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n====== LISTA DE PRODUTOS =====");

            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Nome: " + rs.getString("nome"));
                System.out.println("Preço: " + rs.getDouble("preco"));
                System.out.println("Quantidade: " + rs.getInt("quantidade"));
                System.out.println("-------------------------");
            }

        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
}