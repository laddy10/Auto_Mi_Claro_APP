package tasks.Entretenimiento;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import interactions.validations.ValidarTexto;
import io.appium.java_client.android.AndroidDriver;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.webdriver.WebDriverFacade;
import utils.EvidenciaUtils;

public class ValidarRedireccionClaroMusicaApp implements Task {

  private static final String paso = "Validar redirección a la aplicación Claro Música";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // Obtener el driver Appium
    WebDriverFacade facade = (WebDriverFacade) BrowseTheWeb.as(actor).getDriver();
    AndroidDriver driver = (AndroidDriver) facade.getProxiedDriver();

    // Activar la app Claro Música
    driver.activateApp("com.claro.claromusica.latam");

    // Esperar a que la app cargue
    try {
      Thread.sleep(4000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    // Validar elementos visibles en Claro Música (ajusta si cambian)
    actor.attemptsTo(
        ValidarTexto.validarTexto("Inicio"),
        ValidarTexto.validarTexto("Buscar"),
        ValidarTexto.validarTexto("Mi Música"));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable validar() {
    return instrumented(ValidarRedireccionClaroMusicaApp.class);
  }
}
