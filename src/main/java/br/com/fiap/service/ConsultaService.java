package br.com.fiap.service;

import br.com.fiap.dao.ConsultaDao;
import br.com.fiap.model.Consulta;
import br.com.fiap.model.Paciente;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ConsultaService {

    private final ConsultaDao consultaDao;

    public ConsultaService() throws SQLException, ClassNotFoundException {
        this.consultaDao = new ConsultaDao();
    }

    public boolean cadastrarConsulta(Consulta consulta) throws SQLException {
        if (consulta.getDataHora() == null || consulta.getDataHora().isBefore(LocalDateTime.now())) {
            System.out.println("Data/hora inválida! Deve ser futura.");
            return false;
        }

        if (consulta.getPaciente() == null || consulta.getMedico() == null) {
            System.out.println("Paciente ou médico não selecionado.");
            return false;
        }

        consulta.setStatus("CONFIRMADA");
        return consultaDao.inserir(consulta);
    }

    public List<Consulta> listarConsultas() throws SQLException {
        return consultaDao.listarTodos();
    }

    public List<Consulta> listarConsultasPorPaciente(int pacienteCodigo) throws SQLException {
        return consultaDao.listarPorPaciente(pacienteCodigo);
    }

    public List<Consulta> listarConsultasPorMedico(int medicoCodigo) throws SQLException {
        return consultaDao.listarPorMedico(medicoCodigo);
    }

    public boolean adicionarFeedback(Consulta consulta) throws SQLException {
        if (consulta.getFeedback() == null || consulta.getFeedback().isBlank()) {
            return false;
        }
        return consultaDao.adicionarFeedback(consulta);
    }

    public Consulta buscarPorCodigo(int codigo) throws SQLException {
        return consultaDao.buscarPorCodigo(codigo);
    }

    public void close() throws SQLException {
        consultaDao.close();
    }
}
