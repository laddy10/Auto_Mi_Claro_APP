package tasks.Login;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static org.hamcrest.core.IsEqual.equalTo;
import static userinterfaces.LoginPage.*;
import static utils.Constants.*;

import interactions.Click.ClickElementByText;
import interactions.input.IngresarPasswordSeguro;
import interactions.validations.ValidateInformationText;
import interactions.wait.WaitFor;
import io.appium.java_client.android.AndroidDriver;
import java.util.concurrent.TimeUnit;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import utils.AndroidObject;
import utils.CuentaManager;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

/**
 * Reingreso cuando la sesión está cerrada (popup "por seguridad, hemos cerrado tu sesión…") SIN
 * reiniciar la app.
 *
 * <p>Hay DOS botones "Iniciar sesión" seguidos: el del popup (lleva al home) y el del home (lleva a
 * la pantalla siguiente). {@code abrirRelogin} da los clics necesarios hasta llegar.
 *
 * <p>Después, según el dispositivo, puede aparecer:
 *
 * <ul>
 *   <li>La pantalla de bienvenida con "Ingresar con otra cuenta" (hay cuenta recordada), o
 *   <li>Directamente la pantalla de documento con "Otros métodos de ingreso" (sin cuenta recordada).
 * </ul>
 *
 * <p>{@code ingresarPorCorreo} maneja ambos casos: si existe "Ingresar con otra cuenta" lo pulsa, y
 * si no, continúa directo a "Otros métodos de ingreso". (No se usa el texto "Te damos la bienvenida"
 * para decidir, porque aparece en las dos pantallas.)
 *
 * <p>La decisión de continuar/otra-cuenta se toma con el registro {@code ultimaCuentaLogueada}, no
 * con el correo enmascarado (que es ambiguo).
 */
public class ReingresoRelogin implements Task {

  private final User user = TestDataProvider.getRealUser();

  @Override
  public <T extends Actor> void performAs(T actor) {
    String objetivo = CuentaManager.getIdCuentaActiva();
    String recordada = CuentaManager.getUltimaCuentaLogueada();
    boolean recordadaEsObjetivo = recordada != null && recordada.equalsIgnoreCase(objetivo);

    EvidenciaUtils.registrarCaptura(
            "Reingreso | cuenta recordada: " + recordada + " | objetivo: " + objetivo
                    + " | ¿coincide?: " + recordadaEsObjetivo);

    // Clic(s) en "Iniciar sesión": popup -> home -> pantalla siguiente.
    abrirRelogin(actor);

    EvidenciaUtils.registrarCaptura("Correo recordado en pantalla: " + leerReloginAccount(actor));

    if (recordadaEsObjetivo) {
      // La cuenta recordada es la del escenario -> Continuar y login normal.
      EvidenciaUtils.registrarCaptura("La cuenta recordada es la objetivo. Continuar y login normal.");
      actor.attemptsTo(IngresoSuperApp.ingresoSuperApp());
      return;
    }

    // La cuenta recordada NO es la objetivo -> entrar por correo con la cuenta objetivo,
    // pulsando "Ingresar con otra cuenta" SOLO si esa pantalla aparece.
    ingresarPorCorreo(actor);
    cerrarEmergentes(actor);
    // validar(actor);
  }

  // ─────────────────────────── pasos ───────────────────────────

  /**
   * Da clic en "Iniciar sesión" tantas veces como haga falta (el del popup y el del home) hasta
   * llegar a la pantalla de bienvenida/documento.
   */
  private <T extends Actor> void abrirRelogin(T actor) {
    AndroidDriver driver = obtenerDriver(actor);
    setImplicit(driver, 1);
    try {
      for (int i = 0; i < 5; i++) {
        if (enRelogin(actor)) {
          return;
        }
        if (existe(actor, LBL_INICIAR_SESION, 1, 300)) {
          clickTextoSeguro(actor, INICIAR_SESION);
          dormir(1500); // dar tiempo a la transición entre pantallas
        } else {
          dormir(600);
        }
      }
    } finally {
      setImplicit(driver, 10);
    }
  }

  /** true si ya estamos en la pantalla de bienvenida/relogin o de documento (no en el home). */
  private <T extends Actor> boolean enRelogin(T actor) {
    return visible(actor, LBL_NOS_ALEGRA_TENERTE_DE_VUELTA)
            || visible(actor, LBL_IDENTIFICADOR_USUARIO)
            || visible(actor, TXT_OTRA_CUENTA)
            || visible(actor, BTN_OTROS_METODOS_INGRESO)
            || visible(actor, TXT_USERNAME);
  }

