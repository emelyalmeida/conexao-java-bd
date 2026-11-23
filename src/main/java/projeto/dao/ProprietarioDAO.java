package projeto.dao;

import projeto.Conexao;
import projeto.model.Proprietario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProprietarioDAO {

    public void inserir(Proprietario p) throws SQLException {
        String sql = "INSERT INTO tb_proprietario (cpf_proprietario, nm_proprietario, ds_profissao) VALUES (?, ?, ?)";
        try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, p.getCpf());
            ps.setString(2, p.getNome());
            ps.setString(3, p.getProfissao());
            ps.executeUpdate();
        }
    }

    public void atualizar(Proprietario p) throws SQLException {
        String sql = "UPDATE tb_proprietario SET nm_proprietario = ?, ds_profissao = ? WHERE cpf_proprietario = ?";
        try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getNome());
            ps.setString(2, p.getProfissao());
            ps.setLong(3, p.getCpf());
            ps.executeUpdate();
        }
    }

    public void excluir(long cpf) throws SQLException {
        String sql = "DELETE FROM tb_proprietario WHERE cpf_proprietario = ?";
        try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, cpf);
            ps.executeUpdate();
        }
    }

    public List<Proprietario> listar() throws SQLException {
        List<Proprietario> lista = new ArrayList<>();
        String sql = "SELECT cpf_proprietario, nm_proprietario, ds_profissao FROM tb_proprietario ORDER BY nm_proprietario";
        try (Connection c = Conexao.conectar();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Proprietario(rs.getLong(1), rs.getString(2), rs.getString(3)));
            }
        }
        return lista;
    }

    public int contarVeiculos(long cpf) throws SQLException {
        String sql = "SELECT fn_conta_veiculos_por_cpf(?)";
        try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, cpf);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public void cadastrarViaProcedure(Proprietario p) throws SQLException {
        try (Connection c = Conexao.conectar();
             CallableStatement cs = c.prepareCall("{CALL sp_cadastra_proprietario(?, ?, ?)}")) {
            cs.setLong(1, p.getCpf());
            cs.setString(2, p.getNome());
            cs.setString(3, p.getProfissao());
            cs.execute();
        }
    }
}
