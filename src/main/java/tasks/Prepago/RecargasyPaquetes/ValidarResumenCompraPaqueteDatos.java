package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;
import static utils.ConstantsPaquetes.*;

import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class ValidarResumenCompraPaqueteDatos implements Task {

  private static final String PASO = "Validar resumen de la compra con información del plan";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(PASO);

    actor.attemptsTo(
        ValidarTexto.validarTexto(RESUMEN_COMPRA),
        ValidarTexto.validarTexto(VIGENCIA_LABEL),
        ValidarTexto.validarTexto(VIGENCIA_7_DIAS),
        ValidarTextoQueContengaX.elTextoContiene(DATOS),
        ValidarTexto.validarTexto(DATOS_2_GB),
        ValidarTextoQueContengaX.elTextoContiene(TOTAL),
        ValidarTexto.validarTexto(PRECIO_$9000));
  }

  public static Performable validar() {
    return instrumented(ValidarResumenCompraPaqueteDatos.class);
  }
}
