package mappers;

import entity.Category;
import entity.Month;
import entity.MonthTransaction;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utilities.DBconnector;
import utilities.PerformanceLogger;

public class MonthTransactionMapperTest {

    private static MonthTransactionMapper monthTransactionMapper;
    private static Month month;
    private static Category category;
    private static MonthMapper monthMapper;
    private static CategoryMapper categoryMapper;
    private static DBconnector databaseConnector;
    private static String[] databaseHost = { "jdbc:oracle:thin:@127.0.0.1:1521:XE", "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat" };
    private static String[] databaseUsername = { "bobkoo", "cphbs96", "cphcd77" };
    private static String[] databasePassword = { "qwerty12345", "cphbs96", "cphcd77" };
    private static Connection dbConnection;
    private static MonthTransaction monthTransaction;
    private static String loggerName = "expensesCalculator";
    private static String loggerPath = "/ExpensesCalculator.log";
    private static Logger logger;
    private static PerformanceLogger performanceLogger;

    public MonthTransactionMapperTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        monthMapper = new MonthMapper();
        categoryMapper = new CategoryMapper();
        monthTransactionMapper = new MonthTransactionMapper();
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
//        would be nice to make triggers to delete the month and catgory on month transaction deletion
//        delete all months and categories
        monthMapper.deleteAllMonths( dbConnection );
        categoryMapper.wipeCategoryTable( dbConnection, logger );
        monthTransactionMapper.deleteAllMonthTransactions( dbConnection );

//      insert a month in the db, whose id will be used for the month transaction
        month = new Month();
        month.setName( "August 2016" );
        monthMapper.insertMonth( dbConnection, month );
        int monthId = monthMapper.getMonths( dbConnection ).get( 0 ).getId();

//      insert a category in the db, whose id will be used for the month transaction
        category = new Category();
        category.setName( "food" );
        categoryMapper.insertCategory( dbConnection, logger, category );
        int categoryId = categoryMapper.getCategories( dbConnection, logger ).get( 0 ).getId();

//      insert a month transaction in the db
        monthTransaction = new MonthTransaction();
        monthTransaction.setName( "bread" );
        monthTransaction.setType( "food" );
        monthTransaction.setAmount( 5 );
        monthTransaction.setCategoryId( categoryId );
        monthTransaction.setMonthId( monthId );

        monthTransaction = monthTransactionMapper.insertMonthTransaction( dbConnection, monthTransaction );
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAllMonthsTransactions() {
        MonthTransaction firstMonthTransaction = monthTransactionMapper.getAllTransactions( dbConnection ).get( 0 );
        assertEquals( monthTransaction.getId(), firstMonthTransaction.getId() );
        assertEquals( monthTransaction.getMonthId(), firstMonthTransaction.getMonthId() );
        assertEquals( monthTransaction.getName(), firstMonthTransaction.getName() );
        assertEquals( monthTransaction.getAmount(), firstMonthTransaction.getAmount(), 0);
        assertEquals( monthTransaction.getType(), firstMonthTransaction.getType() );
        assertEquals( monthTransaction.getCategoryId(), firstMonthTransaction.getCategoryId() );
    }

    @Test
    public void testGetMonthTransactionById() {
        System.out.println( "Month id: "+ month.getId() );
        MonthTransaction actualMonthTransaction = monthTransactionMapper.getSpecificTransactionsByMonthID( dbConnection, month.getId() ).get( 0 );
        assertEquals( monthTransaction.getId(), actualMonthTransaction.getId() );
        assertEquals( monthTransaction.getMonthId(), actualMonthTransaction.getMonthId());
        assertEquals( monthTransaction.getName(), actualMonthTransaction.getName() );
        assertEquals( monthTransaction.getAmount(), actualMonthTransaction.getAmount(), 0 );
        assertEquals( monthTransaction.getType(), actualMonthTransaction.getType());
        assertEquals( monthTransaction.getCategoryId(), actualMonthTransaction.getCategoryId());
        
    }
}
