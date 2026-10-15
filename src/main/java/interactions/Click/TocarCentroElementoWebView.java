package interactions.Click;

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.AndroidObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Toca un botón relleno de un WebView. Si los bounds del árbol de accesibilidad son válidos los usa;
 * si quedaron congelados a mitad de una animación (zoom/slide), localiza el botón en el screenshot
 * a partir de la columna central reportada.
 */
public class TocarCentroElementoWebView implements Interaction {

    private static final long ESPERA_ANIMACION_MS = 1500;
    private static final long ESPERA_POST_TAP_MS = 1200;
    private static final int UMBRAL_CAMBIO_COLOR = 60;
    private static final int UMBRAL_SATURACION = 60;
    private static final int UMBRAL_BLANCO = 235;
    /** Ancho mínimo del botón respecto al ancho de pantalla. */
    private static final double ANCHO_MIN_BOTON = 0.25;
    /** Relación ancho/alto mínima para considerar el bloque un botón. */
    private static final double PROPORCION_MIN_BOTON = 2.0;
    /** Porcentaje mínimo de la caja que debe estar relleno (descarta botones con solo borde). */
    private static final double RELLENO_MIN = 0.7;
    private static final int MARGEN_BORDE_PX = 8;
    private static final String RUTA_DEBUG = "Error/boton_debug.png";
    private static final Pattern PATRON_BOUNDS = Pattern.compile("\\[(\\d+),(\\d+)]\\[(\\d+),(\\d+)]");

    private final Target elemento;

    public TocarCentroElementoWebView(Target elemento) {
        this.elemento = elemento;
    }

    public static TocarCentroElementoWebView de(Target elemento) {
        return instrumented(TocarCentroElementoWebView.class, elemento);
    }

    @Override
    @Step("{0} toca #elemento")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(WaitUntil.the(elemento, isVisible()).forNoMoreThan(15).seconds());
        pausa(ESPERA_ANIMACION_MS);

        int[] b = leerBounds(actor);
        int xCentro = (b[0] + b[2]) / 2;
        int yCentro = (b[1] + b[3]) / 2;

        BufferedImage antes = capturarPantalla(actor);
        int x;
        int y;

        if (esSaturado(antes.getRGB(xCentro, yCentro))) {
            x = xCentro;
            y = yCentro;
            System.out.printf("[TocarCentro] %s bounds=[%d,%d][%d,%d] válidos -> tap en (%d,%d)%n",
                    elemento, b[0], b[1], b[2], b[3], x, y);
        } else {
            int[] caja = localizarBotonRelleno(antes, xCentro);
            if (caja == null) {
                guardarDebug(antes);
                throw new AssertionError("No se encontró " + elemento + " en el screenshot (columna x=" + xCentro
                        + "). Screenshot en " + RUTA_DEBUG);
            }
            x = (caja[0] + caja[2]) / 2;
            y = (caja[1] + caja[3]) / 2;
            System.out.printf("[TocarCentro] %s bounds=[%d,%d][%d,%d] desfasados (color %s) | botón en screenshot=[%d,%d][%d,%d] -> tap en (%d,%d)%n",
                    elemento, b[0], b[1], b[2], b[3], hex(antes.getRGB(xCentro, yCentro)),
                    caja[0], caja[1], caja[2], caja[3], x, y);
        }

        int colorAntes = antes.getRGB(x, y);
        PerformsTouchActions driver = (PerformsTouchActions) AndroidObject.androidDriver(actor);
        new TouchAction<>(driver).tap(PointOption.point(x, y)).perform();
        pausa(ESPERA_POST_TAP_MS);

