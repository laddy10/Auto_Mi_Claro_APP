package interactions.Click;

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.offset.PointOption;
import net.serenitybdd.core.pages.WebElementFacade;
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
 * Marca un radio button dibujado por CSS dentro de un WebView, ubicado a la izquierda de un texto.
 * El árbol de accesibilidad del WebView no es confiable durante/después de la animación del bottom sheet,
 * así que los bounds del texto solo se usan para acotar la zona de búsqueda; la posición real del radio
 * se obtiene del screenshot.
 */
public class TocarRadioIzquierdaDeTexto implements Interaction {

    private static final long ESPERA_ANIMACION_MS = 1500;
    private static final long ESPERA_POST_TAP_MS = 1000;
    private static final int UMBRAL_SATURACION = 60;
    private static final int UMBRAL_BLANCO = 235;
    /** Ancho de la franja de búsqueda a la izquierda del texto, en múltiplos del alto del texto. */
    private static final double ANCHO_FRANJA = 3.0;
    /** Rango de tamaño aceptado para el círculo, en múltiplos del alto del texto. */
    private static final double TAMANO_MIN = 0.5;
    private static final double TAMANO_MAX = 1.6;
    private static final int MARGEN_BORDE_PX = 6;
    private static final String RUTA_DEBUG = "Error/radio_debug.png";
    private static final Pattern PATRON_BOUNDS = Pattern.compile("\\[(\\d+),(\\d+)]\\[(\\d+),(\\d+)]");

    private final Target textoReferencia;

    public TocarRadioIzquierdaDeTexto(Target textoReferencia) {
        this.textoReferencia = textoReferencia;
    }

    public static TocarRadioIzquierdaDeTexto de(Target textoReferencia) {
        return instrumented(TocarRadioIzquierdaDeTexto.class, textoReferencia);
    }

    @Override
    @Step("{0} marca el radio button junto a #textoReferencia")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(WaitUntil.the(textoReferencia, isVisible()).forNoMoreThan(10).seconds());
        pausa(ESPERA_ANIMACION_MS);

        int[] bounds = leerBounds(textoReferencia.resolveFor(actor));
        int xTexto = bounds[0];
        int altoTexto = bounds[3] - bounds[1];

        BufferedImage antes = capturarPantalla(actor);
        int[] caja = localizarRadio(antes, xTexto, altoTexto);
        if (caja == null) {
            guardarDebug(antes);
            throw new AssertionError("No se encontró el radio button a la izquierda de x=" + xTexto
                    + ". Screenshot guardado en " + RUTA_DEBUG);
        }
        int x = (caja[0] + caja[2]) / 2;
        int y = (caja[1] + caja[3]) / 2;
        System.out.printf("[TocarRadio] bounds texto x=%d alto=%d | radio=[%d,%d][%d,%d] -> centro (%d,%d)%n",
                xTexto, altoTexto, caja[0], caja[1], caja[2], caja[3], x, y);

        if (estaMarcado(antes, x, y)) {
            System.out.println("[TocarRadio] El radio ya estaba marcado, no se toca");
            return;
        }

        PerformsTouchActions driver = (PerformsTouchActions) AndroidObject.androidDriver(actor);
        new TouchAction<>(driver).tap(PointOption.point(x, y)).perform();
        pausa(ESPERA_POST_TAP_MS);

