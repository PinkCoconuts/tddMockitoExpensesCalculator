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
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/*
 * This Mockito test does not include logging or hamcrest, because of simplicity 
 * reasons.
 */
public class Facade_Test {

    //facade
    private static Facade facade;

    //Mockito mock objects
    private static CategoryMapper mockCategoryMapper;
    private static MonthMapper mockMonthMapper;
    private static MonthTransactionMapper mockMonthTransactionMapper;

    @BeforeClass
    public static void setUpClass() {
        //initialize the mock objects
        mockCategoryMapper = Mockito.mock( CategoryMapper.class );
        mockMonthMapper = Mockito.mock( MonthMapper.class );
        mockMonthTransactionMapper = Mockito.mock( MonthTransactionMapper.class );

        //initialize the facade
        facade = Facade.getInstance();

        //Set the mocked mapper objects to the facade for testing purposes.
        facade.setCategoryMapper( mockCategoryMapper );
        facade.setMonthMapper( mockMonthMapper );
        facade.setMonthTransactionMapper( mockMonthTransactionMapper );
    }

    @AfterClass
    public static void tearDownClass() {
        facade = null;

        mockCategoryMapper = null;
        mockMonthMapper = null;
        mockMonthTransactionMapper = null;
    }

    /*
     * Test Category functionality in facade
     */
    @Test
    public void testGetCategories() {

        Mockito.when(
                mockCategoryMapper.getCategories( null, facade.getCategories( null ) ) )
                .thenAnswer( new Answer() {

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
        int id = 23;
        Category object = new Category( 2, "Music" );

        Mockito.when( mockCategoryMapper.getCategoryByID( null, null, id ) )
                .thenReturn( object );

        assertEquals( object, facade.getCategoryByID( null, id ) );
    }

    @Test
    public void testInsertCategory() {
        Category object = new Category( 2, "Music" );

        Mockito.when( mockCategoryMapper.insertCategory( null, null, object ) )
                .thenReturn( object );

        assertEquals( object, facade.insertCategory( null, object ) );
    }

    @Test
    public void testUpdateCategory() {
        int id = 2;
        Category object = new Category( 2, "Music" );

        Mockito.when( mockCategoryMapper.updateCategory( null, null, id, object ) )
                .thenReturn( object );

        assertEquals( object, facade.updateCategory( null, id, object ) );
    }

    @Test
    public void testDeleteCategory() {
        int id = 2;

        Mockito.when( mockCategoryMapper.deleteCategory( null, null, id ) )
                .thenReturn( true );

        assertEquals( true, facade.deleteCategory( null, id ) );
    }

    @Test
    public void testDeleteAllCategories() {
        Mockito.when( mockCategoryMapper.deleteAllCategories( null, null ) )
                .thenReturn( true );
        assertEquals( true, facade.deleteAllCategories( null ) );
    }

    /*
     * Test month functionality in facade
     */
    @Test
    public void testGetMonths() {

        Mockito.when( mockMonthMapper.getMonths( null, null ) )
                .thenAnswer( new Answer() {

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
        int id = 23;
        Month object = new Month( 23, "Feb 2016" );

        Mockito.when( mockMonthMapper.getMonthByID( null, null, id ) )
                .thenReturn( object );

        assertEquals( object, facade.getMonthByID( null, id ) );
    }

    @Test
    public void testInsertMonth() {
        Month object = new Month( 1, "May 2016" );

        Mockito.when( mockMonthMapper.insertMonth( null, null, object ) )
                .thenReturn( object );

        assertEquals( object, facade.insertMonth( null, object ) );
    }

    @Test
    public void testUpdateMonth() {
        int id = 23;
        Month object = new Month( 1, "May 2016" );

        Mockito.when( mockMonthMapper.updateMonth( null, null, id, object ) )
                .thenReturn( object );

        assertEquals( object, facade.updateMonth( null, id, object ) );
    }

    @Test
    public void testDeleteMonth() {
        int id = 2;

        Mockito.when( mockMonthMapper.deleteMonth( null, null, id ) )
                .thenReturn( true );

        assertEquals( true, facade.deleteMonth( null, id ) );
    }

    @Test
    public void testDeleteAllMonths() {

        Mockito.when( mockMonthMapper.deleteAllMonths( null, null ) )
                .thenReturn( true );

        assertEquals( true, facade.deleteAllMonths( null ) );
    }

    /*
     * Test month transaction functionality in facade
     */
    @Test
    public void testGetMonthTransactions() {

        Mockito.when( mockMonthTransactionMapper.getAllTransactions( null, null ) )
                .thenAnswer( new Answer() {

                    List<MonthTransaction> transactions = new ArrayList();

                    @Override
                    public Object answer( InvocationOnMock invocation ) throws Throwable {
                        transactions.add(
                                new MonthTransaction( 1, "Burger", "Expense", 2, 3, 300 )
                        );

                        return transactions;
                    }
                } );

        List<MonthTransaction> mtl = facade.getAllTransactions( null );
        assertEquals( 1, mtl.size() );
        mtl = facade.getAllTransactions( null );
        assertEquals( 2, mtl.size() );
        mtl = facade.getAllTransactions( null );
        assertEquals( 3, mtl.size() );
    }

    @Test
    public void testGetMonthTransactionsAbstract() {
        int monthId = 3;
        int categoryId = 16;
        String type = "expense";

        Mockito.when( mockMonthTransactionMapper
                .getAllTransactions( null, null, monthId, categoryId, type ) )
                .thenAnswer( new Answer() {

                    List<MonthTransaction> transactions = new ArrayList();

                    @Override
                    public Object answer( InvocationOnMock invocation ) throws Throwable {
                        transactions.add(
                                new MonthTransaction( 1, "Burger", type, monthId,
                                                      categoryId, 300 )
                        );

                        return transactions;
                    }
                } );

        List<MonthTransaction> mtl = facade.getAllTransactions( null, monthId, categoryId, type );
        assertEquals( 1, mtl.size() );
        mtl = facade.getAllTransactions( null, monthId, categoryId, type );
        assertEquals( 2, mtl.size() );
        mtl = facade.getAllTransactions( null, monthId, categoryId, type );
        assertEquals( 3, mtl.size() );
    }

    @Test
    public void testGetTransactionByID() {
        int id = 23;
        MonthTransaction object = new MonthTransaction( 1, "Burger", "Expense", 2, 3, 300 );

        Mockito.when( mockMonthTransactionMapper.getTransactionsByID( null, null, id ) )
                .thenReturn( object );

        assertEquals( object, facade.getTransactionsByID( null, id ) );
    }

    @Test
    public void testInsertMonthTransaction() {
        MonthTransaction object = new MonthTransaction( 1, "Burger", "Expense", 2, 3, 300 );

        Mockito.when( mockMonthTransactionMapper.insertMonthTransaction( null, null, object ) )
                .thenReturn( object );

        assertEquals( object, facade.insertMonthTransaction( null, object ) );
    }

    @Test
    public void testUpdateMonthTransaction() {
        int id = 2;
        MonthTransaction object = new MonthTransaction( 1, "Burger", "Expense", 2, 3, 300 );

        Mockito.when( mockMonthTransactionMapper.updateMonthTransaction( null, null, id, object ) )
                .thenReturn( object );

        assertEquals( object, facade.updateMonthTransaction( null, id, object ) );
    }

    @Test
    public void testDeleteMonthTransaction() {
        int id = 2;

        Mockito.when( mockMonthTransactionMapper.deleteMonthTransaction( null, null, id ) )
                .thenReturn( true );

        assertEquals( true, facade.deleteMonthTransaction( null, id ) );
    }

    @Test
    public void testDeleteAllMonthTransactions() {

        Mockito.when( mockMonthTransactionMapper.deleteAllMonthTransactions( null, null ) )
                .thenReturn( true );

        assertEquals( true, facade.deleteAllMonthTransactions( null ) );
    }

    /*
     @Test
     public void testInsertMonthSQLFailure() throws Exception {
     Month monthObj = new Month( 1, "May 2016" );
        
     Mockito.when( mockMonthMapper.insertMonth( null, monthObj ) ).thenThrow( new SQLException() );
     assertEquals( -1, facade.insertMonth( monthObj ) );
     }
     */
    /*
     * Those tests are working, but they are breaking the Mockito mock object
     * and they fail all other tests.
     *
     @Test
     public void testSetCategoryMapper() {

     CategoryMapper categoryMock = Mockito.mock( CategoryMapper.class );

     Mockito.when( facade.setCategoryMapper( categoryMock ) )
     .thenReturn( true );

     assertEquals( true, facade.setCategoryMapper( categoryMock ) );
     }

     @Test
     public void testSetMonthMapper() {

     MonthMapper monthMapper = Mockito.mock( MonthMapper.class );

     Mockito.when( facade.setMonthMapper( monthMapper ) )
     .thenReturn( true );

     assertEquals( true, facade.setMonthMapper( monthMapper ) );
     }

     @Test
     public void testSetMonthTransactionMapper() {

     MonthTransactionMapper monthTransactionMapper = Mockito.mock(
     MonthTransactionMapper.class );

     Mockito.when( facade.setMonthTransactionMapper( monthTransactionMapper ) )
     .thenReturn( true );

     assertEquals( true, facade.setMonthTransactionMapper( monthTransactionMapper ) );
     }
    
     @Test
     public void testInitializeConnection() {

     Mockito.when( facade.initializeConnection( null ) )
     .thenReturn( true );

     assertEquals( true, facade.initializeConnection( null ) );
     }
     */
}
