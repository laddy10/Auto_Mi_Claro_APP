package exceptions;

import static utils.AndroidObject.androidDriver;

import interactions.wait.WaitFor;
import io.appium.java_client.MobileBy;
import java.util.concurrent.TimeUnit; // ⭐ Alternativa sin Duration
import net.serenitybdd.screenplay.Actor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Excepciones {

  private static final Logger LOGGER = LoggerFactory.getLogger(Excepciones.class);

  public void ExClickElTextoContiene(Actor actor, String text) {
    // Manejar popup "No permitir" si existe
    try {
      LOGGER.debug("Verificando popup 'No permitir'");

      // ⭐ Usando TimeUnit en lugar de Duration
      androidDriver(actor).manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

      androidDriver(actor)
          .findElement(new MobileBy.ByAndroidUIAutomator("new UiSelector().text(\"No permitir\")"))
          .click();

      LOGGER.info("Popup 'No permitir' cerrado");
      actor.attemptsTo(WaitFor.aTime(500));

    } catch (Exception e) {
      LOGGER.debug("Popup no presente, continuando...");
    } finally {
      // ⭐ Restaurar timeout por defecto
      androidDriver(actor).manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    // Click en elemento principal
    try {
      LOGGER.debug("Click en elemento: '{}'", text);

      androidDriver(actor)
          .findElement(
              new MobileBy.ByAndroidUIAutomator("new UiSelector().textContains(\"" + text + "\")"))
          .click();

      LOGGER.info("Click exitoso: '{}'", text);

    } catch (Exception e) {
      LOGGER.error("Error en click: '{}'", text, e);
      throw new RuntimeException("No se pudo hacer click: " + text, e);
    }
  }
}
