package tasks.ConsultarConsumos;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class VerificarInformacionPaquetesRecargas implements Task {

  private static final String paso = "Verificar Información Paquetes y Recargas";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        ValidarTexto.validarTexto(PAQUETES),
        ValidarTexto.validarTexto(RECARGAS),
        ValidarTexto.validarTexto(CONSUMO_DEL_MES),
        ValidarTextoQueContengaX.elTextoContiene(HAZ_RECARGA_RENOVAR_VIGENCIA),
        ValidarTexto.validarTexto(SALDO_MES_ANTERIOR),
        ValidarTexto.validarTexto(JUNIO_2025),
        ValidarTexto.validarTexto(VER_DETALLE),
        ValidarTexto.validarTexto(OTROS));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable verificar() {
    return instrumented(VerificarInformacionPaquetesRecargas.class);
  }
}
