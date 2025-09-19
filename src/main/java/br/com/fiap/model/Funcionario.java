package br.com.fiap.model;

public class Funcionario extends Pessoa {

    public Funcionario() { }

    public Funcionario(int codigo, String nome, String email, String cpf, String telefone, int idade, Endereco endereco) {
        super(codigo,nome, email, cpf, telefone, endereco, idade);
    }

    @Override
    public String toString() {
        return "\nCódigo: " + getCodigo() +
                "\nNome: " + getNome() +
                "\nIdade: " + getIdade() +
                "\nCPF: " + getCpf() +
                "\nTelefone: " + getTelefone();
    }
}
