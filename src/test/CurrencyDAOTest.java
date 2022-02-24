package test;

import java.beans.Transient;
import java.sql.Connection; 
import main.*;
import org.junit.jupiter.api.BeforeEach;

public class CurrencyDAOTest {
    private Database db; 

    @BeforeEach
    void setUp() throw Exception {
        db = new Database(); 
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
        String testResult = null;

        try {
            Connection conn = db.openConnection(); 
            CurrencyDAO cDao = new CurrencyDAO(conn); 
            cDao.createCurrency();
        }
    }
}
