package test;

import main.dao.CurrencyDAO;
import main.dao.DataAccessException;
import main.dao.Database;
import main.model.Currency;
import main.service.ConversionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.shadow.com.univocity.parsers.conversions.Conversion;

import javax.xml.crypto.Data;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ConversionServiceTest {
    private Database db;
    private Currency unitedStatesCur;
    private Currency philippinesCur;
    private Currency euroCur;

    /**
     * Sets up the database tables before every run. Initializes control objects.
     *
     * @throws Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        db = new Database();
        unitedStatesCur = new Currency("USD", 1.0);
        philippinesCur = new Currency("PHP", 43.1232);
        euroCur = new Currency("EUR", 0.82);
        Connection conn = db.openConnection();
        db.createTables();
        try {
            CurrencyDAO cDao = new CurrencyDAO(conn);
            cDao.createCurrency(unitedStatesCur);
            cDao.createCurrency(philippinesCur);
            cDao.createCurrency(euroCur);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        db.closeConnection(true);
    }

    /**
     * Clears the database tables after every test run.
     *
     * @throws Exception
     */
    @AfterEach
    void tearDown() throws Exception {
        db.openConnection();
        db.clearTables();
        db.closeConnection(true);
    }

    /**
     * Simple test for convertCurrency method in ConversionService.java.
     * Tries some permutations of conversion to make sure they match the instances
     * outside the database.
     *
     * @throws Exception
     */
    @Test
    void convertCurrencyTest() throws Exception {
        double controlResult0 = -1.0;
        double controlResult1 = 57.0 * (philippinesCur.getExchangeRate() / unitedStatesCur.getExchangeRate());
        double controlResult2 = 21.0 * (unitedStatesCur.getExchangeRate() / philippinesCur.getExchangeRate());
        double controlResult3 = 32.0 * (euroCur.getExchangeRate() / unitedStatesCur.getExchangeRate());
        double controlResult4 = 44.5 * (unitedStatesCur.getExchangeRate() / euroCur.getExchangeRate());
        double controlResult5 = 56.8 * (philippinesCur.getExchangeRate() / euroCur.getExchangeRate());
        double controlResult6 = 57.0 * (euroCur.getExchangeRate() / philippinesCur.getExchangeRate());

        double testResult0 = -1;
        double testResult1 = -1;
        double testResult2 = -1;
        double testResult3 = -1;
        double testResult4 = -1;
        double testResult5 = -1;
        double testResult6 = -1;

        ConversionService conversionService = new ConversionService();
        try {
            testResult0 = conversionService.convertCurrency(unitedStatesCur.getCurrencyCode(),
                    "What", 10);
            testResult1 = conversionService.convertCurrency(unitedStatesCur.getCurrencyCode(),
                    philippinesCur.getCurrencyCode(), 57);
            testResult2 = conversionService.convertCurrency(philippinesCur.getCurrencyCode(),
                    unitedStatesCur.getCurrencyCode(), 21);
            testResult3 = conversionService.convertCurrency(unitedStatesCur.getCurrencyCode(),
                    euroCur.getCurrencyCode(), 32);
            testResult4 = conversionService.convertCurrency(euroCur.getCurrencyCode(),
                    unitedStatesCur.getCurrencyCode(), 44.5);
            testResult5 = conversionService.convertCurrency(euroCur.getCurrencyCode(),
                    philippinesCur.getCurrencyCode(), 56.8);
            testResult6 = conversionService.convertCurrency(philippinesCur.getCurrencyCode(),
                    euroCur.getCurrencyCode(), 57);
        } catch (DataAccessException e) {
            e.printStackTrace();
            fail();
        }
        assertEquals(controlResult0, testResult0);
        assertEquals(controlResult1, testResult1);
        assertEquals(controlResult2, testResult2);
        assertEquals(controlResult3, testResult3);
        assertEquals(controlResult4, testResult4);
        assertEquals(controlResult5, testResult5);
        assertEquals(controlResult6, testResult6);
    }
}
