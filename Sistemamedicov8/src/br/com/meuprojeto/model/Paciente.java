package br.com.meuprojeto.model;

public class Paciente {
    private int idpaciente;
    private String nome;
    private String cpf;
    private String telefone;

    public Paciente(){

    }

    public Paciente(int idpaciente, String nome, String cpf, String telefone){
        this.idpaciente= idpaciente;
        this.nome= nome;
        this.cpf= cpf;
        this.telefone= telefone;    
    }

    public int getIdpaciente(){
        return idpaciente;
    }

    public void setIdpaciente(int idpaciente){
        this.idpaciente= idpaciente;

    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome= nome;
    }

    public String getCpf(){
        return cpf;
    }

    public void setCpf(String cpf){
        this.cpf= cpf;
    }

    public String getTelefone(){
        return telefone;
    }

    public void setTelefone(String telefone){
        this.telefone= telefone;
    }

     public String detalhes() {
        return "Paciente: " + nome + "\nCPF: " + cpf + "\nTelefone: " + telefone;
    }

    @Override
    public String toString() {
    return this.getNome();
}

}
