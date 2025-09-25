package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Medico;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicoDao implements AutoCloseable {

    private final Connection conn;

    public MedicoDao() throws SQLException, ClassNotFoundException {
        this.conn = ConnectionFactory.getConnection();
    }

    public boolean inserir(Medico medico) throws SQLException {
        String sql = """
            INSERT INTO T_JPS_MEDICO 
            (ID_MEDICO, NM_MEDICO, EM_MEDICO, CPF_MEDICO, IDD_MEDICO, TEL1_MEDICO,TEL2_MEDICO, CRM_MEDICO, ESP_MEDICO) 
            VALUES (SEQ_MEDICO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medico.getNome());
            ps.setString(2, medico.getEmail());
            ps.setString(3, medico.getCpf());
            ps.setInt(6, medico.getIdade());
            ps.setString(4, medico.getTelefone1());
            ps.setString(4, medico.getTelefone2());
            ps.setInt(6, medico.getCrm());
            ps.setString(7, medico.getEspecialidade());
            return ps.executeUpdate() > 0;
        }
    }

    public List<Medico> listarTodos() throws SQLException {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_MEDICO";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                medicos.add(mapResultSetToMedico(rs));
            }
        }
        return medicos;
    }

    public boolean atualizar(Medico medico) throws SQLException {
        String sql = """
            UPDATE T_JPS_MEDICO 
            SET NM_MEDICO=?, EM_MEDICO=?, CPF_MEDICO=?, IDD_MEDICO=?, TEL1_MEDICO=?, TEL2_MEDICO=? CRM_MEDICO=?, ESP_MEDICO=? 
            WHERE ID_MEDICO=?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medico.getNome());
            ps.setString(2, medico.getEmail());
            ps.setString(3, medico.getCpf());
            ps.setInt(4, medico.getIdade());
            ps.setString(5, medico.getTelefone1());
            ps.setString(6, medico.getTelefone2());
            ps.setInt(7, medico.getCrm());
            ps.setString(8, medico.getEspecialidade());
            ps.setInt(9, medico.getCodigo());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deletar(int codigo) throws SQLException {
        String sql = "DELETE FROM T_JPS_MEDICO WHERE CODIGO=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            return ps.executeUpdate() > 0;
        }
    }

    public Medico buscarPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM T_JPS_MEDICO WHERE CODIGO = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMedico(rs);
                }
            }
        }
        return null;
    }

    public Medico buscarPorCrm(int crm) throws SQLException {
        String sql = "SELECT * FROM T_JPS_MEDICO WHERE CRM=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, crm);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToMedico(rs);
                }
            }
        }
        return null;
    }

    private Medico mapResultSetToMedico(ResultSet rs) throws SQLException {

        return new Medico(
                rs.getInt("CODIGO"),
                rs.getString("NOME"),
                rs.getString("EMAIL"),
                rs.getString("CPF"),
                rs.getInt("IDADE"),
                rs.getString("TELEFONE1"),
                rs.getString("TELEFONE2"),
                rs.getInt("CRM"),
                rs.getString("ESPECIALIDADE")
        );
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
