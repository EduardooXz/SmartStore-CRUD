package com.smartstore.service;

import com.smartstore.database.Conexao;
import com.smartstore.model.Categoria;

import java.sql.*;

public class CategoriaManager {
    //Defini os tipos de categorias
    private static final String[] LIST_CATEGORIAS = {"ELETRONICOS", "MOVEIS", "ROUPAS", "REMEDIOS", "ELETRODOMESTICOS"};

    //Cadastra as categorias no BD
    public static void cadastrarCategoria() {
        String insertSql = "INSERT INTO categoria (nome) values (?)";
        String checkSql = "SELECT COUNT(*) FROM categoria";
        try (Connection conn = Conexao.conexaoBD()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(checkSql);
            //Evita duplicar as categorias no BD
            if (rs.next() && rs.getInt(1) == 0) {
                PreparedStatement stmt = conn.prepareStatement(insertSql);
                for (String categoria : LIST_CATEGORIAS) {
                    stmt.setString(1, categoria);
                    stmt.executeUpdate();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void listarCategorias() {
        String sql = "SELECT id, nome FROM categoria";
        try (Connection conn = Conexao.conexaoBD();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("\n===== CATEGORIAS =====");
            while (rs.next()) {
                System.out.println(
                        rs.getLong("id") + " - " +
                                rs.getString("nome")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Verifica se a categoria que o usuario digitou existe
    public static boolean categoriaExiste(long id) {
        String sql = "SELECT id FROM categoria WHERE id = ?";
        try (Connection conn = Conexao.conexaoBD();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next(); // true se encontrou
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //Retorna a categoria que o usuario quer adicionar no produto
    public static Categoria buscarCategoriaPorId(long id) {
        String sql = "SELECT * FROM categoria WHERE id = ?";
        try(Connection conn = Conexao.conexaoBD()) {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Categoria(rs.getLong("id"), rs.getString("nome"));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
