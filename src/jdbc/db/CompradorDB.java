package jdbc.db;

import jdbc.Classes.Comprador;
import jdbc.conn.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompradorDB {

    public static void save(Comprador comprador) {
        String sql = "INSERT INTO `agencia`.`comprador` (`cpf`, `nome`) VALUES ('" + comprador.getCpf() + "', '" + comprador.getNome() + "');";
        Connection conn = ConnectionFactory.getConexao();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            ConnectionFactory.close(conn, stmt);
            System.out.println("Registro inserido com sucesso!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void delete(Comprador comprador) {
        if (comprador == null || comprador.getId() == null) {
            System.out.println("Não foi possivle excluir o registro!");
            return;
        }
        String sql = "DELETE FROM comprador WHERE `id` = '" + comprador.getId() + "'";
        Connection conn = ConnectionFactory.getConexao();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            ConnectionFactory.close(conn, stmt);
            System.out.println("Registro excluido com sucesso!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void update(Comprador comprador) {
        if (comprador == null || comprador.getId() == null) {
            System.out.println("Não foi possivle atualizar o registro!");
            return;
        }
        String sql =
                "UPDATE `agencia`.`comprador` SET `cpf` = '" + comprador.getCpf() + "', `nome` = '" + comprador.getNome() + "' WHERE (`id` = '" + comprador.getId() + "');";
        Connection conn = ConnectionFactory.getConexao();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            ConnectionFactory.close(conn, stmt);
            System.out.println("Registro atualizado com sucesso!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static List<Comprador> selectAll() {
        String sql = "SELECT id, nome, cpf FROM comprador";
        Connection conn = ConnectionFactory.getConexao();
        List<Comprador> compradorList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                compradorList.add(new Comprador
                        (rs.getInt("id"), rs.getString("nome"), rs.getString("cpf")));
            }
            ConnectionFactory.close(conn, stmt, rs);
            return compradorList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static List<Comprador> searchByName(String nome) {
        String sql = "SELECT id, nome, cpf FROM comprador WHERE nome like '%" + nome + "%'";
        Connection conn = ConnectionFactory.getConexao();
        List<Comprador> compradorList = new ArrayList<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                compradorList.add(new Comprador
                        (rs.getInt("id"), rs.getString("nome"), rs.getString("cpf")));
            }
            ConnectionFactory.close(conn, stmt, rs);
            return compradorList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static void selectMetaData() {
        String sql = "SELECT * FROM comprador";
        Connection conn = ConnectionFactory.getConexao();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();

            rs.next();
            int qtdColunas = rsmd.getColumnCount();
            System.out.println("Quantidade de colunas: " + qtdColunas);

            for (int i = 1; i <= qtdColunas; i++) {
                System.out.println("tabela: " + rsmd.getTableName(i));
                System.out.println("Nome coluna: " + rsmd.getColumnName(i));
                System.out.println("Tamanho coluna: " + rsmd.getColumnDisplaySize(i));
            }

            ConnectionFactory.close(conn, stmt, rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void checkDriverStatus() {
        Connection conn = ConnectionFactory.getConexao();

        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            if (dbmd.supportsResultSetType(ResultSet.TYPE_FORWARD_ONLY)) {
                System.out.println("Supporta TYPE_FORWARD_ONLY");
                if (dbmd.supportsResultSetConcurrency(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE)) {
                    System.out.println("e também suporta CONCUR_UPDATABLE");
                }
            }

            if (dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_INSENSITIVE)) {
                System.out.println("Supporta TYPE_SCROLL_INSENSITIVE");
                if (dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                    System.out.println("e também suporta CONCUR_UPDATABLE");
                }
            }

            if (dbmd.supportsResultSetType(ResultSet.TYPE_SCROLL_SENSITIVE)) {
                System.out.println("Supporta TYPE_SCROLL_SENSITIVE");
                if (dbmd.supportsResultSetConcurrency(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                    System.out.println("e também suporta CONCUR_UPDATABLE");
                }
            }

            ConnectionFactory.close(conn);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
