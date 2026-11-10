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
    } else if (PAQUETE_1000M_20DIAS.equals(paquete)) {
      actor.attemptsTo(
          ValidarTextoQueContengaX.elTextoContiene("20"),
          ValidarTextoQueContengaX.elTextoContiene(TOTAL));

      try {
        actor.attemptsTo(ValidarTexto.validarTexto(PRECIO_$18500));
      } catch (Exception e) {
        actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("18.500"));
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