        BufferedImage despues = capturarPantalla(actor);
        int colorDespues = despues.getRGB(x, y);
        System.out.printf("[TocarCentro] color en (%d,%d): antes=%s después=%s%n", x, y, hex(colorAntes), hex(colorDespues));
        if (diferencia(colorAntes, colorDespues) < UMBRAL_CAMBIO_COLOR) {
            guardarDebug(despues);
            throw new AssertionError("Se tocó " + elemento + " en (" + x + "," + y
                    + ") pero la pantalla no cambió. Screenshot en " + RUTA_DEBUG);
        }
    }

    /**
     * Recorre la columna x de arriba hacia abajo; desde cada píxel de color no visitado expande el bloque
     * conectado y lo acepta si tiene forma de botón relleno dentro de un área blanca. Devuelve {minX, minY, maxX, maxY}.
     */
    private int[] localizarBotonRelleno(BufferedImage img, int x) {
        boolean[][] visitado = new boolean[img.getWidth()][img.getHeight()];
        for (int y = 0; y < img.getHeight(); y++) {
            if (visitado[x][y] || !esSaturado(img.getRGB(x, y))) {
                continue;
            }
            int[] resultado = expandirComponente(img, x, y, visitado);
            int[] caja = {resultado[0], resultado[1], resultado[2], resultado[3]};
            int pixeles = resultado[4];
            if (esBotonRellenoDentroDeDialogo(img, caja, pixeles)) {
                return caja;
            }
        }
        return null;
    }

    /** Devuelve {minX, minY, maxX, maxY, cantidadPixeles} del bloque conectado de píxeles de color. */
    private int[] expandirComponente(BufferedImage img, int x0, int y0, boolean[][] visitado) {
        int minX = x0, maxX = x0, minY = y0, maxY = y0, cantidad = 0;
        ArrayDeque<int[]> cola = new ArrayDeque<>();
        cola.add(new int[]{x0, y0});
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
                    cola.add(new int[]{nx, ny});
                }
            }
        }
        return new int[]{minX, minY, maxX, maxY, cantidad};
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
        return esBlancoEn(img, cx, caja[1] - MARGEN_BORDE_PX) && esBlancoEn(img, cx, caja[3] + MARGEN_BORDE_PX);
    }

    private boolean esBlancoEn(BufferedImage img, int x, int y) {
        if (x < 0 || y < 0 || x >= img.getWidth() || y >= img.getHeight()) {
            return false;
        }
        int rgb = img.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF, g = (rgb >> 8) & 0xFF, b = rgb & 0xFF;
        return (r + g + b) / 3 >= UMBRAL_BLANCO && !esSaturado(rgb);
    }

    private boolean esSaturado(int rgb) {
        int r = (rgb >> 16) & 0xFF, g = (rgb >> 8) & 0xFF, b = rgb & 0xFF;
        return Math.max(r, Math.max(g, b)) - Math.min(r, Math.min(g, b)) > UMBRAL_SATURACION;
    }

    private <T extends Actor> int[] leerBounds(T actor) {
        String valor = elemento.resolveFor(actor).getAttribute("bounds");
        Matcher m = PATRON_BOUNDS.matcher(valor == null ? "" : valor);
        if (!m.matches()) {
            throw new IllegalStateException("No se pudo leer el atributo bounds: " + valor);
        }
        return new int[]{Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)),
                Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4))};
    }

    private <T extends Actor> BufferedImage capturarPantalla(T actor) {
        byte[] png = ((TakesScreenshot) AndroidObject.androidDriver(actor)).getScreenshotAs(OutputType.BYTES);
        try {
            return ImageIO.read(new ByteArrayInputStream(png));
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo leer el screenshot del dispositivo", e);
        }
    }

    private void guardarDebug(BufferedImage img) {
        try {
            File archivo = new File(RUTA_DEBUG);
            archivo.getParentFile().mkdirs();
            ImageIO.write(img, "png", archivo);
        } catch (IOException e) {
            System.out.println("[TocarCentro] No se pudo guardar el screenshot de depuración: " + e.getMessage());
        }
    }

    private static int diferencia(int c1, int c2) {
        return Math.abs(((c1 >> 16) & 0xFF) - ((c2 >> 16) & 0xFF))
                + Math.abs(((c1 >> 8) & 0xFF) - ((c2 >> 8) & 0xFF))
                + Math.abs((c1 & 0xFF) - (c2 & 0xFF));
    }

    private static String hex(int rgb) {
        return String.format("#%06X", rgb & 0xFFFFFF);
    }

    private static void pausa(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}