package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.MediosPagoPage.BTN_SELECCIONAR_DAVIPLATA;
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

public class ElegirMedioPagoDaviplata implements Task {

  private static final String PASO = "Elegir medio de pago: Daviplata";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(PASO);

    try {
      actor.attemptsTo(
          WaitFor.aTime(2000),
          Click.on(BTN_SELECCIONAR_DAVIPLATA));
    } catch (Exception e) {
      actor.attemptsTo(
          ClickTextoQueContengaX.elTextoContiene(DAVIPLATA));
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
    } else if (PAQUETE_1000M_20DIAS.equals(paquete)
        || (paquete != null && (paquete.contains("1000") || paquete.toUpperCase().contains("VOZ")))
        || (valor != null && valor.contains("18500"))) {
      actor.attemptsTo(ValidarResumenCompraPaqueteVoz.validar());
    } else if ((valor != null && (valor.contains("25.000") || valor.contains("25000")))
        || (paquete != null && (paquete.contains("7.5") || paquete.contains("20GB") || paquete.contains("20 GB")))) {
      String vigenciaEsperada = (paquete != null && (paquete.contains("20GB") || paquete.contains("20 GB") || paquete.contains("15"))) ? "15" : "20";
      actor.attemptsTo(
          ValidarTexto.validarTexto(RESUMEN_COMPRA),
          ValidarTextoQueContengaX.elTextoContiene(VIGENCIA_LABEL),
          ValidarTextoQueContengaX.elTextoContiene(vigenciaEsperada),
          ValidarTextoQueContengaX.elTextoContiene(TOTAL),
          ValidarTextoQueContengaX.elTextoContiene("25.000"));
      CapturaDePantallaMovil.tomarCapturaPantalla("Resumen de compra paquete Todo Incluido validado");
      EvidenciaUtils.registrarCaptura("Resumen de compra paquete Todo Incluido validado con precio $25.000 y vigencia " + vigenciaEsperada + " días");
    } else if (SALUD_EN_LINEA_30DIAS_4BENEFICIARIOS.equals(paquete)
        || (paquete != null && (paquete.toUpperCase().contains("SALUD") || paquete.contains("4")))
        || (valor != null && (valor.contains("6500") || valor.contains("6.500")))) {
      actor.attemptsTo(ValidarResumenCompraPaqueteApps.validar());
    } else {
      actor.attemptsTo(ValidarResumenCompraPaqueteApps.validar());
    }
  }

  public static Performable elegir() {
    return instrumented(ElegirMedioPagoDaviplata.class);
  }
}
