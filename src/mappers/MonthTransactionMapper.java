package mappers;

import entity.MonthTransaction;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MonthTransactionMapper {

    //here I think we also need get AllTransactions by category
    public List<MonthTransaction> getAllTransactions( Connection connection) {
        return new ArrayList<MonthTransaction>();
    }
    //here I think it should be category instead of type. 
    //And again, we need one more method for getting all transactions for one specific month, without taking into consideration the category
    public List<MonthTransaction> getSpecificTransactionsByMonthID( Connection connection,
            int monthId, String type ) {
        return new ArrayList<MonthTransaction>();
    }

    public MonthTransaction insertMonthTransaction( Connection connection, MonthTransaction object ) {
        return new MonthTransaction();
    }

    public int updateMonthTransaction( Connection connection, int monthTransactionId,
            MonthTransaction newObject ) {
        return 1;
    }

    public int deleteMonthTransaction( Connection connection, int monthTransactionId ) {
        return 1;
    }
}
