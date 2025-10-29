package runners;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.runner.RunWith;
import utils.BeforeSuite;
import utils.DataToFeature;

import java.io.IOException;

/**
 * Runner general para ejecutar escenarios de Cucumber con Serenity BDD.
 *
 * - Carga los feature files generados desde Excel antes de la ejecución.
 * - Usa el listener de Ollama (registrado desde BeforeHook).
 * - Permite ejecutar por tags o escenarios específicos.
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinitions", "utils", "hooks", "listeners"},
        snippets = SnippetType.CAMELCASE,
        plugin = {"pretty"},
        tags = "@SA001"
)
public class GeneralRunner {

    /**
     * Se ejecuta antes de todo el suite de pruebas.
     * Sobrescribe los archivos .feature generados dinámicamente desde Excel.
     */
    @BeforeSuite
    public static void generateFeatureFiles() throws InvalidFormatException, IOException {
        System.out.println("📄 [INFO] Generando archivos .feature desde Excel...");
        DataToFeature.overrideFeatureFiles("src/test/resources/features");
        System.out.println("✅ [INFO] Generación de features completada.");
    }
}
