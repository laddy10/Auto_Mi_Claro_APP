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

    // 🔹 Se ejecuta cuando falla un escenario completo
    @Override
    public void stepFailed(StepFailure failure) {
        String descripcionPaso = (failure.getDescription() != null)
                ? failure.getDescription().getName()
                : "Descripción no disponible";

        String mensajeError = (failure.getException() != null)
                ? failure.getException().getMessage()
                : "Sin mensaje de excepción";

        System.err.println("\n❌ [OLLAMA] Paso fallido detectado: " + descripcionPaso);
        System.err.println("📄 Error: " + mensajeError);

        try {
            String prompt = String.format(
                    "Analiza este error de automatización y sugiere una causa probable y solución:\nPaso: %s\nError: %s",
                    descripcionPaso, mensajeError
            );

            String respuesta = ollamaClient.ask(prompt);
            System.out.println("🧠 [OLLAMA] Análisis:\n" + respuesta);

        } catch (IOException e) {
            System.err.println("⚠️ Error al consultar Ollama: " + e.getMessage());
        }
    }

    // 🔹 Métodos requeridos por StepListener (sin implementación adicional)
    @Override public void testSuiteStarted(Class<?> storyClass) {}
    @Override public void testSuiteStarted(net.thucydides.core.model.Story story) {}
    @Override public void testSuiteFinished() {}
    @Override public void testStarted(String testName) {}
    @Override public void testStarted(String testName, String id) {}
    @Override public void testFinished(TestOutcome result) {}
    @Override public void testIgnored() {}
    @Override public void testSkipped() {}
    @Override public void testPending() {}
    @Override public void testIsManual() {}
    @Override public void notifyScreenChange() {}
    @Override public void useExamplesFrom(DataTable dataTable) {}
    @Override public void addNewExamplesFrom(DataTable dataTable) {}
    @Override public void exampleStarted(Map<String, String> map) {}
    @Override public void exampleFinished() {}
    @Override public void assumptionViolated(String s) {}
    @Override public void testRunFinished() {}
    @Override public void stepFinished() {}

    @Override
    public void testFailed(TestOutcome testOutcome, Throwable throwable) {

    }

    @Override public void stepIgnored() {}
    @Override public void stepPending() {}
    @Override public void stepPending(String s) {}
    @Override public void testRetried() {}
    @Override public void stepStarted(ExecutedStepDescription executedStepDescription) {}
    @Override public void skippedStepStarted(ExecutedStepDescription executedStepDescription) {}
    @Override public void lastStepFailed(StepFailure stepFailure) {}
}
