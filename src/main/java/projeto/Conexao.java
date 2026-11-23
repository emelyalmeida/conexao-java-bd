package projeto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = System.getenv().getOrDefault(
            "DB_URL", "jdbc:mysql://localhost:3306/db_estacionamento");
    private static final String USER = System.getenv().getOrDefault("DB_USER", "root");
    private static final String SENHA = System.getenv().getOrDefault("DB_PASSWORD", "");

    public static Connection conectar() {
        Connection conexao = null;
        try {
            conexao = DriverManager.getConnection(URL, USER, SENHA);
            System.out.println("Conectou!");
        } catch (SQLException e) {
            System.out.println("Erro: " + e.getMessage());
        }
        return conexao;
    }
}
