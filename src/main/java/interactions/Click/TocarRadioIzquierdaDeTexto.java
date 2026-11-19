package interactions.Click;

import io.appium.java_client.HasSettings;
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
import org.openqa.selenium.WebDriver;
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
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;

/**
 * Marca un radio button dibujado por CSS dentro de un WebView, ubicado a la izquierda de un texto.
 *
 * El árbol de accesibilidad del WebView puede quedar congelado a mitad de la animación del bottom sheet
 * (posición desfasada o incluso fuera de pantalla, según el dispositivo). Por eso:
 *  - el texto se busca con allowInvisibleElements y solo se usa para acotar la zona de búsqueda;
 *  - la posición real del radio se obtiene del screenshot, por forma y no por color;
 *  - las coordenadas se convierten entre screenshot y pantalla para soportar cualquier resolución.
 */
public class TocarRadioIzquierdaDeTexto implements Interaction {

    private static final int ESPERA_TEXTO_SEG = 15;
    private static final long ESPERA_ESTABILIDAD_MS = 8000;
    private static final long PAUSA_ENTRE_CAPTURAS_MS = 400;
    private static final long ESPERA_POST_TAP_MS = 1000;

    /** Brillo promedio mínimo para considerar un píxel blanco (fondo del sheet). */
    private static final int UMBRAL_BLANCO = 230;
    /** Saturación máxima de un píxel blanco. */
    private static final int SATURACION_MAX_BLANCO = 30;
    /** Diámetro aceptado del radio, como fracción del ancho de pantalla. */
    private static final double DIAMETRO_MIN = 0.04;
    private static final double DIAMETRO_MAX = 0.08;
    /** Ancho de la franja de búsqueda a la izquierda del texto, en múltiplos del diámetro máximo. */
    private static final double ANCHO_FRANJA = 2.5;
    /** Si no se encuentra el texto, se busca en esta fracción izquierda de la pantalla. */
    private static final double FRANJA_SIN_TEXTO = 0.6;
    private static final int TOLERANCIA_ESTABILIDAD_PX = 3;
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
        WebDriver driver = (WebDriver) AndroidObject.androidDriver(actor);
        double escala = calcularEscala(driver, actor);

        Integer xTextoPantalla = buscarXDelTexto(actor, driver);

        BufferedImage muestra = capturarPantalla(actor);
        int anchoImg = muestra.getWidth();
        int xIni;
        int xFin;
        if (xTextoPantalla != null) {
            int xTextoImg = (int) Math.round(xTextoPantalla / escala);
            xFin = Math.min(anchoImg - 1, xTextoImg - 2);
            xIni = Math.max(0, xFin - (int) Math.round(anchoImg * DIAMETRO_MAX * ANCHO_FRANJA));
        } else {
            xIni = 0;
            xFin = (int) Math.round(anchoImg * FRANJA_SIN_TEXTO);
        }

        Captura captura = esperarRadioEstable(actor, xIni, xFin);
        if (captura == null) {
            guardarDebug(capturarPantalla(actor));
            throw new AssertionError("No se encontró el radio button en la franja x=" + xIni + ".." + xFin
                    + " del screenshot. Screenshot en " + RUTA_DEBUG);
        }

        int xImg = (captura.caja[0] + captura.caja[2]) / 2;
        int yImg = (captura.caja[1] + captura.caja[3]) / 2;
        int xTap = (int) Math.round(xImg * escala);
        int yTap = (int) Math.round(yImg * escala);
        System.out.printf("[TocarRadio] escala=%.3f | texto x=%s | franja=%d..%d | radio=[%d,%d][%d,%d] -> tap en (%d,%d)%n",
                escala, xTextoPantalla, xIni, xFin, captura.caja[0], captura.caja[1],
                captura.caja[2], captura.caja[3], xTap, yTap);

        if (estaMarcado(captura.imagen, xImg, yImg)) {
            System.out.println("[TocarRadio] El radio ya estaba marcado, no se toca");
            return;
        }

        new TouchAction<>((PerformsTouchActions) driver).tap(PointOption.point(xTap, yTap)).perform();
        pausa(ESPERA_POST_TAP_MS);

