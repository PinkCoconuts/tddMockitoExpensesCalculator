package hamcrestMatchersTesting;

import entity.Month;
import static hamcrestMatchers.CustomAbstractEntityClassMatcher.matches;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
import utilities.DatabaseConnector;
import utilities.PerformanceLogger;

public class MonthMapperTest {

    //Mapperclass
    private static MonthMapper monthMapper;

    //Helper entity
    private static Month month;

    //Database Connection
    private static DatabaseConnector databaseConnector;
    private static Connection connection;

    //Database authentication
    private static String[] dbHosts = { "jdbc:oracle:thin:@127.0.0.1:1521:XE",
        "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat" };
    private static String[] dbUsernames = { "bobkoo", "cphbs96", "cphcd77" };
    private static String[] dbPasswords = { "qwerty12345", "cphbs96", "cphcd77" };

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
        databaseConnector = new DatabaseConnector(
                dbHosts[ 1 ], dbUsernames[ 2 ], dbPasswords[ 2 ], null );
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
        List<Month> months = new ArrayList();
        months.add( new Month( 1, "Jan 2016" ) );
        months.add( new Month( 2, "Feb 2016" ) );
        months.add( new Month( 3, "March 2016" ) );

        List<Month> insertedMonths = new ArrayList();
        insertedMonths.add( month ); //this object was added in the DB in the setUp() method
        insertedMonths.add( monthMapper
                .insertMonth( connection, logger, months.get( 0 ) ) );
        insertedMonths.add( monthMapper
                .insertMonth( connection, logger, months.get( 1 ) ) );
        insertedMonths.add( monthMapper
                .insertMonth( connection, logger, months.get( 2 ) ) );

        List<Month> dbMonths = monthMapper.getMonths( connection, logger );

        for ( int m = 0; m < insertedMonths.size(); m++ ) {
            for ( int i = 0; i < dbMonths.size(); i++ ) {
                if ( insertedMonths.get( m ).getId() == dbMonths.get( i ).getId() ) {
                    assertThat( dbMonths.get( i ), matches( insertedMonths.get( m ) ) );
                }
            }
        }
    }

    @Test
    public void testGetMonthById() {
        assertThat( monthMapper.getMonthByID( connection, null, month.getId() ),
                    matches( month ) );
    }

    @Test
    public void testInsertMonth() {
        Month monthToInsert = new Month();
        monthToInsert.setName( "december 2016" );
        assertThat( monthMapper.insertMonth( connection, null, monthToInsert ),
                    matches( monthToInsert ) );
    }

    @Test
    public void testUpdateMonth() {
        month.setName( "updated name" );
        assertThat( month, matches( monthMapper
                    .updateMonth( connection, null, month.getId(), month ) ) );
    }

    @Test
    public void testDeleteMonth() {
        assertThat( monthMapper.deleteMonth( connection, null, month.getId() ),
                    is( true ) );
    }

    @Test
    public void testDeleteAllMonths() {
        assertThat( monthMapper.deleteAllMonths( connection, null ), is( true ) );
    }
}
