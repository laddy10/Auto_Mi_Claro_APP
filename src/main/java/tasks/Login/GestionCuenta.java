package tasks.Login;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.PantallaRapida.ID;
import static utils.PantallaRapida.PAQUETE;
import static utils.PantallaRapida.atributo;
import static utils.PantallaRapida.dormir;
import static utils.PantallaRapida.idCompleto;
import static utils.PantallaRapida.nodo;
import static utils.PantallaRapida.porUiSelector;
import static utils.PantallaRapida.textoDe;
import static utils.PantallaRapida.textosVisibles;
import static utils.PantallaRapida.tiene;
import static utils.PantallaRapida.tieneId;
import static utils.PantallaRapida.tieneTexto;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.By;
import utils.AppReset;
import utils.CuentaManager;
import utils.EvidenciaUtils;
import utils.PantallaRapida;
import utils.TestDataProvider;

/**
 * Deja la app con la sesión de la cuenta que necesita el escenario (tag {@code @cuenta_<id>}; sin
 * tag, la principal).
 *
 * <p><b>Cómo sabe qué cuenta hay en la app:</b> abre el menú del perfil y lee el saludo ("Hola,
 * ..."). Lo compara con el {@code nombreUsuario} de TODAS las cuentas de real-user.json:
 *
 * <ul>
 *   <li>coincide solo con la cuenta del escenario: es la correcta;
 *   <li>coincide solo con otra cuenta: hay que cambiar;
 *   <li>no coincide con ninguna, o coincide con varias: desempata con el correo que muestra "Tus
 *       datos", que es el dato con el que se inicia sesión.
 * </ul>
 *
 * <p><b>Cómo cambia de cuenta:</b> cierra la sesión desde el menú e ingresa con el correo de la
 * cuenta del escenario. Al terminar vuelve a abrir el menú y verifica el saludo: la sesión solo se
 * da por buena después de verla. Borrar los datos de la app ({@link AppReset}) queda como respaldo,
 * una sola vez, si la app no responde o no se logra cerrar la sesión.
 *
 * <p><b>Cómo avanza:</b> un solo ciclo que lee la pantalla (page source) y atiende lo que haya:
 * avisos, permisos, pantallas de ingreso. No depende del orden en que aparezcan ni paga esperas por
 * elementos ausentes (ver {@link PantallaRapida}). Si no lo logra en el tiempo previsto falla con
 * un mensaje que dice qué había en pantalla.
 */
public class GestionCuenta implements Task {

  private static final long PRESUPUESTO_MS = 180_000L;
  private static final long PRESUPUESTO_TRAS_REINICIO_MS = 150_000L;
  private static final long PAUSA_MS = 400L;

  /** Tiempo máximo en una misma pantalla antes de recurrir al reinicio de respaldo. */
  private static final long LIMITE_ESTADO_MS = 40_000L;

  private static final long LIMITE_CARGA_MS = 60_000L;
  private static final long LIMITE_DESCONOCIDO_MS = 25_000L;
  private static final long LIMITE_FUERA_DE_LA_APP_MS = 3_000L;
  private static final long LIMITE_SIN_LECTURA_MS = 20_000L;

  /** Tras enviar la contraseña la app queda varios segundos en blanco antes del inicio. */
  private static final long ESPERA_TRAS_CLAVE_MS = 45_000L;

  private static final int MAX_INTENTOS_CORREO = 2;
  private static final int MAX_INTENTOS_CLAVE = 2;

  private static final Pattern PUBLICIDAD =
      Pattern.compile("<android\\.widget\\.ImageButton\\s[^>]*content-desc=\"Close\"");
  private static final By BTN_CERRAR_PUBLICIDAD =
      By.xpath("//android.widget.ImageButton[@content-desc=\"Close\"]");

  private final User user = TestDataProvider.getRealUser();

  private PantallaRapida pantalla;
  private String idObjetivo;
  private long inicio;
  private long fin;

  private String estadoActual;
  private long estadoDesde;
  private long cargandoHasta;
  private long ultimaAccionFuera;
  private long sinLecturaDesde;
  private int atrasFuera;

