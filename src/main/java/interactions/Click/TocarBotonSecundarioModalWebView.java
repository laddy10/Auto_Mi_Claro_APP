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
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Interactive;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import utils.AndroidObject;

/**
 * Toca el botón SECUNDARIO de una ventana flotante de un WebView: el botón sin relleno que está
 * debajo del botón principal relleno. Ejemplo: popup "Elige tu paquete de una vez" -> principal
 * "Ver paquetes" (rojo), secundario "No, gracias".
 *
 * <p>Por qué no usa localizadores: en estos popups el árbol de accesibilidad del WebView se queda
 * congelado a mitad de la animación de zoom (bounds al ~71 % del tamaño real). Los bounds leídos
 * son estables pero falsos, y el tap termina en "Ver paquetes". Por eso la posición se calcula
 * sobre el screenshot, que siempre muestra la pantalla real:
 *
 * <ol>
 *   <li>Se busca en la columna central el botón principal relleno (bloque de color dentro del área
 *       blanca del popup).
 *   <li>Debajo de él se busca el siguiente bloque no blanco: el botón secundario.
 *   <li>Se toca su centro y se valida que apareció el texto esperado (o que el popup se cerró).
 * </ol>
 *
 * <p>Si algo falla, guarda el screenshot marcado en {@code Error/modal_debug.png}.
 */
public class TocarBotonSecundarioModalWebView implements Interaction {

    private static final int TIEMPO_MAX_APARICION_SEG = 15;
    private static final long ESPERA_ANIMACION_MS = 1500;
    private static final long ESPERA_POST_TAP_MS = 1500;
    private static final long TIEMPO_MAX_VALIDACION_MS = 10_000;
    private static final long INTERVALO_SONDEO_MS = 400;
    private static final int MAX_INTENTOS = 2;

    /** Mismo valor de webdriver.timeouts.implicitlywait en serenity.properties. */
    private static final long IMPLICIT_WAIT_PROYECTO_MS = 10_000;

    // Detección de colores
    private static final int UMBRAL_SATURACION = 60;
    private static final int UMBRAL_BLANCO = 235;
    private static final double ANCHO_MIN_BOTON = 0.25;
    private static final double PROPORCION_MIN_BOTON = 2.0;
    private static final double RELLENO_MIN = 0.7;
    private static final int MARGEN_BORDE_PX = 8;

    /**
     * Respaldo si no se distingue el botón secundario en la imagen: en el layout real su centro está
     * 0.66 alturas del botón principal por debajo del borde inferior de este (1483-1408)/113.
     */
    private static final double OFFSET_RESPALDO = 0.66;

    private static final String RUTA_DEBUG = "Error/modal_debug.png";

    private final String textoModal;
    private final String textoEsperado;

    public TocarBotonSecundarioModalWebView(String textoModal, String textoEsperado) {
        this.textoModal = textoModal;
        this.textoEsperado = textoEsperado;
    }

    /**
     * @param textoModal texto propio del popup (su título), p. ej. "Elige tu paquete"
     * @param textoEsperado texto que debe aparecer después del tap, p. ej. "Ahorra comprando un
     *     paquete". Si va vacío, solo se valida que el popup se cierre.
     */
    public static TocarBotonSecundarioModalWebView de(String textoModal, String textoEsperado) {
        return instrumented(TocarBotonSecundarioModalWebView.class, textoModal, textoEsperado);
    }

    @Override
    @Step("{0} toca el botón secundario de la ventana '#textoModal'")
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
        By modal = By.xpath("//android.widget.TextView[contains(@text,'" + textoModal + "')]");
        esperarAparicion(driver, modal);
        pausa(ESPERA_ANIMACION_MS);

        for (int intento = 1; intento <= MAX_INTENTOS; intento++) {
            BufferedImage img = capturarPantalla(driver);
            int columna = img.getWidth() / 2;

            int[] principal = localizarBotonRelleno(img, columna);
            if (principal == null) {
                guardarDebug(img, null, null, -1, -1);
                throw new AssertionError(
                        "No se encontró el botón principal relleno de la ventana '"
                                + textoModal
                                + "'. Screenshot en "
                                + RUTA_DEBUG);
            }

            int altoPrincipal = principal[3] - principal[1] + 1;
            int[] secundario = localizarBloqueDebajo(img, principal);
            int x = (principal[0] + principal[2]) / 2;
            int y =
                    (secundario != null)
                            ? (secundario[1] + secundario[3]) / 2
                            : principal[3] + (int) Math.round(altoPrincipal * OFFSET_RESPALDO);

            System.out.printf(
                    "[TocarBotonSecundario] Intento %d: principal=[%d,%d][%d,%d] secundario=%s -> tap en"
                            + " (%d,%d)%n",
                    intento,
                    principal[0],
                    principal[1],
                    principal[2],
                    principal[3],
                    (secundario != null)
                            ? String.format(
                            "[%d,%d][%d,%d]", secundario[0], secundario[1], secundario[2], secundario[3])
                            : "no detectado (se usa offset de respaldo)",
                    x,
                    y);

            tap((Interactive) driver, x, y);
            pausa(ESPERA_POST_TAP_MS);

            Resultado r = validar(driver, modal);
            if (r == Resultado.OK) {
                System.out.println("[TocarBotonSecundario] ✅ Tap correcto");
                return;
            }
            if (r == Resultado.NAVEGO_A_OTRA_PANTALLA) {
                guardarDebug(img, principal, secundario, x, y);
                throw new AssertionError(
                        "La ventana '"
                                + textoModal
                                + "' se cerró pero no apareció '"
                                + textoEsperado
                                + "' (posible tap en el botón principal). Screenshot previo al tap en "
                                + RUTA_DEBUG);
            }
            System.out.println("[TocarBotonSecundario] La ventana sigue abierta, se reintenta...");
            if (intento == MAX_INTENTOS) {
                guardarDebug(img, principal, secundario, x, y);
            }
        }

