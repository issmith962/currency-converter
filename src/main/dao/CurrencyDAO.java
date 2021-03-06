package main.dao;

import main.model.Currency;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException; 

public class CurrencyDAO {
    private final Connection conn; 

    public CurrencyDAO(Connection conn) {
        this.conn = conn; 
    }; 

    /** 
     * Clears the currency table in the database. 
     * 
     * @throws DataAccessException
    */
    public void clearTable() throws DataAccessException {
        String sql = "DELETE FROM currency"; 

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace(); 
            throw new DataAccessException("Error found while clearing currency table in the database."); 
        }
    }

    /**
     * Creates a new currency in the database. 
     * 
     * @param newCurrency the Currency object to be added.
     * @throws DataAccessException
     */
    public void createCurrency(Currency newCurrency) throws DataAccessException {
        String sql = "INSERT INTO currency (currency_code, exchange_rate) VALUES(?,?)"; 

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, newCurrency.getCurrencyCode()); 
            stmt.setDouble(2, newCurrency.getExchangeRate()); 
            stmt.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace(); 
            throw new DataAccessException("Error found while adding new currency to the database."); 
        }
    }

    /**
     * Deletes a given currency from the database. 
     * 
     * @param currency the Currency object to be deleted.
     */
    public void deleteCurrency(Currency currency) throws DataAccessException {
        String sql = "DELETE FROM currency WHERE currency_code = ?"; 

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, currency.getCurrencyCode()); 
            stmt.executeUpdate(); 
        } catch (SQLException e) {
            e.printStackTrace(); 
            throw new DataAccessException("Error found while deleting a currency from the database."); 
        }
    }

    /**
     * Reads the current exchange rate of a given currency, stores in a Currency object.
     * 
     * @param currencyCode the currency code for the currency to be retrieved.
     * @return a Currency object representing the currency retrieved from the 
     *         database, or null if currency is not found. 
     * @throws DataAccessException
     */
    public Currency readCurrency(String currencyCode) throws DataAccessException {
        ResultSet resultSet = null; 
        Currency resultCurrency = null; 
        String sql = "SELECT exchange_rate FROM currency WHERE currency_code = ?"; 

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, currencyCode); 
            resultSet = stmt.executeQuery(); 
            if (resultSet.next()) {
                resultCurrency = new Currency(currencyCode, resultSet.getDouble("exchange_rate"));
            }
            return resultCurrency; 
        } catch (SQLException e) {
            e.printStackTrace(); 
            throw new DataAccessException("Error found while getting the current " +
                                          "exchange rate of a currency from the database"); 
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Updates the current exchange rate of a currency in the database. 
     * 
     * @param currencyCode the currency code for the currency to be updated.
     * @param newExchangeRate the new exchange rate to replace the current rate in the database.
     * @throws DataAccessException
     */
    public void updateExchangeRate(String currencyCode, double newExchangeRate) throws DataAccessException {
        String sql = "UPDATE currency SET exchange_rate = ? WHERE currency_code = ?"; 
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newExchangeRate); 
            stmt.setString(2, currencyCode); 

            stmt.executeUpdate(); 
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while updating the exchange rate of " +
                                          "a currency in the database."); 
        }
    }
}
