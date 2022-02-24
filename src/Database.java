import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
    private Connection conn; 

    /**
     * Opens a connection to the project database. 
     * 
     * @return an open connection to the project database 
     * @throws DataAccessException
     */
    public Connection openConnection() throws DataAccessException {
        try {
            final String CONNECTION_URL = "jdbc:sqlite:currencyexchange.sqlite"; 

            conn = DriverManager.getConnection(CONNECTION_URL); 
            createTables(); 
            conn.setAutoCommit(false); 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to open a connection to the database."); 
        }
        return conn; 
    }

    /**
     * Closes a given connection to the project database. 
     * 
     * @param commit a boolean value determining whether to commit or roll back
     *               any changes to the database
     * @throws DataAccessException
     */
    public void closeConnection(boolean commit) throws DataAccessException {
        try {
            if (commit) {
                conn.commit(); 
            } else {
                conn.rollback(); 
            }
            conn.close(); 
            conn = null; 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Unable to close database connection."); 
        }
    }

    /**
     * Creates all tables in the database which do not exist yet. 
     * Currently, currency is the only table in the database. 
     * 
     * @throws DataAccessException
     */
    public void createTables() throws DataAccessException {
        String sql = "CREATE TABLE IF NOT EXISTS currency(" +
                         "currency_code TEXT NOT NULL UNIQUE," + 
                         "exchange_rate DOUBLE UNSIGNED NOT NULL" +
                         ");"; 
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql); 
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("SQL error encountered while creating tables"); 
        }
    }

    /**
     * Clears all tables in the database. 
     * Currently, currency is the only table in the database. 
     * 
     * @throws DataAccessException
     */
    public void clearTables() throws DataAccessException {
        String sql = "DELETE FROM currency;"; 
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql); 
        } catch (SQLException e) {
            throw new DataAccessException("SQL error encountered while clearing tables"); 
        }
    }
}
