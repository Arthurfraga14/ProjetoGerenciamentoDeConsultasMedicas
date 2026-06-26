package br.com.meuprojeto.model;

public class Medico{
    private int idmedico;
    private String nome;
    private String especialidade;
    private String crm;

    public Medico(){

    }


    public Medico(int idmedico, String nome, String especialidade, String crm){
        this.idmedico= idmedico;
        this.nome= nome;
        this.especialidade= especialidade;
        this.crm= crm;
    }

    public int getIdmedico(){
        return idmedico;
    }

    public void setIdmedico(int idmedico){
        this.idmedico= idmedico;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome= nome;
    }

    public String getEspecialidade(){
        return especialidade;
    }

    public void setEspecialidade(String especialidade){
        this.especialidade= especialidade;
    }

    public String getCrm(){
        return crm;
    }

    public void setCrm(String crm){
        this.crm= crm;
    }

    public String detalhes_medico(){
        return "Médico: " + nome + "\nEspecialidade: " + especialidade + "\nCRM: " + crm;
    }

    @Override
    public String toString() {
    return this.getNome();
    }

}
