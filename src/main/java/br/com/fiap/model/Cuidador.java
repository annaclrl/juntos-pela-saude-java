package br.com.fiap.model;

public class Cuidador extends Pessoa {

    private Paciente paciente;

    public Cuidador() { }

    public Cuidador(int codigo, String nome, String email, String cpf, String telefone, int idade, Paciente paciente, Endereco endereco) {
        super(codigo,nome, email, cpf, telefone, endereco, idade);
        this.paciente = paciente;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }


    @Override
    public String toString() {
        return "\nCódigo: " + getCodigo() +
                "\nNome: " + getNome() +
                "\nIdade: " + getIdade() +
                "\nCPF: " + getCpf() +
                "\nTelefone: " + getTelefone() +
                "\nPaciente: " + (paciente != null ? paciente.getNome() : "Nenhum");
    }
}

