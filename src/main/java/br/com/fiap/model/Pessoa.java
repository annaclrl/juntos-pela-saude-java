package br.com.fiap.model;

public abstract class Pessoa {

    private int codigo;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private Endereco endereco;
    private int idade;

    public Pessoa() { }

    public Pessoa(int codigo, String nome, String email, String cpf, String telefone, Endereco endereco, int idade) {
        this.codigo = codigo;
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.idade = idade;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public int getIdade() {
        return idade;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }



}
