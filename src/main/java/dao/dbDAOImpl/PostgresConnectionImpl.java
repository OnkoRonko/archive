package dao.dbDAOImpl;

import org.postgresql.util.PSQLException;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresConnectionImpl extends ConnectionFactory {
    private final Properties config = new Properties();
    private final Properties initialQueries = new Properties();
    private final Properties paths = new Properties();

    public PostgresConnectionImpl() {
        try {
            paths.load(new FileInputStream("src/main/resources/paths.properties"));
            config.load(new FileInputStream(paths.getProperty("config")));
            initialQueries.load(new FileReader(paths.getProperty("initialQueries")));
            Class.forName(config.getProperty("driver"));
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            createDatabase();
        } catch (PSQLException throwables) {
//            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createDatabase() throws SQLException {
        var connection = DriverManager.getConnection(
            config.getProperty("host"),
            config.getProperty("login"),
            config.getProperty("password"));

        connection.prepareStatement(initialQueries.getProperty("createDb")).executeUpdate();
    }

    private void createTables(Connection connection) throws SQLException {
        var stm = connection.prepareStatement(initialQueries.getProperty("createArchives"));
        stm.executeUpdate();

        stm = connection.prepareStatement(initialQueries.getProperty("createFiles"));
        stm.executeUpdate();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:postgresql://localhost:5432/archivedb",
//            config.getProperty("fullPathToHost"),
            config.getProperty("login"),
            config.getProperty("password"));
    }
}
