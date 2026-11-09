package interactions.Click;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.thucydides.core.annotations.Step;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.imageio.ImageIO;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import utils.AndroidObject;

/**
 * Toca una tarjeta de una lista dentro de un WebView (mini app) después de un scroll.
 *
 * <p>Problema que resuelve: tras el swipe de centrado, el árbol de accesibilidad del WebView sigue
 * reportando los bounds de ANTES de que terminara el desplazamiento. {@code element.click()} toca el
 * centro de esos bounds viejos y cae en la tarjeta vecina (ej: se pide 310 263 9171 y abre 310 265 2113).
 *
 * <p>Solución: espera a que termine la inercia del scroll, fuerza el refresco del árbol con
 * {@code getPageSource()}, vuelve a buscar el nodo y solo toca cuando dos lecturas seguidas de bounds
 * coinciden. El tap se hace por coordenadas sobre el centro del nodo más específico encontrado.
 */
public class TocarLineaWebView implements Interaction {

    private static final long ESPERA_FIN_SCROLL_MS = 1200;
    private static final long PAUSA_ENTRE_LECTURAS_MS = 400;
    private static final int LECTURAS_MAX = 10;
    private static final Pattern PATRON_BOUNDS =
            Pattern.compile("\\[(\\d+),(\\d+)]\\[(\\d+),(\\d+)]");

    private final String textoLinea;

    public TocarLineaWebView(String textoLinea) {
        this.textoLinea = textoLinea;
    }

    /** @param textoLinea texto de la tarjeta, ej: "Línea 310 263 9171" */
    public static Interaction conTexto(String textoLinea) {
        return instrumented(TocarLineaWebView.class, textoLinea);
    }

    @Override
    @Step("{0} toca la línea '#textoLinea'")
    public <T extends Actor> void performAs(T actor) {
        AndroidDriver driver = AndroidObject.androidDriver(actor);

        pausa(ESPERA_FIN_SCROLL_MS);
        int[] b = leerBoundsEstables(driver);

        int altoPantalla = driver.manage().window().getSize().getHeight();
        int x = (b[0] + b[2]) / 2;
        int y = (b[1] + b[3]) / 2;

        if (y <= 0 || y >= altoPantalla) {
            throw new AssertionError(
                    "La línea '" + textoLinea + "' quedó fuera de pantalla: bounds="
                            + Arrays.toString(b) + " altoPantalla=" + altoPantalla);
        }

        System.out.printf(
                "[TocarLineaWebView] '%s' bounds=[%d,%d][%d,%d] -> tap en (%d,%d)%n",
                textoLinea, b[0], b[1], b[2], b[3], x, y);

        guardarDebug(driver, b, x, y);
        tap(driver, x, y);
    }

