package br.com.fiap.service;

import br.com.fiap.dao.FuncionarioDao;
import br.com.fiap.model.Funcionario;

import java.sql.SQLException;
import java.util.List;

public class FuncionarioService {
    private final FuncionarioDao funcionarioDao;
    private final ValidationService validationService;

    public FuncionarioService() throws SQLException, ClassNotFoundException {
        this.funcionarioDao = new FuncionarioDao();
        this.validationService = new ValidationService();
    }

    public boolean cadastrarFuncionario(Funcionario funcionario) throws SQLException {
        if (!validationService.validarCPF(funcionario.getCpf())) {
            System.out.println("CPF inválido!");
            return false;
        }

        if (!validationService.validarIdade(funcionario.getIdade())) {
            System.out.println("Idade inválida!");
            return false;
        }

        if (!validationService.validarEmail(funcionario.getEmail())) {
            System.out.println("E-mail inválido!");
            return false;
        }

        if (!validationService.validarTelefoneSecundario(funcionario.getTelefone1(), funcionario.getTelefone2())) {
            System.out.println("O telefone secundário não pode ser igual ao telefone principal!");
            return false;
        }


        Funcionario existente = funcionarioDao.buscarPorCpf(funcionario.getCpf());
        if (existente != null) {
            System.out.println("Já existe um funcionário com este CPF!");
            return false;
        }

        return funcionarioDao.inserir(funcionario);
    }

    public List<Funcionario> listarFuncionarios() throws SQLException {
        return funcionarioDao.listarTodos();
    }

    public Funcionario buscarPorCpf(String cpf) throws SQLException {
        return funcionarioDao.buscarPorCpf(cpf);
    }

    public boolean atualizarFuncionario(Funcionario funcionario) throws SQLException {
        return funcionarioDao.atualizar(funcionario);
    }

    public Funcionario buscarPorCodigo(int codigo) throws SQLException {
        return funcionarioDao.buscarPorCodigo(codigo);
    }

    public boolean deletarFuncionario(int codigo) throws SQLException {
        return funcionarioDao.deletar(codigo);
    }


    public void close() throws SQLException {
        funcionarioDao.close();
    }
}
