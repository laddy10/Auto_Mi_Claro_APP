package interactions.Click;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.VER_MAS;

import io.appium.java_client.MobileBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.Dimension;
import utils.AndroidObject;
import utils.LocalizadorOpcionEnModulo;
import utils.LocalizadorOpcionEnModulo.Accion;
import utils.LocalizadorOpcionEnModulo.Plan;

/**
 * Hace clic en una opción (por defecto "Ver más") que pertenece a un módulo concreto del Home,
 * usando el título del módulo como ancla.
 *
 * <p>Resuelve el problema de varios "Ver más" en pantalla: en lugar de tocar el primero que
 * encuentre UiAutomator, toma el "Ver más" más cercano por debajo del título del módulo dentro
 * del mismo contenedor. Antes de tocar se asegura de que la opción no quede bajo la barra
 * inferior, la barra superior ni el botón flotante de Cupones. Si no es así, desplaza lo
 * necesario y vuelve a medir, así que no depende del tamaño de pantalla ni de los banners.
 *
 * <p>Si un gesto no mueve el contenido (el título queda en la misma posición), cambia al scroll
 * nativo de UiAutomator ({@code UiScrollable.scrollForward/scrollBackward}). Si al final falla,
 * guarda el page source en {@code target/diagnostico/} para analizarlo.
 *
 * <pre>
 *   ClickOpcionDelModulo.verMasDe(PAGOS_Y_CONSULTAS)
 *   ClickOpcionDelModulo.laOpcion(PAGA_TU_FACTURA, PAGOS_Y_CONSULTAS)
 * </pre>
 */
public class ClickOpcionDelModulo implements Interaction {

    private static final int MAX_INTENTOS = 8;
    private static final long ESPERA_TRAS_SCROLL_MS = 700;

    /** Arrastre en tramos cortos: nunca se queda quieto ≥ 500 ms (umbral de pulsación larga). */
    private static final int TRAMOS_ARRASTRE = 8;
    private static final long MS_POR_TRAMO = 60;
    private static final long PAUSA_INICIAL_MS = 100;
    private static final long PAUSA_ANTES_DE_SOLTAR_MS = 300;
    private static final int MARGEN_ARRASTRE_PX = 20;
    /** Si el título se mueve menos que esto tras un gesto, se considera que el gesto no sirvió. */
    private static final int MOVIMIENTO_MINIMO_PX = 10;

    private final String opcion;
    private final String modulo;

    public ClickOpcionDelModulo(String opcion, String modulo) {
        this.opcion = opcion;
        this.modulo = modulo;
    }

    /** Clic en el "Ver más" del módulo cuyo título es {@code modulo}. */
    public static Interaction verMasDe(String modulo) {
        return instrumented(ClickOpcionDelModulo.class, VER_MAS, modulo);
    }

    /** Clic en cualquier opción ({@code opcion}) que pertenezca al módulo {@code modulo}. */
    public static Interaction laOpcion(String opcion, String modulo) {
        return instrumented(ClickOpcionDelModulo.class, opcion, modulo);
    }

    @Override
    @Step("Clic en '#opcion' del módulo '#modulo'")
    public <T extends Actor> void performAs(T actor) {
        AndroidDriver driver = AndroidObject.androidDriver(actor);
        Dimension pantalla = driver.manage().window().getSize();
        boolean yaBuscoModulo = false;
        boolean usarScrollNativo = false;
        Plan anterior = null;
        String pageSource = null;

        for (int intento = 1; intento <= MAX_INTENTOS; intento++) {
            pageSource = driver.getPageSource();
            Plan plan =
                    LocalizadorOpcionEnModulo.calcular(pageSource, modulo, opcion, pantalla.getHeight());

            // ¿El gesto anterior movió algo? Si el título sigue en el mismo sitio, cambiar de estrategia.
            if (anterior != null
                    && anterior.accion == Accion.DESPLAZAR
                    && plan.tituloY >= 0
                    && Math.abs(plan.tituloY - anterior.tituloY) < MOVIMIENTO_MINIMO_PX) {
                usarScrollNativo = true;
            }

            System.out.println("📌 [" + modulo + " → " + opcion + "] intento " + intento + ": " + plan
                    + (usarScrollNativo && plan.accion == Accion.DESPLAZAR ? " (scroll nativo)" : ""));

            switch (plan.accion) {
                case TOCAR:
                    tocar(driver, plan.x, plan.y);
                    return;

                case DESPLAZAR:
                    if (usarScrollNativo) {
                        scrollNativo(driver, plan.desplazamiento > 0);
                        usarScrollNativo = false; // el siguiente intento vuelve al arrastre preciso
                    } else {
                        arrastrar(driver, pantalla.getWidth() / 2, plan);
                    }
                    break;

                case BUSCAR_MODULO:
                    if (yaBuscoModulo) {
                        guardarDiagnostico(pageSource);
                        throw new AssertionError(
                                "No se encontró el módulo '" + modulo + "' en pantalla después de hacer scroll");
                    }
                    AndroidObject.scrollToText(actor, modulo);
                    yaBuscoModulo = true;
                    break;

                case NO_ENCONTRADO:
                default:
                    guardarDiagnostico(pageSource);
                    throw new AssertionError(
                            "El módulo '" + modulo + "' está visible pero no contiene la opción '" + opcion + "'");
            }
            anterior = plan;
            dormir(ESPERA_TRAS_SCROLL_MS);
        }
        guardarDiagnostico(pageSource);
        throw new AssertionError(
                "No se pudo dejar visible la opción '" + opcion + "' del módulo '" + modulo
                        + "' tras " + MAX_INTENTOS + " intentos");
    }

