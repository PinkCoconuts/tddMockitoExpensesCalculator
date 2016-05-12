package mappers;

import entity.Expense;
import java.util.ArrayList;
import java.util.List;

public class ExpensesMapper {

    public List<Expense> getExpencesByMonthID( int monthId ) {
        return new ArrayList<Expense>();
    }

    public int insertExpense( Expense object ) {
        return 1;
    }

    public int updateExpense( int expenseId ) {
        return 1;
    }

    public int deleteExpense( int expenseId ) {
        return 1;
    }
}
