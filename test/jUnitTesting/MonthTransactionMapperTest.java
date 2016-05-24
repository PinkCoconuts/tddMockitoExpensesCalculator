package jUnitTesting;

import entity.Category;
import entity.Month;
import entity.MonthTransaction;
import static hamcrestMatchers.CustomAbstractEntityClassMatcher.matches;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mappers.CategoryMapper;
import mappers.MonthMapper;
import mappers.MonthTransactionMapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utilities.DatabaseConnector;
import utilities.PerformanceLogger;

public class MonthTransactionMapperTest {

    //Mapperclasses
    private static MonthTransactionMapper monthTransactionMapper;
    private static MonthMapper monthMapper;
    private static CategoryMapper categoryMapper;

    //Helper entities
    private static Month month;
    private static Category category;
    private static MonthTransaction monthTransaction;

    //Database Connection
    private static DatabaseConnector databaseConnector;
    private static Connection connection;

    //Database authentication
    private static String[] dbHost = { "jdbc:oracle:thin:@127.0.0.1:1521:XE",
        "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat" };
    private static String[] dbUsernames = { "bobkoo", "cphbs96", "cphcd77" };
    private static String[] dbPasswords = { "qwerty12345", "cphbs96", "cphcd77" };

    //logger
    private static String loggerName = "expensesCalculatorTester";
    private static String loggerPath = "/ExpensesCalculatorTester.log";
    private static Logger logger = null;
    private static PerformanceLogger performanceLogger = null;

