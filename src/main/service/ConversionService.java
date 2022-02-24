package main.service;

import main.dao.CurrencyDAO;
import main.dao.DataAccessException;
import main.dao.Database;
import main.model.Currency;
import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversion;

import java.sql.Connection;

public class ConversionService {
    public ConversionService() {}

    /**
     * Converts an amount of money from one currency to another, given the codes for both currencies.
     * Conversion Equation (pseudo): amount (in currencyFrom) * (currencyTo.rate / currencyFrom.rate)
     *
     * @param currencyFrom the currency code for the original amount given
     * @param currencyTo the currency code for the currency to convert to
     * @param amount a given amount of money in terms of currencyFrom
     * @return a new amount of money in terms of currencyTo. Will return -1 on invalid input
     */
    public double convertCurrency(String currencyFrom, String currencyTo, double amount) throws DataAccessException {
        /*  Input Validation  */
        if (amount == 0) {
            return amount;
        }
        if (amount < 0 || currencyFrom == null || currencyTo == null ||
                !this.isCurrencyCode(currencyFrom) || !this.isCurrencyCode(currencyTo)) {
            return -1;
        }

        /*  Retrieve exchange rates from database, convert, and return new amount  */
        Database db = new Database();
        try {
            Connection conn = db.openConnection();
            CurrencyDAO cDao = new CurrencyDAO(conn);

            Currency oldCurrency = cDao.readCurrency(currencyFrom);
            if (oldCurrency == null) {
                db.closeConnection(true);
                return -1;
            }
            Currency newCurrency = cDao.readCurrency(currencyTo);
            if (newCurrency == null) {
                db.closeConnection(true);
                return -1;
            }
            db.closeConnection(true);
            return amount * (newCurrency.getExchangeRate() / oldCurrency.getExchangeRate());
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(true);
            return -1;
        }
    }

    /**
     * Validates whether a string could be a currency code.
     *
     * @param currencyCode the string to check
     * @return true if currencyCode could be a code, false if not
     */
    private boolean isCurrencyCode(String currencyCode) {
        if (currencyCode.length() != 3) {
            return false;
        }
        if (!this.isAlpha(currencyCode)) {
            return false;
        }
        // TODO: API call to check if the currency code is real?

        return true;
    }

    /**
     * Checks whether a string is comprised only of letters, or includes other
     * symbols like numbers.
     *
     * @param s the string to be checked
     * @return try if s is comprised only of letters, false if not
     */
    private boolean isAlpha(String s) {
        return s.matches("[a-zA-Z]+");
    }
}
    