package cucumber.feature;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class MonthTransactionTwoTest {

    @Given( "/^I have entered \"(.*?)\" as month transaction name into the GUI$/" )
    public void addName( String input_1 ) {
        System.out.println( "input1" + input_1 );
    }

    @Given( "/^I have chosen \"(.*?)\" as a type of the month transaction in the GUI$/" )
    public void addType( String input_2 ) {
        System.out.println( "input2" + input_2 );
    }

    @Given( "/^I have chosen \"(.*?)\" as a month of the month transaction in the GUI$/" )
    public void addMonth( String input_3 ) {
        System.out.println( "input3" + input_3 );
    }

    @Given( "/^I have chosen <input_(\\d+) > as a category of the month transaction in the GUI$/" )
    public void addCategory( String input_4 ) {
        System.out.println( "input4" + input_4 );
    }

    @Given( "/^I have entered (\\d+)\\.(\\d+)  as an ammount of the month transaction in the GUI$/" )
    public void addAmmount( String input_5 ) {
        System.out.println( "input5" + input_5 );
    }

    @When( "/^I press the add button$/" )
    public void whenAdd() {
        System.out.println( "Add clicked!" );
    }

    @Then( "/^the result on the GUI should be \"(.*?)\"$/" )
    public void checkResult( String input_6 ) {
        System.out.println( "input6 " + input_6 );
    }

    @Then( "/^if there is not error the month transaction should be added to the database$/" )
    public void checkStuff() {
        System.out.println( "check stuff1" );
    }

    @Given( "/^I have entered   as an ammount of the month transaction in the GUI$/" )
    public void checkStuff2() {
        System.out.println( "check stuff2" );
    }
}
