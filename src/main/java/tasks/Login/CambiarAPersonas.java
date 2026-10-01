package tasks.Login;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.PAGOS_Y_MAS;

import interactions.Click.ClickTextoQueContengaX;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import java.time.Duration;
import java.util.List;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import utils.AndroidObject;
import utils.EvidenciaUtils;

/**
 * Asegura que la app quede en el contexto PERSONAS y no en "Claro Empresas".
 *
 * <p>En algunos dispositivos la app abre en la pestaña "Claro Empresas"; su botón "Iniciar sesión"
 * lleva al login de Empresas (correo de Claro Empresas + slider "Desliza para Personas") y el caso
 * falla porque los flujos esperan el login de Personas (número de documento / "Otros métodos de
 * ingreso").
 *
 * <p>Cubre dos estados, ambos detectados por page source (una lectura, sin depender del
 * implicitWait que Serenity re-aplica por interacción):
 *
 * <ol>
 *   <li><b>Home de Claro Empresas</b> (pestaña inferior "Claro Empresas" activa): toca la pestaña
 *       "Pagos y más" para volver al home de Personas ANTES de pulsar "Iniciar sesión".
 *   <li><b>Login de Claro Empresas</b> ("Desliza para Personas"): desliza el selector hacia
 *       Personas. Si el deslizamiento no surte efecto, toca el ícono de persona y, como último
 *       recurso, repite el deslizamiento más lento. Tras cada intento confirma que cargó el login
 *       de Personas ("Desliza para Empresas" / "Otros métodos de ingreso" / "número de documento").
 * </ol>
 *
 * <p>Es IDEMPOTENTE: si no está en Empresas, retorna de inmediato (una lectura de page source), así
 * que puede llamarse en cualquier punto previo al login sin afectar el flujo normal.
 */
public class CambiarAPersonas implements Task {

  // ─────────────────────────── firmas de pantalla (page source) ───────────────────────────

  /** Login de Claro Empresas (imagen: "Ingresa con el correo electrónico ... Claro Empresas"). */
  private static final String[] FIRMAS_LOGIN_EMPRESAS = {
    "Desliza para Personas", "registrarte en Claro Empresas"
  };

  /** Login de Personas (imagen: "Ingresa con tu número de documento"). */
  private static final String[] FIRMAS_LOGIN_PERSONAS = {
    "Desliza para Empresas",
    "Otros métodos de ingreso",
    "número de documento",
    "¡Nos alegra tenerte de vuelta!"
  };

  /** Home de la pestaña Claro Empresas (contenido exclusivo de esa pestaña). */
  private static final String[] FIRMAS_HOME_EMPRESAS = {
    "En Mi Claro Empresas lo tienes todo", "Resumen de consumos E", "Consulta tu plan E"
  };

  private static final String XPATH_SLIDER_PERSONAS = "//*[contains(@text,'Desliza para Personas')]";

  private static final int MAX_INTENTOS_SLIDER = 3;
  private static final long ESPERA_CAMBIO_MS = 4_000L;

  @Override
  public <T extends Actor> void performAs(T actor) {
    String xml = pageSource(actor);

    // 1) Home de Claro Empresas -> volver a Personas por la barra inferior.
    if (esHomeEmpresas(xml)) {
      salirDeHomeEmpresas(actor, xml);
      xml = pageSource(actor);
    }

    // 2) Login de Claro Empresas -> deslizar a Personas.
    if (esLoginEmpresas(xml)) {
      cambiarLoginAPersonas(actor);
    }
  }

  // ─────────────────────────── detección pública (reutilizable con un xml ya leído) ───────────

  /** ¿El page source corresponde al login de Claro Empresas? */
  public static boolean esLoginEmpresas(String xml) {
    return contieneAlguno(xml, FIRMAS_LOGIN_EMPRESAS);
  }

  /** ¿El page source corresponde al home de la pestaña Claro Empresas? */
  public static boolean esHomeEmpresas(String xml) {
    return contieneAlguno(xml, FIRMAS_HOME_EMPRESAS) && !esLoginEmpresas(xml);
  }

  /** ¿El page source corresponde al login de Personas? */
  public static boolean esLoginPersonas(String xml) {
    return !esLoginEmpresas(xml) && contieneAlguno(xml, FIRMAS_LOGIN_PERSONAS);
  }

  // ─────────────────────────── estado 1: home de Empresas ───────────────────────────

