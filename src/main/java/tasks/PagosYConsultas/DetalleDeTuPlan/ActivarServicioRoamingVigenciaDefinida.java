package tasks.PagosYConsultas.DetalleDeTuPlan;

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
import utils.AndroidObject;
import utils.EvidenciaUtils;

public class ActivarServicioRoamingVigenciaDefinida implements Task {
  private static final String NUMERO_LINEA = "310 859 8508";
  private static final String paso1 = "Hacer clic en Activar Roaming";
  private static final String paso2 = "Seleccionar línea para administrar Roaming " + NUMERO_LINEA;
  private static final String paso3 = "Activar Roaming con vigencia definida";
  //private static final String paso4 = "Desactivar Roaming";


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
            TocarSwitchWebView.junto("Servicio Roaming"),
            ClickTextoQueContengaX.elTextoContiene(ACTIVAR_POR_TIEMPO_DEFINIDO),
            TocarElementoVentanaWebView.de("Aceptar", "ELIGE LA FECHA FINAL", ""),   // solo valida que el calendario se cierre
            WaitForResponse.withAnyText(PAQUETE_ACTIVADO2),                          // espera la respuesta del servidor, igual que en indefinido
            TocarBotonSecundarioModalWebView.de("Elige tu paquete", "Ahorra comprando un paquete"),
            TocarElementoVentanaWebView.de("Continuar", "Ahorra comprando", "activado exitosamente"),
            TocarElementoVentanaWebView.de("Aceptar", "activado exitosamente", "")
    );

    /*EvidenciaUtils.registrarCaptura(paso4);
    actor.attemptsTo(
            WaitFor.aTime(20000),
            TocarSwitchWebView.junto("Servicio Roaming"),
            TocarElementoVentanaWebView.de("Desactivar", "Deseas desactivar", "Solicitud procesada exitosamente")
    );*/
  }

  public static Performable activarServicioRoamingVigenciaDefinida() {
    return instrumented(ActivarServicioRoamingVigenciaDefinida.class);
  }
}
