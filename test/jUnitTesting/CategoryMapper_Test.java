package jUnitTesting;

import entity.Category;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import mappers.CategoryMapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utilities.DBconnector;
import utilities.PerformanceLogger;

public class CategoryMapper_Test {

    //Mapperclass
    private CategoryMapper categoryMapper;

    //Database Connection
    private DBconnector databaseConnector = null;
    private Connection connection = null;

    //Database authentication
    private static String[] databaseHost = { "jdbc:oracle:thin:@127.0.0.1:1521:XE", "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat" };
    private static String[] databaseUsername = { "bobkoo", "cphbs96", "cphcd77" };
    private static String[] databasePassword = { "qwerty12345", "cphbs96", "cphcd77" };

    //logger
    private static String loggerName = "expensesCalculatorTester";
    private static String loggerPath = "/ExpensesCalculatorTester.log";
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
    public static void beforeClass() {
        //logger initialization
        performanceLogger = new PerformanceLogger();
        logger = performanceLogger.initLogger( loggerName, loggerPath );

    }

    @AfterClass
    public static void afterClass() {
        performanceLogger = null;
        logger = null;
    }

    @Before
    public void beforeTest() {
        categoryMapper = new CategoryMapper();

        databaseConnector = new DBconnector( databaseHost[ 1 ], databaseUsername[ 2 ], databasePassword[ 2 ], null );
        initializeConnection();
    }

    @After
    public void afterTest() {
        categoryMapper.wipeCategoryTable( connection, logger );
        categoryMapper = null;

        databaseConnector.closeConnection( connection, logger );
    }

    @Test
    public void testInsertCategory() {
        Category category = new Category( 1, "Music" );
        Category insertedCategory = categoryMapper.insertCategory( connection, logger, category );

        Category dbCategory = categoryMapper.getCategoryByID( connection, logger, insertedCategory.getId() );

        assertEquals( insertedCategory.getId(), dbCategory.getId() );
    }

    @Test
    public void testUpdateCategory() {
        Category category = new Category( 1, "Music" );
        Category insertedCategory = categoryMapper.insertCategory( connection, logger, category );

        Category newCategory = new Category( 1, "Food" );
        categoryMapper.updateCategory( connection, logger, insertedCategory.getId(), newCategory );

        Category dbCategory = categoryMapper.getCategoryByID( connection, logger, insertedCategory.getId() );

        assertEquals( insertedCategory.getId(), dbCategory.getId() );
        assertEquals( newCategory.getName(), dbCategory.getName() );
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
        Category expectedCategory = new Category( 0, "" );
        assertEquals( expectedCategory.getId(), dbCategoryDeleted.getId() );
        assertEquals( expectedCategory.getName(), dbCategoryDeleted.getName() );
    }

    @Test
    public void testGetCategoryByID() {
        Category category = new Category( 1, "Music" );
        Category insertedCategory = categoryMapper.insertCategory( connection, logger, category );

        Category dbCategory = categoryMapper.getCategoryByID( connection, logger, insertedCategory.getId() );

        assertEquals( insertedCategory.getId(), dbCategory.getId() );
        assertEquals( category.getName(), dbCategory.getName() );
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
            boolean isIdMatching = false, isNameMatching = false;
            for ( int i = 0; i < dbCategories.size(); i++ ) {
                if ( insertedCategories.get( m ).getId() == dbCategories.get( i ).getId() ) {
                    isIdMatching = true;
                }
                if ( insertedCategories.get( m ).getName().equals( dbCategories.get( i ).getName() ) ) {
                    isNameMatching = true;
                }
            }
            assertEquals( isIdMatching, true );
            assertEquals( isNameMatching, true );

        }
    }

    @Test
    public void testwipeCategoryTable() {
        assertEquals( true, categoryMapper.wipeCategoryTable( connection, logger ) );
    }
}
