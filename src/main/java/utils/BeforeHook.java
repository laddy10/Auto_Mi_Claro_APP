package utils;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;

import cucumber.api.Scenario;
import jxl.common.Logger;
import listeners.OllamaStepListener;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.abiities.CallAnApi;
import net.thucydides.core.steps.StepEventBus;
import org.junit.After;
import org.junit.Before;

public class BeforeHook {

  private static final Logger LOGGER = Logger.getLogger(BeforeHook.class);
  private static boolean listenerRegistrado = false;

  @Before
  public void initScenario(Scenario scenario) {
    LOGGER.info("\n══════════════════════════════════════════════════════");
    LOGGER.info("🚀 Iniciando escenario: " + scenario.getName());
    LOGGER.info("══════════════════════════════════════════════════════");

    // 🔹 Registrar el listener de Ollama solo una vez
    if (!listenerRegistrado) {
      StepEventBus.getEventBus().registerListener(new OllamaStepListener());
      listenerRegistrado = true;
      LOGGER.info("[OLLAMA] Listener registrado correctamente ✅");
    }

    // 🔹 Inicializar actores de Serenity
    OnStage.setTheStage(new OnlineCast());
  }

  public static void prepareStage(String urlBase) {
    OnStage.setTheStage(new OnlineCast());
    theActorCalled("Usuario").whoCan(CallAnApi.at(urlBase));
  }

  @After
  public void endScenario(Scenario scenario) {
    LOGGER.info("══════════════════════════════════════════════════════");
    LOGGER.info("🏁 Escenario finalizado: " + scenario.getName());
    LOGGER.info("══════════════════════════════════════════════════════\n");
  }
}
