package facade;

import entity.Month;
import mappers.MonthMapper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;

public class Facade_Test {

    private Facade facade;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
        facade = null;
    }

    @Test
    public void testHelloMethod() {

        MonthMapper mockMonthMapper = Mockito.mock( MonthMapper.class );

        facade = new Facade( null, null, mockMonthMapper );

        Month monthObj = new Month( 1, "May 2016" );

        Mockito.when( mockMonthMapper.insertMonth( monthObj ) ).thenReturn( 35 );

        assertEquals( 35, facade.addMonth( monthObj ) );
    }
}
