package database;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseDAO {
    protected Connection connection;

    
    public BaseDAO(Connection connection) {
        this.connection = connection;
    }

    
    protected void ensureConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DatabaseManager.getConnection();
        }
    }
}