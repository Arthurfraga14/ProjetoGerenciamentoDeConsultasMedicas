package br.com.meuprojeto.util;

import java.sql.*;

public class TestePortas {
    public static void main(String[] args) {
        System.out.println("=== TESTANDO PORTAS DO MYSQL ===");
        
        testarPorta(3306);
        testarPorta(3307);
    }
    
    private static void testarPorta(int porta) {
        String url = "jdbc:mysql://localhost:" + porta + "/welson";
        
        try {
            Connection conn = DriverManager.getConnection(url, "root", "");
            System.out.println("✅ PORTA " + porta + " - CONEXÃO BEM-SUCEDIDA!");
            conn.close();
        } catch (SQLException e) {
            System.out.println("❌ PORTA " + porta + " - FALHA: " + e.getMessage());
        }
    }
}