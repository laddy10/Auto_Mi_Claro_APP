package interactions.Click;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import utils.AndroidObject;

/**
 * Toca un switch renderizado dentro de un WebView (React) haciendo tap físico en el centro de sus
 * bounds.
 *
 * <p>No usa {@code Click.on(Target)} de Serenity porque este valida que el elemento esté
 * habilitado antes del clic, y en los switches del WebView esa validación falla
 * (ElementShouldBeEnabledException) aunque el page source reporte enabled="true".
 *
 * <p>El switch se ubica a partir del texto de su etiqueta (p. ej. "Servicio Roaming"), así no
 * depende de índices ni de un xpath absoluto.
 *
 * <p>Nota: en este WebView el atributo checked del switch siempre viene en false, por eso el
 * resultado del tap se valida con el texto del popup que aparece después, no con checked.
 */
public class TocarSwitchWebView implements Interaction {

    private static final int TIEMPO_MAX_ESPERA_SEG = 15;
    private static final long ESPERA_ENTRE_INTENTOS_MS = 500;
    private static final long ESPERA_POST_TAP_MS = 1000;
    private static final Pattern PATRON_BOUNDS =
            Pattern.compile("\\[(\\d+),(\\d+)]\\[(\\d+),(\\d+)]");

    private final String etiqueta;

    public TocarSwitchWebView(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    /** Toca el switch que está junto al texto indicado (coincidencia parcial). */
    public static TocarSwitchWebView junto(String etiqueta) {
        return instrumented(TocarSwitchWebView.class, etiqueta);
    }

    @Override
    @Step("{0} toca el switch de '#etiqueta'")
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = AndroidObject.androidDriver(actor);

        WebElement sw = buscarSwitch(driver);
        int[] centro = centroDe(sw);

        System.out.printf(
                "[TocarSwitch] Switch de '%s' -> tap en (%d,%d)%n", etiqueta, centro[0], centro[1]);

        tap((Interactive) driver, centro[0], centro[1]);
        pausa(ESPERA_POST_TAP_MS);
    }

    /** Busca el switch ligado a la etiqueta, con varios localizadores en orden de precisión. */
    private WebElement buscarSwitch(WebDriver driver) {
        String lbl = "//android.widget.TextView[contains(@text,'" + etiqueta + "')]";
        List<By> candidatos =
                Arrays.asList(
                        // Switch en el contenedor hermano de la etiqueta (estructura actual de la pantalla)
                        By.xpath(lbl + "/following-sibling::*[1]/descendant-or-self::android.widget.Switch"),
                        By.xpath(lbl + "/following-sibling::*[1]//android.widget.CheckBox"),
                        // Primer switch que aparezca después de la etiqueta
                        By.xpath(lbl + "/following::android.widget.Switch[1]"));

        long limite = System.currentTimeMillis() + TIEMPO_MAX_ESPERA_SEG * 1000L;
        while (System.currentTimeMillis() < limite) {
            for (By by : candidatos) {
                List<WebElement> encontrados = driver.findElements(by);
                if (!encontrados.isEmpty()) {
                    return encontrados.get(0);
                }
            }
            pausa(ESPERA_ENTRE_INTENTOS_MS);
        }
        throw new AssertionError(
                "No se encontró el switch junto al texto '"
                        + etiqueta
                        + "' en "
                        + TIEMPO_MAX_ESPERA_SEG
                        + " segundos");
    }

    /** Centro del elemento a partir de su atributo bounds; si no se puede leer, usa getRect(). */
    private int[] centroDe(WebElement elemento) {
        String bounds = null;
        try {
            bounds = elemento.getAttribute("bounds");
        } catch (Exception ignored) {
            // algunos contextos no exponen bounds; se usa getRect()
        }
        Matcher m = PATRON_BOUNDS.matcher(bounds == null ? "" : bounds);
        if (m.matches()) {
            int x1 = Integer.parseInt(m.group(1));
            int y1 = Integer.parseInt(m.group(2));
            int x2 = Integer.parseInt(m.group(3));
            int y2 = Integer.parseInt(m.group(4));
            return new int[] {(x1 + x2) / 2, (y1 + y2) / 2};
        }
        Rectangle r = elemento.getRect();
        return new int[] {r.getX() + r.getWidth() / 2, r.getY() + r.getHeight() / 2};
    }

    private void tap(Interactive driver, int x, int y) {
        PointerInput dedo = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(dedo, 1);
        tap.addAction(dedo.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(dedo.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(dedo.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), x, y));
        tap.addAction(dedo.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(tap));
    }

    private static void pausa(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}