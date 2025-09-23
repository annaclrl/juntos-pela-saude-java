package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Endereco;
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
            (CODIGO, NOME, EMAIL, CPF, TELEFONE, IDADE, CRM, ESPECIALIDADE, LOGRADOURO, NUMERO, COMPLEMENTO, CEP) 
            VALUES (SEQ_MEDICO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medico.getNome());
            ps.setString(2, medico.getEmail());
            ps.setString(3, medico.getCpf());
            ps.setString(4, medico.getTelefone());
            ps.setInt(5, medico.getIdade());
            ps.setInt(6, medico.getCrm());
            ps.setString(7, medico.getEspecialidade());
            ps.setString(8, medico.getEndereco().getLogradouro());
            ps.setInt(9, medico.getEndereco().getNumero());
            ps.setString(10, medico.getEndereco().getComplemento());
            ps.setString(11, medico.getEndereco().getCep());

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
            SET NOME=?, EMAIL=?, CPF=?, TELEFONE=?, IDADE=?, CRM=?, ESPECIALIDADE=?, LOGRADOURO=?, NUMERO=?, COMPLEMENTO=?, CEP=? 
            WHERE CODIGO=?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, medico.getNome());
            ps.setString(2, medico.getEmail());
            ps.setString(3, medico.getCpf());
            ps.setString(4, medico.getTelefone());
            ps.setInt(5, medico.getIdade());
            ps.setInt(6, medico.getCrm());
            ps.setString(7, medico.getEspecialidade());
            ps.setString(8, medico.getEndereco().getLogradouro());
            ps.setInt(9, medico.getEndereco().getNumero());
            ps.setString(10, medico.getEndereco().getComplemento());
            ps.setString(11, medico.getEndereco().getCep());
            ps.setInt(12, medico.getCodigo());

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
        Endereco endereco = new Endereco(
                rs.getString("LOGRADOURO"),
                rs.getInt("NUMERO"),
                rs.getString("COMPLEMENTO"),
                rs.getString("CEP")
        );

        return new Medico(
                rs.getInt("CODIGO"),
                rs.getString("NOME"),
                rs.getString("EMAIL"),
                rs.getString("CPF"),
                rs.getString("TELEFONE"),
                rs.getInt("IDADE"),
                rs.getInt("CRM"),
                rs.getString("ESPECIALIDADE"),
                endereco
        );
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