  /** Correo leído en "Tus datos" para la sesión actual (null = aún no se ha leído). */
  private String correoEnApp;

  private boolean sesionCerrada;
  private boolean avisoSinSesion;
  private boolean correoDigitado;
  private boolean ingresoConCuentaRecordada;
  private boolean evitarCuentaRecordada;
  private boolean reinicioUsado;
  private int intentosCorreo;
  private int intentosClave;

  @Override
  @Step("{0} deja la app con la sesión de la cuenta que necesita el escenario")
  public <T extends Actor> void performAs(T actor) {
    idObjetivo = CuentaManager.getIdCuentaActiva();
    pantalla = PantallaRapida.de(actor);
    pantalla.sinEsperaImplicita();
    inicio = ahora();
    try {
      asegurarCuenta(actor);
    } finally {
      pantalla.restaurarEsperaImplicita();
    }
  }

  // ─────────────────────────── ciclo principal ───────────────────────────

  private <T extends Actor> void asegurarCuenta(T actor) {
    log("cuenta del escenario: " + idObjetivo + " (" + user.getNombreUsuario() + ")");
    fin = ahora() + PRESUPUESTO_MS;
    while (ahora() < fin) {
      if (atender(actor, pantalla.fuente())) {
        volverAlHome(actor);
        log("listo");
        return;
      }
    }
    fallar(
        "no se logró dejar la sesión lista en " + (ahora() - inicio) / 1000 + " s",
        pantalla.fuente());
  }

  /**
   * Atiende la pantalla actual. Las comprobaciones van de lo que tapa (carga, permisos, avisos) a
   * lo que queda debajo.
   *
   * @return true cuando la sesión de la cuenta del escenario ya está verificada.
   */
  private <T extends Actor> boolean atender(T actor, String xml) {
    if (xml.isEmpty()) {
      // Appium no devuelve la pantalla (sesión caída, UiAutomator2 detenido): reiniciar la app
      // no lo arregla, así que se falla con el error real.
      if (sinLecturaDesde == 0) {
        sinLecturaDesde = ahora();
      } else if (ahora() - sinLecturaDesde > LIMITE_SIN_LECTURA_MS) {
        fallar(
            "Appium no devuelve la pantalla hace "
                + (ahora() - sinLecturaDesde) / 1000
                + " s ("
                + pantalla.ultimoError()
                + ")",
            "");
      }
      dormir(PAUSA_MS);
      return false;
    }
    sinLecturaDesde = 0;

    if (tieneId(xml, "imgLoadingSplash") || tieneId(xml, "animation_splash_lottie")) {
      estado("pantalla de carga", LIMITE_CARGA_MS);
      dormir(PAUSA_MS);
      return false;
    }

    String aviso = atenderPermisoDelSistema(xml);
    if (aviso == null) {
      aviso = atenderAviso(xml);
    }
    if (aviso != null) {
      estado("aviso: " + aviso, LIMITE_ESTADO_MS);
      return false;
    }

    if (tieneId(xml, "profile_welcome_title")) {
      return estado("menú del perfil", LIMITE_ESTADO_MS) && verificarCuenta(xml);
    }

    if (tieneId(xml, "txt_email") && tieneId(xml, "page_top_back")) {
      if (estado("tus datos", LIMITE_ESTADO_MS)) {
        pantalla.atras();
        dormir(800);
      }
      return false;
    }

    if (tieneId(xml, "iv_menu") && !tieneId(xml, "tv_login")) {
      if (estado("inicio con sesión", LIMITE_ESTADO_MS)) {
        pantalla.tocarId("iv_menu");
        pantalla.esperar(x -> tieneId(x, "profile_welcome_title"), 5_000);
      }
      return false;
    }

    if (CambiarAPersonas.esLoginEmpresas(xml) || CambiarAPersonas.esHomeEmpresas(xml)) {
      if (estado("Claro Empresas", LIMITE_ESTADO_MS)) {
        actor.attemptsTo(CambiarAPersonas.cambiarAPersonas());
        pantalla.sinEsperaImplicita();
      }
      return false;
    }

    if (tieneId(xml, "tv_login")) {
      if (estado("inicio sin sesión", LIMITE_ESTADO_MS)) {
        avisarSinSesion();
        pantalla.tocarId("tv_login");
        pantalla.esperar(x -> !tieneId(x, "tv_login"), 6_000);
      }
      return false;
    }

    if (tieneId(xml, "relogin_account") || tieneId(xml, "relogin_use_change_account")) {
      if (estado("cuenta recordada", LIMITE_ESTADO_MS)) {
        atenderCuentaRecordada(xml);
      }
      return false;
    }

    if (xml.contains("Ingresa con:") && tieneId(xml, "forget_pwd_id")) {
      if (estado("métodos de ingreso", LIMITE_ESTADO_MS)) {
        elegirIngresoConCorreo();
      }
      return false;
    }

    if (esPantallaDeClave(xml)) {
      if (estado("contraseña", ESPERA_TRAS_CLAVE_MS + LIMITE_ESTADO_MS)) {
        ingresarClave(xml);
      }
      return false;
    }

    if (tieneId(xml, "tv_forget_pwd_content") && tieneId(xml, "input")) {
      if (estado("correo", LIMITE_ESTADO_MS)) {
        ingresarCorreo(xml);
      }
      return false;
    }

    if (tieneId(xml, "tv_login_entry_other_type")) {
      if (estado("ingreso con documento", LIMITE_ESTADO_MS)) {
        avisarSinSesion();
        pantalla.tocarId("tv_login_entry_other_type");
        pantalla.esperar(x -> x.contains("Ingresa con:"), 5_000);
      }
      return false;
    }

    // Pantallas de bienvenida tras un reinicio con datos limpios.
    if (tieneTexto(xml, "Aceptar", "ACEPTAR")) {
      if (estado("bienvenida: aceptar", LIMITE_ESTADO_MS)) {
        pantalla.tocarTexto("Aceptar", "ACEPTAR");
        dormir(600);
      }
      return false;
    }
    if (tieneTexto(xml, "Omitir", "OMITIR")) {
      if (estado("bienvenida: omitir", LIMITE_ESTADO_MS)) {
        pantalla.tocarTexto("Omitir", "OMITIR");
        dormir(600);
      }
      return false;
    }

    atenderPantallaNoReconocida(actor, xml);
    return false;
  }

