package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Consulta;
import br.com.fiap.model.Medico;
import br.com.fiap.model.Paciente;

import java.sql.*;
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
            (ID_CONSULTA, ID_PACIENTE, ID_MEDICO, ID_FUNCIONARIO, DT_HR_CONSULTA, ST_CONSULTA)
            VALUES (SEQ_CONSULTA.NEXTVAL, ?, ?, ?, ?,?)
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
            SET ID_PACIENTE=?, ID_MEDICO=?, DATAHORA=?, STATUS=?
            WHERE ID_CONSULTA=?
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

    public Consulta buscarPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM T_JPS_CONUSLTA WHERE CODIGO = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToConsulta(rs);
                }
            }
        }
        return null;
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

    public boolean adicionarFeedback(Consulta consulta) throws SQLException {
        String sql = "UPDATE consultas SET feedback = ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, consulta.getFeedback());
            stmt.setInt(2, consulta.getCodigo());
            return stmt.executeUpdate() > 0;
        }
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