    @BeforeClass
    public static void setUpClass() {
        //logger initialization
        performanceLogger = new PerformanceLogger();
        logger = performanceLogger.initLogger( loggerName, loggerPath );

        //database initialization
        databaseConnector = new DatabaseConnector( dbHost[ 1 ], dbUsernames[ 2 ], dbPasswords[ 2 ], null );
        connection = databaseConnector.getConnection( logger );

        //mappers initialization
        monthMapper = new MonthMapper();
        categoryMapper = new CategoryMapper();
        monthTransactionMapper = new MonthTransactionMapper();
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
        //would be nice to make triggers to delete the month and catgory on month transaction deletion
        //delete all months and categories
        monthMapper.deleteAllMonths( connection, null );
        categoryMapper.deleteAllCategories( connection, logger );
        monthTransactionMapper.deleteAllMonthTransactions( connection, null );

        //insert a month in the db, whose id will be used for the month transaction
        Month toInsertmonth = new Month( 0, "August 2016" );
        month = monthMapper.insertMonth( connection, null, toInsertmonth );
        List<Month> lm = monthMapper.getMonths( connection, null );
        int monthId = lm.get( 0 ).getId();

        //insert a category in the db, whose id will be used for the month transaction
        Category toInsertcategory = new Category( 0, "food" );
        category = categoryMapper.insertCategory( connection, logger, toInsertcategory );
        List<Category> cl = categoryMapper.getCategories( connection, logger );
        int categoryId = cl.get( 0 ).getId();

        //insert a month transaction in the db
        monthTransaction = new MonthTransaction( 0, "bread", "food", monthId, categoryId, 5 );

        monthTransaction = monthTransactionMapper
                .insertMonthTransaction( connection, null, monthTransaction );
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getAllMonthsTransactions() {
        Month mon1 = monthMapper.insertMonth( connection, null, new Month( 0, "January 2016" ) );
        Month mon2 = monthMapper.insertMonth( connection, null, new Month( 0, "February 2016" ) );
        Month mon3 = monthMapper.insertMonth( connection, null, new Month( 0, "March 2016" ) );
        Month mon4 = monthMapper.insertMonth( connection, null, new Month( 0, "April 2016" ) );
        Month mon5 = monthMapper.insertMonth( connection, null, new Month( 0, "May 2016" ) );
        Month mon6 = monthMapper.insertMonth( connection, null, new Month( 0, "June 2016" ) );

        Category cat1 = categoryMapper.insertCategory( connection, null, new Category( 0, "salary" ) );
        Category cat2 = categoryMapper.insertCategory( connection, null, new Category( 0, "accommodation" ) );
        Category cat3 = categoryMapper.insertCategory( connection, null, new Category( 0, "transportation" ) );
        Category cat4 = categoryMapper.insertCategory( connection, null, new Category( 0, "food" ) );

        List<MonthTransaction> toInsert = new ArrayList();
        toInsert.add( new MonthTransaction( 0, "accommodation", "expense", mon1.getId(), cat2.getId(), 7000 ) );
        toInsert.add( new MonthTransaction( 0, "gas for car", "expense", mon1.getId(), cat2.getId(), 2000 ) );
        toInsert.add( new MonthTransaction( 0, "apples", "expense", mon1.getId(), cat4.getId(), 1200 ) );
        toInsert.add( new MonthTransaction( 0, "salaryJan", "income", mon1.getId(), cat1.getId(), 21738 ) );
        toInsert.add( new MonthTransaction( 0, "salaryFeb", "income", mon2.getId(), cat1.getId(), 28214 ) );
        toInsert.add( new MonthTransaction( 0, "salaryMar", "income", mon3.getId(), cat1.getId(), 25108 ) );
        toInsert.add( new MonthTransaction( 0, "salary", "income", mon4.getId(), cat1.getId(), 20136 ) );
        toInsert.add( new MonthTransaction( 0, "salary", "income", mon5.getId(), cat1.getId(), 27444 ) );
        toInsert.add( new MonthTransaction( 0, "salary", "income", mon6.getId(), cat1.getId(), 22890 ) );
        toInsert.add( new MonthTransaction( 0, "salad", "expense", mon1.getId(), cat1.getId(), 22890 ) );
        toInsert.add( new MonthTransaction( 0, "bread", "expense", mon2.getId(), cat1.getId(), 22890 ) );

        List<MonthTransaction> inserted = new ArrayList();
        inserted.add( monthTransactionMapper
                .insertMonthTransaction( connection, null, toInsert.get( 0 ) ) );
        inserted.add( monthTransactionMapper
                .insertMonthTransaction( connection, null, toInsert.get( 1 ) ) );
        inserted.add( monthTransactionMapper
                .insertMonthTransaction( connection, null, toInsert.get( 2 ) ) );
        inserted.add( monthTransactionMapper
                .insertMonthTransaction( connection, null, toInsert.get( 3 ) ) );
        inserted.add( monthTransactionMapper
                .insertMonthTransaction( connection, null, toInsert.get( 4 ) ) );
        inserted.add( monthTransactionMapper
                .insertMonthTransaction( connection, null, toInsert.get( 5 ) ) );
        inserted.add( monthTransactionMapper
                .insertMonthTransaction( connection, null, toInsert.get( 6 ) ) );
        inserted.add( monthTransactionMapper
                .insertMonthTransaction( connection, null, toInsert.get( 7 ) ) );
        inserted.add( monthTransactionMapper
                .insertMonthTransaction( connection, null, toInsert.get( 8 ) ) );
        inserted.add( monthTransactionMapper
                .insertMonthTransaction( connection, null, toInsert.get( 9 ) ) );
        inserted.add( monthTransactionMapper
                .insertMonthTransaction( connection, null, toInsert.get( 10 ) ) );

        List<MonthTransaction> actual = monthTransactionMapper.getAllTransactions( connection, logger );

        for ( int m = 0; m < inserted.size(); m++ ) {
            boolean isIdMatching = false,
                    isNameMatching = false,
                    isAmountMatching = false,
                    isCategoryMatching = false,
                    isMonthMatching = false,
                    isTypeMatching = false;
            for ( int i = 0; i < actual.size(); i++ ) {
                if ( inserted.get( m ).getId() == actual.get( i ).getId() ) {
                    isIdMatching = true;
                }
                if ( inserted.get( m ).getName().equals( actual.get( i ).getName() ) ) {
                    isNameMatching = true;
                }
                if ( inserted.get( m ).getAmount() == actual.get( i ).getAmount() ) {
                    isAmountMatching = true;
                }
                if ( inserted.get( m ).getCategoryId() == actual.get( i ).getCategoryId() ) {
                    isCategoryMatching = true;
                }
                if ( inserted.get( m ).getMonthId() == actual.get( i ).getMonthId() ) {
                    isMonthMatching = true;
                }
                if ( inserted.get( m ).getType().equals( actual.get( i ).getType() ) ) {
                    isTypeMatching = true;
                }
            }
            assertEquals( true, isIdMatching );
            assertEquals( true, isNameMatching );
            assertEquals( true, isAmountMatching );
            assertEquals( true, isCategoryMatching );
            assertEquals( true, isMonthMatching );
            assertEquals( true, isTypeMatching );
        }
    }

    @Test
    public void testUpdateMonthTransaction() {
        List<Category> cl = categoryMapper.getCategories( connection, logger );
        int categoryId = cl.get( 0 ).getId();

        List<Month> lm = monthMapper.getMonths( connection, null );
        int monthId = lm.get( 0 ).getId();

        monthTransaction = new MonthTransaction( 0, "coke", "drinks", categoryId, monthId, 20.3 );

        MonthTransaction actualMonthTransaction = monthTransactionMapper
                .updateMonthTransaction( connection, null, monthTransaction.getId(),
                                         monthTransaction );

        assertThat( actualMonthTransaction, matches( monthTransaction ) );

        assertEquals( monthTransaction.getId(), actualMonthTransaction.getId() );
        assertEquals( monthTransaction.getMonthId(), actualMonthTransaction.getMonthId() );
        assertEquals( monthTransaction.getName(), actualMonthTransaction.getName() );
        assertEquals( monthTransaction.getAmount(), actualMonthTransaction.getAmount(), 0 );
        assertEquals( monthTransaction.getType(), actualMonthTransaction.getType() );
        assertEquals( monthTransaction.getCategoryId(), actualMonthTransaction.getCategoryId() );
    }

    @Test
    public void testInsertMonthTransaction() {

        MonthTransaction toInsert
                = new MonthTransaction( 0, "coke", "drinks", month.getId(),
                                        category.getId(), 10 );

        MonthTransaction inserted = monthTransactionMapper
                .insertMonthTransaction( connection, null, toInsert );

        MonthTransaction expected
                = new MonthTransaction( inserted.getId(), "coke", "drinks",
                                        month.getId(), category.getId(), 10 );

        MonthTransaction actual = monthTransactionMapper
                .getTransactionsByID( connection, null, inserted.getId() );

        assertEquals( actual.getId(), expected.getId() );
        assertEquals( actual.getName(), expected.getName() );
        assertEquals( actual.getType(), expected.getType() );
        assertEquals( actual.getMonthId(), expected.getMonthId() );
        assertEquals( actual.getCategoryId(), expected.getCategoryId() );
        /*
         * Assert Equals for Doubles includes epsilon meaning :
         * Epsilon is the value that the 2 numbers can be off by. So it will assert 
         * to true as long as Math.abs(expected - actual) < epsilon
         * Source : 
         * http://stackoverflow.com/questions/5686755/meaning-of-epsilon-argument-of-assertequals-for-double-values
         */
        assertEquals( actual.getAmount(), expected.getAmount(), 0.1 );
    }

    @Test
    public void testDeleteMonthTransaction() {
        assertEquals( true, monthTransactionMapper
                      .deleteMonthTransaction( connection, null, monthTransaction.getId() ) );
    }

    @Test
    public void testDeleteAllMonthTransactions() {
        assertEquals( true, monthTransactionMapper
                      .deleteAllMonthTransactions( connection, null ) );
    }

    @Test
    public void testGetAllTransactionSorted() {
        Month mon1 = monthMapper.insertMonth( connection, null, new Month( 0, "January 2016" ) );
        Month mon2 = monthMapper.insertMonth( connection, null, new Month( 0, "February 2016" ) );
        Month mon3 = monthMapper.insertMonth( connection, null, new Month( 0, "March 2016" ) );
        Month mon4 = monthMapper.insertMonth( connection, null, new Month( 0, "April 2016" ) );
        Month mon5 = monthMapper.insertMonth( connection, null, new Month( 0, "May 2016" ) );
        Month mon6 = monthMapper.insertMonth( connection, null, new Month( 0, "June 2016" ) );

        Category cat1 = categoryMapper.insertCategory( connection, null, new Category( 0, "salary" ) );
        Category cat2 = categoryMapper.insertCategory( connection, null, new Category( 0, "accommodation" ) );
        Category cat3 = categoryMapper.insertCategory( connection, null, new Category( 0, "transportation" ) );
        Category cat4 = categoryMapper.insertCategory( connection, null, new Category( 0, "food" ) );

        List<MonthTransaction> toInsert = new ArrayList();
        toInsert.add( new MonthTransaction( 0, "accommodation", "expense", mon1.getId(), cat2.getId(), 7000 ) );
        toInsert.add( new MonthTransaction( 0, "gas for car", "expense", mon1.getId(), cat2.getId(), 2000 ) );
        toInsert.add( new MonthTransaction( 0, "apples", "expense", mon1.getId(), cat4.getId(), 1200 ) );
        toInsert.add( new MonthTransaction( 0, "salaryJan", "income", mon1.getId(), cat1.getId(), 21738 ) );
        toInsert.add( new MonthTransaction( 0, "salaryFeb", "income", mon2.getId(), cat1.getId(), 28214 ) );
        toInsert.add( new MonthTransaction( 0, "salaryMar", "income", mon3.getId(), cat1.getId(), 25108 ) );
        toInsert.add( new MonthTransaction( 0, "salary", "income", mon4.getId(), cat1.getId(), 20136 ) );
        toInsert.add( new MonthTransaction( 0, "salary", "income", mon5.getId(), cat1.getId(), 27444 ) );
        toInsert.add( new MonthTransaction( 0, "salary", "income", mon6.getId(), cat1.getId(), 22890 ) );
        toInsert.add( new MonthTransaction( 0, "salad", "expense", mon1.getId(), cat1.getId(), 22890 ) );
        toInsert.add( new MonthTransaction( 0, "bread", "expense", mon2.getId(), cat1.getId(), 22890 ) );

        monthTransactionMapper.insertMonthTransaction( connection, null, toInsert.get( 0 ) );
        monthTransactionMapper.insertMonthTransaction( connection, null, toInsert.get( 1 ) );
        monthTransactionMapper.insertMonthTransaction( connection, null, toInsert.get( 2 ) );
        monthTransactionMapper.insertMonthTransaction( connection, null, toInsert.get( 3 ) );
        monthTransactionMapper.insertMonthTransaction( connection, null, toInsert.get( 4 ) );
        monthTransactionMapper.insertMonthTransaction( connection, null, toInsert.get( 5 ) );
        monthTransactionMapper.insertMonthTransaction( connection, null, toInsert.get( 6 ) );
        monthTransactionMapper.insertMonthTransaction( connection, null, toInsert.get( 7 ) );
        monthTransactionMapper.insertMonthTransaction( connection, null, toInsert.get( 8 ) );
        monthTransactionMapper.insertMonthTransaction( connection, null, toInsert.get( 9 ) );
        monthTransactionMapper.insertMonthTransaction( connection, null, toInsert.get( 10 ) );

        List<MonthTransaction> lmt1 = monthTransactionMapper.getAllTransactions( connection, null, 0, 0, "" );
        assertEquals( 12, lmt1.size() );
        List<MonthTransaction> lmt2 = monthTransactionMapper.getAllTransactions( connection, null, mon1.getId(), 0, "" );
        assertEquals( 5, lmt2.size() );
        List<MonthTransaction> lmt3 = monthTransactionMapper.getAllTransactions( connection, null, 0, cat1.getId(), "" );
        assertEquals( 8, lmt3.size() );
        List<MonthTransaction> lmt4 = monthTransactionMapper.getAllTransactions( connection, null, 0, 0, "expense" );
        assertEquals( 5, lmt4.size() );
        List<MonthTransaction> lmt5 = monthTransactionMapper.getAllTransactions( connection, null, mon1.getId(), cat1.getId(), "" );
        assertEquals( 2, lmt5.size() );
        List<MonthTransaction> lmt6 = monthTransactionMapper.getAllTransactions( connection, null, mon1.getId(), 0, "expense" );
        assertEquals( 4, lmt6.size() );
        List<MonthTransaction> lmt7 = monthTransactionMapper.getAllTransactions( connection, null, 0, cat1.getId(), "expense" );
        assertEquals( 2, lmt7.size() );
        List<MonthTransaction> lmt8 = monthTransactionMapper.getAllTransactions( connection, null, mon1.getId(), cat1.getId(), "expense" );
        assertEquals( 1, lmt8.size() );
    }
}
