package cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith( Cucumber.class )
@CucumberOptions(
        format = { "pretty" },
        features = { "test/cucumber/feature/" },
        glue = { "test/cucumber/feature" },
        monochrome = true )
public class RunFeatures {
}

//@Cucumber.Options(
//         format = { "pretty", "json:target/cucumber.json" },
//        features = { "test/cucumber/feature/" },
//        dryRun = true,
//        glue = { "com.example.stepdefspackage" } )
