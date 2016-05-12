package mappers;

import entity.Income;
import java.util.ArrayList;
import java.util.List;

public class IncomesMapper {

    public List<Income> getIncomesByMonthID( int monthId ) {
        return new ArrayList<Income>();
    }

    public int insertIncome( Income object ) {
        return 1;
    }

    public int updateIncome( int incomeId ) {
        return 1;
    }

    public int deleteIncome( int incomeId ) {
        return 1;
    }
}
