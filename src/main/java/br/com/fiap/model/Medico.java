package br.com.fiap.model;

public class Medico extends Pessoa {

    private int codigo;
    private String especialidade;
    private int crm;

    public Medico() { }

    public Medico(int codigo, String nome, String email, String cpf, String telefone, int idade, String especialidade, int crm, Endereco endereco) {
        super(nome, email, cpf, telefone, endereco, idade);
        this.codigo = codigo;
        this.especialidade = especialidade;
        this.crm = crm;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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
        return "\nCódigo: " + codigo +
                "\nNome: " + getNome() +
                "\nEspecialidade: " + especialidade +
                "\nCRM: " + crm;
    }
}
