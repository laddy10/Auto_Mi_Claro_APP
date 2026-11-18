package tasks.PagosYConsultas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

import interactions.Click.*;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.AndroidObject;
import utils.EvidenciaUtils;

public class ActivarServicioRoaming implements Task {
  private static final String NUMERO_LINEA = "310 263 5965";
  private static final String paso1 = "Hacer clic en Activar Roaming";
  private static final String paso2 = "Seleccionar línea para administrar Roaming " + NUMERO_LINEA;
  private static final String paso3 = "Activar Roaming";
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
            ClickTextoQueContengaX.elTextoContiene(ACTIVAR_POR_TIEMPO_INDEFINIDO),
            WaitForResponse.withAnyText(PAQUETE_ACTIVADO2),
            TocarBotonSecundarioModalWebView.de("Elige tu paquete", "Ahorra comprando un paquete"),
            TocarElementoVentanaWebView.de("Continuar", "Ahorra comprando", "fue activado exitosamente"),
            TocarElementoVentanaWebView.de("Aceptar", "fue activado exitosamente", "")   // reemplaza ClickTexto(ACEPTAR_2)
    );

    /*EvidenciaUtils.registrarCaptura(paso4);
    actor.attemptsTo(
            WaitFor.aTime(20000),
            TocarSwitchWebView.junto("Servicio Roaming"),
            TocarElementoVentanaWebView.de("Desactivar", "Deseas desactivar", "Solicitud procesada exitosamente")
    );*/
  }

  public static Performable activarServicioRoaming() {
    return instrumented(ActivarServicioRoaming.class);
  }
}
