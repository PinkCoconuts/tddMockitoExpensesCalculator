package hamcrestMatchersTesting;

import entity.Category;
import static hamcrestMatchers.CustomAbstractEntityClassMatcher.matches;
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
import utilities.DatabaseConnector;
import utilities.PerformanceLogger;

public class CategoryMapperTest {

    //Mapperclass
    private CategoryMapper categoryMapper;

    //Database Connection
    private DatabaseConnector databaseConnector = null;
    private Connection connection = null;

    //Database authentication
    private static String[] dbHosts = { "jdbc:oracle:thin:@127.0.0.1:1521:XE",
        "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat" };
    private static String[] dbUsernames = { "bobkoo", "cphbs96", "cphcd77" };
    private static String[] dbPasswords = { "qwerty12345", "cphbs96", "cphcd77" };

    //logger
    private static String loggerName = "expensesCalculator";
    private static String loggerPath = "/ExpensesCalculator.log";
    private static Logger logger = null;
    private static PerformanceLogger performanceLogger = null;

    public Boolean initializeConnection() {
        if ( connection != null ) {
            return true;
        } else {
            connection = databaseConnector.getConnection( logger );
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
        performanceLogger = null;
        logger = null;
    }

    @Before
    public void setUp() {
        categoryMapper = new CategoryMapper();

        databaseConnector = new DatabaseConnector(
                dbHosts[ 1 ], dbUsernames[ 2 ], dbPasswords[ 2 ], null );
        initializeConnection();
    }

    @After
    public void tearDown() {
        categoryMapper.deleteAllCategories( connection, logger );
        categoryMapper = null;

        databaseConnector.closeConnection( connection, logger );
    }

    @Test
    public void testInsertCategory() {
        Category category = new Category( 1, "Music" );
        Category insertedCategory = categoryMapper
                .insertCategory( connection, logger, category );

        Category dbCategory = categoryMapper
                .getCategoryByID( connection, logger, insertedCategory.getId() );

        assertThat( dbCategory, matches( insertedCategory ) );
    }

    @Test
    public void testUpdateCategory() {
        Category category = new Category( 1, "Music" );
        Category insertedCategory = categoryMapper
                .insertCategory( connection, logger, category );

        Category newCategory = new Category( 1, "Food" );
        categoryMapper
                .updateCategory( connection, logger, insertedCategory.getId(), newCategory );

        Category dbCategory = categoryMapper
                .getCategoryByID( connection, logger, insertedCategory.getId() );
        newCategory = new Category( dbCategory.getId(), "Food" );
        assertThat( dbCategory, matches( newCategory ) );
    }

    @Test
    public void testDeleteCategory() {
        Category category = new Category( 1, "Music" );
        Category insertedCategory = categoryMapper
                .insertCategory( connection, logger, category );

        Category dbCategory = categoryMapper
                .getCategoryByID( connection, logger, insertedCategory.getId() );

        boolean deleteResult = categoryMapper
                .deleteCategory( connection, logger, dbCategory.getId() );
        boolean deleteExpectedResult = true;

        assertEquals( deleteResult, deleteExpectedResult );
        Category dbCategoryDeleted = categoryMapper
                .getCategoryByID( connection, logger, insertedCategory.getId() );
        Category expectedCategory = new Category( 0, "" );
        assertThat( dbCategoryDeleted, matches( expectedCategory ) );
    }

    @Test
    public void testGetCategoryByID() {
        Category category = new Category( 1, "Music" );
        Category insertedCategory = categoryMapper
                .insertCategory( connection, logger, category );

        Category dbCategory = categoryMapper
                .getCategoryByID( connection, logger, insertedCategory.getId() );

        assertThat( dbCategory, matches( insertedCategory ) );
    }

    @Test
    public void testGetCategories() {
        List<Category> categories = new ArrayList();
        categories.add( new Category( 1, "Music" ) );
        categories.add( new Category( 2, "Food" ) );
        categories.add( new Category( 3, "Bills" ) );

        List<Category> expctCats = new ArrayList();
        expctCats.add( categoryMapper
                .insertCategory( connection, logger, categories.get( 0 ) ) );
        expctCats.add( categoryMapper
                .insertCategory( connection, logger, categories.get( 1 ) ) );
        expctCats.add( categoryMapper
                .insertCategory( connection, logger, categories.get( 2 ) ) );

        List<Category> actCats = categoryMapper.getCategories( connection, logger );

        for ( int m = 0; m < expctCats.size(); m++ ) {
            for ( int i = 0; i < actCats.size(); i++ ) {
                if ( expctCats.get( m ).getId() == actCats.get( i ).getId() ) {
                    assertThat( actCats.get( i ), matches( expctCats.get( m ) ) );
                }
            }
        }

    }

    @Test
    public void testwipeCategoryTable() {
        assertThat( categoryMapper.deleteAllCategories( connection, logger ), is( true ) );
    }
}
