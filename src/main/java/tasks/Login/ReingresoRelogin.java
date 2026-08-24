package tasks.Login;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.LoginPage.*;
import static utils.Constants.*;

import interactions.Click.ClickElementByText;
import io.appium.java_client.android.AndroidDriver;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.questions.Presence;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;
import utils.AndroidObject;
import utils.CuentaManager;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

/**
 * Reingreso cuando la sesión está cerrada (popup "por seguridad…") SIN reiniciar la app.
 *
 * <p>Corre dentro de {@code GestionCuenta} (implicitWait bajo), así que las esperas son rápidas y
 * acotadas.
 *
 * <ul>
 *   <li>Clic en "Iniciar sesión" (popup y home).
 *   <li>Decide por {@code ultimaCuentaLogueada}: si la recordada es la objetivo -> Continuar
 *       (IngresoSuperApp); si no -> "Ingresar con otra cuenta" (si aparece) y login por correo.
 *   <li>Tras la contraseña, {@code esperarIngreso} maneja "sesión abierta en otro dispositivo" o el
 *       ingreso directo, esperando el home real.
 * </ul>
 */
public class ReingresoRelogin implements Task {

  private final User user = TestDataProvider.getRealUser();

  private static final Target TXT_OTRA_CUENTA =
      Target.the("Texto 'Ingresar con otra cuenta'")
          .locatedBy(
              "//*[contains(@text,'Ingresar con otra cuenta')"
                  + " or contains(@text,'Iniciar sesión con otra cuenta')]");
  private static final Target OPCION_CORREO =
      Target.the("Opción Correo electrónico").locatedBy("//*[@text='Correo electrónico']");
  private static final Target PANTALLA_CONTRASENA =
      Target.the("Pantalla de contraseña")
          .locatedBy(
              "//*[contains(@text,'Ingresa con tu') and contains(@text,'contraseña')"
                  + " or contains(@text,'Olvidé la contraseña')]");

  @Override
  public <T extends Actor> void performAs(T actor) {
    String objetivo = CuentaManager.getIdCuentaActiva();
    String recordada = CuentaManager.getUltimaCuentaLogueada();
    boolean recordadaEsObjetivo = recordada != null && recordada.equalsIgnoreCase(objetivo);

    EvidenciaUtils.registrarCaptura(
        "Reingreso | cuenta recordada: " + recordada + " | objetivo: " + objetivo
            + " | ¿coincide?: " + recordadaEsObjetivo);

    abrirRelogin(actor);
    EvidenciaUtils.registrarCaptura("Correo recordado en pantalla: " + leerReloginAccount(actor));

    if (recordadaEsObjetivo) {
      EvidenciaUtils.registrarCaptura("La cuenta recordada es la objetivo. Continuar y login normal.");
      actor.attemptsTo(IngresoSuperApp.ingresoSuperApp());
      return;
    }

    ingresarPorCorreo(actor);
    // validar(actor);
  }

  // ─────────────────────────── pasos ───────────────────────────

  /** Clic en "Iniciar sesión" (popup y luego home) hasta llegar a la pantalla de bienvenida/documento. */
  private <T extends Actor> void abrirRelogin(T actor) {
    for (int i = 0; i < 6; i++) {
      if (enRelogin(actor)) {
        return;
      }
      if (visible(actor, LBL_INICIAR_SESION)) {
        clickTextoSeguro(actor, INICIAR_SESION);
        dormir(1500);
      } else {
        dormir(600);
      }
    }
  }

  private <T extends Actor> boolean enRelogin(T actor) {
    return visible(actor, LBL_NOS_ALEGRA_TENERTE_DE_VUELTA)
        || visible(actor, LBL_IDENTIFICADOR_USUARIO)
        || visible(actor, TXT_OTRA_CUENTA)
        || visible(actor, BTN_OTROS_METODOS_INGRESO);
  }

  /** Entra por correo con la cuenta objetivo, robusto a las dos variantes de pantalla. */
  private <T extends Actor> void ingresarPorCorreo(T actor) {
    // 1) "Ingresar con otra cuenta" SOLO si aparece esa pantalla.
    if (hayIngresarOtraCuenta(actor)) {
      EvidenciaUtils.registrarCaptura("Pantalla de bienvenida -> Ingresar con otra cuenta.");
      clickOtraCuenta(actor);
      esperarCualquiera(actor, 12000, BTN_OTROS_METODOS_INGRESO, TXT_USERNAME);
    } else {
      EvidenciaUtils.registrarCaptura("No hay 'Ingresar con otra cuenta'; se continúa a Otros métodos.");
    }

    // 2) Otros métodos de ingreso -> Correo electrónico.
    if (existe(actor, BTN_OTROS_METODOS_INGRESO, 6, 400)) {
      clickTextoSeguro(actor, OTROS_METODOS_DE_INGRESO);
      existe(actor, OPCION_CORREO, 8, 400);
      clickTextoSeguro(actor, CORREO_ELECTRONICO);
    }

    // 3) Correo (escritura robusta con espera de "habilitado").
    if (!existe(actor, TXT_USERNAME, 12, 400)) {
      throw new IllegalStateException(
          "No apareció el campo de correo. La app pudo cerrarse o cambiar de pantalla.");
    }
    escribir(actor, TXT_USERNAME, user.getEmail(), "correo");
    EvidenciaUtils.registrarCaptura("Correo digitado: " + user.getEmail());
    clickTextoSeguro(actor, CONTINUAR);

    // 4) Contraseña.
    existe(actor, PANTALLA_CONTRASENA, 10, 400);
    if (!existe(actor, TXT_PASSWORD, 10, 400)) {
      throw new IllegalStateException(
          "No apareció el campo de contraseña. La app pudo cerrarse o cambiar de pantalla.");
    }
    escribir(actor, TXT_PASSWORD, user.getPassword(), "contraseña");
    EvidenciaUtils.registrarCaptura("Contraseña digitada: ******** (oculta)");
    clickTextoSeguro(actor, CONTINUAR);

    // 5) Esperar el ingreso (maneja "sesión abierta en otro dispositivo" o ingreso directo).
    esperarIngreso(actor);
  }

