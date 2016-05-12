package mappers;

import entity.MonthTransaction;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class MonthTransactionMapper {

    public List<MonthTransaction> getSpecificTransactionsByMonthID( Connection connection,
            int monthId, String type ) {
        return new ArrayList<MonthTransaction>();
    }

    public int insertMonthTransaction( Connection connection, MonthTransaction object ) {
        return 1;
    }

    public int updateMonthTransaction( Connection connection, int monthTransactionId,
            MonthTransaction newObject ) {
        return 1;
    }

    public int deleteMonthTransaction( Connection connection, int monthTransactionId ) {
        return 1;
    }
}
