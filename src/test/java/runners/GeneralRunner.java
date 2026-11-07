package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import java.io.IOException;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.runner.RunWith;
import org.testng.annotations.BeforeSuite;
import utils.DataToFeature;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {"stepDefinitions", "utils", "hooks", "listeners"},
    snippets = SnippetType.CAMELCASE,
    plugin = {"pretty"},
    tags = "@SA004")
public class GeneralRunner {
  @BeforeSuite
  public static void generateFeatureFiles() throws InvalidFormatException, IOException {
    System.out.println("📄 [INFO] Generando archivos .feature desde Excel...");
    DataToFeature.overrideFeatureFiles("src/test/resources/features");
    System.out.println("✅ [INFO] Generación de features completada.");
  }
}
