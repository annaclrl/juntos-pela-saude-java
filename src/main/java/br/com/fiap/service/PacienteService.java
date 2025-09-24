package br.com.fiap.service;

import br.com.fiap.dao.PacienteDao;
import br.com.fiap.model.Medico;
import br.com.fiap.model.Paciente;

import java.sql.SQLException;
import java.util.List;

public class PacienteService {

    private final PacienteDao pacienteDao;
    private final ValidationService validationService;

    public PacienteService() throws SQLException, ClassNotFoundException {
        this.pacienteDao = new PacienteDao();
        this.validationService = new ValidationService();
    }

    public boolean cadastrarPaciente(Paciente paciente) throws SQLException {
        if (!validationService.validarCPF(paciente.getCpf())) {
            System.out.println("CPF inválido!");
            return false;
        }

        if (!validationService.validarIdade(paciente.getIdade())) {
            System.out.println("Idade inválida!");
            return false;
        }

        if (!validationService.validarEmail(paciente.getEmail())) {
            System.out.println("E-mail inválido!");
            return false;
        }

        Paciente existente = pacienteDao.buscarPorCpf(paciente.getCpf());
        if (existente != null) {
            System.out.println("Já existe um paciente com este CPF!");
            return false;
        }

        return pacienteDao.inserir(paciente);
    }

    public List<Paciente> listarPacientes() throws SQLException {
        return pacienteDao.listarTodos();
    }

    public Paciente buscarPorCpf(String cpf) throws SQLException {
        return pacienteDao.buscarPorCpf(cpf);
    }

    public boolean atualizarPaciente(Paciente paciente) throws SQLException {
        return pacienteDao.atualizar(paciente);
    }

    public Paciente buscarPorCodigo(int codigo) throws SQLException {
        return pacienteDao.buscarPorCodigo(codigo);
    }

    public boolean deletarPaciente(int codigo) throws SQLException {
        return pacienteDao.deletar(codigo);
    }


    public void close() throws SQLException {
        pacienteDao.close();
    }

}
