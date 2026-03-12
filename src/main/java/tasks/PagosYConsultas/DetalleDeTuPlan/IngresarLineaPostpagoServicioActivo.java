package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.LBL_LINEA_VER_DETALLE;
import static utils.Constants.*;

import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class IngresarLineaPostpagoServicioActivo implements Task {

  private static final User user = TestDataProvider.getRealUser();
  private static final String paso1 =
      "Seleccionar línea postpago " + user.getNumeroFamiliayAmigos();

  @Override
  public <T extends Actor> void performAs(T actor) {

    AndroidObject.scrollCorto2(
        actor, LINEA + " " + user.getNumeroFamiliayAmigos() + " " + VER_DETALLE);

    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        Click.on(LBL_LINEA_VER_DETALLE),
        // ClickElementByText.clickElementByText(LINEA + " " + user.getNumeroFamiliayAmigos() + " "
        // + VER_DETALLE),
        // ClickTextoQueContengaX.elTextoContiene(user.getNumeroFamiliayAmigos()),
        WaitForResponse.withAnyText(
            FAMILIA_Y_AMIGOS_OPCION, ADELANTA_SALDO_LABEL, COMPRA_POR_CATEGORIA, CLARO_COLOMBIA));
  }

  public static Performable ingresarLineaPostpagoServicioActivo() {
    return instrumented(IngresarLineaPostpagoServicioActivo.class);
  }
}
