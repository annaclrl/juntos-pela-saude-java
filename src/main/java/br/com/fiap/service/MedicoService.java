package br.com.fiap.service;

import br.com.fiap.dao.MedicoDao;
import br.com.fiap.model.Medico;
import br.com.fiap.model.Paciente;

import java.sql.SQLException;
import java.util.List;

public class MedicoService {

    private final MedicoDao medicoDao;
    private final ValidationService validationService;

    public MedicoService() throws SQLException, ClassNotFoundException {
        this.medicoDao = new MedicoDao();
        this.validationService = new ValidationService();
    }

    public boolean cadastrarMedico(Medico medico) throws SQLException {

        if (!validationService.validarNome(medico.getNome())) {
            System.out.println("Nome inválido! Não deve conter números ou caracteres especiais.");
            return false;
        }

        if (!validationService.validarCPF(medico.getCpf())) {
            System.out.println("CPF inválido!");
            return false;
        }

        if (!validationService.validarIdade(medico.getIdade())) {
            System.out.println("Idade inválida!");
            return false;
        }

        if (!validationService.validarEmail(medico.getEmail())) {
            System.out.println("E-mail inválido!");
            return false;
        }

        if (!validationService.validarTelefoneSecundario(medico.getTelefone1(), medico.getTelefone2())) {
            System.out.println("O telefone secundário não pode ser igual ao telefone principal!");
            return false;
        }

        if (medicoDao.buscarPorCpf(medico.getCpf()) != null) {
            System.out.println("Já existe um médico com este CPF!");
            return false;
        }

        if (medicoDao.buscarPorEmail(medico.getEmail()) != null) {
            System.out.println("Já existe um médico com este email!");
            return false;
        }

        if (medicoDao.buscarPorTelefone(medico.getTelefone1(), medico.getTelefone2()) != null) {
            System.out.println("Já existe um médico com estes telefones!");
            return false;
        }

        if (medicoDao.buscarPorCrm(medico.getCrm()) != null) {
            System.out.println("Já existe um médico com este CRM!");
            return false;
        }

        return medicoDao.inserir(medico);
    }

    public List<Medico> listarMedicos() throws SQLException {
        return medicoDao.listarTodos();
    }

    public Medico buscarPorCrm(int crm) throws SQLException {
        return medicoDao.buscarPorCrm(crm);
    }

    public boolean atualizarMedico(Medico medico) throws SQLException {
        return medicoDao.atualizar(medico);
    }

    public Medico buscarPorCodigo(int codigo) throws SQLException {
        return medicoDao.buscarPorCodigo(codigo);
    }


    public boolean deletarMedico(int codigo) throws SQLException {
        return medicoDao.deletar(codigo);
    }


    public void close() throws SQLException {
        medicoDao.close();
    }

}
