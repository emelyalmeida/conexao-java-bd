package projeto.dao;

import projeto.Conexao;
import projeto.model.Veiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {

    public void inserir(Veiculo v) throws SQLException {
        String sql = "INSERT INTO tb_veiculo (cd_placa, nm_marca, nm_modelo, ds_cor, fk_cpf_proprietario, ano_veiculo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, v.getPlaca());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getModelo());
            ps.setString(4, v.getCor());
            ps.setLong(5, v.getCpfProprietario());
            ps.setInt(6, v.getAno());
            ps.executeUpdate();
        }
    }

    public void atualizar(Veiculo v) throws SQLException {
        String sql = "UPDATE tb_veiculo SET nm_marca = ?, nm_modelo = ?, ds_cor = ?, fk_cpf_proprietario = ?, ano_veiculo = ? WHERE cd_placa = ?";
        try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, v.getMarca());
            ps.setString(2, v.getModelo());
            ps.setString(3, v.getCor());
            ps.setLong(4, v.getCpfProprietario());
            ps.setInt(5, v.getAno());
            ps.setString(6, v.getPlaca());
            ps.executeUpdate();
        }
    }

    public void excluir(String placa) throws SQLException {
        String sql = "DELETE FROM tb_veiculo WHERE cd_placa = ?";
        try (Connection c = Conexao.conectar(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, placa);
            ps.executeUpdate();
        }
    }

    public List<Veiculo> listar() throws SQLException {
        List<Veiculo> lista = new ArrayList<>();
        String sql = "SELECT cd_placa, nm_marca, nm_modelo, ds_cor, fk_cpf_proprietario, ano_veiculo FROM tb_veiculo ORDER BY ano_veiculo DESC";
        try (Connection c = Conexao.conectar();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Veiculo(rs.getString(1), rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getLong(5), rs.getInt(6)));
            }
        }
        return lista;
    }
}