  private <T extends Actor> void salirDeHomeEmpresas(T actor, String xml) {
    EvidenciaUtils.registrarCaptura("La app abrió en la pestaña Claro Empresas.");
    if (!contiene(xml, PAGOS_Y_MAS)) {
      EvidenciaUtils.registrarCaptura(
          "No se encontró la pestaña '" + PAGOS_Y_MAS + "'. Se valida en el login.");
      return;
    }
    try {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(PAGOS_Y_MAS));
    } catch (Exception e) {
      EvidenciaUtils.registrarCaptura("No se pudo pulsar '" + PAGOS_Y_MAS + "'.");
      return;
    }
    long fin = System.currentTimeMillis() + ESPERA_CAMBIO_MS;
    while (System.currentTimeMillis() < fin) {
      if (!esHomeEmpresas(pageSource(actor))) {
        EvidenciaUtils.registrarCaptura("Se cambió de Claro Empresas al home de Personas.");
        return;
      }
      dormir(500);
    }
    // No es fatal: si "Iniciar sesión" lleva al login de Empresas, el paso 2 lo corrige.
    EvidenciaUtils.registrarCaptura("La pestaña Claro Empresas sigue activa tras el cambio.");
  }

  // ─────────────────────────── estado 2: login de Empresas ───────────────────────────

  private <T extends Actor> void cambiarLoginAPersonas(T actor) {
    EvidenciaUtils.registrarCaptura("Login de Claro Empresas detectado. Cambiando a Personas.");

    for (int intento = 1; intento <= MAX_INTENTOS_SLIDER; intento++) {
      try {
        AndroidDriver driver = AndroidObject.androidDriver(actor);
        List<WebElement> sliders = driver.findElements(By.xpath(XPATH_SLIDER_PERSONAS));
        if (sliders.isEmpty()) {
          // Ya no está el slider: o cambió solo o la pantalla aún carga.
          if (esperarLoginPersonas(actor)) {
            return;
          }
          continue;
        }
        Rectangle r = sliders.get(0).getRect();

        switch (intento) {
          case 1:
            deslizarHaciaPersonas(driver, r, 600);
            break;
          case 2:
            tocarIconoPersona(driver, r);
            break;
          default:
            deslizarHaciaPersonas(driver, r, 1200);
            break;
        }
      } catch (Exception e) {
        // Elemento obsoleto o gesto rechazado: se reintenta con la siguiente estrategia.
      }

      if (esperarLoginPersonas(actor)) {
        EvidenciaUtils.registrarCaptura(
            "Login de Personas cargado (intento " + intento + "). Continúa el caso.");
        return;
      }
    }

    EvidenciaUtils.registrarCaptura("No fue posible cambiar del login de Empresas a Personas.");
    throw new IllegalStateException(
        "CambiarAPersonas: la app sigue en el login de Claro Empresas tras "
            + MAX_INTENTOS_SLIDER
            + " intentos (deslizar / tocar ícono de persona). Revisa el slider 'Desliza para"
            + " Personas' contra el page source del dispositivo.");
  }

  /**
   * Arrastra el selector (píldora oscura con el texto) hacia la izquierda, donde está el ícono de
   * persona. El destino se calcula desde el propio texto para no depender de la resolución.
   */
  private void deslizarHaciaPersonas(AndroidDriver driver, Rectangle texto, long duracionMs) {
    Dimension pantalla = driver.manage().window().getSize();
    int y = texto.getY() + texto.getHeight() / 2;
    int inicioX = texto.getX() + texto.getWidth() / 2;
    int finX = Math.max((int) (pantalla.getWidth() * 0.05), texto.getX() - texto.getWidth());

    new TouchAction<>(driver)
        .press(PointOption.point(inicioX, y))
        .waitAction(WaitOptions.waitOptions(Duration.ofMillis(duracionMs)))
        .moveTo(PointOption.point(finX, y))
        .release()
        .perform();
  }

  /** Toca el ícono de persona, ubicado a la izquierda de la píldora del selector. */
  private void tocarIconoPersona(AndroidDriver driver, Rectangle texto) {
    int y = texto.getY() + texto.getHeight() / 2;
    int x = Math.max(5, texto.getX() - (int) (texto.getHeight() * 2.2));
    new TouchAction<>(driver).tap(PointOption.point(x, y)).perform();
  }

  private <T extends Actor> boolean esperarLoginPersonas(T actor) {
    long fin = System.currentTimeMillis() + ESPERA_CAMBIO_MS;
    while (System.currentTimeMillis() < fin) {
      if (esLoginPersonas(pageSource(actor))) {
        return true;
      }
      dormir(400);
    }
    return false;
  }

  // ─────────────────────────── utilidades ───────────────────────────

  private static String pageSource(Actor actor) {
    try {
      AndroidDriver d = AndroidObject.androidDriver(actor);
      return d == null ? null : d.getPageSource();
    } catch (Exception e) {
      return null;
    }
  }

  private static boolean contiene(String xml, String clave) {
    return xml != null && xml.contains(clave);
  }

  private static boolean contieneAlguno(String xml, String... claves) {
    if (xml == null || xml.isEmpty()) {
      return false;
    }
    for (String c : claves) {
      if (xml.contains(c)) {
        return true;
      }
    }
    return false;
  }

  private static void dormir(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  public static Performable cambiarAPersonas() {
    return instrumented(CambiarAPersonas.class);
  }

  /** Alias con el nombre original para no romper llamados existentes. */
  @Deprecated
  public static Performable cambiarAPesonas() {
    return cambiarAPersonas();
  }
}
