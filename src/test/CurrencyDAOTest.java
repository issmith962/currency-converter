package test;

import java.sql.Connection; 
import main.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyDAOTest {
    private Database db; 
    private Currency unitedStatesCur; 
    private Currency phillipinesCur; 

    @BeforeEach
    void setUp() throws Exception {
        db = new Database(); 
        unitedStatesCur = new Currency("USD", 1.0); 
        phillipinesCur = new Currency("PHP", 43.1232); 
        db.openConnection(); 
        db.createTables();
        db.closeConnection(true);
    }

    @AfterEach
    void tearDown() throws Exception {
        db.openConnection(); 
        db.clearTables();
        db.closeConnection(true);
    }

    @Test
    void addNewCurrency() throws Exception {
        Currency testResult = null;

        try {
            Connection conn = db.openConnection(); 
            CurrencyDAO cDao = new CurrencyDAO(conn); 
            cDao.createCurrency(unitedStatesCur);
            testResult = cDao.readExchangeRate(unitedStatesCur.getCurrencyCode());
            db.closeConnection(true);
        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        assertNotNull(testResult);
        assertEquals(unitedStatesCur, testResult);
    }
}
