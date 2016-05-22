package facade;

import entity.Category;
import entity.Month;
import entity.MonthTransaction;
import java.sql.Connection;
import java.util.logging.Logger;
import mappers.CategoryMapper;
import mappers.MonthTransactionMapper;
import mappers.MonthMapper;
import utilities.DatabaseConnector;

public class Facade {

    //Database authentication
    private static String[] dbHosts = { "jdbc:oracle:thin:@127.0.0.1:1521:XE",
        "jdbc:oracle:thin:@datdb.cphbusiness.dk:1521:dat" };
    private static String[] dbUsernames = { "bobkoo", "cphbs96", "cphcd77" };
    private static String[] dbPasswords = { "qwerty12345", "cphbs96", "cphcd77" };

    //Database Connection
    private DatabaseConnector databaseConnector = null;
    private Connection connection = null;

    //mappers
    private static MonthTransactionMapper monthTransactionMapper;
    private static MonthMapper monthMapper;
    private static CategoryMapper categoryMapper;

    //facade instance
    private static Facade instance = null;

    private Facade() {
        //mappers initialization
        this.monthMapper = new MonthMapper();
        this.monthTransactionMapper = new MonthTransactionMapper();
        this.categoryMapper = new CategoryMapper();

        this.databaseConnector
                = new DatabaseConnector(
                        dbHosts[ 1 ], dbUsernames[ 1 ], dbPasswords[ 1 ], null );
    }

    /*
     * Singleton initialization.
     */
    public static Facade getInstance() {
        if ( instance == null ) {
            instance = new Facade();
        }
        return instance;
    }

    /*
     * Initialize connection with database.
     */
    public Boolean initializeConnection( Logger logger ) {
        if ( connection != null ) {
            System.out.println( "Connection already existing" );
            if ( logger != null ) {
                logger.info( "Connection with database is already existing!" );
            } else {
                System.out.println( "Connection with database is already existing!"
                        + "\nError in the initializeConnection method: "
                        + "Logger not initialized" );
            }
        } else {
            connection = databaseConnector.getConnection( logger );
            if ( connection != null ) {
                if ( logger != null ) {
                    logger.info( "Connection with database initialized" );
                } else {
                    System.out.println( "Connection with database initialized!"
                            + "\nError in the initializeConnection method: "
                            + "Logger not initialized" );
                }
            } else {
                if ( logger != null ) {
                    logger.severe( "Error Connection with database NOT initialized" );
                } else {
                    System.out.println( "Error : Connection with database NOT initialized!"
                            + "\nError in the initializeConnection method: "
                            + "Logger not initialized" );
                }
                return false;
            }
        }
        return true;
    }

    /*
     * The return types are presented in each mapper. Check the mappers.
     *
     * Category table SELECT, INSERT, UPDATE and DELETE functionality.
     */
    public <T> T getCategories( Logger logger ) {
        return categoryMapper.getCategories( connection, logger );
    }

    public <T> T getCategoryByID( Logger logger, int id ) {
        return categoryMapper.getCategoryByID( connection, logger, id );
    }

    public <T> T insertCategory( Logger logger, Category object ) {
        return categoryMapper.insertCategory( connection, logger, object );
    }

    public <T> T updateCategory( Logger logger, int id, Category newObject ) {
        return categoryMapper.updateCategory( connection, logger, id, newObject );
    }

    public boolean deleteCategory( Logger logger, int id ) {
        return categoryMapper.deleteCategory( connection, logger, id );
    }

    public boolean deleteAllCategories( Logger logger ) {
        return categoryMapper.deleteAllCategories( connection, logger );
    }

    /*
     * Month table SELECT , INSERT, UPDATE and DELETE functionality.
     */
    public <T> T getMonths( Logger logger ) {
        return monthMapper.getMonths( connection, logger );
    }

    public <T> T getMonthByID( Logger logger, int monthId ) {
        return monthMapper.getMonthByID( connection, logger, monthId );
    }

    public <T> T insertMonth( Logger logger, Month object ) {
        return monthMapper.insertMonth( connection, logger, object );
    }

    public <T> T updateMonth( Logger logger, int monthId, Month newObject ) {
        return monthMapper.updateMonth( connection, logger, monthId, newObject );
    }

    public boolean deleteMonth( Logger logger, int monthId ) {
        return monthMapper.deleteMonth( connection, logger, monthId );
    }

    public boolean deleteAllMonths( Logger logger ) {
        return monthMapper.deleteAllMonths( connection, logger );
    }

    /*
     * Month Transaction table SELECT , INSERT, UPDATE and DELETE functionality.
     */
    public <T> T getAllTransactions( Logger logger ) {
        return monthTransactionMapper.getAllTransactions( connection, logger );
    }

    public <T> T getAllTransactions( Logger logger, int monthId,
            int categoryId, String type ) {
        return monthTransactionMapper.
                getAllTransactions( connection, logger, monthId, categoryId, type );
    }

    public <T> T getTransactionsByID( Logger logger, int id ) {
        return monthTransactionMapper.
                getTransactionsByID( connection, logger, id );
    }

    public <T> T insertMonthTransaction( Logger logger, MonthTransaction object ) {
        return monthTransactionMapper.
                insertMonthTransaction( connection, logger, object );
    }

    public <T> T updateMonthTransaction( Logger logger, int id,
            MonthTransaction newObject ) {
        return monthTransactionMapper
                .updateMonthTransaction( connection, logger, id, newObject );
    }

    public boolean deleteMonthTransaction( Logger logger, int id ) {
        return monthTransactionMapper.deleteMonthTransaction( connection, logger, id );
    }

    public boolean deleteAllMonthTransactions( Logger logger ) {
        return monthTransactionMapper.deleteAllMonthTransactions( connection, logger );
    }

    /*
     * Setter and getters for testing purposes
     */
    public static void setCategoryMapper( CategoryMapper categoryMapper ) {
        Facade.categoryMapper = categoryMapper;
    }

    public static void setMonthMapper( MonthMapper monthMapper ) {
        Facade.monthMapper = monthMapper;
    }

    public static void setMonthTransactionMapper( MonthTransactionMapper monthTransactionMapper ) {
        Facade.monthTransactionMapper = monthTransactionMapper;
    }

    public Connection getConnection() {
        return connection;
    }

}
