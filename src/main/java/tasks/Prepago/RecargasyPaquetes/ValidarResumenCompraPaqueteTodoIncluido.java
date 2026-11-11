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

public class ValidarResumenCompraPaqueteTodoIncluido implements Task {

  private static final String PASO = "Validar resumen de la compra paquete Todo Incluido 7 días Win Sports";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(PASO);

    actor.attemptsTo(
        ValidarTexto.validarTexto(RESUMEN_COMPRA),
        ValidarTexto.validarTexto(VIGENCIA_LABEL),
        ValidarTexto.validarTexto(VIGENCIA_7_DIAS));

    try {
      actor.attemptsTo(ValidarTexto.validarTexto(PRECIO_$15000));
    } catch (Exception e) {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("15.000"));
    }
  }

  public static Performable validar() {
    return instrumented(ValidarResumenCompraPaqueteTodoIncluido.class);
  }
}
