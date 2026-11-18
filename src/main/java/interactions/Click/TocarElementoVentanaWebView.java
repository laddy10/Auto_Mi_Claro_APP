package interactions.Click;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import utils.AndroidObject;

/**
 * Toca cualquier elemento (por su texto) de una ventana flotante de un WebView cuyo árbol de
 * accesibilidad quedó congelado a mitad de la animación de zoom.
 *
 * <p>Problema: los popups del WebView de Roaming aparecen con un zoom desde su centro. El árbol de
 * accesibilidad deja de actualizarse a mitad de la animación, así que los bounds que devuelve
 * Appium son los del popup "pequeño" (p. ej. 294 px de ancho cuando el real mide ~866 px). Un clic
 * por localizador cae en otro sitio.
 *
 * <p>Solución: el zoom es uniforme y centrado (el centro del popup no se mueve), así que la
 * posición relativa de cada elemento se conserva. Se mide en el screenshot el ancho real del
 * recuadro blanco del popup (sobre el fondo oscurecido) y se convierte la posición congelada a la
 * real:
 *
 * <pre>
 *   real = centro + (congelado - centro) * (anchoReal / anchoCongelado)
 * </pre>
 *
 * <p>Si el árbol NO está congelado, la escala da ~1 y el tap cae en los mismos bounds, así que la
 * interacción también funciona en el caso normal.
 *
 * <p>Si algo falla, guarda el screenshot marcado en {@code Error/ventana_debug.png}.
 */
public class TocarElementoVentanaWebView implements Interaction {

    private static final int TIEMPO_MAX_APARICION_SEG = 15;
    private static final long ESPERA_ANIMACION_MS = 1500;
    private static final long ESPERA_POST_TAP_MS = 1500;
    private static final long TIEMPO_MAX_VALIDACION_MS = 10_000;
    private static final long INTERVALO_SONDEO_MS = 400;
    private static final int MAX_INTENTOS = 2;

    /** Mismo valor de webdriver.timeouts.implicitlywait en serenity.properties. */
    private static final long IMPLICIT_WAIT_PROYECTO_MS = 10_000;

    private static final int UMBRAL_BLANCO = 235;
    private static final int UMBRAL_SATURACION = 60;
    /** Píxeles blancos seguidos para considerar que se llegó al borde del popup. */
    private static final int RACHA_BORDE = 15;
    /** Diferencia tolerada entre el centro medido y el del árbol (solo para advertir en el log). */
    private static final int TOLERANCIA_CENTRO_PX = 25;

    private static final String RUTA_DEBUG = "Error/ventana_debug.png";
    private static final Pattern PATRON_BOUNDS =
            Pattern.compile("\\[(\\d+),(\\d+)]\\[(\\d+),(\\d+)]");

    private final String textoElemento;
    private final String textoModal;
    private final String textoEsperado;

    public TocarElementoVentanaWebView(String textoElemento, String textoModal, String textoEsperado) {
        this.textoElemento = textoElemento;
        this.textoModal = textoModal;
        this.textoEsperado = textoEsperado;
    }

    /**
     * @param textoElemento texto del elemento a tocar (coincidencia parcial), p. ej. "Continuar"
     * @param textoModal texto propio del popup, p. ej. "Ahorra comprando"
     * @param textoEsperado texto (parcial) que debe aparecer después del tap, p. ej. "fue activado
     *     exitosamente". Si va vacío, solo se valida que el popup se cierre.
     */
    public static TocarElementoVentanaWebView de(
            String textoElemento, String textoModal, String textoEsperado) {
        return instrumented(
                TocarElementoVentanaWebView.class, textoElemento, textoModal, textoEsperado);
    }

