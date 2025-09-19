package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Cuidador;
import br.com.fiap.model.Endereco;
import br.com.fiap.model.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuidadorDao {

    private final Connection conn;

    public CuidadorDao() throws SQLException, ClassNotFoundException {
        this.conn = ConnectionFactory.getConnection();
    }

    public boolean inserir(Cuidador cuidador) throws SQLException {
        String sql = """
            INSERT INTO T_JPS_CUIDADOR
            (CODIGO, NOME, EMAIL, CPF, TELEFONE, IDADE, LOGRADOURO, NUMERO, COMPLEMENTO, CEP, PACIENTE_CODIGO)
            VALUES (SEQ_CUIDADOR.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cuidador.getNome());
            ps.setString(2, cuidador.getEmail());
            ps.setString(3, cuidador.getCpf());
            ps.setString(4, cuidador.getTelefone());
            ps.setInt(5, cuidador.getIdade());
            ps.setString(6, cuidador.getEndereco().getLogradouro());
            ps.setInt(7, cuidador.getEndereco().getNumero());
            ps.setString(8, cuidador.getEndereco().getComplemento());
            ps.setString(9, cuidador.getEndereco().getCep());
            // Item 2: permite paciente nulo
            ps.setObject(10, cuidador.getPaciente() != null ? cuidador.getPaciente().getCodigo() : null);

            return ps.executeUpdate() > 0;
        }
    }

    public List<Cuidador> listarTodos() throws SQLException {
        List<Cuidador> cuidadores = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_CUIDADOR";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                cuidadores.add(mapResultSetToCuidador(rs));
            }
        }
        return cuidadores;
    }

    public boolean atualizar(Cuidador cuidador) throws SQLException {
        String sql = """
            UPDATE T_JPS_CUIDADOR
            SET NOME=?, EMAIL=?, CPF=?, TELEFONE=?, IDADE=?, LOGRADOURO=?, NUMERO=?, COMPLEMENTO=?, CEP=?, PACIENTE_CODIGO=?
            WHERE CODIGO=?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cuidador.getNome());
            ps.setString(2, cuidador.getEmail());
            ps.setString(3, cuidador.getCpf());
            ps.setString(4, cuidador.getTelefone());
            ps.setInt(5, cuidador.getIdade());
            ps.setString(6, cuidador.getEndereco().getLogradouro());
            ps.setInt(7, cuidador.getEndereco().getNumero());
            ps.setString(8, cuidador.getEndereco().getComplemento());
            ps.setString(9, cuidador.getEndereco().getCep());
            ps.setObject(10, cuidador.getPaciente() != null ? cuidador.getPaciente().getCodigo() : null);
            ps.setInt(11, cuidador.getCodigo());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deletar(int codigo) throws SQLException {
        String sql = "DELETE FROM T_JPS_CUIDADOR WHERE CODIGO=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            return ps.executeUpdate() > 0;
        }
    }

    public List<Cuidador> buscarPorPaciente(int pacienteCodigo) throws SQLException {
        List<Cuidador> cuidadores = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_CUIDADOR WHERE PACIENTE_CODIGO=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pacienteCodigo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cuidadores.add(mapResultSetToCuidador(rs));
                }
            }
        }
        return cuidadores;
    }

    private Cuidador mapResultSetToCuidador(ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco(
                rs.getString("LOGRADOURO"),
                rs.getInt("NUMERO"),
                rs.getString("COMPLEMENTO"),
                rs.getString("CEP")
        );

        Paciente paciente = new Paciente();
        paciente.setCodigo(rs.getInt("PACIENTE_CODIGO"));

        return new Cuidador(
                rs.getInt("CODIGO"),
                rs.getString("NOME"),
                rs.getString("EMAIL"),
                rs.getString("CPF"),
                rs.getString("TELEFONE"),
                rs.getInt("IDADE"),
                paciente,
                endereco
        );
    }

    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
