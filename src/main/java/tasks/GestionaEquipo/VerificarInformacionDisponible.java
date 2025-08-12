package tasks.GestionaEquipo;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.validations.ValidarTexto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

/** Task para verificar información disponible en actualizar datos */
public class VerificarInformacionDisponible implements Task {

  private static final String paso = "Verificar Información Disponible";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        ValidarTexto.validarTexto(IMEI_LABEL),
        ValidarTexto.validarTexto(MARCA_LABEL),
        ValidarTexto.validarTexto(MODELO_LABEL));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable verificar() {
    return instrumented(VerificarInformacionDisponible.class);
  }
}
