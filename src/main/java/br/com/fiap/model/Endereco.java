package br.com.fiap.model;

public class Endereco {

    private String logradouro;
    private int numero;
    private String endereco;
    private String cep;

    public Endereco(){

    }

    public Endereco(String logradouro, int numero, String endereco, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.endereco = endereco;
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
}

