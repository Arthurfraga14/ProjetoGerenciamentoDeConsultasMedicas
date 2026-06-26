package br.com.meuprojeto.dao;

import br.com.meuprojeto.model.Consulta;
import br.com.meuprojeto.model.Medico;
import br.com.meuprojeto.model.Paciente;
import br.com.meuprojeto.util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO {

    public void agendar(Consulta consulta) throws SQLException {
        String sql = "INSERT INTO consulta (medico_idmedico, paciente_idpaciente, data_consulta, hora_consulta, observacao) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, consulta.getMedico().getIdmedico());
            stmt.setInt(2, consulta.getPaciente().getIdpaciente());
            stmt.setDate(3, consulta.getData());
            stmt.setTime(4, consulta.getHora());
            stmt.setString(5, consulta.getObservacao());
            stmt.executeUpdate();
        }
    }
    
    public void atualizar(Consulta consulta) throws SQLException {
         String sql = "UPDATE consulta SET medico_idmedico = ?, paciente_idpaciente = ?, data_consulta = ?, hora_consulta = ?, observacao = ? WHERE idconsulta = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, consulta.getMedico().getIdmedico());
            stmt.setInt(2, consulta.getPaciente().getIdpaciente());
            stmt.setDate(3, consulta.getData());
            stmt.setTime(4, consulta.getHora());
            stmt.setString(5, consulta.getObservacao());
            stmt.setInt(6, consulta.getIdconsulta());
            stmt.executeUpdate();
        }
    }
    
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM consulta WHERE idconsulta = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Consulta> listarTodas() throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT c.*, " +
                     "p.nome AS nome_paciente, " +
                     "m.nome AS nome_medico, m.especialidade " +
                     "FROM consulta c " +
                     "JOIN paciente p ON c.paciente_idpaciente = p.idpaciente " +
                     "JOIN medico m ON c.medico_idmedico = m.idmedico";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Paciente p = new Paciente();
                p.setIdpaciente(rs.getInt("paciente_idpaciente"));
                p.setNome(rs.getString("nome_paciente"));
                
                Medico m = new Medico();
                m.setIdmedico(rs.getInt("medico_idmedico"));
                m.setNome(rs.getString("nome_medico"));
                m.setEspecialidade(rs.getString("especialidade"));
                
                Consulta c = new Consulta();
                c.setIdconsulta(rs.getInt("idconsulta"));
                c.setData(rs.getDate("data_consulta"));
                c.setHora(rs.getTime("hora_consulta"));
                c.setObservacao(rs.getString("observacao"));
                c.setPaciente(p); 
                c.setMedico(m);   
                
                consultas.add(c);
            }
        }
        return consultas;
    }
}