        verificarMarcado(actor, xIni, xFin, xImg, yImg);
    }

    // ---------------------------------------------------------------- texto de referencia

    /**
     * Devuelve la X (en coordenadas de pantalla) del texto de referencia, o null si no aparece.
     * Habilita temporalmente allowInvisibleElements porque, con el árbol desfasado, UiAutomator2
     * puede considerar el texto fuera de pantalla y no devolverlo.
     */
    private <T extends Actor> Integer buscarXDelTexto(T actor, WebDriver driver) {
        cambiarElementosInvisibles(driver, true);
        try {
            actor.attemptsTo(WaitUntil.the(textoReferencia, isPresent()).forNoMoreThan(ESPERA_TEXTO_SEG).seconds());
            int[] bounds = leerBounds(textoReferencia.resolveFor(actor));
            return bounds[0];
        } catch (Throwable e) {
            System.out.println("[TocarRadio] No se encontró el texto de referencia; se busca el radio sin ancla: "
                    + e.getClass().getSimpleName());
            return null;
        } finally {
            cambiarElementosInvisibles(driver, false);
        }
    }

    private void cambiarElementosInvisibles(WebDriver driver, boolean permitir) {
        try {
            ((HasSettings) driver).setSetting("allowInvisibleElements", permitir);
        } catch (Exception e) {
            System.out.println("[TocarRadio] No se pudo cambiar allowInvisibleElements: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------------- búsqueda visual

    /** Toma screenshots hasta que el radio aparece en el mismo lugar en dos capturas seguidas. */
    private <T extends Actor> Captura esperarRadioEstable(T actor, int xIni, int xFin) {
        long limite = System.currentTimeMillis() + ESPERA_ESTABILIDAD_MS;
        Captura anterior = null;
        Captura ultima = null;

        while (System.currentTimeMillis() < limite) {
            BufferedImage img = capturarPantalla(actor);
            int[] caja = localizarRadio(img, xIni, xFin);
            if (caja != null) {
                ultima = new Captura(img, caja);
                if (anterior != null && mismaCaja(anterior.caja, caja)) {
                    return ultima;
                }
            }
            anterior = caja != null ? ultima : null;
            pausa(PAUSA_ENTRE_CAPTURAS_MS);
        }
        return ultima;
    }

    /**
     * Busca de abajo hacia arriba, dentro de la franja, un bloque de píxeles no blancos con forma de círculo
     * rodeado de blanco. Devuelve {minX, minY, maxX, maxY} en coordenadas del screenshot.
     */
    private int[] localizarRadio(BufferedImage img, int xIni, int xFin) {
        if (xFin <= xIni) {
            return null;
        }
        boolean[][] visitado = new boolean[xFin - xIni + 1][img.getHeight()];
        for (int y = img.getHeight() - 1; y >= 0; y--) {
            for (int x = xIni; x <= xFin; x++) {
                if (visitado[x - xIni][y] || esBlanco(img.getRGB(x, y))) {
                    continue;
                }
                int[] caja = expandirComponente(img, x, y, xIni, xFin, visitado);
                if (pareceRadio(img, caja)) {
                    return caja;
                }
            }
        }
        return null;
    }

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
                if (!visitado[nx - xIni][ny] && !esBlanco(img.getRGB(nx, ny))) {
                    visitado[nx - xIni][ny] = true;
                    cola.add(new int[]{nx, ny});
                }
            }
        }
        return new int[]{minX, minY, maxX, maxY};
    }

    /** Tamaño proporcional a la pantalla, casi cuadrado, esquinas blancas (forma circular) y rodeado de blanco. */
    private boolean pareceRadio(BufferedImage img, int[] caja) {
        int ancho = caja[2] - caja[0] + 1;
        int alto = caja[3] - caja[1] + 1;
        double min = img.getWidth() * DIAMETRO_MIN;
        double max = img.getWidth() * DIAMETRO_MAX;
        if (ancho < min || ancho > max || alto < min || alto > max) {
            return false;
        }
        double proporcion = (double) ancho / alto;
        if (proporcion < 0.8 || proporcion > 1.25) {
            return false;
        }

        int insetX = Math.max(1, (int) Math.round(ancho * 0.1));
        int insetY = Math.max(1, (int) Math.round(alto * 0.1));
        boolean esquinasBlancas = esBlancoEn(img, caja[0] + insetX, caja[1] + insetY)
                && esBlancoEn(img, caja[2] - insetX, caja[1] + insetY)
                && esBlancoEn(img, caja[0] + insetX, caja[3] - insetY)
                && esBlancoEn(img, caja[2] - insetX, caja[3] - insetY);
        if (!esquinasBlancas) {
            return false;
        }

        int margen = Math.max(4, ancho / 8);
        int cx = (caja[0] + caja[2]) / 2;
        int cy = (caja[1] + caja[3]) / 2;
        return esBlancoEn(img, cx, caja[1] - margen)
                && esBlancoEn(img, cx, caja[3] + margen)
                && esBlancoEn(img, caja[0] - margen, cy);
    }

    // ---------------------------------------------------------------- verificación

    private <T extends Actor> void verificarMarcado(T actor, int xIni, int xFin, int xEsperada, int yEsperada) {
        BufferedImage despues = capturarPantalla(actor);
        int[] caja = localizarRadio(despues, xIni, xFin);
        int toleranciaY = (int) Math.round(despues.getWidth() * DIAMETRO_MAX);

        if (caja == null || Math.abs((caja[1] + caja[3]) / 2 - yEsperada) > toleranciaY) {
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

    private boolean estaMarcado(BufferedImage img, int x, int y) {
        return !esBlanco(img.getRGB(x, y));
    }

    // ---------------------------------------------------------------- utilidades

    /** Relación entre coordenadas de pantalla (Appium) y píxeles del screenshot. */
    private <T extends Actor> double calcularEscala(WebDriver driver, T actor) {
        try {
            int anchoPantalla = driver.manage().window().getSize().getWidth();
            int anchoImg = capturarPantalla(actor).getWidth();
            return anchoImg > 0 ? (double) anchoPantalla / anchoImg : 1.0;
        } catch (Exception e) {
            return 1.0;
        }
    }

    private boolean mismaCaja(int[] a, int[] b) {
        for (int i = 0; i < 4; i++) {
            if (Math.abs(a[i] - b[i]) > TOLERANCIA_ESTABILIDAD_PX) {
                return false;
            }
        }
        return true;
    }

    private boolean esBlancoEn(BufferedImage img, int x, int y) {
        if (x < 0 || y < 0 || x >= img.getWidth() || y >= img.getHeight()) {
            return false;
        }
        return esBlanco(img.getRGB(x, y));
    }

    private boolean esBlanco(int rgb) {
        int r = (rgb >> 16) & 0xFF, g = (rgb >> 8) & 0xFF, b = rgb & 0xFF;
        int saturacion = Math.max(r, Math.max(g, b)) - Math.min(r, Math.min(g, b));
        return (r + g + b) / 3 >= UMBRAL_BLANCO && saturacion <= SATURACION_MAX_BLANCO;
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

    /** Screenshot junto con la caja del radio encontrada en él. */
    private static final class Captura {
        private final BufferedImage imagen;
        private final int[] caja;

        private Captura(BufferedImage imagen, int[] caja) {
            this.imagen = imagen;
            this.caja = caja;
        }
    }
}