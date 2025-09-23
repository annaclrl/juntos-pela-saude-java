package br.com.fiap.service;

import br.com.fiap.dao.ConsultaDao;
import br.com.fiap.model.Consulta;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class ConsultaService {

    private final ConsultaDao consultaDao;

    public ConsultaService() throws SQLException, ClassNotFoundException {
        this.consultaDao = new ConsultaDao();
    }

    public boolean agendarConsulta(Consulta consulta) throws SQLException {
        if (consulta.getDataHora() == null || consulta.getDataHora().isBefore(LocalDateTime.now())) {
            System.out.println("Data/hora inválida! Deve ser futura.");
            return false;
        }

        if (consulta.getPaciente() == null || consulta.getMedico() == null) {
            System.out.println("Paciente ou médico não selecionado.");
            return false;
        }

        consulta.setStatus("AGENDADA");
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

    public void close() throws SQLException {
        consultaDao.close();
    }
}
