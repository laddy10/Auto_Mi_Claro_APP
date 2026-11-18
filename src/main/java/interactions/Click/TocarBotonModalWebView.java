package interactions.Click;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import utils.AndroidObject;

/**
 * Toca un botón de una ventana flotante (popup) renderizada dentro de un WebView.
 *
 * <p>Problema que resuelve: el popup y la página de fondo están en el mismo WebView. Mientras el
 * popup se anima (aparece con zoom/slide), sus bounds cambian y los toques pueden caer en el botón
 * de la página de fondo que está debajo (p. ej. "Ver paquetes" queda justo detrás de "No,
 * gracias").
 *
 * <p>Qué hace:
 *
 * <ol>
 *   <li>Busca el botón SOLO dentro del contenedor del popup, identificado por un texto propio del
 *       popup (su título), para no confundirlo con botones iguales del fondo.
 *   <li>Espera a que los bounds del botón queden estables (sin animación) antes de tocar.
 *   <li>Hace tap físico en el centro.
 *   <li>Verifica que el popup se cerró; si sigue abierto, reintenta.
 * </ol>
 */
public class TocarBotonModalWebView implements Interaction {

    private static final int TIEMPO_MAX_APARICION_SEG = 15;
    private static final int LECTURAS_ESTABLES = 3;
    private static final long INTERVALO_LECTURA_MS = 300;
    private static final long TIEMPO_MAX_ESTABILIDAD_MS = 8000;
    private static final long TIEMPO_MAX_CIERRE_MS = 4000;
    private static final int MAX_INTENTOS = 3;
    /** Mismo valor de webdriver.timeouts.implicitlywait en serenity.properties. */
    private static final long IMPLICIT_WAIT_PROYECTO_MS = 10_000;

    private static final Pattern PATRON_BOUNDS =
            Pattern.compile("\\[(\\d+),(\\d+)]\\[(\\d+),(\\d+)]");

    private final String textoBoton;
    private final String textoModal;

    public TocarBotonModalWebView(String textoBoton, String textoModal) {
        this.textoBoton = textoBoton;
        this.textoModal = textoModal;
    }

    /**
     * @param textoBoton texto del botón a tocar (coincidencia parcial), p. ej. "No, gracias"
     * @param textoModal texto que solo existe en el popup (su título), p. ej. "Elige tu paquete"
     */
    public static TocarBotonModalWebView boton(String textoBoton, String textoModal) {
        return instrumented(TocarBotonModalWebView.class, textoBoton, textoModal);
    }

    @Override
    @Step("{0} toca el botón '#textoBoton' de la ventana '#textoModal'")
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = AndroidObject.androidDriver(actor);

        // Sin espera implícita mientras se sondea; si no, cada búsqueda vacía tarda 10 s
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        try {
            tocarConReintentos(driver);
        } finally {
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_PROYECTO_MS, TimeUnit.MILLISECONDS);
        }
    }

    private void tocarConReintentos(WebDriver driver) {
        String xpathModal = "//*[android.widget.TextView[contains(@text,'" + textoModal + "')]]";
        By modal = By.xpath(xpathModal);
        By boton =
                By.xpath(xpathModal + "//android.widget.Button[contains(@text,'" + textoBoton + "')]");

        esperarAparicion(driver, boton);

        for (int intento = 1; intento <= MAX_INTENTOS; intento++) {
            int[] b = esperarBoundsEstables(driver, boton);
            if (b == null) {
                // El botón ya no está: el popup se cerró entre lecturas
                System.out.printf("[TocarBotonModal] '%s' ya no está visible%n", textoBoton);
                return;
            }

            int x = (b[0] + b[2]) / 2;
            int y = (b[1] + b[3]) / 2;
            System.out.printf(
                    "[TocarBotonModal] Intento %d: '%s' bounds=[%d,%d][%d,%d] -> tap en (%d,%d)%n",
                    intento, textoBoton, b[0], b[1], b[2], b[3], x, y);

            tap((Interactive) driver, x, y);

            if (esperarCierre(driver, modal)) {
                System.out.printf("[TocarBotonModal] Ventana '%s' cerrada%n", textoModal);
                return;
            }
            System.out.printf("[TocarBotonModal] La ventana sigue abierta, se reintenta...%n");
        }

        throw new AssertionError(
                "No fue posible tocar '"
                        + textoBoton
                        + "' en la ventana '"
                        + textoModal
                        + "' después de "
                        + MAX_INTENTOS
                        + " intentos");
    }

    private void esperarAparicion(WebDriver driver, By boton) {
        long limite = System.currentTimeMillis() + TIEMPO_MAX_APARICION_SEG * 1000L;
        while (System.currentTimeMillis() < limite) {
            if (!driver.findElements(boton).isEmpty()) {
                return;
            }
            pausa(INTERVALO_LECTURA_MS);
        }
        throw new AssertionError(
                "No apareció el botón '"
                        + textoBoton
                        + "' dentro de la ventana '"
                        + textoModal
                        + "' en "
                        + TIEMPO_MAX_APARICION_SEG
                        + " segundos");
    }

    /**
     * Lee los bounds del botón hasta obtener {@link #LECTURAS_ESTABLES} lecturas iguales seguidas.
     * Devuelve null si el botón desapareció.
     */
    private int[] esperarBoundsEstables(WebDriver driver, By boton) {
        long limite = System.currentTimeMillis() + TIEMPO_MAX_ESTABILIDAD_MS;
        int[] anterior = null;
        int iguales = 0;

        while (System.currentTimeMillis() < limite) {
            int[] actual = leerBounds(driver, boton);
            if (actual == null) {
                return null;
            }
            if (anterior != null && Arrays.equals(anterior, actual)) {
                iguales++;
                if (iguales >= LECTURAS_ESTABLES - 1) {
                    return actual;
                }
            } else {
                iguales = 0;
            }
            anterior = actual;
            pausa(INTERVALO_LECTURA_MS);
        }
        // Si nunca se estabilizó, usa la última lectura disponible
        System.out.println("[TocarBotonModal] ⚠️ Los bounds no se estabilizaron, se usa la última lectura");
        return anterior;
    }

    private int[] leerBounds(WebDriver driver, By boton) {
        List<WebElement> encontrados = driver.findElements(boton);
        if (encontrados.isEmpty()) {
            return null;
        }
        try {
            String valor = encontrados.get(0).getAttribute("bounds");
            Matcher m = PATRON_BOUNDS.matcher(valor == null ? "" : valor);
            if (m.matches()) {
                return new int[] {
                        Integer.parseInt(m.group(1)),
                        Integer.parseInt(m.group(2)),
                        Integer.parseInt(m.group(3)),
                        Integer.parseInt(m.group(4))
                };
            }
        } catch (Exception e) {
            // elemento obsoleto mientras el WebView se redibuja: se lee de nuevo
        }
        return null;
    }

    private boolean esperarCierre(WebDriver driver, By modal) {
        long limite = System.currentTimeMillis() + TIEMPO_MAX_CIERRE_MS;
        while (System.currentTimeMillis() < limite) {
            pausa(INTERVALO_LECTURA_MS);
            if (driver.findElements(modal).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void tap(Interactive driver, int x, int y) {
        PointerInput dedo = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(dedo, 1);
        tap.addAction(dedo.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(dedo.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(
                dedo.createPointerMove(Duration.ofMillis(100), PointerInput.Origin.viewport(), x, y));
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