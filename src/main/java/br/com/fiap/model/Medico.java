package br.com.fiap.model;

public class Medico extends Pessoa {

    private String especialidade;
    private int crm;

    public Medico(int codigo, String nome, String email, String cpf, String telefone, int idade, int crm, String especialidade, Endereco endereco) {
        super(codigo, nome, email, cpf, telefone, endereco, idade);
        this.crm = crm;
        this.especialidade = especialidade;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public int getCrm() {
        return crm;
    }

    public void setCrm(int crm) {
        this.crm = crm;
    }

    public boolean isCrmValido() {
        return crm > 0 && String.valueOf(crm).length() >= 6;
    }

    @Override
    public String toString() {
        return "\nCódigo: " + getCodigo() +
                "\nNome: " + getNome() +
                "\nEspecialidade: " + especialidade +
                "\nCRM: " + crm;
    }
}
