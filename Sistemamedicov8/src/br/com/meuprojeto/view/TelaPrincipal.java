package br.com.meuprojeto.view;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        // Configuração da Janela
        setTitle("Sistema de Gerenciamento de Consultas");
        setSize(876, 734); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setLocationRelativeTo(null); 

        // Criar as ABAS
        JTabbedPane painelComAbas = new JTabbedPane();

        // Criar os painéis de cada ABA
        JPanel painelPaciente = new PainelPaciente();
        JPanel painelMedico = new PainelMedico();
        JPanel painelConsulta = new PainelConsulta();

        // Adicionar os painéis nas ABAS
        painelComAbas.addTab("Pacientes", painelPaciente);
        painelComAbas.addTab("Médicos", painelMedico);
        painelComAbas.addTab("Consultas", painelConsulta);

        // Adicionar as abas na Janela
        this.add(painelComAbas);
    }

    // Ponto de partida (MAIN)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal tela = new TelaPrincipal();
            tela.setVisible(true);
        });
    }
}