  /**
   * Espera tras la contraseña. Cubre los dos caminos y espera el HOME (no depende del logo "Espera un
   * momento"). Solo maneja lo que puede aparecer en un REINGRESO (sin reinicio): el popup "sesión
   * abierta en otro dispositivo" y la publicidad. Los permisos/condiciones NO aplican aquí (esos solo
   * salen tras un reinicio limpio, y esos flujos usan IngresoSuperApp/PreparacionApp).
   */
  private static final long ESPERA_INGRESO_MS = 60_000L;

  /**
   * Espera post-contraseña: descarta los modales que aparecen ENCIMA del home tras el login
   * (autorización de medición de velocidad, "sesión abierta en otro dispositivo", publicidad) y
   * confirma la entrada al home. Detecta por page source para no depender del implicitWait.
   */
  private <T extends Actor> void esperarIngreso(T actor) {
    long fin = System.currentTimeMillis() + ESPERA_INGRESO_MS;
    while (System.currentTimeMillis() < fin) {
      String xml = pageSource(actor);

      // Modal "Autorización de medición de velocidad" (sale sobre el home) -> Aceptar.
      if (contiene(xml, "medición de velocidad") || contiene(xml, "Medición de Velocidad")) {
        clickTextoSeguro(actor, ACEPTAR_2); // "Aceptar"
        dormir(800);
        continue;
      }

      // Sesión abierta en otro dispositivo -> Continuar.
      if (contiene(xml, "sesión abierta en otro dispositivo")) {
        clickTextoSeguro(actor, CONTINUAR); // "Continuar"
        dormir(800);
        continue;
      }

      // Banner publicitario sobre el home -> cerrar.
      if (visible(actor, BTN_CERRAR_PUBLICIDAD)) {
        actor.attemptsTo(Click.on(BTN_CERRAR_PUBLICIDAD));
        dormir(500);
        continue;
      }

      // Home confirmado (señal real de éxito).
      if (enHome(xml)) {
        EvidenciaUtils.registrarCaptura(
                "Login exitoso. Home confirmado para: " + CuentaManager.getIdCuentaActiva());
        return;
      }

      dormir(1000);
    }

    EvidenciaUtils.registrarCaptura("No se confirmó el ingreso al home tras la contraseña (timeout).");
    throw new IllegalStateException(
            "ReingresoRelogin: no se confirmó el home tras la contraseña para '"
                    + CuentaManager.getIdCuentaActiva()
                    + "'. Revisa modales post-login (velocidad / sesión abierta) o el locator del home.");
  }

  private boolean enHome(String xml) {
    return contiene(xml, ":id/home_user_name_tv")
            || contiene(xml, ":id/iv_menu")
            || contiene(xml, ":id/card_mini_program_title_tv")
            || contiene(xml, "Tus servicios favoritos");
  }

  private String pageSource(Actor actor) {
    try {
      AndroidDriver d = AndroidObject.androidDriver(actor);
      return d == null ? null : d.getPageSource();
    } catch (Exception e) {
      return null;
    }
  }

  private boolean contiene(String xml, String clave) {
    return xml != null && xml.contains(clave);
  }

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

  // ─────────────────────────── utilidades ───────────────────────────

  /** Escribe esperando (acotado) a que el campo esté presente y HABILITADO, para no colgarse. */
  private <T extends Actor> void escribir(T actor, Target campo, String valor, String etiqueta) {
    if (!esperarHabilitado(actor, campo, 12000)) {
      throw new IllegalStateException(
          "El campo '" + etiqueta + "' no quedó habilitado a tiempo (la app pudo cerrarse).");
    }
    actor.attemptsTo(Enter.theValue(valor).into(campo));
  }

  private <T extends Actor> boolean esperarHabilitado(T actor, Target campo, long maxMs) {
    long fin = System.currentTimeMillis() + maxMs;
    while (System.currentTimeMillis() < fin) {
      try {
        if (!Presence.of(campo).viewedBy(actor).resolveAll().isEmpty()
            && campo.resolveFor(actor).isEnabled()) {
          return true;
        }
      } catch (Exception ignore) {
        // reintenta
      }
      dormir(400);
    }
    return false;
  }

  private <T extends Actor> String leerReloginAccount(T actor) {
    try {
      if (visible(actor, LBL_IDENTIFICADOR_USUARIO)) {
        return Text.of(LBL_IDENTIFICADOR_USUARIO).viewedBy(actor).asString();
      }
    } catch (Exception ignore) {
      // (no visible)
    }
    return "(no visible)";
  }

  private <T extends Actor> void clickTextoSeguro(T actor, String texto) {
    try {
      actor.attemptsTo(ClickElementByText.clickElementByText(texto));
    } catch (Exception e) {
      EvidenciaUtils.registrarCaptura("No se pudo clicar '" + texto + "'.");
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
}