  private <T extends Actor> void atenderPantallaNoReconocida(T actor, String xml) {
    if (CerrarTiendaClaro.hayQueVolverAlHome(xml)) {
      if (estado("otra pestaña", LIMITE_ESTADO_MS)) {
        actor.attemptsTo(CerrarTiendaClaro.cerrarTiendaClaro());
        pantalla.sinEsperaImplicita();
      }
      return;
    }

    if (!xml.contains("package=\"" + PAQUETE + "\"")) {
      atenderFueraDeLaApp(xml);
      return;
    }

    if (ahora() < cargandoHasta) {
      estado("cargando tras el ingreso", ESPERA_TRAS_CLAVE_MS);
    } else {
      estado("pantalla no reconocida", LIMITE_DESCONOCIDO_MS);
    }
    dormir(PAUSA_MS);
  }

  /**
   * Lo que se ve no es de Mi Claro: una ventana encima (autocompletar de Android, un aviso del
   * sistema) o la app quedó en segundo plano. Primero se intenta cerrar lo que tapa con Atrás (dos
   * veces como máximo) y, si sigue sin verse la app, se trae al frente.
   */
  private void atenderFueraDeLaApp(String xml) {
    if (!estado("fuera de la app", LIMITE_ESTADO_MS)) {
      return;
    }
    long t = ahora();
    if (t - estadoDesde < LIMITE_FUERA_DE_LA_APP_MS || t - ultimaAccionFuera < 2_500) {
      dormir(PAUSA_MS);
      return;
    }
    ultimaAccionFuera = t;
    if (atrasFuera < 2) {
      atrasFuera++;
      log("en pantalla hay otra app (" + paquetes(xml) + "): se pulsa Atrás");
      pantalla.atras();
    } else {
      atrasFuera = 0;
      log("en pantalla hay otra app (" + paquetes(xml) + "): se trae Mi Claro al frente");
      try {
        pantalla.driver().activateApp(PAQUETE);
      } catch (RuntimeException e) {
        log("no se pudo traer la app al frente: " + e.getMessage());
      }
    }
    dormir(800);
  }

