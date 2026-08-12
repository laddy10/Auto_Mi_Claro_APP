package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import models.User;

/**
 * Administra los usuarios de prueba (real-user.json) y la cuenta activa. Mantiene UNA instancia viva
 * de User: al cambiar de cuenta copia por reflexión los campos, así las clases con
 * {@code static final User user} siguen viendo los datos de la cuenta activa sin refactor.
 *
 * <p>Además lleva un REGISTRO de la última cuenta con la que se inició sesión ("ultimaCuentaLogueada")
 * que persiste durante toda la corrida. Sirve para identificar de forma CONFIABLE qué cuenta está en
 * la app sin tener que leer/adivinar el saludo del home (que puede ser igual entre cuentas).
 */
public class CuentaManager {

  private static final String RUTA_USUARIOS = "src/test/resources/config/real-user.json";
  private static final String CUENTA_POR_DEFECTO = "principal";
  private static final Pattern TAG_CUENTA =
      Pattern.compile("^@?cuenta[_-](.+)$", Pattern.CASE_INSENSITIVE);

  private static Map<String, User> pool;
  private static final User USUARIO_ACTUAL = new User();
  private static String idActual = null;

  // Última cuenta con la que se hizo login (persiste toda la corrida; null = desconocido).
  private static String ultimaCuentaLogueada = null;

  private CuentaManager() {}

  private static synchronized Map<String, User> pool() {
    if (pool == null) {
      pool = cargar();
    }
    return pool;
  }

  private static Map<String, User> cargar() {
    ObjectMapper mapper = new ObjectMapper();
    File archivo = new File(RUTA_USUARIOS);
    Map<String, User> cuentas = new LinkedHashMap<>();
    try {
      cuentas = mapper.readValue(archivo, new TypeReference<LinkedHashMap<String, User>>() {});
    } catch (Exception ignore) {
      cuentas = new LinkedHashMap<>();
    }
    if (cuentas.isEmpty() || !cuentas.containsKey(CUENTA_POR_DEFECTO)) {
      try {
        User plano = mapper.readValue(archivo, User.class);
        cuentas.put(CUENTA_POR_DEFECTO, plano);
      } catch (IOException e) {
        if (cuentas.isEmpty()) {
          throw new RuntimeException("No se pudo leer real-user.json", e);
        }
      }
    }
    System.out.println("\u2705 [CuentaManager] Cuentas cargadas: " + cuentas.keySet());
    return cuentas;
  }

  private static void copiarCampos(User destino, User origen) {
    for (Field f : User.class.getDeclaredFields()) {
      if (Modifier.isStatic(f.getModifiers())) {
        continue;
      }
      f.setAccessible(true);
      try {
        f.set(destino, f.get(origen));
      } catch (IllegalAccessException e) {
        System.err.println("\u26A0\uFE0F [CuentaManager] No se pudo copiar el campo " + f.getName());
      }
    }
  }

  public static synchronized User getCuentaActiva() {
    if (idActual == null) {
      activarCuenta(CUENTA_POR_DEFECTO);
    }
    return USUARIO_ACTUAL;
  }

  public static synchronized void activarCuenta(String id) {
    if (id == null || id.trim().isEmpty()) {
      id = CUENTA_POR_DEFECTO;
    }
    id = id.trim().toLowerCase();
    Map<String, User> p = pool();
    User origen = p.get(id);
    if (origen == null) {
      System.err.println(
          "\u26A0\uFE0F [CuentaManager] La cuenta '" + id + "' no existe. Se usa '" + CUENTA_POR_DEFECTO + "'.");
      id = CUENTA_POR_DEFECTO;
      origen = p.get(id);
    }
    copiarCampos(USUARIO_ACTUAL, origen);
    idActual = id;
    System.out.println(
        "\uD83D\uDD00 [CuentaManager] Cuenta activa: " + id + " (usuario: " + USUARIO_ACTUAL.getNombreUsuario() + ")");
  }

  public static void activarDesdeTags(Collection<String> tags) {
    String id = CUENTA_POR_DEFECTO;
    if (tags != null) {
      for (String tag : tags) {
        Matcher m = TAG_CUENTA.matcher(tag.trim());
        if (m.matches()) {
          id = m.group(1).toLowerCase();
          break;
        }
      }
    }
    activarCuenta(id);
  }

  public static String getIdCuentaActiva() {
    return idActual == null ? CUENTA_POR_DEFECTO : idActual;
  }

  public static User getCuenta(String id) {
    return pool().get(id == null ? CUENTA_POR_DEFECTO : id.trim().toLowerCase());
  }

  // ─── Registro de la última cuenta logueada (identificación confiable) ───

  public static void setUltimaCuentaLogueada(String id) {
    ultimaCuentaLogueada = (id == null ? null : id.trim().toLowerCase());
    System.out.println("\uD83D\uDCDD [CuentaManager] Última cuenta logueada: " + ultimaCuentaLogueada);
  }

  public static String getUltimaCuentaLogueada() {
    return ultimaCuentaLogueada;
  }

  /** Cambia SOLO la cuenta activa a "principal" (no toca el registro de última logueada). */
  public static void reset() {
    activarCuenta(CUENTA_POR_DEFECTO);
  }
}
