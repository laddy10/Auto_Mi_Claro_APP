package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import utils.EvidenciaUtils;

public class DesactivarServicioRoaming implements Task {
  private static final int MAX_INTENTOS = 3;
  static final By MENSAJE_ERROR_DESACTIVACION =
      By.xpath(
          "//*[contains(translate(@text,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'no se pud')"
              + " or contains(translate(@content-desc,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'no se pud')"
              + " or contains(translate(@text,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'su solicitud no pudo ser procesada')"
              + " or contains(translate(@content-desc,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'su solicitud no pudo ser procesada')]");

  @Override
  public <T extends Actor> void performAs(T actor) {
    try (RoamingScreen screen = new RoamingScreen(actor)) {
      if (!screen.state()) {
        throw new IllegalStateException("Roaming debe estar activo antes de probar la desactivacion");
      }
      desactivar(screen);
    }
  }

  public static void desactivar(RoamingScreen screen) {
    for (int intento = 1; intento <= MAX_INTENTOS; intento++) {
      // Una respuesta tardia puede apagar el servicio antes del siguiente intento.
      if (!screen.state()) {
        screen.waitUntilClear(MENSAJE_ERROR_DESACTIVACION);
        if (screen.waitUntilOff(MENSAJE_ERROR_DESACTIVACION)) {
          EvidenciaUtils.registrarCaptura("Servicio Roaming desactivado");
          return;
        }
        throw new IllegalStateException("No se confirma Roaming apagado; no se vuelve a activar");
      }
      EvidenciaUtils.registrarCaptura("Desactivar Roaming - intento " + intento);
      screen.tapSwitch();
      screen.tapText("Desactivar");
      boolean off;
      try {
        off = screen.waitUntilOff(MENSAJE_ERROR_DESACTIVACION);
      } catch (TimeoutException e) {
        EvidenciaUtils.registrarCaptura("Tiempo agotado esperando desactivacion - intento " + intento);
        off = false;
      }
      if (off) {
        EvidenciaUtils.registrarCaptura("Servicio Roaming desactivado");
        return;
      }
      EvidenciaUtils.registrarCaptura("Solicitud de desactivacion no procesada - intento " + intento);
      if (intento < MAX_INTENTOS) {
        screen.waitUntilClear(MENSAJE_ERROR_DESACTIVACION);
      }
    }
    throw new IllegalStateException("No se pudo desactivar Roaming despues de " + MAX_INTENTOS + " intentos");
  }

  public static Performable desactivarServicioRoaming() {
    return instrumented(DesactivarServicioRoaming.class);
  }
}
