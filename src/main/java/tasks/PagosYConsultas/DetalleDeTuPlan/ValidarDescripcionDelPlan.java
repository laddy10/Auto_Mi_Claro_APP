package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class ValidarDescripcionDelPlan implements Task {

  private static final String paso1 = "Hacer clic en Descripción del plan";
  private static final String paso2 = "Validar información de la descripción del plan";

  @Override
  public <T extends Actor> void performAs(T actor) {

    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(DESCRIPCION_DEL_PLAN),
        ScrollHastaTexto.conTexto(FAMILIA_Y_AMIGOS));

    EvidenciaUtils.registrarCaptura(paso2);

    actor.attemptsTo(
        // Validar elementos comunes que aparecen en todas las descripciones
        ValidarTextoQueContengaX.elTextoContiene(CARGO_FIJO_MENSUAL_TEXTO),
        ValidarTextoQueContengaX.elTextoContiene(INCLUYE_TEXTO),
        ValidarTextoQueContengaX.elTextoContiene(SMS_ILIMITADO_TEXTO),
        ValidarTextoQueContengaX.elTextoContiene(LLAMADAS_TEXTO),
        ValidarTextoQueContengaX.elTextoContiene(NO_INCLUYE_TEXTO),
        ValidarTextoQueContengaX.elTextoContiene(ROAMING_TEXTO),
        ValidarTextoQueContengaX.elTextoContiene(INFO_WWW_CLARO));
  }

  public static Performable validarInformacionDescripcion() {
    return instrumented(ValidarDescripcionDelPlan.class);
  }
}
