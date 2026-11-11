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

public class ValidarResumenCompraPaqueteApps implements Task {

  private static final String PASO = "Validar resumen de la compra paquete de apps";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(PASO);

    actor.attemptsTo(
        ValidarTexto.validarTexto(RESUMEN_COMPRA),
        ValidarTextoQueContengaX.elTextoContiene(VIGENCIA_LABEL),
        ValidarTextoQueContengaX.elTextoContiene("30"),
        ValidarTextoQueContengaX.elTextoContiene(TOTAL),
        ValidarTextoQueContengaX.elTextoContiene("6.500"));

    CapturaDePantallaMovil.tomarCapturaPantalla("Resumen de compra paquete de apps validado");
    EvidenciaUtils.registrarCaptura("Resumen de compra paquete de apps validado con precio $6.500");
  }

  public static Performable validar() {
    return instrumented(ValidarResumenCompraPaqueteApps.class);
  }
}