        verificarMarcado(actor, xTexto, altoTexto, x, y);
    }

    private <T extends Actor> void verificarMarcado(T actor, int xTexto, int altoTexto, int xEsperada, int yEsperada) {
        BufferedImage despues = capturarPantalla(actor);
        int[] caja = localizarRadio(despues, xTexto, altoTexto);

        if (caja == null || Math.abs((caja[1] + caja[3]) / 2 - yEsperada) > altoTexto) {
            guardarDebug(despues);
            throw new AssertionError("Después del tap ya no se ve el radio en (" + xEsperada + "," + yEsperada
                    + "): el bottom sheet probablemente se cerró. Screenshot en " + RUTA_DEBUG);
        }
        if (!estaMarcado(despues, xEsperada, yEsperada)) {
            guardarDebug(despues);
            throw new AssertionError("Se tocó el radio en (" + xEsperada + "," + yEsperada
                    + ") pero no quedó marcado. Screenshot en " + RUTA_DEBUG);
        }
        System.out.println("[TocarRadio] Radio marcado correctamente");
    }

    /**
     * Busca, de abajo hacia arriba, un grupo conectado de píxeles de color en la franja a la izquierda
     * del texto que tenga forma y tamaño de radio y esté rodeado de blanco. Devuelve {minX, minY, maxX, maxY}.
     */
    private int[] localizarRadio(BufferedImage img, int xTexto, int altoTexto) {
        int xIni = Math.max(0, xTexto - (int) Math.round(altoTexto * ANCHO_FRANJA));
        int xFin = Math.min(img.getWidth() - 1, xTexto - 2);
        if (xFin <= xIni) {
            return null;
        }
        boolean[][] visitado = new boolean[xFin - xIni + 1][img.getHeight()];

        for (int y = img.getHeight() - 1; y >= 0; y--) {
            for (int x = xIni; x <= xFin; x++) {
                if (visitado[x - xIni][y] || !esSaturado(img.getRGB(x, y))) {
                    continue;
                }
                int[] caja = expandirComponente(img, x, y, xIni, xFin, visitado);
                if (pareceRadioDentroDelSheet(img, caja, altoTexto)) {
                    return caja;
                }
            }
        }
        return null;
    }

    /** Recorre en anchura el grupo de píxeles saturados conectados y devuelve su caja {minX, minY, maxX, maxY}. */
    private int[] expandirComponente(BufferedImage img, int x0, int y0, int xIni, int xFin, boolean[][] visitado) {
        int minX = x0, maxX = x0, minY = y0, maxY = y0;
        ArrayDeque<int[]> cola = new ArrayDeque<>();
        cola.add(new int[]{x0, y0});
        visitado[x0 - xIni][y0] = true;
        int[][] vecinos = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        while (!cola.isEmpty()) {
            int[] p = cola.poll();
            minX = Math.min(minX, p[0]);
            maxX = Math.max(maxX, p[0]);
            minY = Math.min(minY, p[1]);
            maxY = Math.max(maxY, p[1]);
            for (int[] v : vecinos) {
                int nx = p[0] + v[0], ny = p[1] + v[1];
                if (nx < xIni || nx > xFin || ny < 0 || ny >= img.getHeight()) {
                    continue;
                }
                if (!visitado[nx - xIni][ny] && esSaturado(img.getRGB(nx, ny))) {
                    visitado[nx - xIni][ny] = true;
                    cola.add(new int[]{nx, ny});
                }
            }
        }
        return new int[]{minX, minY, maxX, maxY};
    }

    private boolean pareceRadioDentroDelSheet(BufferedImage img, int[] caja, int altoTexto) {
        int ancho = caja[2] - caja[0] + 1;
        int alto = caja[3] - caja[1] + 1;
        double min = altoTexto * TAMANO_MIN, max = altoTexto * TAMANO_MAX;
        if (ancho < min || ancho > max || alto < min || alto > max) {
            return false;
        }
        double proporcion = (double) ancho / alto;
        if (proporcion < 0.75 || proporcion > 1.33) {
            return false;
        }
        int cx = (caja[0] + caja[2]) / 2;
        int cy = (caja[1] + caja[3]) / 2;
        // Arriba, abajo e izquierda deben ser fondo blanco del sheet (a la derecha puede empezar el texto).
        return esBlancoEn(img, cx, caja[1] - MARGEN_BORDE_PX)
                && esBlancoEn(img, cx, caja[3] + MARGEN_BORDE_PX)
                && esBlancoEn(img, caja[0] - MARGEN_BORDE_PX, cy);
    }

    private boolean esBlancoEn(BufferedImage img, int x, int y) {
        if (x < 0 || y < 0 || x >= img.getWidth() || y >= img.getHeight()) {
            return false;
        }
        int rgb = img.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF, g = (rgb >> 8) & 0xFF, b = rgb & 0xFF;
        return (r + g + b) / 3 >= UMBRAL_BLANCO && !esSaturado(rgb);
    }

    private boolean estaMarcado(BufferedImage img, int x, int y) {
        int rgb = img.getRGB(x, y);
        int r = (rgb >> 16) & 0xFF, g = (rgb >> 8) & 0xFF, b = rgb & 0xFF;
        return (r + g + b) / 3 < UMBRAL_BLANCO;
    }

    private boolean esSaturado(int rgb) {
        int r = (rgb >> 16) & 0xFF, g = (rgb >> 8) & 0xFF, b = rgb & 0xFF;
        return Math.max(r, Math.max(g, b)) - Math.min(r, Math.min(g, b)) > UMBRAL_SATURACION;
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
            System.out.println("[TocarRadio] No se pudo guardar el screenshot de depuración: " + e.getMessage());
        }
    }

    /** Devuelve {x1, y1, x2, y2} del atributo bounds. */
    private int[] leerBounds(WebElementFacade elemento) {
        String valor = elemento.getAttribute("bounds");
        Matcher m = PATRON_BOUNDS.matcher(valor == null ? "" : valor);
        if (!m.matches()) {
            throw new IllegalStateException("No se pudo leer el atributo bounds: " + valor);
        }
        return new int[]{Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)),
                Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4))};
    }

    private static void pausa(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}