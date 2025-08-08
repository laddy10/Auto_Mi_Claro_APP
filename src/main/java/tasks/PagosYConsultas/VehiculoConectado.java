package tasks.PagosYConsultas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.BTN_TRES_PUNTOS_MAS;
import static userinterfaces.PagosYConsultasPage.LBL_INFORMACION_IMPORTANTE;
import static utils.Constants.*;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import java.util.List;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

public class VehiculoConectado implements Task {

  private static final String paso1 = "Hacer clic en Vehículo conectado";
  private static final String paso2 = "Validar mini versión Vehivulo conectado";
  private static final String paso3 = "Validar direccionamiento correcto carro conectado";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // 1. Hacer scroll hasta encontrar Vehículo conectado
    actor.attemptsTo(ScrollHastaTexto.conTexto(VEHICULO_CONECTADO));

    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(VEHICULO_CONECTADO),
        WaitForResponse.withText(PERSONAS));

    List<WebElementFacade> lblinfoimportante = LBL_INFORMACION_IMPORTANTE.resolveAllFor(actor);
    if (!lblinfoimportante.isEmpty()) {
      actor.attemptsTo(ClickElementByText.clickElementByText(ENTENDIDO));
    }

    // 2. Validar versión de miniprograma
    actor.attemptsTo(
        Click.on(BTN_TRES_PUNTOS_MAS),
        ClickTextoQueContengaX.elTextoContiene(ACERCA_DE),
        WaitForResponse.withText(DECLARACION_SERVICIO),
        ValidarTexto.validarTexto(VEHICULO_CONECTADO),
        ValidarTexto.validarTexto(DECLARACION_SERVICIO),
        ValidarTextoQueContengaX.elTextoContiene(VER));

    EvidenciaUtils.registrarCaptura(paso2);

    actor.attemptsTo(Atras.irAtras());

    // 3. Validar direccionamiento correcto del carro conectado
    actor.attemptsTo(
        ValidarTextoQueContengaX.elTextoContiene(CARRO_CONECTADO),
        ValidarTextoQueContengaX.elTextoContiene(SE_EL_PRIMERO_EN_TENER),
        ValidarTextoQueContengaX.elTextoContiene(REGISTRATE_Y_RECIBE_INFORMACION));

    EvidenciaUtils.registrarCaptura(paso3);
  }

  public static Performable validarDireccionamiento() {
    return instrumented(VehiculoConectado.class);
  }
}
