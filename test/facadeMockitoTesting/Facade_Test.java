package facadeMockitoTesting;

import entity.Category;
import entity.Month;
import entity.MonthTransaction;
import facade.Facade;
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
        facade = Facade.getInstance();
        facade.setCategoryMapper( mockCategoryMapper );
        facade.setMonthMapper( mockMonthMapper );
        facade.setMonthTransactionMapper( mockMonthTransactionMapper );
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

        Mockito.when( mockMonthMapper.getMonths( null, null ) ).thenAnswer( new Answer() {

            List<Month> months = new ArrayList();

            @Override
            public Object answer( InvocationOnMock invocation ) throws Throwable {
                months.add( new Month( 1, "May 2016" ) );
                return months;
            }
        } );
        List<Month> lm = facade.getMonths( null );
        assertEquals( 1, lm.size() );
        lm = facade.getMonths( null );
        assertEquals( 2, lm.size() );
        lm = facade.getMonths( null );
        assertEquals( 3, lm.size() );
    }

    @Test
    public void testGetMonthByID() {
        int monthId = 23;
        Month monthObj = new Month( 23, "Feb 2016" );
        Mockito.when( mockMonthMapper.getMonthByID( null, null, monthId ) ).thenReturn( monthObj );

        assertEquals( monthObj, facade.getMonthByID( null, monthId ) );
    }

    @Test
    public void testInsertMonth() {
        Month monthObj = new Month( 1, "May 2016" );
        Mockito.when( mockMonthMapper.insertMonth( null, null, monthObj ) ).thenReturn( monthObj );

        assertEquals( monthObj, facade.insertMonth( null, monthObj ) );
    }

    @Test
    public void testUpdateMonth() {
        int monthId = 23;
        Month newObject = new Month( 1, "May 2016" );
        Mockito.when( mockMonthMapper.updateMonth( null, null, monthId, newObject ) ).thenReturn( newObject );

        assertEquals( newObject, facade.updateMonth( null, monthId, newObject ) );
    }

    @Test
    public void testDeleteMonth() {
        int monthId = 2;
        Mockito.when( mockMonthMapper.deleteMonth( null, null, monthId ) ).thenReturn( true );

        assertEquals( true, facade.deleteMonth( null, monthId ) );
    }

    @Test
    public void testInsertMonthTransaction() {
        MonthTransaction monthTransObj = new MonthTransaction( 1, "Burger", "Expense", 2, 3, 300 );
        Mockito.when( mockMonthTransactionMapper.insertMonthTransaction( null, null, monthTransObj ) ).thenReturn( monthTransObj );

        assertEquals( monthTransObj, facade.insertMonthTransaction( null, monthTransObj ) );
    }

    @Test
    public void testUpdateMonthTransaction() {
        int monthTransactionId = 2;
        MonthTransaction monthTransObj = new MonthTransaction( 1, "Burger", "Expense", 2, 3, 300 );
        Mockito.when( mockMonthTransactionMapper.updateMonthTransaction( null, null, monthTransactionId, monthTransObj ) ).thenReturn( monthTransObj );

        assertEquals( monthTransObj, facade.updateMonthTransaction( null, monthTransactionId, monthTransObj ) );
    }

    @Test
    public void testDeletMonthTransaction() {
        int monthTransactionId = 2;
        Mockito.when( mockMonthTransactionMapper.deleteMonthTransaction( null, null, monthTransactionId ) ).thenReturn( true );

        assertEquals( true, facade.deleteMonthTransaction( null, monthTransactionId ) );
    }

    @Test
    public void testGetCategories() {

        Mockito.when( mockCategoryMapper.getCategories( null, facade.getCategories( null ) ) ).thenAnswer( new Answer() {

            List<Category> categories = new ArrayList();

            @Override
            public Object answer( InvocationOnMock invocation ) throws Throwable {
                categories.add( new Category( 1, "Food" ) );
                return categories;
            }
        } );

        List<Category> cl = facade.getCategories( null );
        assertEquals( 1, cl.size() );
        cl = facade.getCategories( null );
        assertEquals( 2, cl.size() );
        cl = facade.getCategories( null );
        assertEquals( 3, cl.size() );
    }

    @Test
    public void testGetCategoryByID() {
        int categoryId = 23;
        Category categoryObj = new Category( 2, "Music" );
        Mockito.when( mockCategoryMapper.getCategoryByID( null, null, categoryId ) ).thenReturn( categoryObj );

        assertEquals( categoryObj, facade.getCategoryByID( null, categoryId ) );
    }

    @Test
    public void testInsertCategory() {
        Category categoryObj = new Category( 2, "Music" );
        Mockito.when( mockCategoryMapper.insertCategory( null, null, categoryObj ) ).thenReturn( categoryObj );

        assertEquals( categoryObj, facade.insertCategory( null, categoryObj ) );
    }

    @Test
    public void testUpdateCategory() {
        int categoryId = 2;
        Category categoryObj = new Category( 2, "Music" );
        Mockito.when( mockCategoryMapper.updateCategory( null, null, categoryId, categoryObj ) ).thenReturn( categoryObj );

        assertEquals( categoryObj, facade.updateCategory( null, categoryId, categoryObj ) );
    }

    @Test
    public void testDeletCategory() {
        int categoryId = 2;
        Mockito.when( mockCategoryMapper.deleteCategory( null, null, categoryId ) ).thenReturn( true );

        assertEquals( true, facade.deleteCategory( null, categoryId ) );
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
