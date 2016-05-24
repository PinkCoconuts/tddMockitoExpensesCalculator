package cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith( Cucumber.class )
@CucumberOptions( features = "/feature" )
public class RunFeatures {
}

//@Cucumber.Options(
//         format = { "pretty", "json:target/cucumber.json" },
//        features = { "test/cucumber/feature/" },
//        dryRun = true,
//        glue = { "com.example.stepdefspackage" } )
