package hamcrestTests;

import entity.Category;
import static hamcrestTests.CustomMatcherMonthTest.matches;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.AdditionalMatchers.not;

public class CustomizedMatcher {

    public CustomizedMatcher() {
    }

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
    public void hello() {
        Category cat1 = new Category( 1, "Tennis" );
        Category cat2 = new Category( 1, "Tennis" );
        Category cat3 = new Category( 1, "Blah" );
        assertThat( cat1, matches( cat2 ) );
    }
}