  private static String paquetes(String xml) {
    java.util.Set<String> encontrados = new java.util.LinkedHashSet<>();
    java.util.regex.Matcher m = Pattern.compile("package=\"([^\"]+)\"").matcher(xml);
    while (m.find() && encontrados.size() < 3) {
      encontrados.add(m.group(1));
    }
    return encontrados.isEmpty() ? "sin elementos" : String.join(", ", encontrados);
  }

  /**
   * Registra en qué pantalla está el ciclo. Si lleva demasiado en la misma, recurre al reinicio de
   * respaldo.
   *
   * @return false si reinició la app (lo que se iba a hacer en esa pantalla ya no aplica).
   */
  private boolean estado(String nombre, long limiteMs) {
    long t = ahora();
    if (!nombre.equals(estadoActual)) {
      estadoActual = nombre;
      estadoDesde = t;
      atrasFuera = 0;
      log(nombre);
      return true;
    }
    if (t - estadoDesde <= limiteMs) {
      return true;
    }
    reiniciarDeRespaldo("la app lleva " + (t - estadoDesde) / 1000 + " s en '" + nombre + "'");
    return false;
  }

  // ─────────────────────────── permisos y avisos ───────────────────────────

  /** Diálogos de permisos de Android. Ubicación: solo en uso; el resto: no permitir. */
  private String atenderPermisoDelSistema(String xml) {
    boolean esDialogo =
        tieneId(xml, "permission_message")
            || tieneId(xml, "permission_deny_button")
            || tiene(xml, "¿Permitir que Mi Claro", "¿Quieres permitir que Mi Claro");
    if (!esDialogo) {
      return null;
    }
    if (xml.toLowerCase(Locale.ROOT).contains("ubicación")) {
      String boton = idCompleto(xml, "permission_allow_foreground_only_button");
      if (boton == null || !pantalla.tocar(By.id(boton))) {
        pantalla.tocarTextoQueCumpla("(?i).*mientras.*");
      }
      dormir(600);
      return "permiso de ubicación";
    }
    String boton = idCompleto(xml, "permission_deny_button");
    if (boton == null || !pantalla.tocar(By.id(boton))) {
      pantalla.tocarTextoQueCumpla("(?i)no permitir");
    }
    dormir(600);
    return "permiso de Android";
  }

  /**
   * Avisos de la app que pueden salir en cualquier momento del ingreso.
   *
   * @return nombre del aviso atendido, o null si no había ninguno.
   */
  private String atenderAviso(String xml) {
    String bajo = xml.toLowerCase(Locale.ROOT);

    if (bajo.contains("autorización de medición de velocidad")
        || bajo.contains("autorización para medición de velocidad")) {
      // La variante "para Medición" abre otra pantalla al aceptar: se regresa con Atrás.
      boolean conRegreso = xml.contains("Autorización para Medición de Velocidad");
      if (!pantalla.tocarId("bt_top")) {
        pantalla.tocarTexto("Aceptar", "ACEPTAR");
      }
      if (conRegreso) {
        dormir(1000);
        pantalla.atras();
      }
      dormir(600);
      return "medición de velocidad";
    }

    // "¿Quieres continuar en este?": se continúa. La app cierra la sesión en el otro celular (los
    // celulares que comparten cuenta se la quitan entre sí; ese celular vuelve a ingresar solo).
    if (xml.contains("sesión abierta en otro dispositivo")) {
      if (!pantalla.tocarId("bt_top")) {
        pantalla.tocarTexto("Continuar");
      }
      dormir(2000);
      return "sesión abierta en otro dispositivo";
    }

    if (bajo.contains("activa el ingreso biométrico") && tieneTexto(xml, "En otro momento")) {
      pantalla.tocarTexto("En otro momento");
      dormir(600);
      return "ingreso biométrico";
    }

    if (xml.contains("sesión se ha cerrado por seguridad")) {
      if (!pantalla.tocarTexto("Aceptar", "ACEPTAR")) {
        pantalla.tocarId("bt_top");
      }
      dormir(600);
      return "sesión cerrada por seguridad";
    }

    if (tieneId(xml, "iv_protocol")) {
      String casilla = nodo(xml, "iv_protocol");
      boolean marcada =
          "true".equals(atributo(casilla, "selected"))
              || "true".equals(atributo(casilla, "checked"));
      if (!marcada) {
        pantalla.tocarId("iv_protocol");
        dormir(300);
      }
      pantalla.tocarTexto("Continuar");
      pantalla.esperar(x -> !tieneId(x, "iv_protocol"), 5_000);
      return "términos y condiciones";
    }

    // Confirmación de cierre de sesión que quedó abierta sin haberla pedido aquí.
    if (xml.contains("¿Quieres cerrar la sesión?") && tieneId(xml, "bt_bottom")) {
      pantalla.tocarId("bt_bottom");
      dormir(600);
      return "confirmación de cierre de sesión";
    }

    if (PUBLICIDAD.matcher(xml).find()) {
      pantalla.tocar(BTN_CERRAR_PUBLICIDAD);
      dormir(500);
      return "publicidad";
    }
    return null;
  }

