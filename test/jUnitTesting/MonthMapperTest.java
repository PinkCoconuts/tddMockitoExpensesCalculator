package jUnitTesting;

import entity.Month;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappers.MonthMapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utilities.DBconnector;
import utilities.PerformanceLogger;

public class MonthMapperTest {

    private static MonthMapper monthMapper;
    private static DBconnector databaseConnector;
    private static String[] databaseHost = { "jdbc:oracle:thin:@127.0.0.1:1521:XE", "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat" };
    private static String[] databaseUsername = { "bobkoo", "cphbs96", "cphcd77" };
    private static String[] databasePassword = { "qwerty12345", "cphbs96", "cphcd77" };
    private static Connection dbConnection;
    private static Month month;
    private static String loggerName = "expensesCalculator";
    private static String loggerPath = "/ExpensesCalculator.log";
    private static Logger logger;
    private static PerformanceLogger performanceLogger;

    public MonthMapperTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        monthMapper = new MonthMapper();
        performanceLogger = new PerformanceLogger();
        logger = performanceLogger.initLogger( loggerName, loggerPath );
        databaseConnector = new DBconnector( databaseHost[ 1 ], databaseUsername[ 2 ], databasePassword[ 2 ], null );
        dbConnection = databaseConnector.getConnection( logger );
    }

    @AfterClass
    public static void tearDownClass() {
        try {
            dbConnection.close();
        } catch ( SQLException ex ) {
            logger.info( "Error closing the connection" );
        }
    }

    @Before
    public void setUp() {
        month = new Month();
        month.setName( "July 2016" );
        monthMapper.deleteAllMonths( dbConnection, null );
        month = monthMapper.insertMonth( dbConnection, null, month );
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetAllMonths() {
        List<Month> lm = monthMapper.getMonths( dbConnection, null );
        assertEquals( month.getId(), lm.get( 0 ).getId() );
        assertEquals( month.getName(), lm.get( 0 ).getName() );
    }

    @Test
    public void testGetMonthById() {
        Month m = monthMapper.getMonthByID( dbConnection, null, month.getId() );
        assertEquals( month.getId(), m.getId() );
        assertEquals( month.getName(), m.getName() );
    }

    @Test
    public void testInsertMonth() {
        Month monthToInsert = new Month();
        monthToInsert.setName( "december 2016" );
        assertEquals( monthToInsert, monthMapper.insertMonth( dbConnection, null, monthToInsert ) );
    }

    @Test
    public void testUpdateMonth() {
        month.setName( "updated name" );
        Month um = monthMapper.updateMonth( dbConnection, null, month.getId(), month );
        assertEquals( month.getId(), um.getId() );
        assertEquals( month.getName(), um.getName() );
    }

    @Test
    public void testDeleteMonth() {
        assertEquals( true, monthMapper.deleteMonth( dbConnection, null, month.getId() ) );
    }

    @Test
    public void testDeleteAllMonths() {
        assertEquals( true, monthMapper.deleteAllMonths( dbConnection, null ) );
    }
}
