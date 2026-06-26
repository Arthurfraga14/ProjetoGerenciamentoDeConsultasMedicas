package br.com.meuprojeto.controller;

import br.com.meuprojeto.dao.PacienteDAO;
import br.com.meuprojeto.model.Paciente;
import br.com.meuprojeto.view.PainelPaciente; 
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PacienteController {

    private final PainelPaciente view;
    private final PacienteDAO dao;
    private Integer idPacienteSelecionado = null;

    public PacienteController(PainelPaciente view) {
        this.view = view;
        this.dao = new PacienteDAO();
    }

    public void salvar() {
        // Pega os dados da tela
        String nome = view.getTxtNome().getText();
        String cpf = view.getTxtCpf().getText();
        String telefone = view.getTxtTelefone().getText();

        if (nome.trim().isEmpty() || cpf.trim().isEmpty()) {
            view.mostrarMensagem("Erro: Nome e CPF são obrigatórios!");
            return;
        }

        Paciente p = new Paciente();
        p.setNome(nome);
        p.setCpf(cpf);
        p.setTelefone(telefone);

        try {
            if (idPacienteSelecionado == null) {
                dao.adicionar(p);
                view.mostrarMensagem("Paciente cadastrado com sucesso!");
            } else {
                p.setIdpaciente(this.idPacienteSelecionado);
                dao.atualizar(p);
                view.mostrarMensagem("Paciente atualizado com sucesso!"+ this.idPacienteSelecionado);
                
            }
            // Garantindo que a variável de controle seja limpa
            view.limparCampos(); 
            atualizarTabela();
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao salvar no banco: " + e.getMessage());
        }
    }
    
    public void excluir() {
        if (this.idPacienteSelecionado == null) {
            view.mostrarMensagem("Selecione um paciente na tabela primeiro.");
            return;
        }
        
        try {
            int id = this.idPacienteSelecionado;
            int resposta = JOptionPane.showConfirmDialog(view, 
                    "Deseja realmente excluir este paciente?", "Confirmação", 
                    JOptionPane.YES_NO_OPTION);
            
            if (resposta == JOptionPane.YES_OPTION) {
                dao.excluir(id);
                view.mostrarMensagem("Paciente excluído com sucesso!");
                view.limparCampos();
                atualizarTabela();
            }
        } catch (SQLException e) {
             view.mostrarMensagem("Erro ao excluir. Verifique se o paciente possui consultas.");
        }
    }

    public void atualizarTabela() {
        JTable tabela = view.getTabelaPacientes(); 
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();
        model.setRowCount(0); 

        try {
            List<Paciente> pacientes = dao.listarTodos(); 
            for (Paciente p : pacientes) {
                model.addRow(new Object[]{
                    p.getIdpaciente(),
                    p.getNome(),
                    p.getCpf(),
                    p.getTelefone()
                });
            }
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao carregar pacientes: " + e.getMessage());
        }
    }
    
    public void selecionarPacienteDaTabela() {
        JTable tabela = view.getTabelaPacientes();
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada == -1) return;
        
        try {
            Integer id = (Integer) tabela.getValueAt(linhaSelecionada, 0);
            this.idPacienteSelecionado = id;
            String nome = tabela.getValueAt(linhaSelecionada, 1).toString();
            String telefone = tabela.getValueAt(linhaSelecionada, 2).toString();
            String cpf = tabela.getValueAt(linhaSelecionada, 3).toString();
            
            view.preencherCampos(nome, cpf, telefone);
            
        } catch (Exception e) {
            view.mostrarMensagem("Erro ao selecionar a linha: " + e.getMessage());
            this.idPacienteSelecionado = null;
            view.limparCampos();
        }
    }

    public void limparIdSelecionado() {
        this.idPacienteSelecionado = null;
    }
}