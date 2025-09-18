package br.com.fiap.model;

public class Paciente extends Pessoa {

    private int codigo;

    public Paciente() { }

    public Paciente(int codigo, String nome, String email, String cpf, String telefone, int idade, Endereco endereco) {
        super(nome, email, cpf, telefone, endereco, idade);
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "\nCódigo: " + codigo +
                "\nNome: " + getNome() +
                "\nIdade: " + getIdade() +
                "\nCPF: " + getCpf() +
                "\nTelefone: " + getTelefone();
    }
}

