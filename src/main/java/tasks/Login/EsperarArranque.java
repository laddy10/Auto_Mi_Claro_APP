package tasks.Login;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.PantallaRapida.tiene;
import static utils.PantallaRapida.tieneId;
import static utils.PantallaRapida.tieneTexto;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.thucydides.core.annotations.Step;
import utils.PantallaRapida;

/**
 * Espera a que Mi Claro termine la pantalla de carga. Termina en cuanto se ve una pantalla conocida
 * (inicio, ingreso, un aviso o un permiso) o, si no se reconoce ninguna, cuando la carga lleva unos
 * segundos sin verse. No falla: lo que haya en pantalla lo atiende el ingreso.
 */
public class EsperarArranque implements Task {

  private static final long MAXIMO_MS = 45_000L;
  private static final long SIN_CARGA_MS = 3_000L;

  @Override
  @Step("{0} espera a que cargue Mi Claro")
  public <T extends Actor> void performAs(T actor) {
    PantallaRapida pantalla = PantallaRapida.de(actor);
    pantalla.sinEsperaImplicita();
    try {
      esperar(pantalla);
    } finally {
      pantalla.restaurarEsperaImplicita();
    }
  }

  private void esperar(PantallaRapida pantalla) {
    long inicio = System.currentTimeMillis();
    long fin = inicio + MAXIMO_MS;
    long sinCargaDesde = -1;
    while (System.currentTimeMillis() < fin) {
      String xml = pantalla.fuente();
      long ahora = System.currentTimeMillis();
      if (xml.isEmpty() || cargando(xml)) {
        sinCargaDesde = -1;
      } else if (pantallaConocida(xml)) {
        break;
      } else if (sinCargaDesde < 0) {
        sinCargaDesde = ahora;
      } else if (ahora - sinCargaDesde >= SIN_CARGA_MS) {
        break;
      }
      PantallaRapida.dormir(400);
    }
    System.out.println(
        "[EsperarArranque] "
            + (System.currentTimeMillis() - inicio) / 1000.0
            + " s hasta que cargó la app");
  }

  private static boolean cargando(String xml) {
    return tieneId(xml, "imgLoadingSplash")
        || tieneId(xml, "animation_splash_lottie")
        || tiene(xml, "Espera un momento");
  }

  private static boolean pantallaConocida(String xml) {
    return tieneId(xml, "iv_menu")
        || tieneId(xml, "tv_login")
        || tieneId(xml, "relogin_account")
        || tieneId(xml, "tv_login_entry_other_type")
        || tieneId(xml, "bt_top")
        || tieneId(xml, "iv_protocol")
        || tieneId(xml, "permission_message")
        || tieneId(xml, "permission_deny_button")
        || tieneTexto(xml, "Omitir", "OMITIR");
  }

  public static Performable deLaApp() {
    return instrumented(EsperarArranque.class);
  }
}