    /**
     * DIAGNÓSTICO: guarda un screenshot tomado justo antes del tap con:
     * verde = bounds de la línea objetivo, azul = bounds de las demás líneas, rojo = punto del tap.
     * Si los rectángulos no coinciden con las tarjetas reales, los bounds del WebView están desfasados.
     */
    private void guardarDebug(AndroidDriver driver, int[] objetivo, int x, int y) {
        try {
            byte[] png = ((org.openqa.selenium.TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(png));
            org.openqa.selenium.Dimension ventana = driver.manage().window().getSize();
            System.out.printf(
                    "[TocarLineaWebView] screenshot=%dx%d ventana=%dx%d%n",
                    img.getWidth(), img.getHeight(), ventana.getWidth(), ventana.getHeight());

            Graphics2D g = img.createGraphics();
            g.setStroke(new BasicStroke(4));
            g.setFont(new Font("SansSerif", Font.BOLD, 26));

            List<WebElement> lineas =
                    driver.findElements(
                            MobileBy.AndroidUIAutomator("new UiSelector().textContains(\"" + "Línea " + "\")"));
            for (WebElement e : lineas) {
                Matcher m = PATRON_BOUNDS.matcher(String.valueOf(e.getAttribute("bounds")));
                if (!m.matches()) {
                    continue;
                }
                int x1 = Integer.parseInt(m.group(1)), y1 = Integer.parseInt(m.group(2));
                int x2 = Integer.parseInt(m.group(3)), y2 = Integer.parseInt(m.group(4));
                g.setColor(Color.BLUE);
                g.drawRect(x1, y1, x2 - x1, y2 - y1);
                g.drawString(e.getText(), x1 + 6, y1 + 28);
                System.out.printf("[TocarLineaWebView] nodo '%s' bounds=[%d,%d][%d,%d]%n",
                        e.getText(), x1, y1, x2, y2);
            }

            g.setColor(Color.GREEN);
            g.drawRect(objetivo[0], objetivo[1], objetivo[2] - objetivo[0], objetivo[3] - objetivo[1]);
            g.setColor(Color.RED);
            g.fillOval(x - 15, y - 15, 30, 30);
            g.dispose();

            String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            File archivo = new File("Error/tocar_linea_debug_" + ts + ".png");
            archivo.getParentFile().mkdirs();
            ImageIO.write(img, "png", archivo);
            System.out.println("[TocarLineaWebView] Debug guardado en " + archivo.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("[TocarLineaWebView] No se pudo guardar el debug: " + e.getMessage());
        }
    }

    /** Lee los bounds hasta que dos lecturas consecutivas coincidan (árbol ya actualizado). */
    private int[] leerBoundsEstables(AndroidDriver driver) {
        int[] anterior = null;
        for (int intento = 1; intento <= LECTURAS_MAX; intento++) {
            driver.getPageSource(); // obliga a UiAutomator2 a refrescar el árbol del WebView
            int[] actual = leerBounds(driver, textoLinea);

            if (actual != null && Arrays.equals(actual, anterior)) {
                return actual;
            }
            anterior = actual;
            pausa(PAUSA_ENTRE_LECTURAS_MS);
        }

        if (anterior == null) {
            throw new AssertionError("No se encontró en pantalla la línea: " + textoLinea);
        }
        System.out.println(
                "[TocarLineaWebView] ⚠ Bounds no se estabilizaron, se usa la última lectura: "
                        + Arrays.toString(anterior));
        return anterior;
    }

    /**
     * Busca los nodos visibles que contienen el texto y devuelve los bounds del más pequeño (el más
     * específico), para no tocar un contenedor padre que también contenga el texto.
     */
    private static int[] leerBounds(AndroidDriver driver, String textoLinea) {
        List<WebElement> elementos =
                driver.findElements(
                        MobileBy.AndroidUIAutomator(
                                "new UiSelector().textContains(\"" + textoLinea + "\")"));

        int[] mejor = null;
        long menorArea = Long.MAX_VALUE;
        for (WebElement elemento : elementos) {
            if (!elemento.isDisplayed()) {
                continue;
            }
            Matcher m = PATRON_BOUNDS.matcher(String.valueOf(elemento.getAttribute("bounds")));
            if (!m.matches()) {
                continue;
            }
            int[] b = {
                    Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)),
                    Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4))
            };
            long area = (long) (b[2] - b[0]) * (b[3] - b[1]);
            if (area > 0 && area < menorArea) {
                menorArea = area;
                mejor = b;
            }
        }
        return mejor;
    }

    private static void tap(AndroidDriver driver, int x, int y) {
        PointerInput dedo = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(dedo, 1);
        tap.addAction(dedo.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(dedo.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(dedo.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(tap));
    }

    // ---------------------------------------------------------------------------------------------
    // Scroll SIN inercia: reemplaza a scrollCorto2 en listas de mini apps (WebView).
    // swipeVertical hace un "fling" (la espera queda en 0 s por el cast (long) 0.3), la lista sigue
    // deslizándose después de soltar y el árbol de accesibilidad del WebView queda con la posición
    // de antes del deslizamiento final: cada caja termina rotulada con la línea de la tarjeta vecina.
    // Aquí cada arrastre es lento y se sostiene el dedo quieto antes de soltar, así no hay inercia.
    // ---------------------------------------------------------------------------------------------

    private static final int ARRASTRES_MAX = 10;
    private static final long DURACION_ARRASTRE_MS = 900;
    private static final long SOSTENER_ANTES_DE_SOLTAR_MS = 500;
    private static final long ESPERA_TRAS_ARRASTRE_MS = 800;

    /** Desplaza la lista sin inercia hasta dejar la línea cerca del centro de la pantalla. */
    public static void desplazarSinInercia(Actor actor, String textoLinea) {
        AndroidDriver driver = AndroidObject.androidDriver(actor);
        int alto = driver.manage().window().getSize().getHeight();
        int ancho = driver.manage().window().getSize().getWidth();
        int x = ancho / 2;

        for (int intento = 1; intento <= ARRASTRES_MAX; intento++) {
            driver.getPageSource();
            int[] b = leerBounds(driver, textoLinea);

            if (b != null) {
                int centro = (b[1] + b[3]) / 2;
                if (centro > alto * 0.35 && centro < alto * 0.65) {
                    System.out.println("[TocarLineaWebView] Línea en zona central: " + Arrays.toString(b));
                    return;
                }
                int distancia = centro - alto / 2; // > 0: está abajo, hay que subir el contenido
                int desde = distancia > 0 ? (int) (alto * 0.75) : (int) (alto * 0.30);
                int hasta = desde - distancia;
                hasta = Math.max((int) (alto * 0.25), Math.min((int) (alto * 0.85), hasta));
                System.out.printf("[TocarLineaWebView] Centrando sin inercia: %d -> %d%n", desde, hasta);
                arrastrarSinInercia(driver, x, desde, hasta);
            } else {
                System.out.println("[TocarLineaWebView] Arrastre sin inercia #" + intento);
                arrastrarSinInercia(driver, x, (int) (alto * 0.75), (int) (alto * 0.40));
            }
            pausa(ESPERA_TRAS_ARRASTRE_MS);
        }
        throw new AssertionError("No se encontró la línea tras " + ARRASTRES_MAX + " arrastres: " + textoLinea);
    }

    private static void arrastrarSinInercia(AndroidDriver driver, int x, int desdeY, int hastaY) {
        PointerInput dedo = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence arrastre = new Sequence(dedo, 1);
        arrastre.addAction(dedo.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, desdeY));
        arrastre.addAction(dedo.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        arrastre.addAction(dedo.createPointerMove(
                Duration.ofMillis(DURACION_ARRASTRE_MS), PointerInput.Origin.viewport(), x, hastaY));
        // Dedo quieto antes de soltar: velocidad final 0 => sin fling
        arrastre.addAction(dedo.createPointerMove(
                Duration.ofMillis(SOSTENER_ANTES_DE_SOLTAR_MS), PointerInput.Origin.viewport(), x, hastaY));
        arrastre.addAction(dedo.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Collections.singletonList(arrastre));
    }

    // ---------------------------------------------------------------------------------------------
    // Verificación: confirma en la pantalla de detalle que se abrió la línea pedida.
    // ---------------------------------------------------------------------------------------------

    private static final Pattern PATRON_NUMERO = Pattern.compile("\\b3\\d{2} ?\\d{3} ?\\d{4}\\b");

    /**
     * Llamar después de esperar PAGAR_FACTURA. Falla si en pantalla aparece otro número de línea y no
     * el esperado; si la pantalla no muestra ningún número, solo deja una advertencia en consola.
     */
    public static void verificarLineaAbierta(Actor actor, String numeroEsperado) {
        String fuente = AndroidObject.androidDriver(actor).getPageSource();
        String esperadoSinEspacios = numeroEsperado.replace(" ", "");

        Matcher m = PATRON_NUMERO.matcher(fuente);
        String otro = null;
        while (m.find()) {
            String encontrado = m.group().replace(" ", "");
            if (encontrado.equals(esperadoSinEspacios)) {
                System.out.println("[TocarLineaWebView] ✅ Detalle abierto de la línea " + numeroEsperado);
                return;
            }
            otro = m.group();
        }
        if (otro != null) {
            throw new AssertionError(
                    "Se abrió el detalle de la línea " + otro + " y se esperaba " + numeroEsperado);
        }
        System.out.println(
                "[TocarLineaWebView] ⚠ El detalle no muestra número de línea; no se pudo verificar " + numeroEsperado);
    }

    private static void pausa(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}