  // ─────────────────────────── qué cuenta hay y cuál se necesita ───────────────────────────

  /**
   * Con el menú del perfil abierto: decide si la sesión es la de la cuenta del escenario. Si lo es,
   * cierra el menú y termina; si es otra, cierra la sesión para ingresar con la correcta.
   */
  private boolean verificarCuenta(String xmlMenu) {
    String saludo = textoDe(xmlMenu, "profile_welcome_title");
    if (saludo.isEmpty()) {
      String conSaludo =
          pantalla.esperar(x -> !textoDe(x, "profile_welcome_title").isEmpty(), 5_000);
      if (conSaludo == null) {
        return false; // el menú aún no pinta el nombre: se reevalúa
      }
      saludo = textoDe(conSaludo, "profile_welcome_title");
    }

    List<String> cuentasConEseSaludo = new ArrayList<>();
    for (String id : CuentaManager.getIdsCuentas()) {
      User cuenta = CuentaManager.getCuenta(id);
      if (cuenta != null && coincideNombre(saludo, cuenta.getNombreUsuario())) {
        cuentasConEseSaludo.add(id);
      }
    }
    boolean saludoDelObjetivo = cuentasConEseSaludo.contains(idObjetivo);

    boolean correcta;
    String comoSeSupo;
    if (saludoDelObjetivo && cuentasConEseSaludo.size() == 1) {
      correcta = true;
      comoSeSupo = "por el saludo";
    } else if (!saludoDelObjetivo && !cuentasConEseSaludo.isEmpty()) {
      correcta = false;
      comoSeSupo = "el saludo es el de la cuenta " + cuentasConEseSaludo.get(0);
    } else {
      // El saludo no distingue (no es de ninguna cuenta configurada, o es de varias).
      String correo = correoDeLaSesion();
      if (correo.isEmpty()) {
        correcta = saludoDelObjetivo;
        comoSeSupo = "por el saludo (no se pudo leer el correo en Tus datos)";
      } else {
        correcta = mismoCorreo(correo, user.getEmail());
        comoSeSupo = "por el correo de Tus datos (" + correo + ")";
      }
    }
    log(
        "sesión de '"
            + saludo
            + "' -> "
            + (correcta ? "es" : "NO es")
            + " la cuenta "
            + idObjetivo
            + ", "
            + comoSeSupo);

    if (correcta) {
      if (!saludoDelObjetivo) {
        log(
            "AVISO: el nombreUsuario de '"
                + idObjetivo
                + "' en real-user.json ('"
                + user.getNombreUsuario()
                + "') no coincide con el saludo de la app ('"
                + saludo
                + "')");
      }
      evidencia("Cuenta verificada en el menú: '" + saludo + "' (cuenta " + idObjetivo + ")");
      cerrarMenu();
      CuentaManager.setUltimaCuentaLogueada(idObjetivo);
      return true;
    }

    if (correoDigitado) {
      fallar(
          "se ingresó con el correo de la cuenta pero la app muestra la sesión de '"
              + saludo
              + "' ("
              + comoSeSupo
              + "). Revisar el correo y el nombreUsuario de esa cuenta en real-user.json",
          pantalla.fuente());
    }
    evidencia(
        "La app tiene la sesión de '"
            + saludo
            + "' y el escenario necesita la cuenta "
            + idObjetivo
            + " ("
            + user.getNombreUsuario()
            + "): se cierra la sesión");
    if (ingresoConCuentaRecordada) {
      evitarCuentaRecordada = true; // la cuenta que recordaba la app no era la del escenario
    }
    cerrarSesion();
    return false;
  }

