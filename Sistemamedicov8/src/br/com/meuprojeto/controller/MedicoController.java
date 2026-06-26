package br.com.meuprojeto.controller;

import br.com.meuprojeto.dao.MedicoDAO;
import br.com.meuprojeto.model.Medico;
import br.com.meuprojeto.view.PainelMedico;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class MedicoController {

    private final PainelMedico view;
    private final MedicoDAO dao;
    private Integer idMedicoSelecionado = null; 


    public MedicoController(PainelMedico view) {
        this.view = view;
        this.dao = new MedicoDAO();
    }

    public void salvar() {
        String nome = view.getTxtNome().getText();
        String crm = view.getTxtCRM().getText();
        String especialidade = view.getTxtEspecialidade().getText();
        if (nome.trim().isEmpty() || crm.trim().isEmpty() || especialidade.trim().isEmpty()) {
            view.mostrarMensagem("Erro: Todos os campos são obrigatórios!");
            return;
        }

        Medico m = new Medico();
        m.setNome(nome); 
        m.setCrm(crm);
        m.setEspecialidade(especialidade);

        try {
            // 2. Verifica se o ID interno existe para decidir se é ATUALIZAÇÃO ou INSERÇÃO
            if (this.idMedicoSelecionado == null) { 
                // Inserção
                dao.adicionar(m);
                view.mostrarMensagem("Médico cadastrado com sucesso!");
            } else {
                // Atualização
                m.setIdmedico(this.idMedicoSelecionado);
                dao.atualizar(m);
                view.mostrarMensagem("Médico atualizado com sucesso! ID: " + this.idMedicoSelecionado);
                this.idMedicoSelecionado = null; // Limpa o ID após a atualização
            }
            
            view.limparCampos();
            atualizarTabela();
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao salvar no banco: " + e.getMessage());
        }
    }

    public void excluir() {
        if (this.idMedicoSelecionado == null) { 
            view.mostrarMensagem("Selecione um médico na tabela primeiro.");
            return;
        }

        try {
            int resposta = JOptionPane.showConfirmDialog(view, 
                    "Deseja realmente excluir este médico?", "Confirmação", 
                    JOptionPane.YES_NO_OPTION);

            if (resposta == JOptionPane.YES_OPTION) {
                dao.excluir(this.idMedicoSelecionado);
                view.mostrarMensagem("Médico excluído com sucesso! ID: " + this.idMedicoSelecionado);
                
                view.limparCampos();
                this.idMedicoSelecionado = null; // Zera o ID de controle após a exclusão
                atualizarTabela();
            }
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao excluir. Verifique se o médico possui consultas.");
        }
    }

    public void atualizarTabela() {
        DefaultTableModel model = (DefaultTableModel) view.getTabelaMedicos().getModel();
        model.setRowCount(0);

        try {
            List<Medico> medicos = dao.listarTodos();
            for (Medico m : medicos) {
                model.addRow(new Object[]{
                    m.getIdmedico(),
                    m.getNome(),
                    m.getCrm(),
                    m.getEspecialidade()
                });
            }
        } catch (SQLException e) {
            view.mostrarMensagem("Erro ao carregar médicos: " + e.getMessage());
        }
    }

    public void selecionarMedicoDaTabela() {
        int linhaSelecionada = view.getTabelaMedicos().getSelectedRow();
        if (linhaSelecionada == -1) return;

        try {
            Integer id = (Integer) view.getTabelaMedicos().getValueAt(linhaSelecionada, 0);
            this.idMedicoSelecionado = id;
            
            // 5. Captura os outros campos
            String nome = view.getTabelaMedicos().getValueAt(linhaSelecionada, 1).toString();
            String crm = view.getTabelaMedicos().getValueAt(linhaSelecionada, 2).toString();
            String especialidade = view.getTabelaMedicos().getValueAt(linhaSelecionada, 3).toString();

            // 6. Preenche a View
            view.preencherCampos(nome, crm, especialidade);
            
        } catch (Exception e) {
             view.mostrarMensagem("Erro ao selecionar linha: " + e.getMessage());
             this.idMedicoSelecionado = null;
        }
    }
}