package java.feature.monthtransaction;

import controller.Controller;
import cucumber.api.junit.Cucumber;
import entity.MonthTransaction;
import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.*;

/**
 *
 * @author Cristi
 */
public class MonthTransactionAddSteps {

    private MonthTransaction monthTransaction;
    private List<MonthTransaction> transactions = new ArrayList<>();
    private Controller controller = new Controller();
    private String name, type, monthId, categoryId, amount;
    private Boolean result;

    @Given("'(.+)' as month transaction name, '(.+)' as a type of the transaction, '(.+)' as a month, '(.+)' as a category, (.+)  as an ammount")
    public void addMonthTransaction(final String name, final String type, final String monthId, final String categoryId, final String amount) {
        this.name = name;
        this.type = type;
        this.monthId = monthId;
        this.categoryId = categoryId;
        this.amount = amount;
    }

    @When("I press the '(.+)' button")
    public void setOperation(final String op) {
        if (op.equals("add")) {
            result = controller.addMonthTransactions(name, monthId, categoryId, type, amount);
        }
    }

    @Then("the result should be '(.+)'")
    public void verifyAmountOfBooksFound(final String expectedResult) {
        if (result == true) {
            assertEquals(expectedResult, "Month Transaction was inserted successfully!");
        } else {
            assertEquals(expectedResult, "Ups! An error occurred. Please try again later");
        }
    }

}