  /** Correo de la sesión actual según "Tus datos" (se lee una vez por sesión). */
  private String correoDeLaSesion() {
    if (correoEnApp != null) {
      return correoEnApp;
    }
    String correo = "";
    if (pantalla.tocarId("profile_your_data")) {
      String datos = pantalla.esperar(x -> !textoDe(x, "txt_email").isEmpty(), 8_000);
      if (datos != null) {
        correo = textoDe(datos, "txt_email");
      }
      if (!tieneId(pantalla.fuente(), "profile_welcome_title")) {
        pantalla.atras();
        pantalla.esperar(x -> tieneId(x, "profile_welcome_title"), 4_000);
      }
    }
    correoEnApp = correo;
    return correo;
  }

  private void cerrarMenu() {
    for (int i = 0; i < 3; i++) {
      String xml = pantalla.fuente();
      if (tieneId(xml, "txt_email") && tieneId(xml, "page_top_back")) {
        pantalla.atras();
        dormir(800);
      } else if (tieneId(xml, "profile_welcome_title")) {
        pantalla.tocarId("iv_close");
        pantalla.esperar(x -> !tieneId(x, "profile_welcome_title"), 3_000);
      } else {
        return;
      }
    }
  }

  /** Cierra la sesión desde el menú del perfil (ya abierto). */
  private void cerrarSesion() {
    correoEnApp = null;
    boolean pulsado =
        pantalla.tocarId("profile_logout")
            || pantalla.tocar(
                porUiSelector(
                    "new UiScrollable(new UiSelector().scrollable(true)).scrollIntoView("
                        + "new UiSelector().resourceId(\""
                        + ID
                        + "profile_logout\"))"));
    String confirmacion =
        pulsado
            ? pantalla.esperar(x -> tieneId(x, "bt_top") && x.contains("cerrar la sesi"), 6_000)
            : null;
    if (confirmacion == null || !pantalla.tocarId("bt_top")) {
      reiniciarDeRespaldo("no se pudo cerrar la sesión desde el menú");
      return;
    }
    String sinSesion =
        pantalla.esperar(
            x ->
                tieneId(x, "tv_login")
                    || tieneId(x, "relogin_account")
                    || tieneId(x, "tv_login_entry_other_type"),
            20_000);
    if (sinSesion == null) {
      reiniciarDeRespaldo("la app no cerró la sesión");
      return;
    }
    sesionCerrada = true;
    log("sesión anterior cerrada");
  }

  // ─────────────────────────── ingreso ───────────────────────────

  /**
   * Pantalla "¡Nos alegra tenerte de vuelta!": la app recuerda la última cuenta. Si es la del
   * escenario solo pide la contraseña; si es otra se elige "Ingresar con otra cuenta".
   */
  private void atenderCuentaRecordada(String xml) {
    avisarSinSesion();
    String recordada = textoDe(xml, "relogin_account");
    boolean esLaDelEscenario =
        !evitarCuentaRecordada && coincideCorreoEnmascarado(recordada, user.getEmail());
    log(
        "la app recuerda '"
            + recordada
            + "' -> "
            + (esLaDelEscenario ? "es" : "no es")
            + " la cuenta "
            + idObjetivo);
    if (esLaDelEscenario && pantalla.tocarId("login_entry_next")) {
      ingresoConCuentaRecordada = true;
    } else {
      pantalla.tocarId("relogin_use_change_account");
    }
    pantalla.esperar(x -> !tieneId(x, "relogin_account"), 8_000);
  }

