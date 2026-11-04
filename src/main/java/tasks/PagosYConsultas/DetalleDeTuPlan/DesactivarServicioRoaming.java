package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static userinterfaces.PagosyConsultasPrePage.BTON_ACTIVAR_ROAMING;
import static userinterfaces.PagosyConsultasPrePage.BTON_DESACTIVAR_ROAMING;

import io.appium.java_client.AppiumDriver;
import java.time.Duration;
import java.util.Collections;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedCondition;
import utils.AndroidObject;
import utils.EvidenciaUtils;

public class DesactivarServicioRoaming implements Task {

    private static final int MAX_INTENTOS = 3;
    static final By MENSAJE_ERROR_DESACTIVACION =
            By.xpath(
                    "//*[contains(translate(@text,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'no se pud')"
                            + " or contains(translate(@content-desc,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'no se pud')"
                            + " or contains(translate(@text,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'su solicitud no pudo ser procesada')"
                            + " or contains(translate(@content-desc,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'su solicitud no pudo ser procesada')]");

    @Override
    public <T extends Actor> void performAs(T actor) {
        AppiumDriver<?> driver = AndroidObject.androidDriver(actor);
        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.withMessage("El servicio Roaming no aparece activo antes de desactivarlo");
        wait.until(
                ignored -> "true".equals(BTON_ACTIVAR_ROAMING.resolveFor(actor).getAttribute("checked")));

        for (int intento = 1; intento <= MAX_INTENTOS; intento++) {
            if (intento > 1 && roamingDesactivado(actor)) {
                if (esperarDesactivacion(actor, driver)) {
                    EvidenciaUtils.registrarCaptura("Servicio Roaming desactivado");
                    return;
                }
                throw new IllegalStateException(
                        "No se pudo confirmar la desactivacion de Roaming; no se vuelve a tocar un interruptor apagado");
            }
            EvidenciaUtils.registrarCaptura("Desactivar el servicio Roaming activo - intento " + intento);
            tocarCentro(actor, BTON_ACTIVAR_ROAMING);

            actor.attemptsTo(
                    WaitUntil.the(BTON_DESACTIVAR_ROAMING, isClickable()).forNoMoreThan(30).seconds());
            EvidenciaUtils.registrarCaptura("Confirmar la desactivacion de Roaming");
            tocarCentro(actor, BTON_DESACTIVAR_ROAMING);

            if (!esperarDesactivacion(actor, driver)) {
                EvidenciaUtils.registrarCaptura(
                        "Desactivacion no confirmada o solicitud no pudo ser procesada - intento " + intento);
                if (intento == MAX_INTENTOS) {
                    throw new IllegalStateException(
                            "No se pudo desactivar Roaming despues de " + MAX_INTENTOS + " intentos");
                }
                new WebDriverWait(driver, 15)
                        .withMessage("El aviso de error de Roaming no desaparecio antes del reintento")
                        .until(ignored -> !mensajeErrorVisible(driver));
                continue;
            }

            EvidenciaUtils.registrarCaptura("Servicio Roaming desactivado");
            return;
        }
    }

    private boolean roamingDesactivado(Actor actor) {
        return "false".equals(BTON_ACTIVAR_ROAMING.resolveFor(actor).getAttribute("checked"));
    }

    private boolean mensajeErrorVisible(AppiumDriver<?> driver) {
        return driver.findElements(MENSAJE_ERROR_DESACTIVACION).stream()
                .anyMatch(WebElement::isDisplayed);
    }

    private boolean esperarDesactivacion(Actor actor, AppiumDriver<?> driver) {
        final boolean[] errorDetectado = {false};
        try {
            new WebDriverWait(driver, 30)
                    .until(new ExpectedCondition<Boolean>() {
                        private long desactivadoDesde = -1;

                        @Override
                        public Boolean apply(WebDriver ignored) {
                            if (mensajeErrorVisible(driver)) {
                                errorDetectado[0] = true;
                                EvidenciaUtils.registrarCaptura("Aviso de error al desactivar Roaming detectado");
                                return true;
                            }
                            if (!roamingDesactivado(actor)) {
                                desactivadoDesde = -1;
                                return false;
                            }
                            long ahora = System.nanoTime();
                            if (desactivadoDesde < 0) {
                                desactivadoDesde = ahora;
                            }
                            return ahora - desactivadoDesde >= Duration.ofSeconds(3).toNanos();
                        }
                    });
            return !errorDetectado[0];
        } catch (TimeoutException e) {
            EvidenciaUtils.registrarCaptura("Tiempo agotado esperando Roaming apagado y sin avisos de error");
            return false;
        }
    }

    private void tocarCentro(Actor actor, Target target) {
        actor.attemptsTo(WaitUntil.the(target, isClickable()).forNoMoreThan(30).seconds());
        Rectangle bounds = target.resolveFor(actor).getRect();
        int x = bounds.getX() + bounds.getWidth() / 2;
        int y = bounds.getY() + bounds.getHeight() / 2;

        // El click de accesibilidad del WebView no siempre dispara el evento tactil.
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(new Pause(finger, Duration.ofMillis(150)));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        AndroidObject.androidDriver(actor).perform(Collections.singletonList(tap));
    }

    public static Performable desactivarServicioRoaming() {
        return instrumented(DesactivarServicioRoaming.class);
    }
}