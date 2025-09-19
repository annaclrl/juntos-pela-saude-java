package br.com.fiap.service;

public class ValidationService {

    public boolean validarCPF(String cpf) {
        if (cpf == null) return false;
        String cpfNumeros = cpf.replaceAll("\\D", "");
        return cpfNumeros.length() == 11;
    }

    public boolean validarIdade(int idade) {
        return idade > 0 && idade < 120;
    }

    public boolean validarEmail(String email) {
        if (email == null || email.isBlank()) return false;
        return email.contains("@");
    }
}