    @Override
    @Step("{0} toca '#textoElemento' en la ventana '#textoModal'")
    public <T extends Actor> void performAs(T actor) {
        WebDriver driver = AndroidObject.androidDriver(actor);
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        try {
            ejecutar(driver);
        } finally {
            driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_PROYECTO_MS, TimeUnit.MILLISECONDS);
        }
    }

    private void ejecutar(WebDriver driver) {
        String xpathContenedor = "//*[android.widget.TextView[contains(@text,'" + textoModal + "')]]";
        By contenedor = By.xpath(xpathContenedor);
        By elemento = By.xpath(xpathContenedor + "//*[contains(@text,'" + textoElemento + "')]");
        By titulo = By.xpath("//android.widget.TextView[contains(@text,'" + textoModal + "')]");

        esperarAparicion(driver, elemento);
        pausa(ESPERA_ANIMACION_MS);

        for (int intento = 1; intento <= MAX_INTENTOS; intento++) {
            int[] cCong = leerBounds(driver, contenedor);
            int[] eCong = leerBounds(driver, elemento);
            if (cCong == null || eCong == null) {
                System.out.println("[TocarEnVentana] La ventana ya no está");
                return;
            }

            BufferedImage img = capturarPantalla(driver);
            int[] real = medirVentanaReal(img, cCong);

            int x;
            int y;
            if (real == null) {
                // No se pudo medir: se usa la posición del árbol tal cual
                x = (eCong[0] + eCong[2]) / 2;
                y = (eCong[1] + eCong[3]) / 2;
                System.out.printf(
                        "[TocarEnVentana] ⚠️ No se pudo medir la ventana real; tap en bounds del árbol (%d,%d)%n",
                        x, y);
            } else {
                // Zoom uniforme y centrado: una sola escala y el centro no se mueve
                double escala = (double) (real[2] - real[0]) / (cCong[2] - cCong[0]);
                double cx = (cCong[0] + cCong[2]) / 2.0;
                double cy = (cCong[1] + cCong[3]) / 2.0;
                x = (int) Math.round(cx + ((eCong[0] + eCong[2]) / 2.0 - cx) * escala);
                y = (int) Math.round(cy + ((eCong[1] + eCong[3]) / 2.0 - cy) * escala);

                double cxMedido = (real[0] + real[2]) / 2.0;
                if (Math.abs(cxMedido - cx) > TOLERANCIA_CENTRO_PX) {
                    System.out.printf(
                            "[TocarEnVentana] ⚠️ El centro medido (%.0f) no coincide con el del árbol (%.0f)%n",
                            cxMedido, cx);
                }
                real[1] = (int) Math.round(cy - (cCong[3] - cCong[1]) / 2.0 * escala);
                real[3] = (int) Math.round(cy + (cCong[3] - cCong[1]) / 2.0 * escala);

                System.out.printf(
                        "[TocarEnVentana] Intento %d: ventana árbol=[%d,%d][%d,%d] real≈[%d,%d][%d,%d]"
                                + " escala=%.2f | '%s' árbol=[%d,%d][%d,%d] -> tap en (%d,%d)%n",
                        intento, cCong[0], cCong[1], cCong[2], cCong[3], real[0], real[1], real[2], real[3],
                        escala, textoElemento, eCong[0], eCong[1], eCong[2], eCong[3], x, y);
            }

            tap((Interactive) driver, x, y);
            pausa(ESPERA_POST_TAP_MS);

            Resultado r = validar(driver, titulo);
            if (r == Resultado.OK) {
                System.out.println("[TocarEnVentana] ✅ Tap correcto");
                return;
            }
            guardarDebug(img, real, x, y);
            if (r == Resultado.NAVEGO_A_OTRA_PANTALLA) {
                throw new AssertionError(
                        "La ventana '" + textoModal + "' se cerró pero no apareció '" + textoEsperado
                                + "'. Screenshot previo al tap en " + RUTA_DEBUG);
            }
            System.out.println("[TocarEnVentana] La ventana sigue abierta, se reintenta...");
        }

        throw new AssertionError(
                "No fue posible tocar '" + textoElemento + "' en la ventana '" + textoModal
                        + "' después de " + MAX_INTENTOS + " intentos. Screenshot en " + RUTA_DEBUG);
    }

    // ===================================================================
    // Medición del popup real en el screenshot
    // ===================================================================

    /**
     * Mide en el screenshot el ancho real del popup, desde los bordes de la pantalla hacia dentro: el
     * fondo está oscurecido (no blanco), así que el primer tramo blanco es el borde del popup. Se
     * mide en varias filas cercanas al centro y se toma la mediana. Devuelve {izq, 0, der, 0} (el
     * alto se deduce de la escala) o null si no se pudo medir.
     */
    private int[] medirVentanaReal(BufferedImage img, int[] cCong) {
        int yc = (cCong[1] + cCong[3]) / 2; // el zoom es centrado: el centro no se mueve
        int[] filas = {yc - 60, yc - 30, yc, yc + 30, yc + 60};
        int[] izqs = new int[filas.length];
        int[] ders = new int[filas.length];
        for (int i = 0; i < filas.length; i++) {
            izqs[i] = bordeIzquierdo(img, filas[i]);
            ders[i] = bordeDerecho(img, filas[i]);
        }
        int izq = mediana(izqs);
        int der = mediana(ders);
        if (izq < 0 || der < 0 || der - izq < (cCong[2] - cCong[0]) * 0.8) {
            return null;
        }
        return new int[] {izq, 0, der, 0};
    }

    private int bordeIzquierdo(BufferedImage img, int y) {
        if (y < 0 || y >= img.getHeight()) return -1;
        int racha = 0;
        for (int x = 0; x < img.getWidth(); x++) {
            racha = esBlanco(img.getRGB(x, y)) ? racha + 1 : 0;
            if (racha >= RACHA_BORDE) return x - RACHA_BORDE + 1;
        }
        return -1;
    }

    private int bordeDerecho(BufferedImage img, int y) {
        if (y < 0 || y >= img.getHeight()) return -1;
        int racha = 0;
        for (int x = img.getWidth() - 1; x >= 0; x--) {
            racha = esBlanco(img.getRGB(x, y)) ? racha + 1 : 0;
            if (racha >= RACHA_BORDE) return x + RACHA_BORDE - 1;
        }
        return -1;
    }

    private static int mediana(int[] v) {
        int[] c = v.clone();
        Arrays.sort(c);
        return c[c.length / 2];
    }

    private boolean esBlanco(int rgb) {
        int r = (rgb >> 16) & 0xFF, g = (rgb >> 8) & 0xFF, b = rgb & 0xFF;
        boolean saturado = Math.max(r, Math.max(g, b)) - Math.min(r, Math.min(g, b)) > UMBRAL_SATURACION;
        return (r + g + b) / 3 >= UMBRAL_BLANCO && !saturado;
    }

    // ===================================================================
    // Validación posterior al tap
    // ===================================================================

    private enum Resultado {
        OK,
        VENTANA_SIGUE_ABIERTA,
        NAVEGO_A_OTRA_PANTALLA
    }

    private Resultado validar(WebDriver driver, By titulo) {
        boolean hayEsperado = textoEsperado != null && !textoEsperado.trim().isEmpty();
        By esperado = hayEsperado ? By.xpath("//*[contains(@text,'" + textoEsperado + "')]") : null;

        long limite = System.currentTimeMillis() + TIEMPO_MAX_VALIDACION_MS;
        boolean ventanaPresente = true;
        while (System.currentTimeMillis() < limite) {
            ventanaPresente = !driver.findElements(titulo).isEmpty();
            if (hayEsperado) {
                if (!driver.findElements(esperado).isEmpty()) {
                    return Resultado.OK;
                }
            } else if (!ventanaPresente) {
                return Resultado.OK;
            }
            pausa(INTERVALO_SONDEO_MS);
        }
        return ventanaPresente ? Resultado.VENTANA_SIGUE_ABIERTA : Resultado.NAVEGO_A_OTRA_PANTALLA;
    }

    private void esperarAparicion(WebDriver driver, By elemento) {
        long limite = System.currentTimeMillis() + TIEMPO_MAX_APARICION_SEG * 1000L;
        while (System.currentTimeMillis() < limite) {
            if (!driver.findElements(elemento).isEmpty()) {
                return;
            }
            pausa(INTERVALO_SONDEO_MS);
        }
        throw new AssertionError(
                "No apareció '" + textoElemento + "' dentro de la ventana '" + textoModal + "' en "
                        + TIEMPO_MAX_APARICION_SEG + " s");
    }

    // ===================================================================
    // Utilidades
    // ===================================================================

    private int[] leerBounds(WebDriver driver, By by) {
        List<WebElement> encontrados = driver.findElements(by);
        if (encontrados.isEmpty()) {
            return null;
        }
        try {
            Matcher m = PATRON_BOUNDS.matcher(String.valueOf(encontrados.get(0).getAttribute("bounds")));
            if (m.matches()) {
                return new int[] {
                        Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)),
                        Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4))
                };
            }
        } catch (Exception ignored) {
            // elemento obsoleto mientras el WebView se redibuja
        }
        return null;
    }

    private BufferedImage capturarPantalla(WebDriver driver) {
        byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        try {
            return ImageIO.read(new ByteArrayInputStream(png));
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo leer el screenshot del dispositivo", e);
        }
    }

    /** Guarda el screenshot con la ventana medida (verde) y el punto del tap (magenta). */
    private void guardarDebug(BufferedImage img, int[] real, int x, int y) {
        try {
            BufferedImage copia =
                    new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = copia.createGraphics();
            g.drawImage(img, 0, 0, null);
            g.setStroke(new BasicStroke(6));
            if (real != null) {
                g.setColor(Color.GREEN);
                g.drawRect(real[0], real[1], real[2] - real[0], real[3] - real[1]);
            }
            g.setColor(Color.MAGENTA);
            g.fillOval(x - 15, y - 15, 30, 30);
            g.dispose();
            File archivo = new File(RUTA_DEBUG);
            archivo.getParentFile().mkdirs();
            ImageIO.write(copia, "png", archivo);
        } catch (IOException e) {
            System.out.println("[TocarEnVentana] No se pudo guardar el debug: " + e.getMessage());
        }
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