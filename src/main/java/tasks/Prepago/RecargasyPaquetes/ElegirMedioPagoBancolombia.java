package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.MediosPagoPage.BTN_SELECCIONAR_BANCOLOMBIA;
import static utils.Constants.*;
import static utils.ConstantsPaquetes.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;

public class ElegirMedioPagoBancolombia implements Task {

  private static final String PASO = "Elegir medio de pago: Bancolombia";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(PASO);

    try {
      actor.attemptsTo(
          WaitFor.aTime(2000),
          Click.on(BTN_SELECCIONAR_BANCOLOMBIA));
    } catch (Exception e) {
      actor.attemptsTo(
          ClickTextoQueContengaX.elTextoContiene(BOTON_BANCOLOMBIA));
    }

    actor.attemptsTo(WaitForResponse.withText(RESUMEN_COMPRA), WaitFor.aTime(1500));

    String valor = actor.recall("VALOR_COMPRA");
    String paquete = actor.recall("PAQUETE_CANAL");
    if (NAVEGACION_2GB_7DIAS.equals(paquete)
        || (paquete != null && paquete.toUpperCase().contains("NAVEGACION"))
        || (valor != null && valor.contains("9000"))) {
      actor.attemptsTo(
          ValidarTexto.validarTexto(RESUMEN_COMPRA),
          ValidarTextoQueContengaX.elTextoContiene(VIGENCIA_LABEL),
          ValidarTextoQueContengaX.elTextoContiene(TOTAL),
          ValidarTextoQueContengaX.elTextoContiene("9.000"));
      CapturaDePantallaMovil.tomarCapturaPantalla("Resumen de compra paquete Navegación 2GB validado");
      EvidenciaUtils.registrarCaptura("Resumen de compra paquete Navegación 2GB validado con precio $9.000");
    } else if (TODO_INCLUIDO_7DIAS_2GB.equals(paquete)
        || (paquete != null && paquete.toUpperCase().contains("TODO_INCLUIDO") && paquete.contains("2GB"))
        || (valor != null && valor.contains("11000"))) {
      actor.attemptsTo(
          ValidarTexto.validarTexto(RESUMEN_COMPRA),
          ValidarTextoQueContengaX.elTextoContiene(VIGENCIA_LABEL),
          ValidarTextoQueContengaX.elTextoContiene(TOTAL),
          ValidarTextoQueContengaX.elTextoContiene("11.000"));
      CapturaDePantallaMovil.tomarCapturaPantalla("Resumen de compra paquete Todo Incluido 2GB validado");
      EvidenciaUtils.registrarCaptura("Resumen de compra paquete Todo Incluido 2GB validado con precio $11.000");
    } else if (TODO_INCLUIDO_7DIAS_10GB_WIN.equals(paquete)
        || (paquete != null && (paquete.contains("10GB") || paquete.contains("Win")))
        || (valor != null && valor.contains("15000"))) {
      actor.attemptsTo(
          ValidarTexto.validarTexto(RESUMEN_COMPRA),
          ValidarTextoQueContengaX.elTextoContiene(VIGENCIA_LABEL),
          ValidarTextoQueContengaX.elTextoContiene(TOTAL),
          ValidarTextoQueContengaX.elTextoContiene("15.000"));
      CapturaDePantallaMovil.tomarCapturaPantalla("Resumen de compra paquete Todo Incluido 10GB Win Sports validado");
      EvidenciaUtils.registrarCaptura("Resumen de compra paquete Todo Incluido 10GB Win Sports validado con precio $15.000");
    } else if (PAQUETE_1000M_20DIAS.equals(paquete)
        || (paquete != null && (paquete.contains("1000") || paquete.toUpperCase().contains("VOZ")))
        || (valor != null && valor.contains("18500"))) {
      actor.attemptsTo(ValidarResumenCompraPaqueteVoz.validar());
    } else if (SALUD_EN_LINEA_30DIAS_4BENEFICIARIOS.equals(paquete)
        || (paquete != null && paquete.toUpperCase().contains("SALUD"))
        || (valor != null && valor.contains("6500"))) {
      actor.attemptsTo(
          ValidarTexto.validarTexto(RESUMEN_COMPRA),
          ValidarTextoQueContengaX.elTextoContiene(VIGENCIA_LABEL),
          ValidarTextoQueContengaX.elTextoContiene("30"),
          ValidarTextoQueContengaX.elTextoContiene(TOTAL),
          ValidarTextoQueContengaX.elTextoContiene("6.500"));
      CapturaDePantallaMovil.tomarCapturaPantalla("Resumen de compra paquete Salud en línea validado");
      EvidenciaUtils.registrarCaptura("Resumen de compra paquete Salud en línea validado con precio $6.500");
    } else {
      actor.attemptsTo(ValidarResumenCompraPaqueteVoz.validar());
    }
  }

  public static Performable elegir() {
    return instrumented(ElegirMedioPagoBancolombia.class);
  }
}
