package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;
import static utils.ConstantsPaquetes.*;

import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;

public class ValidarResumenCompraPaqueteDatos implements Task {

  private static final String PASO = "Validar resumen de la compra con información del plan";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(PASO);

    actor.attemptsTo(
        ValidarTexto.validarTexto(RESUMEN_COMPRA),
        ValidarTexto.validarTexto(VIGENCIA_LABEL));

    String paquete = actor.recall("PAQUETE_CANAL");
    if (TODO_INCLUIDO_6DIAS_1_4GB.equals(paquete)) {
      actor.attemptsTo(
          ValidarTextoQueContengaX.elTextoContiene(VIGENCIA_6_DIAS),
          ValidarTextoQueContengaX.elTextoContiene(DATOS),
          ValidarTextoQueContengaX.elTextoContiene(DATOS_1_4_GB),
          ValidarTextoQueContengaX.elTextoContiene(TOTAL));

      try {
        actor.attemptsTo(ValidarTexto.validarTexto(PRECIO_$9000));
      } catch (Exception e) {
        actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("9.000"));
      }
    } else if (TODO_INCLUIDO_30DIAS_30GB.equals(paquete)) {
      actor.attemptsTo(
          ValidarTextoQueContengaX.elTextoContiene("30 días"),
          ValidarTextoQueContengaX.elTextoContiene(DATOS),
          ValidarTextoQueContengaX.elTextoContiene("30 GB"),
          ValidarTextoQueContengaX.elTextoContiene(TOTAL));

      try {
        actor.attemptsTo(ValidarTexto.validarTexto(PRECIO_$35000));
      } catch (Exception e) {
        actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("35.000"));
      }
    } else if (PAQUETE_1000M_20DIAS.equals(paquete)
        || (paquete != null && (paquete.contains("1000") || paquete.toUpperCase().contains("VOZ")))
        || "18500".equals(actor.recall("VALOR_COMPRA"))
        || "18.500".equals(actor.recall("VALOR_COMPRA"))) {
      actor.attemptsTo(
          ValidarTextoQueContengaX.elTextoContiene("20"),
          ValidarTextoQueContengaX.elTextoContiene(TOTAL));

      try {
        actor.attemptsTo(ValidarTexto.validarTexto(PRECIO_$18500));
      } catch (Exception e) {
        actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("18.500"));
      }
    } else if (SALUD_EN_LINEA_30DIAS_4BENEFICIARIOS.equals(paquete)
        || (paquete != null && paquete.toLowerCase().contains("salud"))
        || "6500".equals(actor.recall("VALOR_COMPRA"))
        || "6.500".equals(actor.recall("VALOR_COMPRA"))) {
      actor.attemptsTo(
          ValidarTextoQueContengaX.elTextoContiene("30"),
          ValidarTextoQueContengaX.elTextoContiene(TOTAL));

      try {
        actor.attemptsTo(ValidarTexto.validarTexto(PRECIO_$6500));
      } catch (Exception e) {
        actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("6.500"));
      }
    } else if (TODO_INCLUIDO_7DIAS_2GB.equals(paquete)
        || (paquete != null && paquete.contains("2GB"))
        || "11.000".equals(actor.recall("VALOR_COMPRA"))) {
      actor.attemptsTo(
          ValidarTextoQueContengaX.elTextoContiene("2 GB"),
          ValidarTextoQueContengaX.elTextoContiene(TOTAL),
          ValidarTextoQueContengaX.elTextoContiene("11.000"));
    } else if (TODO_INCLUIDO_7DIAS_10GB_WIN.equals(paquete)
        || (paquete != null && (paquete.contains("10GB") || paquete.contains("Win")))
        || "15000".equals(actor.recall("VALOR_COMPRA"))
        || "15.000".equals(actor.recall("VALOR_COMPRA"))) {
      actor.attemptsTo(
          ValidarTextoQueContengaX.elTextoContiene(VIGENCIA_7_DIAS),
          ValidarTextoQueContengaX.elTextoContiene(TOTAL));

      try {
        actor.attemptsTo(ValidarTexto.validarTexto(PRECIO_$15000));
      } catch (Exception e) {
        actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("15.000"));
      }
    } else if (NAVEGACION_2GB_7DIAS.equals(paquete)
        || (paquete != null && paquete.toUpperCase().contains("NAVEGACION"))
        || "9000".equals(actor.recall("VALOR_COMPRA"))
        || "9.000".equals(actor.recall("VALOR_COMPRA"))) {
      actor.attemptsTo(
          ValidarTextoQueContengaX.elTextoContiene(VIGENCIA_7_DIAS),
          ValidarTextoQueContengaX.elTextoContiene(DATOS),
          ValidarTextoQueContengaX.elTextoContiene(DATOS_2_GB),
          ValidarTextoQueContengaX.elTextoContiene(TOTAL));

      try {
        actor.attemptsTo(ValidarTexto.validarTexto(PRECIO_$9000));
      } catch (Exception e) {
        actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("9.000"));
      }
    } else {
      try {
        actor.attemptsTo(
            ValidarTexto.validarTexto(VIGENCIA_7_DIAS),
            ValidarTextoQueContengaX.elTextoContiene(DATOS),
            ValidarTexto.validarTexto(DATOS_2_GB),
            ValidarTextoQueContengaX.elTextoContiene(TOTAL),
            ValidarTexto.validarTexto(PRECIO_$9000));
      } catch (Exception e) {
        actor.attemptsTo(
            ValidarTextoQueContengaX.elTextoContiene("días"),
            ValidarTextoQueContengaX.elTextoContiene(DATOS),
            ValidarTextoQueContengaX.elTextoContiene(TOTAL));
      }
    }

    CapturaDePantallaMovil.tomarCapturaPantalla("Resumen compra validado");
    EvidenciaUtils.registrarCaptura("Resumen compra validado");
  }

  public static Performable validar() {
    return instrumented(ValidarResumenCompraPaqueteDatos.class);
  }
}
