package test;

import java.sql.Connection;

import main.dao.CurrencyDAO;
import main.dao.DataAccessException;
import main.dao.Database;
import main.model.Currency;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyDAOTest {
    private Database db;
    private Currency unitedStatesCur;
    private Currency philippinesCur;

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
        db.openConnection(); 
        db.createTables();
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
     * Simple test for createCurrency() and readCurrency() methods in CurrencyDAO.java.
     * Creates two currencies, then reads them out to verify that they match the original.
     * @throws Exception
     */
    @Test
    void createAndReadTest() throws Exception {
        Currency testResult1 = null;
        Currency testResult2 = null;

        try {
            Connection conn = db.openConnection(); 
            CurrencyDAO cDao = new CurrencyDAO(conn);

            cDao.createCurrency(unitedStatesCur);
            cDao.createCurrency(philippinesCur);

            testResult1 = cDao.readCurrency(unitedStatesCur.getCurrencyCode());
            testResult2 = cDao.readCurrency(philippinesCur.getCurrencyCode());
        } catch (DataAccessException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(true);
        }
        assertNotNull(testResult1);
        assertNotNull(testResult2);
        assertEquals(unitedStatesCur, testResult1);
        assertEquals(philippinesCur, testResult2);
    }

    /**
     * Simple positive test for the deleteCurrency() method in CurrencyDAO.java.
     * Creates two currencies, deletes them, then reads them to verify that
     * they were deleted.
     *
     * @throws Exception
     */
    @Test
    void deleteTest() throws Exception {
        Currency testResult1 = null;
        Currency testResult2 = null;

        try {
            Connection conn = db.openConnection();
            CurrencyDAO cDao = new CurrencyDAO(conn);

            cDao.createCurrency(unitedStatesCur);
            cDao.createCurrency(philippinesCur);

            cDao.deleteCurrency(unitedStatesCur);
            cDao.deleteCurrency(philippinesCur);

            testResult1 = cDao.readCurrency(unitedStatesCur.getCurrencyCode());
            testResult2 = cDao.readCurrency(philippinesCur.getCurrencyCode());
        } catch (DataAccessException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(true);
        }
        assertNull(testResult1);
        assertNull(testResult2);
    }

    /**
     * Simple positive test for the updateExchangeRate() method in CurrencyDAO.java.
     * Creates two currencies, alters their exchange rates in the database,
     * then reads them to verify that they were changed.
     *
     * @throws Exception
     */
    @Test
    void updateTest() throws Exception {
        Currency result1 = new Currency("USD", 3.21);
        Currency result2 = new Currency("PHP", 8.3);

        Currency testResult1 = null;
        Currency testResult2 = null;

        try {
            Connection conn = db.openConnection();
            CurrencyDAO cDao = new CurrencyDAO(conn);

            cDao.createCurrency(unitedStatesCur);
            cDao.createCurrency(philippinesCur);

            cDao.updateExchangeRate(unitedStatesCur.getCurrencyCode(), 3.21);
            cDao.updateExchangeRate(philippinesCur.getCurrencyCode(), 8.3);

            testResult1 = cDao.readCurrency(unitedStatesCur.getCurrencyCode());
            testResult2 = cDao.readCurrency(philippinesCur.getCurrencyCode());
        } catch (DataAccessException e) {
            e.printStackTrace();
        } finally {
            db.closeConnection(true);
        }
        assertNotNull(testResult1);
        assertNotNull(testResult2);
        assertEquals(result1, testResult1);
        assertEquals(result2, testResult2);
    }
}
