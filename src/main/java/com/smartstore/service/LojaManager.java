package com.smartstore.service;

import com.smartstore.database.Conexao;
import com.smartstore.dto.RelatorioCategoria;
import com.smartstore.model.Produto;
import com.smartstore.model.Categoria;
import com.smartstore.model.TypeCategoria;

import java.sql.*;
import javax.xml.transform.Result;
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

    public static List<RelatorioCategoria> gerarRelatorioPorCategoria() {
        List<RelatorioCategoria> relatorio = new ArrayList<>();
        String sql = """
            SELECT 
                c.nome AS categoria,
                COUNT(p.id) AS total_produtos,
                SUM(p.quantidade) AS total_estoque,
                SUM(p.preco * p.quantidade) AS valor_total
            FROM categoria c
            LEFT JOIN produto p ON p.idcategoria = c.id
            GROUP BY c.id, c.nome
        """;
        try(Connection conn = Conexao.conexaoBD()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                RelatorioCategoria relatorioCategoria = new RelatorioCategoria(
                        rs.getString("categoria"),
                        rs.getInt("total_produtos"),
                        rs.getInt("total_estoque"),
                        rs.getDouble("valor_total")
                );
                relatorio.add(relatorioCategoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return relatorio;
    }

    public List<Produto> buscarPorCategoria(TypeCategoria categoriaTipo) {

        List<Produto> produtos = new ArrayList<>();

        String sql = """
        SELECT p.id, p.nome, p.preco, p.quantidade,
               c.id as categoria_id, c.nome as categoria_nome
        FROM produto p
        INNER JOIN categoria c ON c.id = p.idcategoria
        WHERE c.nome = ?
    """;

        try (Connection conn = Conexao.conexaoBD();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoriaTipo.name());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Categoria categoria = new Categoria(
                        rs.getLong("categoria_id"),
                        rs.getString("categoria_nome")
                );

                Produto produto = new Produto();

                produto.setId(rs.getLong("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPreco(rs.getDouble("preco"));
                produto.setEstoque(rs.getInt("quantidade"));
                produto.setCategoria(categoria);

                produtos.add(produto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return produtos;
    }
}