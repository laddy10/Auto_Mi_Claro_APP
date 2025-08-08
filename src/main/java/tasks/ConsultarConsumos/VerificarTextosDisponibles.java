package tasks.ConsultarConsumos;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class VerificarTextosDisponibles implements Task {

  private static final String paso = "Verificar Textos Disponibles";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        ValidarTexto.validarTexto(DETALLE_DE_CONSUMOS),
        ValidarTexto.validarTexto(TU_PLAN_INF),
        // ValidarTexto.validarTexto(CONECTADOS_25_V_73_SALUD),
        // ValidarTexto.validarTexto(GB_PLAN_160),
        ValidarTextoQueContengaX.elTextoContiene("Fecha inicio:"),
        ValidarTextoQueContengaX.elTextoContiene("Fecha carga:"),
        ValidarTexto.validarTexto(CONSUMO_DE_DATOS),
        ValidarTexto.validarTexto(APPS_SIN_LIMITE_CONSUMO),
        ValidarTexto.validarTexto(CONSUMO_DE_VOZ),
        ValidarTexto.validarTexto(CONSUMO_DE_SMS),
        ValidarTexto.validarTexto(CONSUMO_PAQUETES_Y_RECARGAS),
        ValidarTexto.validarTexto(COMPRAR_PAQUETES_Y_RECARGAS));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable verificar() {
    return instrumented(VerificarTextosDisponibles.class);
  }
}
