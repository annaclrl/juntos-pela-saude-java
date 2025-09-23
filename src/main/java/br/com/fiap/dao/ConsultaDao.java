package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Consulta;
import br.com.fiap.model.Medico;
import br.com.fiap.model.Paciente;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDao implements AutoCloseable  {

    private final Connection conn;

    public ConsultaDao() throws SQLException, ClassNotFoundException {
        this.conn = ConnectionFactory.getConnection();
    }

    public boolean inserir(Consulta consulta) throws SQLException {
        String sql = """
            INSERT INTO T_JPS_CONSULTA 
            (CODIGO, PACIENTE_CODIGO, MEDICO_CODIGO, DATAHORA, STATUS)
            VALUES (SEQ_CONSULTA.NEXTVAL, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, consulta.getPaciente().getCodigo());
            ps.setInt(2, consulta.getMedico().getCodigo());
            ps.setTimestamp(3, Timestamp.valueOf(consulta.getDataHora()));
            ps.setString(4, consulta.getStatus());

            return ps.executeUpdate() > 0;
        }
    }

    public List<Consulta> listarTodos() throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_CONSULTA";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                consultas.add(mapResultSetToConsulta(rs));
            }
        }
        return consultas;
    }

    public boolean atualizar(Consulta consulta) throws SQLException {
        String sql = """
            UPDATE T_JPS_CONSULTA 
            SET PACIENTE_CODIGO=?, MEDICO_CODIGO=?, DATAHORA=?, STATUS=?
            WHERE CODIGO=?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, consulta.getPaciente().getCodigo());
            ps.setInt(2, consulta.getMedico().getCodigo());
            ps.setTimestamp(3, Timestamp.valueOf(consulta.getDataHora()));
            ps.setString(4, consulta.getStatus());
            ps.setInt(5, consulta.getCodigo());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deletar(int codigo) throws SQLException {
        String sql = "DELETE FROM T_JPS_CONSULTA WHERE CODIGO=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Consulta> listarPorPaciente(int pacienteCodigo) throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_CONSULTA WHERE PACIENTE_CODIGO=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pacienteCodigo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    consultas.add(mapResultSetToConsulta(rs));
                }
            }
        }
        return consultas;
    }

    public List<Consulta> listarPorMedico(int medicoCodigo) throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_CONSULTA WHERE MEDICO_CODIGO=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, medicoCodigo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    consultas.add(mapResultSetToConsulta(rs));
                }
            }
        }
        return consultas;
    }


    private Consulta mapResultSetToConsulta(ResultSet rs) throws SQLException {
        Paciente paciente = new Paciente();
        paciente.setCodigo(rs.getInt("PACIENTE_CODIGO"));

        Medico medico = new Medico();
        medico.setCodigo(rs.getInt("MEDICO_CODIGO"));

        return new Consulta(
                rs.getInt("CODIGO"),
                paciente,
                medico,
                rs.getTimestamp("DATAHORA").toLocalDateTime(),
                rs.getString("STATUS")
        );
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}