  /**
   * Entra por correo con la cuenta objetivo, robusto a las dos variantes de pantalla:
   *
   * <ol>
   *   <li>Si aparece "Ingresar con otra cuenta" -> lo pulsa (y espera la pantalla de documento).
   *   <li>Si NO aparece -> continúa directo.
   *   <li>"Otros métodos de ingreso" -> "Correo electrónico" -> correo -> contraseña.
   * </ol>
   */
  private <T extends Actor> void ingresarPorCorreo(T actor) {
    // 1) ¿Está la pantalla con "Ingresar con otra cuenta"?  (chequeo rápido)
    AndroidDriver driver = obtenerDriver(actor);
    boolean hayOtraCuenta;
    setImplicit(driver, 1);
    try {
      hayOtraCuenta = hayIngresarOtraCuenta(actor);
    } finally {
      setImplicit(driver, 10);
    }

    if (hayOtraCuenta) {
      EvidenciaUtils.registrarCaptura("Pantalla de bienvenida -> Ingresar con otra cuenta.");
      clickOtraCuenta(actor);
      esperarCualquiera(actor, 15000, BTN_OTROS_METODOS_INGRESO, TXT_USERNAME);
    } else {
      EvidenciaUtils.registrarCaptura(
              "No hay 'Ingresar con otra cuenta'; se continúa a Otros métodos de ingreso.");
    }

    // 2) Otros métodos de ingreso -> Correo electrónico.
    if (existe(actor, BTN_OTROS_METODOS_INGRESO, 3, 500)) {
      clickTextoSeguro(actor, OTROS_METODOS_DE_INGRESO);
      AndroidObject.existeConReintentos(actor, OPCION_CORREO, 12, 500);
      clickTextoSeguro(actor, CORREO_ELECTRONICO);
    }

    // 3) Correo.
    AndroidObject.existeConReintentos(actor, TXT_USERNAME, 20, 500);
    actor.attemptsTo(Enter.theValue(user.getEmail()).into(TXT_USERNAME));
    EvidenciaUtils.registrarCaptura("Correo digitado: " + user.getEmail());
    clickTextoSeguro(actor, CONTINUAR);
    if (isVisibleFast(actor, LBL_SESION_ABIERTA)) {
      actor.attemptsTo(ClickElementByText.clickElementByText(CONTINUAR), WaitFor.aTime(6000));
    }

    // 4) Contraseña.
    AndroidObject.existeConReintentos(actor, PANTALLA_CONTRASENA, 15, 500);
    AndroidObject.existeConReintentos(actor, TXT_PASSWORD, 10, 500);
    actor.attemptsTo(IngresarPasswordSeguro.en(TXT_PASSWORD, user.getPassword()));
    EvidenciaUtils.registrarCaptura("Contraseña digitada: ******** (oculta)");
    clickTextoSeguro(actor, CONTINUAR);
    if (isVisibleFast(actor, LBL_SESION_ABIERTA)) {
      actor.attemptsTo(ClickElementByText.clickElementByText(CONTINUAR), WaitFor.aTime(6000));
    }

    try {
      actor.attemptsTo(
              WaitUntil.the(LOADING_ESPERA_UN_MOMENTO, isNotPresent()).forNoMoreThan(30).seconds());
    } catch (Exception ignore) {
      // continuamos
    }
  }

  /** true si en pantalla está el botón/enlace "Ingresar con otra cuenta". */
  private <T extends Actor> boolean hayIngresarOtraCuenta(T actor) {
    return visible(actor, TXT_OTRA_CUENTA)
            || visible(actor, BTN_INGRESAR_OTRA_CUENTA)
            || visible(actor, LNK_INGRESAR_OTRA_CUENTA);
  }

  private <T extends Actor> void clickOtraCuenta(T actor) {
    try {
      actor.attemptsTo(ClickElementByText.clickElementByText("Ingresar con otra cuenta"));
    } catch (Exception e) {
      if (visible(actor, BTN_INGRESAR_OTRA_CUENTA)) {
        actor.attemptsTo(Click.on(BTN_INGRESAR_OTRA_CUENTA));
      } else if (visible(actor, LNK_INGRESAR_OTRA_CUENTA)) {
        actor.attemptsTo(Click.on(LNK_INGRESAR_OTRA_CUENTA));
      } else {
        EvidenciaUtils.registrarCaptura("No se pudo pulsar 'Ingresar con otra cuenta'.");
      }
    }
  }

