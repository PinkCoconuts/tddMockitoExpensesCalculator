package testing;

import facade.Facade;
import java.sql.Connection;
import mappers.MonthTransactionMapper;

public class Tester {
    
    public static void main( String[] args ) {
        new Tester().tester();
    }
    
    private void tester() {
        Facade facade = Facade.getInstance();
        MonthTransactionMapper mtm = new MonthTransactionMapper();
        facade.initializeConnection( null );
        Connection connection = facade.getConnection();
        mtm.getAllTransactions( connection, null, 0, 0, "" );
        mtm.getAllTransactions( connection, null, 1, 0, "" );
        mtm.getAllTransactions( connection, null, 0, 1, "" );
        mtm.getAllTransactions( connection, null, 0, 0, "expense" );
        mtm.getAllTransactions( connection, null, 1, 1, "" );
        mtm.getAllTransactions( connection, null, 1, 0, "expense" );
        mtm.getAllTransactions( connection, null, 0, 1, "expense" );
        mtm.getAllTransactions( connection, null, 1, 1, "expense" );
    }
}
