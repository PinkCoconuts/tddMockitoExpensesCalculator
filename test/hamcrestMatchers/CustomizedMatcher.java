package hamcrestMatchers;

import entity.Category;
import entity.Month;
import entity.MonthTransaction;
import static hamcrestMatchers.CustomAbstractEntityClassMatcher.matches;
import static org.hamcrest.CoreMatchers.not;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class CustomizedMatcher {

    /*
     * Hamcrest Matchers Example
     */
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
    }

    @Test
    public void categoryTest() {
        Category cat1 = new Category( 1, "Tennis" );
        Category cat2 = new Category( 1, "Tennis" );
        Category cat3 = new Category( 1, "Blah" );
        assertThat( cat1, matches( cat2 ) );
        assertThat( cat1, not( matches( cat3 ) ) );
    }

    @Test
    public void monthTest() {
        Month mon1 = new Month( 1, "Jan 2016" );
        Month mon2 = new Month( 1, "Jan 2016" );
        Month mon3 = new Month( 1, "Feb 2016" );
        assertThat( mon1, matches( mon2 ) );
        assertThat( mon1, not( matches( mon3 ) ) );
    }

    @Test
    public void monthTransactionTest() {
        MonthTransaction mon1 = new MonthTransaction( 1, "rent", "accommodation", 1, 1, 10 );
        MonthTransaction mon2 = new MonthTransaction( 1, "rent", "accommodation", 1, 1, 10 );
        MonthTransaction mon3 = new MonthTransaction( 1, "rent", "accommodation 2", 1, 1, 10 );
        assertThat( mon1, matches( mon2 ) );
        assertThat( mon1, not( matches( mon3 ) ) );
    }
}
