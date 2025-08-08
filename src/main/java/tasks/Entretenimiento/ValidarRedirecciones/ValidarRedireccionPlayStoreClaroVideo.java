package tasks.Entretenimiento.ValidarRedirecciones;

import io.appium.java_client.android.AndroidDriver;
import interactions.validations.ValidarTexto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.webdriver.WebDriverFacade;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ValidarRedireccionPlayStoreClaroVideo implements Task {

    private static final String paso = "Validar redirección a la Play Store";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Obtener el driver original de Appium desde el facade de Serenity
        WebDriverFacade facade = (WebDriverFacade) BrowseTheWeb.as(actor).getDriver();
        AndroidDriver driver = (AndroidDriver) facade.getProxiedDriver();

        // Activar la app Play Store
        driver.activateApp("com.android.vending");

        // Esperar unos segundos para que la Play Store cargue
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Validar textos visibles en Play Store
        actor.attemptsTo(
                ValidarTexto.validarTexto("Claro video"),
                ValidarTexto.validarTexto("Abrir"),
                ValidarTexto.validarTexto("Desinstalar")
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable validar() {
        return instrumented(ValidarRedireccionPlayStoreClaroVideo.class);
    }
}
