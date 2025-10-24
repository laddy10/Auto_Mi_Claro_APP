package listeners;

import net.thucydides.core.model.DataTable;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.steps.ExecutedStepDescription;
import net.thucydides.core.steps.StepFailure;
import net.thucydides.core.steps.StepListener;
import utils.OllamaClient;

import java.io.IOException;
import java.util.Map;

public class OllamaStepListener implements StepListener {

    private final OllamaClient ollamaClient = new OllamaClient();

    @Override
    public void testFailed(TestOutcome result, Throwable cause) {
        System.out.println("❌ Prueba fallida detectada: " + result.getName());
        System.out.println("📄 Error: " + cause.getMessage());

        try {
            // ✅ Enviar el error y el nombre del test a Ollama
            String prompt = String.format(
                    "Analiza el siguiente error de automatización y sugiere una causa probable y posible solución:\n" +
                            "Test: %s\nError: %s",
                    result.getName(), cause.getMessage()
            );

            String respuesta = ollamaClient.ask(prompt);
            System.out.println("🧠 Análisis de Ollama:\n" + respuesta);

        } catch (IOException e) {
            System.err.println("⚠️ Error al consultar Ollama: " + e.getMessage());
        }
    }

    // Otros métodos de StepListener (puedes dejarlos vacíos)
    @Override public void testSuiteStarted(Class<?> storyClass) {}
    @Override public void testSuiteStarted(net.thucydides.core.model.Story story) {}
    @Override public void testSuiteFinished() {}
    @Override public void testStarted(String testName) {}
    @Override public void testStarted(String testName, String id) {}
    @Override public void testFinished(TestOutcome result) {}
    @Override public void testIgnored() {}
    @Override public void testSkipped() {}
    @Override public void testPending() {}

    @Override
    public void testIsManual() {

    }

    @Override
    public void notifyScreenChange() {

    }

    @Override
    public void useExamplesFrom(DataTable dataTable) {

    }

    @Override
    public void addNewExamplesFrom(DataTable dataTable) {

    }

    @Override
    public void exampleStarted(Map<String, String> map) {

    }

    @Override
    public void exampleFinished() {

    }

    @Override
    public void assumptionViolated(String s) {

    }

    @Override
    public void testRunFinished() {

    }

    @Override public void stepFinished() {}
    @Override public void stepIgnored() {}
    @Override public void stepPending() {}

    @Override
    public void stepPending(String s) {

    }

    @Override public void testRetried() {}

    @Override
    public void stepStarted(ExecutedStepDescription executedStepDescription) {

    }

    @Override
    public void skippedStepStarted(ExecutedStepDescription executedStepDescription) {

    }

    @Override
    public void stepFailed(StepFailure stepFailure) {

    }

    @Override
    public void lastStepFailed(StepFailure stepFailure) {

    }
}
