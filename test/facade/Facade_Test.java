package facade;

import entity.Month;
import entity.MonthTransaction;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mappers.CategoryMapper;
import mappers.MonthMapper;
import mappers.MonthTransactionMapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.doThrow;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class Facade_Test {

    private static Facade facade;
    private static MonthMapper mockMonthMapper;
    private static MonthTransactionMapper mockMonthTransactionMapper;
    private static CategoryMapper mockCategoryMapper;

    @BeforeClass
    public static void setUpClass() {
        mockMonthMapper = Mockito.mock( MonthMapper.class );
        mockMonthTransactionMapper = Mockito.mock( MonthTransactionMapper.class );
        mockCategoryMapper = Mockito.mock( CategoryMapper.class );
        facade = new Facade( mockMonthMapper, mockMonthTransactionMapper, mockCategoryMapper );
    }

    @AfterClass
    public static void tearDownClass() {
        facade = null;
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testGetMonths() {

        Mockito.when( mockMonthMapper.getMonths( null ) ).thenAnswer( new Answer() {

            List<Month> months = new ArrayList();

            @Override
            public Object answer( InvocationOnMock invocation ) throws Throwable {
                months.add( new Month( 1, "May 2016" ) );
                return months;
            }
        } );

        assertEquals( 1, facade.getMonths().size() );
        assertEquals( 2, facade.getMonths().size() );
        assertEquals( 3, facade.getMonths().size() );
    }

    @Test
    public void testInsertMonth() {
        Month monthObj = new Month( 1, "May 2016" );
        Mockito.when( mockMonthMapper.insertMonth( null, monthObj ) ).thenReturn( 35 );

        assertEquals( 35, facade.insertMonth( monthObj ) );
    }

    @Test
    public void testUpdateMonth() {
        int monthId = 23;
        Month newObject = new Month( 1, "May 2016" );
        Mockito.when( mockMonthMapper.updateMonth( null, monthId, newObject ) ).thenReturn( 2 );

        assertEquals( 2, facade.updateMonth( monthId, newObject ) );
    }

    @Test
    public void testDeleteMonth() {
        int monthId = 2;
        Mockito.when( mockMonthMapper.deleteMonth( null, monthId ) ).thenReturn( 5 );

        assertEquals( 5, facade.deleteMonth( monthId ) );
    }

    @Test
    public void testGetSpecificTransactionsByMonthID() {
        int monthTransactionId = 2;
        String type = "expenses";
        Mockito.when( mockMonthTransactionMapper.getSpecificTransactionsByMonthID( null, monthTransactionId, type ) ).thenAnswer( new Answer() {

            List<MonthTransaction> monthTransactions = new ArrayList();

            @Override
            public Object answer( InvocationOnMock invocation ) throws Throwable {
                monthTransactions.add( new MonthTransaction( 1, "Burger", "Expense", 2, 3, 300 ) );
                return monthTransactions;
            }
        } );

        assertEquals( 1, facade.getSpecificTransactionsByMonthID( monthTransactionId, type ).size() );
        assertEquals( 2, facade.getSpecificTransactionsByMonthID( monthTransactionId, type ).size() );
        assertEquals( 3, facade.getSpecificTransactionsByMonthID( monthTransactionId, type ).size() );
    }

    @Test
    public void testInsertMonthTransaction() {
        MonthTransaction monthTransObj = new MonthTransaction( 1, "Burger", "Expense", 2, 3, 300 );
        Mockito.when( mockMonthTransactionMapper.insertMonthTransaction( null, monthTransObj ) ).thenReturn( 2 );

        assertEquals( 2, facade.insertMonthTransaction( monthTransObj ) );
    }

    @Test
    public void testUpdateMonthTransaction() {
        int monthTransactionId = 2;
        MonthTransaction monthTransObj = new MonthTransaction( 1, "Burger", "Expense", 2, 3, 300 );
        Mockito.when( mockMonthTransactionMapper.updateMonthTransaction( null, monthTransactionId, monthTransObj ) ).thenReturn( 2 );

        assertEquals( 2, facade.updateMonthTransaction( monthTransactionId, monthTransObj ) );
    }

    /*
     @Test
     public void testInsertMonthSQLFailure() throws Exception {
     Month monthObj = new Month( 1, "May 2016" );
        
     Mockito.when( mockMonthMapper.insertMonth( null, monthObj ) ).thenThrow( new SQLException() );
     assertEquals( -1, facade.insertMonth( monthObj ) );
     }
     */
}
