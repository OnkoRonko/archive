package dao.dbDAOImpl;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class ConnectionFactory {
    abstract Connection getConnection() throws SQLException;
}
