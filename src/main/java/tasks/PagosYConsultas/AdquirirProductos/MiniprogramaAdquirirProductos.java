package tasks.PagosYConsultas.AdquirirProductos;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.BTN_TRES_PUNTOS_MAS;
import static utils.Constants.*;
import static utils.ConstantsMiniVersiones.Versiones.MINI_VERSION_ADQUIRIR_PRODUCTOS_CONSTANT;

import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.wait.WaitFor;

import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

public class MiniprogramaAdquirirProductos implements Task {

  private static final String paso1 = "Validar versión de miniprograma Adquirir productos";

  @Override
  public <T extends Actor> void performAs(T actor) {

    actor.attemptsTo(
        Click.on(BTN_TRES_PUNTOS_MAS),
        ClickTextoQueContengaX.elTextoContiene(ACERCA_DE),
        WaitForResponse.withText(DECLARACION_SERVICIO),
        ValidarTexto.validarTexto(ADQUIRIR_PRODUCTOS),
        ValidarTexto.validarTexto(DECLARACION_SERVICIO),
        ValidarTexto.validarTexto(MINI_VERSION_ADQUIRIR_PRODUCTOS_CONSTANT));

    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
            Atras.irAtras(),
            WaitFor.aTime(3000));
  }

  public static Performable validarVersion() {
    return instrumented(MiniprogramaAdquirirProductos.class);
  }
}
