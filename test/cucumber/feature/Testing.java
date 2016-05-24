package cucumber.feature;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Testing {

    @Given( "/^this is my first step$/" )
    public void givenStuff() {
        System.out.println( "GIVEN" );
    }

    @When( "/^this is my second step$/" )
    public void whenStuff() {
        System.out.println( "WHEN" );
    }

    @Then( "/^this is my final step$/" )
    public void thenStuff() {
        System.out.println( "THEN" );
    }
}
