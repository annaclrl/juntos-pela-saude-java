package br.com.fiap.model;

public class Cuidador extends Pessoa {

    private int codigo;
    private Paciente paciente;

    public Cuidador() { }

    public Cuidador(int codigo, String nome, String email, String cpf, String telefone, int idade, Paciente paciente, Endereco endereco) {
        super(nome, email, cpf, telefone, endereco, idade);
        this.codigo = codigo;
        this.paciente = paciente;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    @Override
    public String toString() {
        return "\nCódigo: " + codigo +
                "\nNome: " + getNome() +
                "\nIdade: " + getIdade() +
                "\nCPF: " + getCpf() +
                "\nTelefone: " + getTelefone() +
                "\nPaciente: " + (paciente != null ? paciente.getNome() : "Nenhum");
    }
}