  private <T extends Actor> void cerrarEmergentes(T actor) {
    clickSiExiste(actor, BTN_PERMISO_UBICACION, MIENTRAS_APP_ESTA_EN_USO);
    clickSiExiste(actor, BTN_ACEPTAR_PERMISO, ACEPTAR_2);
    clickSiExiste(actor, SMS_PERMISO_LLAMADAS, NO_PERMITIR);
    clickSiExiste(actor, SMS_PERMISO_NOTIFICACIONES, NO_PERMITIR);
    clickSiExiste(actor, BTN_OMITIR, OMITIR);
    if (visible(actor, LBL_BIENVENIDA)) {
      actor.attemptsTo(Click.on(CHECK_TC));
      clickTextoSeguro(actor, CONTINUAR);
    }
    clickSiExiste(actor, TXT_AUTORIZACION_VELOCIDAD, ACEPTAR_2);
    if (visible(actor, BTN_CERRAR_PUBLICIDAD)) {
      actor.attemptsTo(Click.on(BTN_CERRAR_PUBLICIDAD));
      dormir(500);
    }
  }

  private <T extends Actor> void validar(T actor) {
    AndroidObject.existeConReintentos(actor, LBL_ENCABEZADO_USUARIO, 30, 1000);
    actor.should(
            seeThat(
                    ValidateInformationText.validateInformationText(LBL_ENCABEZADO_USUARIO),
                    equalTo(user.getNombreUsuario())));
    EvidenciaUtils.registrarCaptura(
            "Login exitoso con la cuenta: " + CuentaManager.getIdCuentaActiva());
  }

  // ─────────────────────────── utilidades ───────────────────────────

  private <T extends Actor> String leerReloginAccount(T actor) {
    try {
      if (visible(actor, LBL_IDENTIFICADOR_USUARIO)) {
        return Text.of(LBL_IDENTIFICADOR_USUARIO).viewedBy(actor).asString();
      }
    } catch (Exception ignore) {
      // fallback
    }
    try {
      Target correo = Target.the("Correo visible").locatedBy("//*[contains(@text,'@')]");
      if (visible(actor, correo)) {
        return Text.of(correo).viewedBy(actor).asString();
      }
    } catch (Exception ignore) {
      // vacío
    }
    return "(no visible)";
  }

  private <T extends Actor> void clickTextoSeguro(T actor, String texto) {
    try {
      actor.attemptsTo(ClickElementByText.clickElementByText(texto));
    } catch (Exception e) {
      EvidenciaUtils.registrarCaptura("No se pudo clicar '" + texto + "': " + e.getMessage());
    }
  }

  private <T extends Actor> void clickSiExiste(T actor, Target elemento, String texto) {
    if (visible(actor, elemento)) {
      clickTextoSeguro(actor, texto);
    }
  }

  private <T extends Actor> boolean existe(T actor, Target t, int intentos, long ms) {
    return AndroidObject.existeConReintentos(actor, t, intentos, ms);
  }

  private <T extends Actor> boolean esperarCualquiera(T actor, long maxMs, Target... targets) {
    long fin = System.currentTimeMillis() + maxMs;
    while (System.currentTimeMillis() < fin) {
      for (Target t : targets) {
        if (visible(actor, t)) {
          return true;
        }
      }
      dormir(500);
    }
    return false;
  }

  private <T extends Actor> boolean visible(T actor, Target t) {
    try {
      return !Presence.of(t).viewedBy(actor).resolveAll().isEmpty();
    } catch (Exception e) {
      return false;
    }
  }

  private AndroidDriver obtenerDriver(Actor actor) {
    try {
      return AndroidObject.androidDriver(actor);
    } catch (Exception e) {
      return null;
    }
  }

  private void setImplicit(AndroidDriver driver, int seconds) {
    if (driver != null) {
      try {
        driver.manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
      } catch (Exception ignore) {
        // no crítico
      }
    }
  }

  private void dormir(long ms) {
    try {
      Thread.sleep(ms);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  public static Performable paraCuentaActiva() {
    return instrumented(ReingresoRelogin.class);
  }

  private <T extends Actor> boolean isVisibleFast(T actor, Target element) {
    try {
      return !Presence.of(element).viewedBy(actor).resolveAll().isEmpty();
    } catch (Exception e) {
      return false;
    }
  }
}