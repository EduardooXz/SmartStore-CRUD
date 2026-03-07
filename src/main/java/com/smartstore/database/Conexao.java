package com.smartstore.database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Conexao {

    private static final String URL;
    private static final String USER;
    private static final String PASSWORD;

    static {
        try (InputStream input = Conexao.class
                .getClassLoader()
                .getResourceAsStream("database.properties")) {

            if (input == null) {
                throw new RuntimeException("application.properties não encontrado!");
            }

            Properties props = new Properties();
            props.load(input);

            URL      = props.getProperty("db.url");
            USER     = props.getProperty("db.user");
            PASSWORD = props.getProperty("db.password");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar configurações do banco", e);
        }
    }

    public static Connection conexaoBD() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}