        throw new AssertionError(
                "No fue posible tocar el botón secundario de la ventana '"
                        + textoModal
                        + "' después de "
                        + MAX_INTENTOS
                        + " intentos. Screenshot en "
                        + RUTA_DEBUG);
    }

    // ===================================================================
    // Validación posterior al tap
    // ===================================================================

    private enum Resultado {
        OK,
        MODAL_SIGUE_ABIERTO,
        NAVEGO_A_OTRA_PANTALLA
    }

    private Resultado validar(WebDriver driver, By modal) {
        boolean hayEsperado = textoEsperado != null && !textoEsperado.trim().isEmpty();
        By esperado =
                hayEsperado
                        ? By.xpath("//*[contains(@text,'" + textoEsperado + "')]")
                        : null;

        long limite = System.currentTimeMillis() + TIEMPO_MAX_VALIDACION_MS;
        boolean modalPresente = true;
        while (System.currentTimeMillis() < limite) {
            modalPresente = !driver.findElements(modal).isEmpty();
            if (hayEsperado) {
                if (!driver.findElements(esperado).isEmpty()) {
                    return Resultado.OK;
                }
            } else if (!modalPresente) {
                return Resultado.OK;
            }
            pausa(INTERVALO_SONDEO_MS);
        }
        return modalPresente ? Resultado.MODAL_SIGUE_ABIERTO : Resultado.NAVEGO_A_OTRA_PANTALLA;
    }

    private void esperarAparicion(WebDriver driver, By modal) {
        long limite = System.currentTimeMillis() + TIEMPO_MAX_APARICION_SEG * 1000L;
        while (System.currentTimeMillis() < limite) {
            if (!driver.findElements(modal).isEmpty()) {
                return;
            }
            pausa(INTERVALO_SONDEO_MS);
        }
        throw new AssertionError(
                "No apareció la ventana '" + textoModal + "' en " + TIEMPO_MAX_APARICION_SEG + " s");
    }

    // ===================================================================
    // Análisis del screenshot
    // ===================================================================

    /**
     * Recorre la columna x de arriba hacia abajo y devuelve {minX, minY, maxX, maxY} del primer
     * bloque de color con forma de botón relleno rodeado de blanco (dentro del popup).
     */
    private int[] localizarBotonRelleno(BufferedImage img, int x) {
        boolean[][] visitado = new boolean[img.getWidth()][img.getHeight()];
        for (int y = 0; y < img.getHeight(); y++) {
            if (visitado[x][y] || !esSaturado(img.getRGB(x, y))) {
                continue;
            }
            int[] r = expandirComponente(img, x, y, visitado);
            int[] caja = {r[0], r[1], r[2], r[3]};
            if (esBotonRellenoDentroDeDialogo(img, caja, r[4])) {
                return caja;
            }
        }
        return null;
    }

    /**
     * Busca el primer bloque no blanco debajo del botón principal, entre sus mismas columnas. Debe
     * terminar en blanco (seguir dentro del popup) y tener una altura de botón; si no, devuelve null.
     */
    private int[] localizarBloqueDebajo(BufferedImage img, int[] principal) {
        int alto = principal[3] - principal[1] + 1;
        int espacioMax = Math.max(20, (int) (alto * 0.4));
        int limiteY = Math.min(img.getHeight() - 1, principal[3] + alto * 3);

        int y = principal[3] + 3; // saltar el antialias del borde
        while (y <= limiteY && filaBlanca(img, y, principal[0], principal[2])) {
            y++;
        }
        if (y > limiteY) {
            return null;
        }
        int inicio = y;
        int ultimoNoBlanco = y;
        int blancasSeguidas = 0;
        boolean cerradoEnBlanco = false;

        for (; y <= limiteY; y++) {
            if (filaBlanca(img, y, principal[0], principal[2])) {
                blancasSeguidas++;
                if (blancasSeguidas >= espacioMax) {
                    cerradoEnBlanco = true;
                    break;
                }
            } else {
                blancasSeguidas = 0;
                ultimoNoBlanco = y;
            }
        }

        int altoBloque = ultimoNoBlanco - inicio + 1;
        if (!cerradoEnBlanco || altoBloque < alto * 0.2 || altoBloque > alto * 1.6) {
            return null;
        }
        return new int[] {principal[0], inicio, principal[2], ultimoNoBlanco};
    }

    private boolean filaBlanca(BufferedImage img, int y, int x0, int x1) {
        for (int x = Math.max(0, x0); x <= Math.min(img.getWidth() - 1, x1); x += 3) {
            if (!esBlanco(img.getRGB(x, y))) {
                return false;
            }
        }
        return true;
    }

    /** Devuelve {minX, minY, maxX, maxY, cantidadPixeles} del bloque conectado de píxeles de color. */
    private int[] expandirComponente(BufferedImage img, int x0, int y0, boolean[][] visitado) {
        int minX = x0, maxX = x0, minY = y0, maxY = y0, cantidad = 0;
        ArrayDeque<int[]> cola = new ArrayDeque<>();
        cola.add(new int[] {x0, y0});
        visitado[x0][y0] = true;
        int[][] vecinos = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!cola.isEmpty()) {
            int[] p = cola.poll();
            cantidad++;
            minX = Math.min(minX, p[0]);
            maxX = Math.max(maxX, p[0]);
            minY = Math.min(minY, p[1]);
            maxY = Math.max(maxY, p[1]);
            for (int[] v : vecinos) {
                int nx = p[0] + v[0], ny = p[1] + v[1];
                if (nx < 0 || ny < 0 || nx >= img.getWidth() || ny >= img.getHeight()) {
                    continue;
                }
                if (!visitado[nx][ny] && esSaturado(img.getRGB(nx, ny))) {
                    visitado[nx][ny] = true;
                    cola.add(new int[] {nx, ny});
                }
            }
        }
        return new int[] {minX, minY, maxX, maxY, cantidad};
    }

    private boolean esBotonRellenoDentroDeDialogo(BufferedImage img, int[] caja, int pixeles) {
        int ancho = caja[2] - caja[0] + 1;
        int alto = caja[3] - caja[1] + 1;
        if (ancho < img.getWidth() * ANCHO_MIN_BOTON || (double) ancho / alto < PROPORCION_MIN_BOTON) {
            return false;
        }
        if ((double) pixeles / (ancho * alto) < RELLENO_MIN) {
            return false;
        }
        int cx = (caja[0] + caja[2]) / 2;
        return esBlancoEn(img, cx, caja[1] - MARGEN_BORDE_PX)
                && esBlancoEn(img, cx, caja[3] + MARGEN_BORDE_PX);
    }

    private boolean esBlancoEn(BufferedImage img, int x, int y) {
        if (x < 0 || y < 0 || x >= img.getWidth() || y >= img.getHeight()) {
            return false;
        }
        return esBlanco(img.getRGB(x, y));
    }

    private boolean esBlanco(int rgb) {
        int r = (rgb >> 16) & 0xFF, g = (rgb >> 8) & 0xFF, b = rgb & 0xFF;
        return (r + g + b) / 3 >= UMBRAL_BLANCO && !esSaturado(rgb);
    }

    private boolean esSaturado(int rgb) {
        int r = (rgb >> 16) & 0xFF, g = (rgb >> 8) & 0xFF, b = rgb & 0xFF;
        return Math.max(r, Math.max(g, b)) - Math.min(r, Math.min(g, b)) > UMBRAL_SATURACION;
    }

    // ===================================================================
    // Utilidades
    // ===================================================================

    private BufferedImage capturarPantalla(WebDriver driver) {
        byte[] png = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        try {
            return ImageIO.read(new ByteArrayInputStream(png));
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo leer el screenshot del dispositivo", e);
        }
    }

    /** Guarda el screenshot marcando el botón principal (azul), el secundario (verde) y el tap. */
    private void guardarDebug(BufferedImage img, int[] principal, int[] secundario, int x, int y) {
        try {
            BufferedImage copia =
                    new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g = copia.createGraphics();
            g.drawImage(img, 0, 0, null);
            g.setStroke(new BasicStroke(6));
            if (principal != null) {
                g.setColor(Color.BLUE);
                g.drawRect(principal[0], principal[1], principal[2] - principal[0], principal[3] - principal[1]);
            }
            if (secundario != null) {
                g.setColor(Color.GREEN);
                g.drawRect(
                        secundario[0], secundario[1], secundario[2] - secundario[0], secundario[3] - secundario[1]);
            }
            if (x >= 0 && y >= 0) {
                g.setColor(Color.MAGENTA);
                g.fillOval(x - 15, y - 15, 30, 30);
            }
            g.dispose();
            File archivo = new File(RUTA_DEBUG);
            archivo.getParentFile().mkdirs();
            ImageIO.write(copia, "png", archivo);
        } catch (IOException e) {
            System.out.println("[TocarBotonSecundario] No se pudo guardar el debug: " + e.getMessage());
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