package hamcrestMappers;

import entity.Category;
import static hamcrestTests.CustomAbstractEntityClassMatcher.matches;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import mappers.CategoryMapper;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utilities.DBconnector;
import utilities.PerformanceLogger;

public class CategoryMapperTest {

    //Mapperclass
    private CategoryMapper categoryMapper;

    //Database Connection
    private DBconnector databaseConnector = null;
    private Connection connection = null;

    //Database authentication
    private static String[] databaseHost = { "jdbc:oracle:thin:@127.0.0.1:1521:XE", "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat" };
    private static String[] databaseUsername = { "bobkoo", "cphbs96" };
    private static String[] databasePassword = { "qwerty12345", "cphbs96" };

    //logger
    private static String loggerName = "expensesCalculator";
    private static String loggerPath = "/ExpensesCalculator.log";
    private static Logger logger = null;
    private static PerformanceLogger performanceLogger = null;

    public Boolean initializeConnection() {
        if ( connection != null ) {
            System.out.println( "Connection already existing" );
            logger.info( "Connection with database is already existing!" );
            return true;
        } else {
            connection = databaseConnector.getConnection( logger );
            logger.info( "Connection with database initialized" );
        }
        return true;
    }

    @BeforeClass
    public static void setUpClass() {
        //logger initialization
        performanceLogger = new PerformanceLogger();
        logger = performanceLogger.initLogger( loggerName, loggerPath );

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        categoryMapper = new CategoryMapper();

        databaseConnector = new DBconnector( databaseHost[ 1 ], databaseUsername[ 1 ], databasePassword[ 1 ], null );
        initializeConnection();
    }

    @After
    public void tearDown() {
        categoryMapper.wipeCategoryTable( connection, logger );
        categoryMapper = null;

        databaseConnector.closeConnection( connection, logger );
    }

    @Test
    public void testInsertCategory() {
        Category category = new Category( 1, "Music" );
        Category insertedCategory = categoryMapper.insertCategory( connection, logger, category );

        Category dbCategory = categoryMapper.getCategoryByID( connection, logger, insertedCategory.getId() );

        assertThat( insertedCategory, matches( dbCategory ) );
    }

    @Test
    public void testUpdateCategory() {
        Category category = new Category( 1, "Music" );
        Category insertedCategory = categoryMapper.insertCategory( connection, logger, category );

        Category newCategory = new Category( 1, "Food" );
        categoryMapper.updateCategory( connection, logger, insertedCategory.getId(), newCategory );

        Category dbCategory = categoryMapper.getCategoryByID( connection, logger, insertedCategory.getId() );
        newCategory.setId( dbCategory.getId() );
        assertThat( newCategory, matches( dbCategory ) );
    }

    @Test
    public void testDeleteCategory() {
        Category category = new Category( 1, "Music" );
        Category insertedCategory = categoryMapper.insertCategory( connection, logger, category );

        Category dbCategory = categoryMapper.getCategoryByID( connection, logger, insertedCategory.getId() );

        boolean deleteResult = categoryMapper.deleteCategory( connection, logger, dbCategory.getId() );
        boolean deleteExpectedResult = true;

        assertEquals( deleteResult, deleteExpectedResult );
        Category dbCategoryDeleted = categoryMapper.getCategoryByID( connection, logger, insertedCategory.getId() );
        Category expectedCategory = new Category(0, "");
        assertThat( expectedCategory, matches( dbCategoryDeleted ) );
    }

    @Test
    public void testGetCategoryByID() {
        Category category = new Category( 1, "Music" );
        Category insertedCategory = categoryMapper.insertCategory( connection, logger, category );

        Category dbCategory = categoryMapper.getCategoryByID( connection, logger, insertedCategory.getId() );

        assertThat( insertedCategory, matches( dbCategory ) );
    }

    @Test
    public void testGetCategories() {
        List<Category> categories = new ArrayList();
        categories.add( new Category( 1, "Music" ) );
        categories.add( new Category( 2, "Food" ) );
        categories.add( new Category( 3, "Bills" ) );

        List<Category> insertedCategories = new ArrayList();
        insertedCategories.add( categoryMapper.insertCategory( connection, logger, categories.get( 0 ) ) );
        insertedCategories.add( categoryMapper.insertCategory( connection, logger, categories.get( 1 ) ) );
        insertedCategories.add( categoryMapper.insertCategory( connection, logger, categories.get( 2 ) ) );

        List<Category> dbCategories = categoryMapper.getCategories( connection, logger );

        for ( int m = 0; m < insertedCategories.size(); m++ ) {
            for ( int i = 0; i < dbCategories.size(); i++ ) {
                if ( insertedCategories.get( m ).getId() == dbCategories.get( i ).getId() ) {
                    assertThat( insertedCategories.get( m ), matches( dbCategories.get( i ) ) );
                }
            }
        }

    }

    @Test
    public void testwipeCategoryTable() {
        assertThat( true, is( categoryMapper.wipeCategoryTable( connection, logger ) ) );
    }
}
