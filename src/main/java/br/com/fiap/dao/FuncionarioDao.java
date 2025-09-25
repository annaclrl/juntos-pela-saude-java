package br.com.fiap.dao;

import br.com.fiap.factory.ConnectionFactory;
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
            (ID_FUNCIONARIO, NM_FUNCIONARIO, EM_FUNCIONARIO, CPF_FUNCIONARIO, IDD_FUNCIONARIO, TEL1_FUNCIONARIO, TEL2_FUNCIONARIO )
            VALUES (SEQ_FUNCIONARIO.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getEmail());
            ps.setString(3, funcionario.getCpf());
            ps.setInt(5, funcionario.getIdade());
            ps.setString(4, funcionario.getTelefone1());
            ps.setString(4, funcionario.getTelefone2());
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
            SET NOME=?, EMAIL=?, CPF=?, IDADE=?, TELEFONE1=?, TELEFONE2=?
            WHERE CODIGO=?
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getEmail());
            ps.setString(3, funcionario.getCpf());
            ps.setInt(5, funcionario.getIdade());
            ps.setString(4, funcionario.getTelefone1());
            ps.setString(4, funcionario.getTelefone2());
            ps.setInt(10, funcionario.getCodigo());

            return ps.executeUpdate() > 0;
        }
    }

    public Funcionario buscarPorCodigo(int codigo) throws SQLException {
        String sql = "SELECT * FROM T_JPS_FUNCIONARIO WHERE CODIGO = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToFuncionario(rs);
                }
            }
        }
        return null;
    }

    public boolean deletar(int codigo) throws SQLException {
        String sql = "DELETE FROM T_JPS_FUNCIONARIO WHERE CODIGO=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, codigo);
            return ps.executeUpdate() > 0;
        }
    }

    public Funcionario buscarPorCpf(String cpf) throws SQLException {
        String sql = "SELECT * FROM T_JPS_FUNCIONARIO WHERE CPF=?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToFuncionario(rs);
                }
            }
        }
        return null;
    }

    private Funcionario mapResultSetToFuncionario(ResultSet rs) throws SQLException {
        return new Funcionario(
                rs.getInt("ID_FUNCIONARIO"),
                rs.getString("NM_FUNCIONARIO"),
                rs.getString("EM_FUNCIONARIO"),
                rs.getString("CPF_FUNCIONARIO"),
                rs.getInt("IDD_FUNCIONARIO"),
                rs.getString("TEL1_FUNCIONARIO"),
                rs.getString("TEL2_FUNCIONARIO")
        );
    }

    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}