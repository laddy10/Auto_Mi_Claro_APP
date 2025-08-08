package tasks.PagosYConsultas.eSIM;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class ValidarOpcionesEsim implements Task {

  private static final String paso1 = "Presionar botón Continuar";
  private static final String paso2 = "Validar direccionamiento opciones eSIM";

  @Override
  public <T extends Actor> void performAs(T actor) {

    EvidenciaUtils.registrarCaptura(paso2);

    actor.attemptsTo(
        ValidarTextoQueContengaX.elTextoContiene(CAMBIATE_A_ESIM_DISFRUTA),
        // ValidarTextoQueContengaX.elTextoContiene(RECOMENDACIONES_PARA_INSTALAR_ESIM),
        ValidarTextoQueContengaX.elTextoContiene(ELIGE_EL_CELULAR_DONDE_VAS),
        ValidarTextoQueContengaX.elTextoContiene(SELECCIONA_LA_MARCA_CELULAR),
        ValidarTextoQueContengaX.elTextoContiene(SELECCIONA_EL_MODELO_CELULAR),
        ValidarTextoQueContengaX.elTextoContiene(CAMBIARME_A_ESIM),
        ValidarTextoQueContengaX.elTextoContiene(QUE_ES_LA_ESIM_CLARO));
  }

  public static Performable validarOpciones() {
    return instrumented(ValidarOpcionesEsim.class);
  }
}
