package facade;

import entity.Month;
import mappers.ExpensesMapper;
import mappers.IncomesMapper;
import mappers.MonthMapper;

public class Facade {

    private final ExpensesMapper expensesMapper;
    private final IncomesMapper incomesMapper;
    private final MonthMapper monthMapper;

    public Facade() {
        this.expensesMapper = new ExpensesMapper();
        this.incomesMapper = new IncomesMapper();
        this.monthMapper = new MonthMapper();
    }

    public Facade( ExpensesMapper expensesMapper, IncomesMapper incomesMapper, MonthMapper monthMapper ) {
        this.expensesMapper = expensesMapper;
        this.incomesMapper = incomesMapper;
        this.monthMapper = monthMapper;
    }

    public int addMonth( Month object ) {
        return monthMapper.insertMonth( object );
    }

}
