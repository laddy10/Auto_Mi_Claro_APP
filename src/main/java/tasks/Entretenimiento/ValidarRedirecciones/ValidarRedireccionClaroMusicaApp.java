package tasks.Entretenimiento.ValidarRedirecciones;

import interactions.wait.WaitFor;
import io.appium.java_client.android.AndroidDriver;
import interactions.validations.ValidarTexto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.targets.Target;
import net.thucydides.core.webdriver.WebDriverFacade;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.EntretenimientoPage.*;

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
        actor.attemptsTo(WaitFor.aTime(500)); // medio segundo antes de validar
        if (isVisible(actor, LBL_MENSAJE_ALERT)) {
            actor.attemptsTo(
                    Click.on(BTN_ALERT_CONFIRM)
            );
        } else {
            actor.attemptsTo(WaitFor.aTime(1000));
        }
        // Validar elementos visibles en Claro Música (ajusta si cambian)
        actor.attemptsTo(
                ValidarTexto.validarTexto("Inicio"),
                ValidarTexto.validarTexto("Buscar"),
                ValidarTexto.validarTexto("Mi Música")
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    private <T extends Actor> boolean isVisible(T actor, Target element) {
        return Presence.of(element).answeredBy(actor);
    }

    public static Performable validar() {
        return instrumented(ValidarRedireccionClaroMusicaApp.class);
    }
}
