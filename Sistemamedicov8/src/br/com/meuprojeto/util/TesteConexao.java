package br.com.meuprojeto.util;

import com.sun.jdi.connect.spi.Connection;
import java.io.IOException;

public class TesteConexao {
    public static void main(String[] args) {
        System.out.println("=== TESTE FINAL DE CONEXÃO ===");
        try {
            Connection conn = (Connection) ConnectionFactory.getConnection();
            System.out.println("✅ CONEXÃO BEM-SUCEDIDA NA PORTA 3306!");
            conn.close();
        } catch (IOException e) {
            System.out.println("❌ ERRO: " + e.getMessage());
        }
    }
}