  private void elegirIngresoConCorreo() {
    boolean pulsado =
        pantalla.tocar(
                porUiSelector(
                    "new UiSelector().resourceId(\""
                        + ID
                        + "forget_pwd_id\").text(\"Correo electrónico\")"))
            || pantalla.tocarTexto("Correo electrónico");
    if (pulsado) {
      pantalla.esperar(x -> !x.contains("Ingresa con:"), 6_000);
    } else {
      dormir(PAUSA_MS);
    }
  }

  private void ingresarCorreo(String xml) {
    String correo = user.getEmail() == null ? "" : user.getEmail().trim();
    if (correo.isEmpty()) {
      fallar("la cuenta no tiene correo en real-user.json", xml);
    }
    if (intentosCorreo >= MAX_INTENTOS_CORREO) {
      reiniciarDeRespaldo("la app no avanzó después de digitar el correo");
      return;
    }
    intentosCorreo++;
    if (!pantalla.escribir("input", correo)) {
      return;
    }
    // Con el teclado abierto el botón sube y cambia de id (forget_password_next_top).
    String conBoton =
        pantalla.esperar(
            x -> tieneId(x, "forget_password_next") || tieneId(x, "forget_password_next_top"),
            5_000);
    evidencia("Ingreso con el correo de la cuenta " + idObjetivo);
    if (conBoton == null || !tocarPrimero("forget_password_next", "forget_password_next_top")) {
      return;
    }
    correoDigitado = true;
    pantalla.esperar(
        x ->
            esPantallaDeClave(x)
                || !(tieneId(x, "forget_password_next") || tieneId(x, "forget_password_next_top")),
        15_000);
  }

  /** Pulsa el primero de esos ids que esté en pantalla. */
  private boolean tocarPrimero(String... ids) {
    for (String id : ids) {
      if (pantalla.tocarId(id)) {
        return true;
      }
    }
    return false;
  }

  private boolean esPantallaDeClave(String xml) {
    return tieneId(xml, "login_password_next")
        || tieneId(xml, "login_password_next_top")
        || tieneId(xml, "tv_lose_pass");
  }

  /**
   * Digita la contraseña sin dejarla en el reporte. Si la app no la acepta NO se insiste: varios
   * intentos fallidos bloquean la cuenta.
   */
  private void ingresarClave(String xml) {
    String clave = user.getPassword();
    if (clave == null || clave.isEmpty()) {
      fallar("la cuenta no tiene contraseña en real-user.json", xml);
    }
    if (intentosClave >= MAX_INTENTOS_CLAVE) {
      fallar(
          "la app volvió a pedir la contraseña después de digitarla "
              + intentosClave
              + " veces; no se insiste para no bloquear la cuenta",
          xml);
    }
    if (!pantalla.escribir("input", clave)) {
      return;
    }
    intentosClave++;
    dormir(300);
    if (!tocarPrimero("login_password_next", "login_password_next_top")) {
      pantalla.tocarTexto("Continuar");
    }
    // Termina al salir de la pantalla o al aparecer un aviso encima (p. ej. "Tienes una sesión
    // abierta en otro dispositivo").
    String despues =
        pantalla.esperar(x -> !esPantallaDeClave(x) || tieneId(x, "bt_top"), ESPERA_TRAS_CLAVE_MS);
    if (despues == null) {
      fallar(
          "la app no aceptó la contraseña (sigue en esa pantalla tras "
              + ESPERA_TRAS_CLAVE_MS / 1000
              + " s)",
          pantalla.fuente());
    }
    cargandoHasta = ahora() + ESPERA_TRAS_CLAVE_MS;
    log("contraseña enviada");
  }

  // ─────────────────────────── respaldo y cierre ───────────────────────────

