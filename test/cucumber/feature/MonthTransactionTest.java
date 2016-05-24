package cucumber.feature;

import cucumber.api.CucumberOptions;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import entity.MonthTransaction;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;

public class MonthTransactionTest {

    private MonthTransaction monthTransaction;
    private List<MonthTransaction> list = new ArrayList();

    @Given( "^a User has no money in their account$" )
    public void addANewMonthTransaction( String input_1, String input_2, String input_3,
            String input_4, double input_5 ) {
        System.out.println( "Hi : " + input_1 + ", " + input_2 + " ..." );

        monthTransaction = new MonthTransaction( 0, input_1, input_2, 0, 0, input_5 );
    }

    @When( "^£(\\d+) is deposited in to the account$" )
    public void whenUserPressesButton( String arg1 ) {

        if ( "button".equals( arg1 ) ) {
            list.add( monthTransaction );
        }
    }

    @Then( "^the balance should be £(\\d+)$" )
    public void theResultFromTheGUIShouldBe( String arg1 ) {
        assertEquals( "Month Transaction was inserted successfully!", arg1 );
    }

}
