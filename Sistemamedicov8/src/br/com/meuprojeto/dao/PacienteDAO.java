package br.com.meuprojeto.dao;

import br.com.meuprojeto.model.Paciente;
import br.com.meuprojeto.util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public void adicionar(Paciente paciente) throws SQLException {
        String sql = "INSERT INTO paciente (nome, cpf, telefone) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, paciente.getTelefone());
            stmt.executeUpdate();
        }
    }

    public void atualizar(Paciente paciente) throws SQLException {
        String sql = "UPDATE paciente SET nome = ?, cpf = ?, telefone = ? WHERE idpaciente = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getCpf());
            stmt.setString(3, paciente.getTelefone());
            stmt.setInt(4, paciente.getIdpaciente());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM paciente WHERE idpaciente = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Paciente> listarTodos() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM paciente";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Paciente p = new Paciente(
                    rs.getInt("idpaciente"),
                    rs.getString("nome"),
                    rs.getString("cpf"),
                    rs.getString("telefone")
                );
                pacientes.add(p);
            }
        }
        return pacientes;
    }
}