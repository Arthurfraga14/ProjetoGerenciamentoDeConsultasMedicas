package br.com.meuprojeto.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    
    // Porta 3306 (confirmado pelo teste)
    private static final String URL = "jdbc:mysql://localhost:3306/welson?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "root"; 
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver MySQL carregado com sucesso!");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL não encontrado!", e);
        }
    }

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Conexão com MySQL estabelecida!");
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco: " + e.getMessage(), e);
        }
    }
}