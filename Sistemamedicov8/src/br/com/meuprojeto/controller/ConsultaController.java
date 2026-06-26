package br.com.meuprojeto.controller;

import br.com.meuprojeto.dao.ConsultaDAO;
import br.com.meuprojeto.dao.MedicoDAO;
import br.com.meuprojeto.dao.PacienteDAO;
import br.com.meuprojeto.model.Consulta;
import br.com.meuprojeto.model.Medico;
import br.com.meuprojeto.model.Paciente;
import br.com.meuprojeto.view.PainelConsulta;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;

public final class ConsultaController {

    private final PainelConsulta view;
    private final ConsultaDAO consultaDAO;
    private final PacienteDAO pacienteDAO;
    private final MedicoDAO medicoDAO;

    public ConsultaController(PainelConsulta view) {
        this.view = view;
        this.consultaDAO = new ConsultaDAO();
        this.pacienteDAO = new PacienteDAO();
        this.medicoDAO = new MedicoDAO();
        carregarCombos();
        atualizarTabela();
    }

    private void carregarCombos() {
        try {
            // Carregar pacientes
            List<Paciente> pacientes = pacienteDAO.listarTodos();
            view.getComboPaciente().removeAllItems();
            for (Paciente p : pacientes) {
                view.getComboPaciente().addItem(p.getNome());
            }

            // Carregar médicos
            List<Medico> medicos = medicoDAO.listarTodos();
            view.getComboMedico().removeAllItems();
            for (Medico m : medicos) {
                view.getComboMedico().addItem(m.getNome());
            }
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao carregar dados: " + e.getMessage());
        }
    }

    public void agendarConsulta() {
        try {
            // Verificar se há itens selecionados
            if (view.getComboPaciente().getSelectedItem() == null || 
                view.getComboMedico().getSelectedItem() == null) {
                view.mostrarMensagem("Selecione um paciente e um médico!");
                return;
            }
            
            // Pega os nomes selecionados
            String nomePaciente = (String) view.getComboPaciente().getSelectedItem();
            String nomeMedico = (String) view.getComboMedico().getSelectedItem();
            
            // Busca os objetos completos pelos nomes
            Paciente paciente = buscarPacientePorNome(nomePaciente);
            Medico medico = buscarMedicoPorNome(nomeMedico);
            
            if (paciente == null || medico == null) {
                view.mostrarMensagem("Erro ao encontrar paciente ou médico!");
                return;
            }
            
            String dataStr = view.getTxtData().getText().trim();
            String horaStr = view.getTxtHora().getText().trim();
            String observacao = view.getTxtObservacao().getText().trim();

            // Validações
            if (dataStr.isEmpty() || horaStr.isEmpty()) {
                view.mostrarMensagem("Data e hora são obrigatórios!");
                return;
            }

            // Formatar data e hora
            Date data = Date.valueOf(dataStr);
            
            if (!horaStr.contains(":")) {
                view.mostrarMensagem("Formato de hora inválido! Use HH:mm");
                return;
            }
            if (horaStr.length() == 5) {
                horaStr += ":00";
            }
            Time hora = Time.valueOf(horaStr);

            Consulta consulta = new Consulta();
            consulta.setPaciente(paciente);
            consulta.setMedico(medico);
            consulta.setData(data);
            consulta.setHora(hora);
            consulta.setObservacao(observacao);

            consultaDAO.agendar(consulta);
            view.mostrarMensagem("Consulta agendada com sucesso!");
            view.limparCampos();
            carregarCombos(); // Recarregar combos para garantir dados atualizados
            atualizarTabela();

        } catch (IllegalArgumentException e) {
            view.mostrarMensagem("Formato de data ou hora inválido! Use:\nData: AAAA-MM-DD\nHora: HH:mm");
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao agendar consulta: " + e.getMessage());
        } catch (Exception e) {
            view.mostrarMensagem("Erro inesperado: " + e.getMessage());
        }
    }

    // MÉTODOS AUXILIARES PARA BUSCAR OBJETOS PELO NOME
    private Paciente buscarPacientePorNome(String nome) throws SQLException {
        List<Paciente> pacientes = pacienteDAO.listarTodos();
        for (Paciente p : pacientes) {
            if (p.getNome().equals(nome)) {
                return p;
            }
        }
        return null;
    }

    private Medico buscarMedicoPorNome(String nome) throws SQLException {
        List<Medico> medicos = medicoDAO.listarTodos();
        for (Medico m : medicos) {
            if (m.getNome().equals(nome)) {
                return m;
            }
        }
        return null;
    }

    public void atualizarTabela() {
        try {
            DefaultTableModel model = (DefaultTableModel) view.getTabelaConsultas().getModel();
            model.setRowCount(0); // Limpar tabela

            List<Consulta> consultas = consultaDAO.listarTodas();
            SimpleDateFormat formatadorHora = new SimpleDateFormat("HH:mm");
            SimpleDateFormat formatadorData = new SimpleDateFormat("dd/MM/yyyy");
            for (Consulta c : consultas) {
                // Tratamento e Formatação da HORA
            String horaFormatada;
            if (c.getHora() != null) {
                horaFormatada = formatadorHora.format(c.getHora());
            } else {
                horaFormatada = "-"; 
            }
            String dataFormatada;
                if (c.getData() != null) {
                    dataFormatada = formatadorData.format(c.getData());
                } else {
                    dataFormatada = "-"; 
                }
                model.addRow(new Object[]{
                    c.getIdconsulta(),
                    c.getPaciente().getNome(),
                    c.getMedico().getNome(),
                    dataFormatada,
                    horaFormatada,
                    c.getObservacao()
                });
            }
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao carregar consultas: " + e.getMessage());
        }
    }

    // Método para excluir consulta
    public void excluirConsulta() {
        int linhaSelecionada = view.getTabelaConsultas().getSelectedRow();
        if (linhaSelecionada == -1) {
            view.mostrarMensagem("Selecione uma consulta na tabela para excluir.");
            return;
        }

        try {
            int idConsulta = (int) view.getTabelaConsultas().getValueAt(linhaSelecionada, 0);
            
            int resposta = JOptionPane.showConfirmDialog(
                view, 
                "Deseja realmente excluir esta consulta?", 
                "Confirmação", 
                JOptionPane.YES_NO_OPTION
            );

            if (resposta == JOptionPane.YES_OPTION) {
                consultaDAO.excluir(idConsulta);
                view.mostrarMensagem("Consulta excluída com sucesso!");
                atualizarTabela();
            }
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao excluir consulta: " + e.getMessage());
        }
    }
}