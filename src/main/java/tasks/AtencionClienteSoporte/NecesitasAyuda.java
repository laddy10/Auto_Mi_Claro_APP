package tasks.AtencionClienteSoporte;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class NecesitasAyuda implements Task {

  private static final String paso1 = "Ingresar a ¿Necesitas ayuda?";

  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(NECESITAS_AYUDA),
       WaitForResponse.withText(POSTPAGO));
  }

  public static Performable ingresar() {
    return instrumented(NecesitasAyuda.class);
  }

  public static class SeleccionarLineaYVerDetalle implements Task {
    private final User user = TestDataProvider.getRealUser();

    @Override
    public <T extends Actor> void performAs(T actor) {
      EvidenciaUtils.registrarCaptura("Seleccionar línea y Continuar - Necesitas ayuda");

      AndroidObject.scrollCorto2(actor, LINEA + " " + user.getNumero() + " " + CONTINUAR);

      actor.attemptsTo(
          ClickTextoQueContengaX.elTextoContiene(user.getNumero()),
          WaitForResponse.withAnyText(CLAROBOT));
    }

    public static Performable ejecutar() {
      return instrumented(SeleccionarLineaYVerDetalle.class);
    }
  }

  public static class VerificarRedireccionClarobot implements Task {
    @Override
    public <T extends Actor> void performAs(T actor) {
      EvidenciaUtils.registrarCaptura("Verificar redirección a ClaroBot");

      actor.attemptsTo(
          ValidarTextoQueContengaX.elTextoContiene(CLAROBOT),
          ValidarTextoQueContengaX.elTextoContiene(MUNDO_CLARO),
          ValidarTextoQueContengaX.elTextoContiene(LO_MAS_CONSULTADO));
    }

    public static Performable ejecutar() {
      return instrumented(VerificarRedireccionClarobot.class);
    }
  }

  public static Performable seleccionarLineaYVerDetalle() {
    return SeleccionarLineaYVerDetalle.ejecutar();
  }

  public static Performable verificarRedireccionClarobot() {
    return VerificarRedireccionClarobot.ejecutar();
  }
}