  /** Borra los datos de la app y la relanza. Solo una vez por escenario. */
  private void reiniciarDeRespaldo(String motivo) {
    if (reinicioUsado) {
      fallar(motivo + " (ya se había reiniciado la app una vez)", pantalla.fuente());
    }
    reinicioUsado = true;
    evidencia("Respaldo: " + motivo + ". Se reinicia la app con datos limpios");
    try {
      AppReset.reiniciarApp(pantalla.driver());
    } catch (RuntimeException e) {
      fallar(
          motivo + "; además no se pudo reiniciar la app (" + e.getMessage() + ")",
          pantalla.fuente());
    }
    pantalla.sinEsperaImplicita();
    estadoActual = null;
    correoEnApp = null;
    correoDigitado = false;
    ingresoConCuentaRecordada = false;
    evitarCuentaRecordada = false;
    intentosCorreo = 0;
    sesionCerrada = true;
    fin = Math.max(fin, ahora() + PRESUPUESTO_TRAS_REINICIO_MS);
  }

  /** El ingreso deja la app en la última pestaña usada; los casos parten del inicio. */
  private <T extends Actor> void volverAlHome(T actor) {
    if (CerrarTiendaClaro.hayQueVolverAlHome(pantalla.fuente())) {
      actor.attemptsTo(CerrarTiendaClaro.cerrarTiendaClaro());
    }
  }

  private void avisarSinSesion() {
    if (!avisoSinSesion && !sesionCerrada) {
      evidencia("La app está sin sesión: se ingresa con la cuenta " + idObjetivo);
    }
    avisoSinSesion = true;
  }

  private void evidencia(String paso) {
    EvidenciaUtils.registrarCaptura(paso);
    pantalla.sinEsperaImplicita(); // la captura pasa por Serenity, que reimpone la espera
  }

  private void fallar(String motivo, String xml) {
    String mensaje =
        "Inicio de sesión con la cuenta '"
            + idObjetivo
            + "': "
            + motivo
            + ". En pantalla: "
            + textosVisibles(xml);
    log("FALLO: " + mensaje);
    try {
      EvidenciaUtils.registrarCaptura("No se pudo iniciar sesión: " + motivo);
    } catch (RuntimeException e) {
      // la evidencia no debe tapar el motivo real del fallo
    }
    throw new IllegalStateException(mensaje);
  }

  // ─────────────────────────── comparaciones ───────────────────────────

  private boolean coincideNombre(String actual, String objetivo) {
    String a = norm(actual);
    String o = norm(objetivo);
    if (a.isEmpty() || o.isEmpty()) {
      return false;
    }
    return a.equals(o) || a.contains(o) || o.contains(a);
  }

  private String norm(String s) {
    return s == null
        ? ""
        : s.trim().toLowerCase().replaceAll(",", " ").replaceAll("\\s+", " ").trim();
  }

  private boolean mismoCorreo(String enApp, String configurado) {
    if (enApp == null || configurado == null) {
      return false;
    }
    return enApp.contains("*")
        ? coincideCorreoEnmascarado(enApp, configurado)
        : enApp.trim().equalsIgnoreCase(configurado.trim());
  }

  /** Compara un correo enmascarado ("anai****@hotmail.com") con uno completo. */
  static boolean coincideCorreoEnmascarado(String enmascarado, String correo) {
    if (enmascarado == null || correo == null) {
      return false;
    }
    String m = enmascarado.trim().toLowerCase(Locale.ROOT);
    String c = correo.trim().toLowerCase(Locale.ROOT);
    if (c.isEmpty() || !m.contains("@")) {
      return false;
    }
    int primero = m.indexOf('*');
    if (primero < 0) {
      return m.equals(c);
    }
    String antes = m.substring(0, primero);
    String despues = m.substring(m.lastIndexOf('*') + 1);
    return c.length() >= antes.length() + despues.length()
        && c.startsWith(antes)
        && c.endsWith(despues);
  }

  // ─────────────────────────── utilidades ───────────────────────────

  private long ahora() {
    return System.currentTimeMillis();
  }

  private void log(String texto) {
    System.out.println(
        String.format(
            Locale.ROOT, "[GestionCuenta +%.1fs] %s", (ahora() - inicio) / 1000.0, texto));
  }

  public static Performable segunCuenta() {
    return instrumented(GestionCuenta.class);
  }
}
