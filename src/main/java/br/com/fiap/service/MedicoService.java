package br.com.fiap.service;

import br.com.fiap.dao.MedicoDao;
import br.com.fiap.model.Medico;

import java.sql.SQLException;

public class MedicoService {

    private final MedicoDao medicoDao;
    private final ValidationService validationService;

    public MedicoService() throws SQLException, ClassNotFoundException {
        this.medicoDao = new MedicoDao();
        this.validationService = new ValidationService();
    }

    public boolean cadastrarMedico(Medico medico) throws SQLException {
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

        Medico existente = medicoDao.buscarPorCrm(medico.getCrm());
        if (existente != null) {
            System.out.println("Já existe um médico com este CRM!");
            return false;
        }

        return medicoDao.inserir(medico);
    }

    public void close() throws SQLException {
        medicoDao.close();
    }
}
