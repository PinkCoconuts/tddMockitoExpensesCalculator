package facade;

import entity.Category;
import entity.Month;
import entity.MonthTransaction;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;
import mappers.CategoryMapper;
import mappers.MonthTransactionMapper;
import mappers.MonthMapper;
import utilities.DBconnector;
import utilities.PerformanceLogger;

public class Facade {

    //logger
    private String loggerName = "expensesCalculator";
    private String loggerPath = "/ExpensesCalculator.log";
    private static Logger logger = null;
    private static PerformanceLogger performanceLogger = null;

    //mappers
    private static MonthTransactionMapper monthTransactionMapper;
    private static MonthMapper monthMapper;
    private static CategoryMapper categoryMapper;

    //Database Connection
    private DBconnector databaseConnector = null;
    private Connection connection = null;

    //Database authentication
    private static String[] databaseHost = { "jdbc:oracle:thin:@127.0.0.1:1521:XE", "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat" };
    private static String[] databaseUsername = { "bobkoo", "cphbs96", "cphcd77" };
    private static String[] databasePassword = { "qwerty12345", "cphbs96", "cphcd77" };

    //facade instance
    private static Facade instance = null;

    private Facade() {
        this.monthMapper = new MonthMapper();
        this.monthTransactionMapper = new MonthTransactionMapper();
        this.categoryMapper = new CategoryMapper();
    }

    private Facade( MonthMapper monthMapper, MonthTransactionMapper monthTransactionMapper,
            CategoryMapper categoryMapper ) {

        //logger initialization
        performanceLogger = new PerformanceLogger();
        logger = performanceLogger.initLogger( loggerName, loggerPath );

        //mappers initialization
        if ( monthMapper == null ) {
            this.monthMapper = new MonthMapper();
        } else {
            this.monthMapper = monthMapper;
        }

        if ( monthTransactionMapper == null ) {
            this.monthTransactionMapper = new MonthTransactionMapper();
        } else {
            this.monthTransactionMapper = monthTransactionMapper;
        }

        if ( categoryMapper == null ) {
            this.categoryMapper = new CategoryMapper();
        } else {
            this.categoryMapper = categoryMapper;
        }

        this.databaseConnector = new DBconnector( databaseHost[ 1 ], databaseUsername[ 1 ], databasePassword[ 1 ], null );

    }

    public static Logger getLogger() {
        return logger;
    }

    public static Facade getInstance( MonthMapper monthMapper, MonthTransactionMapper monthTransactionMapper,
            CategoryMapper categoryMapper ) {
        if ( instance == null ) {
            instance = new Facade( monthMapper, monthTransactionMapper, categoryMapper );
        }
        return instance;
    }

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

    public List<Month> getMonths() {
        return monthMapper.getMonths( connection );
    }

    public Month getMonthByID( int monthId ) {
        return monthMapper.getMonthByID( connection, monthId );
    }

    public Month insertMonth( Month object ) {
        return monthMapper.insertMonth( connection, object );
    }

    public Month updateMonth( int monthId, Month newObject ) {
        return monthMapper.updateMonth( connection, monthId, newObject );
    }

    public int deleteMonth( int monthId ) {
        return monthMapper.deleteMonth( connection, monthId );
    }

    public List<MonthTransaction> getMonthTransactions() {
        return monthTransactionMapper.getAllTransactions( connection );
    }

    public List<MonthTransaction> getMonthTransactions(int monthId, int categoryId, String type ) {
        System.out.println( "From the facade: month id "+ monthId + " category id: "+ categoryId + " type: "+ type );
        return monthTransactionMapper.getAllTransactions( connection, logger, monthId, categoryId, type );
    }

    public List<MonthTransaction> getSpecificTransactionsByMonthID( int monthId ) {
        return monthTransactionMapper.getSpecificTransactionsByMonthID( connection, monthId );
    }

    public List<MonthTransaction> getSpecificTransactionsByCategoryID( int categoryId ) {
        return monthTransactionMapper.getSpecificTransactionsByCategoryID( connection, categoryId );
    }

    public List<MonthTransaction> getSpecificTransactionsByType( String type ) {
        return monthTransactionMapper.getSpecificTransactionsByType( connection, type );
    }

    public boolean insertMonthTransaction( MonthTransaction object ) {
        MonthTransaction mt = monthTransactionMapper.insertMonthTransaction( connection, object );
        if ( mt != null ) {
            return true;
        }
        return false;
    }

    public MonthTransaction updateMonthTransaction( int monthTransactionID, MonthTransaction newObject ) {
        return monthTransactionMapper.updateMonthTransaction( connection, monthTransactionID, newObject );
    }

    public int deleteMonthTransaction( int monthTransactionID ) {
        return monthTransactionMapper.deleteMonthTransaction( connection, monthTransactionID );
    }

    public List<Category> getCategories() {
        return categoryMapper.getCategories( connection, logger );
    }

    public Category getCategoryByID( int categoryId ) {
        return categoryMapper.getCategoryByID( connection, logger, categoryId );
    }

    public Category insertCategory( Category object ) {
        return categoryMapper.insertCategory( connection, logger, object );
    }

    public int updateCategory( int categoryId, Category newObject ) {
        return categoryMapper.updateCategory( connection, logger, categoryId, newObject );
    }

    public int deleteCategory( int categoryID ) {
        return categoryMapper.deleteCategory( connection, logger, categoryID );
    }

    //testing
    public Connection getConnection() {
        return connection;
    }

}