    private static void tocar(AndroidDriver driver, int x, int y) {
        new TouchAction<>(driver).tap(PointOption.point(x, y)).perform();
    }

    /**
     * Arrastre que imita un dedo real: baja, se mueve en tramos cortos y hace una pausa antes de
     * soltar para no generar inercia (fling). Nunca queda quieto el tiempo suficiente para que
     * Android lo interprete como pulsación larga sobre un banner o una mini app. Si el
     * desplazamiento excede la zona segura se limita, y el siguiente intento completa el resto.
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void arrastrar(AndroidDriver driver, int x, Plan plan) {
        int sup = plan.zonaSuperior + MARGEN_ARRASTRE_PX;
        int inf = plan.zonaInferior - MARGEN_ARRASTRE_PX;
        int desde;
        int hasta;
        if (plan.desplazamiento > 0) { // contenido sube → dedo de abajo hacia arriba
            desde = inf;
            hasta = Math.max(sup, desde - plan.desplazamiento);
        } else { // contenido baja → dedo de arriba hacia abajo
            desde = sup;
            hasta = Math.min(inf, desde - plan.desplazamiento);
        }

        TouchAction gesto =
                new TouchAction(driver)
                        .press(PointOption.point(x, desde))
                        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PAUSA_INICIAL_MS)));
        for (int i = 1; i <= TRAMOS_ARRASTRE; i++) {
            int y = desde + (hasta - desde) * i / TRAMOS_ARRASTRE;
            gesto =
                    gesto
                            .moveTo(PointOption.point(x, y))
                            .waitAction(WaitOptions.waitOptions(Duration.ofMillis(MS_POR_TRAMO)));
        }
        gesto
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(PAUSA_ANTES_DE_SOLTAR_MS)))
                .release()
                .perform();
    }

    /** Scroll nativo de UiAutomator sobre el primer contenedor con scroll (el ScrollView del Home). */
    private static void scrollNativo(AndroidDriver driver, boolean haciaAbajo) {
        String accion = haciaAbajo ? "scrollForward()" : "scrollBackward()";
        // Con implicit wait > 0 Appium reintenta el selector (y repite el scroll) hasta agotar la
        // espera; se anula solo durante esta llamada. Serenity lo vuelve a aplicar en la siguiente
        // interacción.
        driver.manage().timeouts().implicitlyWait(0, TimeUnit.MILLISECONDS);
        try {
            driver.findElement(
                    new MobileBy.ByAndroidUIAutomator(
                            "new UiScrollable(new UiSelector().scrollable(true))." + accion));
        } catch (Exception ignorada) {
            // UiScrollable no devuelve elemento al hacer scroll; la excepción es esperada.
        } finally {
            driver.manage().timeouts().implicitlyWait(implicitWaitConfigurado(), TimeUnit.MILLISECONDS);
        }
    }

    /** Lee webdriver.timeouts.implicitlywait de serenity.properties (10 s si no está). */
    private static long implicitWaitConfigurado() {
        try (InputStream in = Files.newInputStream(Paths.get("serenity.properties"))) {
            Properties props = new Properties();
            props.load(in);
            return Long.parseLong(props.getProperty("webdriver.timeouts.implicitlywait", "10000").trim());
        } catch (Exception e) {
            return 10_000L;
        }
    }

    private void guardarDiagnostico(String pageSource) {
        if (pageSource == null) return;
        try {
            Path dir = Paths.get("target", "diagnostico");
            Files.createDirectories(dir);
            String hora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            Path archivo =
                    dir.resolve("ClickOpcionDelModulo_" + modulo.replaceAll("\\W+", "_") + "_" + hora + ".xml");
            Files.write(archivo, pageSource.getBytes(StandardCharsets.UTF_8));
            System.out.println("📝 Page source guardado para diagnóstico: " + archivo.toAbsolutePath());
        } catch (Exception e) {
            System.out.println("⚠️ No se pudo guardar el page source: " + e.getMessage());
        }
    }

    private static void dormir(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}