package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Endereco;
import br.com.fiap.model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDao  implements  AutoCloseable{

    private final Connection conn;

    public PacienteDao() throws SQLException, ClassNotFoundException {
        this.conn = ConnectionFactory.getConnection();
    }

    public boolean inserir(Paciente paciente) throws SQLException {
        String sql = """
            INSERT INTO T_JPS_PACIENTE 
            (CODIGO, NOME, EMAIL, CPF, TELEFONE, IDADE, LOGRADOURO, NUMERO, COMPLEMENTO, CEP) 
            VALUES (SEQ_PACIENTE.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, paciente.getNome());
            ps.setString(2, paciente.getEmail());
            ps.setString(3, paciente.getCpf());
            ps.setString(4, paciente.getTelefone());
            ps.setInt(5, paciente.getIdade());
            ps.setString(6, paciente.getEndereco().getLogradouro());
            ps.setInt(7, paciente.getEndereco().getNumero());
            ps.setString(8, paciente.getEndereco().getComplemento());
            ps.setString(9, paciente.getEndereco().getCep());

            return ps.executeUpdate() > 0;
        }
    }

    public List<Paciente> listarTodos() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_PACIENTE";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                pacientes.add(mapResultSetToPaciente(rs));
            }
        }
        return pacientes;
    }

    public boolean atualizar(Paciente paciente) throws SQLException {
        String sql = """
            UPDATE T_JPS_PACIENTE 
            SET NOME=?, EMAIL=?, CPF=?, TELEFONE=?, IDADE=?, LOGRADOURO=?, NUMERO=?, COMPLEMENTO=?, CEP=? 
            WHERE CODIGO=?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, paciente.getNome());
            ps.setString(2, paciente.getEmail());
            ps.setString(3, paciente.getCpf());
            ps.setString(4, paciente.getTelefone());
            ps.setInt(5, paciente.getIdade());
            ps.setString(6, paciente.getEndereco().getLogradouro());
            ps.setInt(7, paciente.getEndereco().getNumero());
            ps.setString(8, paciente.getEndereco().getComplemento());
            ps.setString(9, paciente.getEndereco().getCep());
            ps.setInt(10, paciente.getCodigo());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deletar(int codigo) throws SQLException {
        String sql = "DELETE FROM T_JPS_PACIENTE WHERE CODIGO=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            return ps.executeUpdate() > 0;
        }
    }

    public Paciente buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM T_JPS_PACIENTE WHERE CPF=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPaciente(rs);
                }
            }
        }
        return null;
    }

    private Paciente mapResultSetToPaciente(ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco(
                rs.getString("LOGRADOURO"),
                rs.getInt("NUMERO"),
                rs.getString("COMPLEMENTO"),
                rs.getString("CEP")
        );

        return new Paciente(
                rs.getInt("CODIGO"),
                rs.getString("NOME"),
                rs.getString("EMAIL"),
                rs.getString("CPF"),
                rs.getString("TELEFONE"),
                rs.getInt("IDADE"),
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
