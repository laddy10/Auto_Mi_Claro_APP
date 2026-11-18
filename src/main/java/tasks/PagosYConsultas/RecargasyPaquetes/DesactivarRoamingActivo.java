package tasks.PagosYConsultas.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Click.TocarBotonSecundarioModalWebView;
import interactions.Click.TocarElementoVentanaWebView;
import interactions.Click.TocarSwitchWebView;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import tasks.PagosYConsultas.DetalleDeTuPlan.DesactivarServicioRoaming;
import tasks.PagosYConsultas.DetalleDeTuPlan.RoamingTestLines;
import tasks.PagosYConsultas.DetalleDeTuPlan.RoamingScreen;
import utils.AndroidObject;
import utils.EvidenciaUtils;

public class DesactivarRoamingActivo implements Task {
  private static final String NUMERO_LINEA = "310 859 8508";
  private static final String paso1 = "Hacer clic en Activar Roaming";
  private static final String paso2 = "Seleccionar línea para administrar Roaming " + NUMERO_LINEA;
  private static final String paso3 = "Desactivar Roaming";


  @Override
  public <T extends Actor> void performAs(T actor) {
    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(ACTIVAR_ROAMING),
            WaitForResponse.withText(ACTIVA_ROAMING));


    EvidenciaUtils.registrarCaptura(paso2);

    // Seleccionar la línea específica del usuario
    AndroidObject.scrollCorto2(actor, LINEA_TEXTO + " " + NUMERO_LINEA + " " + ADMINISTRAR);

    actor.attemptsTo(
            ClickTextoQueContengaX.elTextoContiene(NUMERO_LINEA),
            WaitForResponse.withText(SERVICIO_ROAMING));

    EvidenciaUtils.registrarCaptura(paso3);

    actor.attemptsTo(
            WaitFor.aTime(5000),
            TocarSwitchWebView.junto("Servicio Roaming"),
            TocarElementoVentanaWebView.de("Desactivar", "Deseas desactivar", "Solicitud procesada exitosamente")
    );
  }

  public static Performable desactivarRoamingActivo() {
    return instrumented(DesactivarRoamingActivo.class);
  }
}
