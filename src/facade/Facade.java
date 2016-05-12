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
    private Logger logger = null;
    private PerformanceLogger performanceLogger = null;
    //mappers
    private static MonthTransactionMapper monthTransactionMapper;
    private static MonthMapper monthMapper;
    private static CategoryMapper categoryMapper;
    //DB connection
    private DBconnector databaseConnector = null;
    private Connection connection;
    //facade instance
    private static Facade instance = null;

    private Facade() {
        this.monthMapper = new MonthMapper();
        this.monthTransactionMapper = new MonthTransactionMapper();
        this.categoryMapper = new CategoryMapper();
    }

    private Facade(MonthMapper monthMapper, MonthTransactionMapper monthTransactionMapper,
            CategoryMapper categoryMapper) {
        //logger initialization
        performanceLogger = new PerformanceLogger();
        logger = performanceLogger.initLogger(loggerName, loggerPath);
        //mappers initialization
        this.monthMapper = monthMapper;
        this.monthTransactionMapper = monthTransactionMapper;
        this.categoryMapper = categoryMapper;
    }

    public static Facade getInstance(MonthMapper monthMapper, MonthTransactionMapper monthTransactionMapper,
            CategoryMapper categoryMapper) {
        if (instance == null) {
            instance = new Facade(monthMapper, monthTransactionMapper, categoryMapper);
            monthTransactionMapper = monthTransactionMapper;
            monthMapper = monthMapper;
            categoryMapper = categoryMapper;
        }
        return instance;
    }

    public Boolean initializeConnection() {
        if (connection != null) {
            System.out.println("Connection already existing");
            logger.info("Connection with database is already existing!");
            return true;
        } else {
            connection = databaseConnector.getConnection(logger);
            logger.info("Connection with database initialized");
        }
        return true;
    }

    public List<Month> getMonths() {
        return monthMapper.getMonths(connection);
    }

    public Month getMonthByID(int monthId) {
        return monthMapper.getMonthByID(connection, monthId);
    }

    public int insertMonth(Month object) {
        return monthMapper.insertMonth(connection, object);
    }

    public int updateMonth(int monthId, Month newObject) {
        return monthMapper.updateMonth(connection, monthId, newObject);
    }

    public int deleteMonth(int monthId) {
        return monthMapper.deleteMonth(connection, monthId);
    }

    public List<MonthTransaction> getSpecificTransactionsByMonthID(int monthId, String type) {
        return monthTransactionMapper.getSpecificTransactionsByMonthID(connection, monthId, type);
    }

    public int insertMonthTransaction(MonthTransaction object) {
        return monthTransactionMapper.insertMonthTransaction(connection, object);
    }

    public int updateMonthTransaction(int monthTransactionID, MonthTransaction newObject) {
        return monthTransactionMapper.updateMonthTransaction(connection, monthTransactionID, newObject);
    }

    public int deleteMonthTransaction(int monthTransactionID) {
        return monthTransactionMapper.deleteMonthTransaction(connection, monthTransactionID);
    }

    public List<Category> getCategories() {
        return categoryMapper.getCategories(connection);
    }

    public Category getCategoryByID(int categoryId) {
        return categoryMapper.getCategoryByID(connection, categoryId);
    }

    public int insertCategory(Category object) {
        return categoryMapper.insertCategory(connection, object);
    }

    public int updateCategory(int categoryId, Category newObject) {
        return categoryMapper.updateCategory(connection, categoryId, newObject);
    }

    public int deleteCategory(int categoryID) {
        return categoryMapper.deleteCategory(connection, categoryID);
    }
}
