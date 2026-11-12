package listeners;

import java.util.Map;
import net.thucydides.core.model.DataTable;
import net.thucydides.core.model.Story;
import net.thucydides.core.model.TestOutcome;
import net.thucydides.core.steps.ExecutedStepDescription;
import net.thucydides.core.steps.StepFailure;
import net.thucydides.core.steps.StepListener;
import utils.EstadoPrueba;

/**
 * Listener de Serenity que guarda la descripción del error del paso fallido para el reporte Word
 * ({@link EstadoPrueba#capturarError(String)}).
 *
 * <p>Única responsabilidad: no hace análisis con IA, no captura page source ni hace llamadas
 * HTTP.
 *
 * <p>Compatible con: Serenity BDD 2.0.71, Cucumber API 2.x, Java 11
 */
public class ErrorStepListener implements StepListener {

  @Override
  public void stepFailed(StepFailure failure) {
    // Reporte Word: guardar la descripción del error (solo se conserva la primera del escenario)
    EstadoPrueba.capturarError(
        (failure != null && failure.getException() != null)
            ? failure.getException().getMessage()
            : null);
  }

  // ===================================================================
  // Implementación de StepListener (sin comportamiento adicional)
  // ===================================================================

  @Override
  public void testSuiteStarted(Class<?> storyClass) {}

  @Override
  public void testSuiteStarted(Story story) {}

  @Override
  public void testSuiteFinished() {}

  @Override
  public void testStarted(String description, String id) {}

  @Override
  public void testStarted(String description) {}

  @Override
  public void testFinished(TestOutcome result) {}

  @Override
  public void testRetried() {}

  @Override
  public void stepStarted(ExecutedStepDescription description) {}

  @Override
  public void skippedStepStarted(ExecutedStepDescription description) {}

  @Override
  public void stepFinished() {}

  @Override
  public void stepIgnored() {}

  @Override
  public void stepPending() {}

  @Override
  public void stepPending(String message) {}

  @Override
  public void lastStepFailed(StepFailure stepFailure) {}

  @Override
  public void testFailed(TestOutcome testOutcome, Throwable cause) {}

  @Override
  public void testIgnored() {}

  @Override
  public void testSkipped() {}

  @Override
  public void testPending() {}

  @Override
  public void testIsManual() {}

  @Override
  public void notifyScreenChange() {}

  @Override
  public void useExamplesFrom(DataTable table) {}

  @Override
  public void addNewExamplesFrom(DataTable table) {}

  @Override
  public void exampleStarted(Map<String, String> data) {}

  @Override
  public void exampleFinished() {}

  @Override
  public void assumptionViolated(String message) {}

  @Override
  public void testRunFinished() {}
}
