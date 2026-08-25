package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.Scenario;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import models.User;

/**
 * Deja constancia, escenario por escenario, de CON QUE DATOS se corrio la prueba: cuenta, correo y
 * linea. Es el lado "proyecto" del contrato st-context con Smart Tester.
 *
 * <p>Como funciona: al terminar cada escenario se escribe UN archivo JSON en {@code
 * target/st-context/}. El orquestador lee esa carpeta antes de archivar el workspace, la mete en
 * report_metadata.json y la publica en /api/status; Smart Tester la convierte en las variables de
 * plantilla {@code {{correoPrueba}}}, {@code {{lineaPrueba}}}, {@code {{cuentaPrueba}}}.
 *
 * <p>Un archivo por escenario (y no uno compartido) a proposito: el build corre con {@code
 * maxParallelForks}, y varios JVM escribiendo el mismo archivo se pisarian. Ademas, si la corrida se
 * cae a la mitad, lo ya escrito sobrevive.
 *
 * <p>REGLA: aqui NO se escriben contrasenas (ni password, ni contrasena, ni passwordClaroMusica).
 * Este archivo viaja a Smart Tester y su contenido termina en mensajes de WhatsApp/Teams.
 */
public class ContextoST {

  private static final String CARPETA = "target/st-context";

  private ContextoST() {}

  /**
   * Registra la cuenta con la que corrio el escenario. Nunca lanza: si algo falla, el escenario no
   * se entera (esto es telemetria, no parte de la prueba).
   */
  public static void registrarEscenario(Scenario scenario) {
    try {
      // La cuenta del escenario es la ACTIVA al terminar: sale del tag @cuenta_<id> o
      // del step EL USUARIO CAMBIA A LA CUENTA, asi que ya recoge los cambios a mitad
      // de escenario. loginConfirmado dice si ademas hubo un login real con ella
      // (GestionCuenta lo registra); si el escenario murio antes de loguearse, el dato
      // sigue siendo el que se iba a usar, pero queda marcado como no confirmado.
      String cuentaId = CuentaManager.getIdCuentaActiva();
      String ultimaLogueada = CuentaManager.getUltimaCuentaLogueada();
      boolean loginConfirmado = ultimaLogueada != null && ultimaLogueada.equals(cuentaId);

      User cuenta = CuentaManager.getCuenta(cuentaId);

      List<String> tags = new ArrayList<>();
      if (scenario != null && scenario.getSourceTagNames() != null) {
        tags.addAll(scenario.getSourceTagNames());
      }

      Map<String, Object> datos = new LinkedHashMap<>();
      datos.put("escenario", scenario == null ? null : scenario.getName());
      datos.put("tags", tags);
      datos.put("cuenta", cuentaId);
      datos.put("correo", cuenta == null ? null : cuenta.getEmail());
      datos.put("linea", cuenta == null ? null : cuenta.getNumero());
      datos.put("documento", cuenta == null ? null : cuenta.getCedula());
      datos.put("usuario", cuenta == null ? null : cuenta.getNombreUsuario());
      datos.put("loginConfirmado", loginConfirmado);
      datos.put("resultado", scenario != null && scenario.isFailed() ? "FAILED" : "PASSED");
      datos.put("registradoEn", LocalDateTime.now().toString());

      escribir(datos);

      System.out.println(
          "[ContextoST] Escenario registrado | cuenta="
              + cuentaId
              + " | correo="
              + datos.get("correo")
              + " | linea="
              + datos.get("linea")
              + (loginConfirmado ? "" : " (sin login confirmado)"));

    } catch (Exception e) {
      System.err.println("[ContextoST] No se pudo registrar el contexto del escenario: " + e);
    }
  }

  private static void escribir(Map<String, Object> datos) throws Exception {
    Path carpeta = Paths.get(CARPETA);
    Files.createDirectories(carpeta);
    String nombre =
        System.currentTimeMillis() + "-" + UUID.randomUUID().toString().substring(0, 8) + ".json";
    File destino = carpeta.resolve(nombre).toFile();
    new ObjectMapper().writerWithDefaultPrettyPrinter().writeValue(destino, datos);
  }
}
