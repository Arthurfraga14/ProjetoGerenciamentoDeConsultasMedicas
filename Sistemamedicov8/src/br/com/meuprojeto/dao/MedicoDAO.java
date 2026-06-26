package br.com.meuprojeto.dao;

import br.com.meuprojeto.model.Medico;
import br.com.meuprojeto.util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDAO {

    public void adicionar(Medico medico) throws SQLException {
        String sql = "INSERT INTO medico (nome, especialidade, crm) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getEspecialidade());
            stmt.setString(3, medico.getCrm());
            stmt.executeUpdate();
        }
    }
    
    public void atualizar(Medico medico) throws SQLException {
        String sql = "UPDATE medico SET nome = ?, especialidade = ?, crm = ? WHERE idmedico = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, medico.getNome());
            stmt.setString(2, medico.getEspecialidade());
            stmt.setString(3, medico.getCrm());
            stmt.setInt(4, medico.getIdmedico());
            stmt.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM medico WHERE idmedico = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Medico> listarTodos() throws SQLException {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM medico";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Medico m = new Medico(
                    rs.getInt("idmedico"),
                    rs.getString("nome"),
                    rs.getString("especialidade"),
                    rs.getString("crm")
                );
                medicos.add(m);
            }
        }
        return medicos;
    }
}