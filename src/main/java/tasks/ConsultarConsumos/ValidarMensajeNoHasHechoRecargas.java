package tasks.ConsultarConsumos;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class ValidarMensajeNoHasHechoRecargas implements Task {

  private static final String paso = "Validar Mensaje No Has Hecho Recargas";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        ValidarTextoQueContengaX.elTextoContiene(AUN_NO_HAS_HECHO_RECARGAS),
        ValidarTextoQueContengaX.elTextoContiene(HAZ_RECARGA_RENOVAR_VIGENCIA));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable validar() {
    return instrumented(ValidarMensajeNoHasHechoRecargas.class);
  }
}
