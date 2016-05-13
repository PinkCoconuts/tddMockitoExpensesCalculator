package mappers;

import entity.Month;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
//        System.out.println( "Result from the delete: " + monthMapper.deleteAllMonths( dbConnection ) );
        month = monthMapper.insertMonth( dbConnection, month );
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetAllMonths() {
        assertEquals( month.getId(), monthMapper.getMonths( dbConnection ).get( 0 ).getId() );
        assertEquals( month.getName(), monthMapper.getMonths( dbConnection ).get( 0 ).getName() );
    }

    @Test
    public void testGetMonthById() {
        assertEquals( month.getId(), monthMapper.getMonthByID( dbConnection, month.getId() ).getId() );
        assertEquals( month.getName(), monthMapper.getMonthByID( dbConnection, month.getId() ).getName() );
    }

    @Test
    public void testDeleteMonth() {
        assertEquals( 1, monthMapper.deleteMonth( dbConnection, month.getId() ) );
    }
}
