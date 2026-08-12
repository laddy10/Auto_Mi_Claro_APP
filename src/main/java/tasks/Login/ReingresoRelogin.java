package tasks.Login;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isNotPresent;
import static org.hamcrest.core.IsEqual.equalTo;
import static userinterfaces.LoginPage.*;
import static utils.Constants.*;

import interactions.Click.ClickElementByText;
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
 * la pantalla de bienvenida/relogin). {@code abrirRelogin} da los clics necesarios hasta llegar.
 *
 * <p>La decisión NO se toma leyendo el correo enmascarado (es ambiguo). Se usa el registro
 * {@code ultimaCuentaLogueada}, que es la cuenta que la app recuerda en esa pantalla:
 *
 * <ul>
 *   <li>Es la objetivo -> "Continuar" e inicia sesión normal (IngresoSuperApp).
 *   <li>No es la objetivo (o desconocida) -> "Ingresar con otra cuenta" y login por correo con la
 *       cuenta objetivo.
 * </ul>
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

    // Clic(s) en "Iniciar sesión": popup -> home -> pantalla de bienvenida/login.
    abrirRelogin(actor);

    EvidenciaUtils.registrarCaptura("Correo recordado en pantalla: " + leerReloginAccount(actor));

    if (recordadaEsObjetivo) {
      // La cuenta recordada es la del escenario -> Continuar y login normal.
      EvidenciaUtils.registrarCaptura("La cuenta recordada es la objetivo. Continuar y login normal.");
      actor.attemptsTo(IngresoSuperApp.ingresoSuperApp());
      return;
    }

    // La cuenta recordada NO es la objetivo -> Ingresar con otra cuenta.
    if (visible(actor, LBL_WELCOME_BACK) || visible(actor, LBL_NOS_ALEGRA_TENERTE_DE_VUELTA)) {
      EvidenciaUtils.registrarCaptura("No es la cuenta objetivo -> Ingresar con otra cuenta.");
      clickOtraCuenta(actor);
    }
    loginPorCorreo(actor);
    cerrarEmergentes(actor);
    //validar(actor);
  }

  // ─────────────────────────── pasos ───────────────────────────

  /**
   * Da clic en "Iniciar sesión" tantas veces como haga falta (el del popup y el del home) hasta
   * llegar a la pantalla de bienvenida/relogin o de login.
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

  /** true si ya estamos en la pantalla de bienvenida/relogin o de login (no en el home). */
  private <T extends Actor> boolean enRelogin(T actor) {
    return visible(actor, LBL_WELCOME_BACK)
        || visible(actor, LBL_NOS_ALEGRA_TENERTE_DE_VUELTA)
        || visible(actor, LBL_IDENTIFICADOR_USUARIO)
        || visible(actor, BTN_OTROS_METODOS_INGRESO)
        || visible(actor, TXT_USERNAME);
  }

  private <T extends Actor> void clickOtraCuenta(T actor) {
    try {
      actor.attemptsTo(ClickElementByText.clickElementByText("Ingresar con otra cuenta"));
    } catch (Exception e) {
      if (existe(actor, BTN_INGRESAR_OTRA_CUENTA, 2, 400)) {
        actor.attemptsTo(Click.on(BTN_INGRESAR_OTRA_CUENTA));
      } else if (existe(actor, LNK_INGRESAR_OTRA_CUENTA, 1, 300)) {
        actor.attemptsTo(Click.on(LNK_INGRESAR_OTRA_CUENTA));
      } else {
        EvidenciaUtils.registrarCaptura("No se encontró 'Ingresar con otra cuenta'.");
      }
    }
    esperarCualquiera(actor, 15000, BTN_OTROS_METODOS_INGRESO, TXT_USERNAME);
  }

  private <T extends Actor> void loginPorCorreo(T actor) {
    if (existe(actor, BTN_OTROS_METODOS_INGRESO, 2, 500)) {
      clickTextoSeguro(actor, OTROS_METODOS_DE_INGRESO);
      Target opcionCorreo =
          Target.the("Opción Correo electrónico").locatedBy("//*[@text='Correo electrónico']");
      AndroidObject.existeConReintentos(actor, opcionCorreo, 12, 500);
      clickTextoSeguro(actor, CORREO_ELECTRONICO);
    }

    AndroidObject.existeConReintentos(actor, TXT_USERNAME, 20, 500);
    actor.attemptsTo(Enter.theValue(user.getEmail()).into(TXT_USERNAME));
    EvidenciaUtils.registrarCaptura("Correo digitado: " + user.getEmail());
    clickTextoSeguro(actor, CONTINUAR);

    Target pantallaPass =
        Target.the("Pantalla de contraseña")
            .locatedBy(
                "//*[contains(@text,'Ingresa con tu') and contains(@text,'contraseña')"
                    + " or contains(@text,'Olvidé la contraseña')]");
    AndroidObject.existeConReintentos(actor, pantallaPass, 15, 500);
    AndroidObject.existeConReintentos(actor, TXT_PASSWORD, 10, 500);
    actor.attemptsTo(Enter.theValue(user.getPassword()).into(TXT_PASSWORD));
    EvidenciaUtils.registrarCaptura("Contraseña digitada: ******** (oculta)");
    clickTextoSeguro(actor, CONTINUAR);

    try {
      actor.attemptsTo(
          WaitUntil.the(LOADING_ESPERA_UN_MOMENTO, isNotPresent()).forNoMoreThan(30).seconds());
    } catch (Exception ignore) {
      // continuamos
    }
  }

  private <T extends Actor> void cerrarEmergentes(T actor) {
    if (isVisibleFast(actor, LBL_SESION_ABIERTA)) {
      actor.attemptsTo(ClickElementByText.clickElementByText(CONTINUAR), WaitFor.aTime(6000));
    }
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
    if (existe(actor, elemento, 1, 300)) {
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

  private <T extends Actor> boolean isVisibleFast(T actor, Target element) {
    try {
      return !Presence.of(element).viewedBy(actor).resolveAll().isEmpty();
    } catch (Exception e) {
      return false;
    }
  }

  public static Performable paraCuentaActiva() {
    return instrumented(ReingresoRelogin.class);
  }
}
