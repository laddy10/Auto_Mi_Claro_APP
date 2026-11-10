package tasks.Prepago.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosyConsultasPrePage.BTN_COMPRAR_2;
import static userinterfaces.PagosyConsultasPrePage.LBL_VER_DETALLE_2;
import static utils.Constants.*;
import static utils.ConstantsPaquetes.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.AndroidObject;
import utils.CapturaDePantallaMovil;
import utils.EvidenciaUtils;
import utils.FormateadorValor;

public class TodoIncluido30DiasMinIlim30GBNoIncluyeRedes extends AndroidObject implements Task {

  private final String precioTexto = PRECIO_$35000;
  private final String valorFormateado = FormateadorValor.aFormatoCmax(precioTexto);

  @Override
  public <T extends Actor> void performAs(T actor) {

    actor.attemptsTo(
        Scroll.scrollUnaVista(),
        Scroll.scrollUnaVista(),
        Scroll.scrollUnaVista());

    try {
      actor.attemptsTo(ValidarTexto.validarTexto(precioTexto));
    } catch (Exception e) {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("35.000"));
    }

    try {
      actor.attemptsTo(Click.on(LBL_VER_DETALLE_2));
    } catch (Exception e) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(VER_DETALLE_DEL_PAQUETE));
    }

    try {
      actor.attemptsTo(ValidarTexto.validarTexto(TODO_INCLUIDO_30DIAS_30GB));
    } catch (Exception e) {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("30 GB de navegacion"));
    }

    actor.remember("VALOR_COMPRA", valorFormateado);
    actor.remember("PAQUETE_CANAL", TODO_INCLUIDO_30DIAS_30GB);

    CapturaDePantallaMovil.tomarCapturaPantalla("captura_pantalla");
    EvidenciaUtils.registrarCaptura("Detalle paquete Todo Incluido 30 días 30GB validado");

    try {
      actor.attemptsTo(Click.on(BTN_COMPRAR_2));
    } catch (Exception e) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(COMPRAR));
    }

    actor.attemptsTo(WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO));

    try {
      actor.attemptsTo(ValidarTexto.validarTexto(precioTexto));
    } catch (Exception e) {
      actor.attemptsTo(ValidarTextoQueContengaX.elTextoContiene("35.000"));
    }

    EvidenciaUtils.registrarCaptura("Redirección a elegir medio de pago con precio $35.000");
  }

  public static Performable todoIncluido30DiasMinIlim30GBNoIncluyeRedes() {
    return instrumented(TodoIncluido30DiasMinIlim30GBNoIncluyeRedes.class);
  }
}
