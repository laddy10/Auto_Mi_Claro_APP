package tasks.GestionaEquipo;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

public class RegistrarEquipo implements Task {

  private static final String paso = "Registrar Equipo";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(REGISTRAR_EQUIPO),
        WaitFor.aTime(2000),
        ValidarTexto.validarTexto(REGISTRAR_EQUIPO),
        ValidarTextoQueContengaX.elTextoContiene(RECUERDA_TENER_SIM_CARD),
        ValidarTexto.validarTexto(REGISTRAR),
        ValidarTexto.validarTexto(CANCELAR),
        ClickElementByText.clickElementByText(REGISTRAR),
        WaitFor.aTime(3000),
        ValidarTextoQueContengaX.elTextoContiene(EQUIPO_REGISTRADO),
        ClickElementByText.clickElementByText(CERRAR));

    EvidenciaUtils.registrarCaptura(paso);
  }

  public static Performable registrar() {
    return instrumented(RegistrarEquipo.class);
  }
}
