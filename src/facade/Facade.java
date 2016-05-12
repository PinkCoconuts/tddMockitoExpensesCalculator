package facade;

import entity.Month;
import entity.MonthTransaction;
import java.sql.Connection;
import java.util.List;
import mappers.MonthTransactionMapper;
import mappers.MonthMapper;

public class Facade {

    private final MonthTransactionMapper monthTransactionMapper;
    private final MonthMapper monthMapper;
    private Connection connection;

    public Facade() {
        this.monthMapper = new MonthMapper();
        this.monthTransactionMapper = new MonthTransactionMapper();
    }

    public Facade( MonthMapper monthMapper, MonthTransactionMapper monthTransactionMapper ) {
        this.monthMapper = monthMapper;
        this.monthTransactionMapper = monthTransactionMapper;
    }

    public List<Month> getMonths() {
        return monthMapper.getMonths();
    }

    public int insertMonth( Month object ) {
        return monthMapper.insertMonth( connection, object );
    }

    public int updateMonth( int monthId ) {
        return monthMapper.updateMonth( connection, monthId );
    }

    public int deleteMonth( int monthId ) {
        return monthMapper.deleteMonth( connection, monthId );
    }

    public List<MonthTransaction> getIncomesByMonthID( int monthId, String type ) {
        return monthTransactionMapper.getSpecificTransactionsByMonthID( connection, monthId, type );
    }

    public int insertIncome( MonthTransaction object ) {
        return monthTransactionMapper.insertMonthTransaction( connection, object );
    }

    public int updateIncome( int monthTransactionID ) {
        return monthTransactionMapper.updateMonthTransaction( connection, monthTransactionID );
    }

    public int deleteIncome( int monthTransactionID ) {
        return monthTransactionMapper.deleteMonthTransaction( connection, monthTransactionID );
    }
}
