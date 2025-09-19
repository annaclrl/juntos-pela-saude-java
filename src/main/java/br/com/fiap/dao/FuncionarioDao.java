package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
import br.com.fiap.model.Endereco;
import br.com.fiap.model.Funcionario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDao {

    private final Connection conn;

    public FuncionarioDao() throws SQLException, ClassNotFoundException {
        this.conn = ConnectionFactory.getConnection();
    }

    public boolean inserir(Funcionario funcionario) throws SQLException {
        String sql = """
            INSERT INTO T_JPS_FUNCIONARIO
            (CODIGO, NOME, EMAIL, CPF, TELEFONE, IDADE, LOGRADOURO, NUMERO, COMPLEMENTO, CEP)
            VALUES (SEQ_FUNCIONARIO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getEmail());
            ps.setString(3, funcionario.getCpf());
            ps.setString(4, funcionario.getTelefone());
            ps.setInt(5, funcionario.getIdade());
            ps.setString(6, funcionario.getEndereco().getLogradouro());
            ps.setInt(7, funcionario.getEndereco().getNumero());
            ps.setString(8, funcionario.getEndereco().getComplemento());
            ps.setString(9, funcionario.getEndereco().getCep());

            return ps.executeUpdate() > 0;
        }
    }

    public List<Funcionario> listarTodos() throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM T_JPS_FUNCIONARIO";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                funcionarios.add(mapResultSetToFuncionario(rs));
            }
        }
        return funcionarios;
    }

    public boolean atualizar(Funcionario funcionario) throws SQLException {
        String sql = """
            UPDATE T_JPS_FUNCIONARIO
            SET NOME=?, EMAIL=?, CPF=?, TELEFONE=?, IDADE=?, LOGRADOURO=?, NUMERO=?, COMPLEMENTO=?, CEP=?
            WHERE CODIGO=?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getEmail());
            ps.setString(3, funcionario.getCpf());
            ps.setString(4, funcionario.getTelefone());
            ps.setInt(5, funcionario.getIdade());
            ps.setString(6, funcionario.getEndereco().getLogradouro());
            ps.setInt(7, funcionario.getEndereco().getNumero());
            ps.setString(8, funcionario.getEndereco().getComplemento());
            ps.setString(9, funcionario.getEndereco().getCep());
            ps.setInt(10, funcionario.getCodigo());

            return ps.executeUpdate() > 0;
        }
    }

    public boolean deletar(int codigo) throws SQLException {
        String sql = "DELETE FROM T_JPS_FUNCIONARIO WHERE CODIGO=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            return ps.executeUpdate() > 0;
        }
    }

    private Funcionario mapResultSetToFuncionario(ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco(
                rs.getString("LOGRADOURO"),
                rs.getInt("NUMERO"),
                rs.getString("COMPLEMENTO"),
                rs.getString("CEP")
        );

        return new Funcionario(
                rs.getInt("CODIGO"),
                rs.getString("NOME"),
                rs.getString("EMAIL"),
                rs.getString("CPF"),
                rs.getString("TELEFONE"),
                rs.getInt("IDADE"),
                endereco
        );
    }

    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}

