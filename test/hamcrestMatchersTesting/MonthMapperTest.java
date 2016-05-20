package hamcrestMatchersTesting;

import entity.Month;
import static hamcrestMatchers.CustomAbstractEntityClassMatcher.matches;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappers.MonthMapper;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utilities.DBconnector;
import utilities.PerformanceLogger;

public class MonthMapperTest {

    //Mapperclass
    private static MonthMapper monthMapper;

    //Helper entity
    private static Month month;

    //Database Connection
    private static DBconnector databaseConnector;
    private static Connection connection;

    //Database authentication
    private static String[] databaseHost = { "jdbc:oracle:thin:@127.0.0.1:1521:XE", "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat" };
    private static String[] databaseUsername = { "bobkoo", "cphbs96", "cphcd77" };
    private static String[] databasePassword = { "qwerty12345", "cphbs96", "cphcd77" };

    //logger
    private static String loggerName = "expensesCalculatorTester";
    private static String loggerPath = "/ExpensesCalculatorTester.log";
    private static Logger logger;
    private static PerformanceLogger performanceLogger;

    @BeforeClass
    public static void setUpClass() {
        //logger initialization
        performanceLogger = new PerformanceLogger();
        logger = performanceLogger.initLogger( loggerName, loggerPath );

        //database initialization
        databaseConnector = new DBconnector( databaseHost[ 1 ], databaseUsername[ 2 ], databasePassword[ 2 ], null );
        connection = databaseConnector.getConnection( logger );

        //mapper initialization
        monthMapper = new MonthMapper();
    }

    @AfterClass
    public static void tearDownClass() {
        performanceLogger = null;
        logger = null;

        try {
            connection.close();
        } catch ( SQLException ex ) {
            logger.log( Level.INFO, "Error while closing the connection{0}", ex );
        }
    }

    @Before
    public void setUp() {
        month = new Month();
        month.setName( "July 2016" );
        monthMapper.deleteAllMonths( connection, null );
        month = monthMapper.insertMonth( connection, null, month );
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetAllMonths() {
        List<Month> lm = monthMapper.getMonths( connection, null );
        assertThat( month, matches( lm.get( 0 ) ) );
    }

    @Test
    public void testGetMonthById() {
        System.out.println( "ID ?? : " + month.getId() );
        assertThat( month, matches( monthMapper.getMonthByID( connection, null, month.getId() ) ) );
    }

    @Test
    public void testInsertMonth() {
        Month monthToInsert = new Month();
        monthToInsert.setName( "december 2016" );
        assertThat( monthToInsert, matches( monthMapper.insertMonth( connection, null, monthToInsert ) ) );
    }

    @Test
    public void testUpdateMonth() {
        month.setName( "updated name" );
        assertThat( month, matches( monthMapper.updateMonth( connection, null, month.getId(), month ) ) );
    }

    @Test
    public void testDeleteMonth() {
        assertThat( true, is( monthMapper.deleteMonth( connection, null, month.getId() ) ) );
    }

    @Test
    public void testDeleteAllMonths() {
        assertThat( true, is( monthMapper.deleteAllMonths( connection, null ) ) );
    }
}
