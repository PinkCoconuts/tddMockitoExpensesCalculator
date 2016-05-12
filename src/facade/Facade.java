package facade;

import entity.Category;
import entity.Month;
import entity.MonthTransaction;
import java.sql.Connection;
import java.util.List;
import mappers.CategoryMapper;
import mappers.MonthTransactionMapper;
import mappers.MonthMapper;

public class Facade {

    private final MonthTransactionMapper monthTransactionMapper;
    private final MonthMapper monthMapper;
    private final CategoryMapper categoryMapper;
    private Connection connection;

    public Facade() {
        this.monthMapper = new MonthMapper();
        this.monthTransactionMapper = new MonthTransactionMapper();
        this.categoryMapper = new CategoryMapper();
    }

    public Facade( MonthMapper monthMapper, MonthTransactionMapper monthTransactionMapper,
            CategoryMapper categoryMapper ) {
        this.monthMapper = monthMapper;
        this.monthTransactionMapper = monthTransactionMapper;
        this.categoryMapper = categoryMapper;
    }

    public List<Month> getMonths() {
        return monthMapper.getMonths( connection );
    }

    public Month getMonthByID( int monthId ) {
        return monthMapper.getMonthByID( connection, monthId );
    }

    public int insertMonth( Month object ) {
        return monthMapper.insertMonth( connection, object );
    }

    public int updateMonth( int monthId, Month newObject ) {
        return monthMapper.updateMonth( connection, monthId, newObject );
    }

    public int deleteMonth( int monthId ) {
        return monthMapper.deleteMonth( connection, monthId );
    }

    public List<MonthTransaction> getSpecificTransactionsByMonthID( int monthId, String type ) {
        return monthTransactionMapper.getSpecificTransactionsByMonthID( connection, monthId, type );
    }

    public int insertMonthTransaction( MonthTransaction object ) {
        return monthTransactionMapper.insertMonthTransaction( connection, object );
    }

    public int updateMonthTransaction( int monthTransactionID, MonthTransaction newObject ) {
        return monthTransactionMapper.updateMonthTransaction( connection, monthTransactionID, newObject );
    }

    public int deleteMonthTransaction( int monthTransactionID ) {
        return monthTransactionMapper.deleteMonthTransaction( connection, monthTransactionID );
    }

    public List<Category> getCategories() {
        return categoryMapper.getCategories( connection );
    }

    public Category getCategoryByID( int categoryId ) {
        return categoryMapper.getCategoryByID( connection, categoryId );
    }

    public int insertCategory( Category object ) {
        return categoryMapper.insertCategory( connection, object );
    }

    public int updateCategory( int categoryId, Category newObject ) {
        return categoryMapper.updateCategory( connection, categoryId, newObject );
    }

    public int deleteCategory( int categoryID ) {
        return categoryMapper.deleteCategory( connection, categoryID );
    }
}
