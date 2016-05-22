package jUnitTesting;

import entity.Month;
import static hamcrestMatchers.CustomAbstractEntityClassMatcher.matches;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private static String[] databaseHost = { "jdbc:oracle:thin:@127.0.0.1:1521:XE", "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat" };
    private static String[] databaseUsername = { "bobkoo", "cphbs96", "cphcd77" };
    private static String[] databasePassword = { "qwerty12345", "cphbs96", "cphcd77" };

    //logger
    private static String loggerName = "expensesCalculatorTester";
    private static String loggerPath = "/ExpensesCalculatorTester.log";
    private static Logger logger;
    private static PerformanceLogger performanceLogger;

    public Boolean initializeConnection() {
        if ( connection != null ) {
            return true;
        } else {
            connection = databaseConnector.getConnection( logger );
        }
        return true;
    }

    @BeforeClass
    public static void beforeClass() {
        //logger initialization
        performanceLogger = new PerformanceLogger();
        logger = performanceLogger.initLogger( loggerName, loggerPath );

        //database initialization
        databaseConnector = new DatabaseConnector( databaseHost[ 1 ], databaseUsername[ 2 ], databasePassword[ 2 ], null );
        connection = databaseConnector.getConnection( logger );

        //mapper initialization
        monthMapper = new MonthMapper();
    }

    @AfterClass
    public static void afterClass() {
        performanceLogger = null;
        logger = null;

        try {
            connection.close();
        } catch ( SQLException ex ) {
            logger.log( Level.INFO, "Error while closing the connection{0}", ex );
        }
    }

    @Before
    public void before() {
        month = new Month();
        month.setName( "July 2016" );
        monthMapper.deleteAllMonths( connection, null );
        month = monthMapper.insertMonth( connection, null, month );
    }

    @After
    public void after() {
    }

    @Test
    public void testGetAllMonths() {
        List<Month> months = new ArrayList();
        months.add( new Month( 1, "Jan 2016" ) );
        months.add( new Month( 2, "Feb 2016" ) );
        months.add( new Month( 3, "March 2016" ) );

        List<Month> insertedMonths = new ArrayList();
        insertedMonths.add( month ); //this object was added in the DB in the setUp() method
        insertedMonths.add( monthMapper.insertMonth( connection, logger, months.get( 0 ) ) );
        insertedMonths.add( monthMapper.insertMonth( connection, logger, months.get( 1 ) ) );
        insertedMonths.add( monthMapper.insertMonth( connection, logger, months.get( 2 ) ) );

        List<Month> dbMonths = monthMapper.getMonths( connection, logger );

        for ( int m = 0; m < insertedMonths.size(); m++ ) {
            boolean isIdMatching = false, isNameMatching = false;
            for ( int i = 0; i < dbMonths.size(); i++ ) {
                if ( insertedMonths.get( m ).getId() == dbMonths.get( i ).getId() ) {
                    isIdMatching = true;
                }
                if ( insertedMonths.get( m ).getName().equals( dbMonths.get( i ).getName() ) ) {
                    isNameMatching = true;
                }
            }
            assertEquals( isIdMatching, true );
            assertEquals( isNameMatching, true );
        }
    }

    @Test
    public void testGetMonthById() {
        Month m = monthMapper.getMonthByID( connection, null, month.getId() );
        assertEquals( month.getId(), m.getId() );
        assertEquals( month.getName(), m.getName() );
    }

    @Test
    public void testInsertMonth() {
        Month monthToInsert = new Month();
        monthToInsert.setName( "december 2016" );
        assertEquals( monthToInsert, monthMapper.insertMonth( connection, null, monthToInsert ) );
    }

    @Test
    public void testUpdateMonth() {
        month.setName( "updated name" );
        Month um = monthMapper.updateMonth( connection, null, month.getId(), month );
        assertEquals( month.getId(), um.getId() );
        assertEquals( month.getName(), um.getName() );
    }

    @Test
    public void testDeleteMonth() {
        assertEquals( true, monthMapper.deleteMonth( connection, null, month.getId() ) );
    }

    @Test
    public void testDeleteAllMonths() {
        assertEquals( true, monthMapper.deleteAllMonths( connection, null ) );
    }
}
