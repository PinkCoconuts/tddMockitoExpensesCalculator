package facade;

import entity.Month;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import mappers.MonthMapper;
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

    @BeforeClass
    public static void setUpClass() {
        mockMonthMapper = Mockito.mock( MonthMapper.class );
        facade = new Facade( mockMonthMapper, null, null );
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
        Mockito.when( mockMonthMapper.updateMonth( null, monthId ) ).thenReturn( 2 );

        assertEquals( 2, facade.updateMonth( monthId ) );
    }

    @Test
    public void testDeleteMonth() {
        int monthId = 13;
        Mockito.when( mockMonthMapper.deleteMonth( null, monthId ) ).thenReturn( 5 );

        assertEquals( 5, facade.deleteMonth( monthId ) );
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
