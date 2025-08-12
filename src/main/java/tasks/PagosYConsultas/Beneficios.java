package tasks.PagosYConsultas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.BTN_TRES_PUNTOS_MAS;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

public class Beneficios implements Task {
  private static final String paso1 = "Hacer clic en Beneficios";
  private static final String paso2 = "Verificar versión de miniprograma";
  private static final String paso3 = "Verificar direccionamiento correcto";

  @Override
  public <T extends Actor> void performAs(T actor) {

    actor.attemptsTo(ScrollHastaTexto.conTexto(BENEFICIOS));

    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(BENEFICIOS), WaitForResponse.withText(PERSONAS));

    actor.attemptsTo(
        Click.on(BTN_TRES_PUNTOS_MAS),
        ClickTextoQueContengaX.elTextoContiene(ACERCA_DE),
        WaitForResponse.withText(DECLARACION_SERVICIO),
        ValidarTexto.validarTexto(BENEFICIOS),
        ValidarTexto.validarTexto(DECLARACION_SERVICIO),
        ValidarTextoQueContengaX.elTextoContiene(VER));

    EvidenciaUtils.registrarCaptura(paso2);

    actor.attemptsTo(Atras.irAtras());

    EvidenciaUtils.registrarCaptura(paso3);

    actor.attemptsTo(
        ValidarTexto.validarTexto(BENEFICIOS_CLARO),
        ValidarTextoQueContengaX.elTextoContiene(DESCUBRE_BENEFICIOS),
        ValidarTextoQueContengaX.elTextoContiene(LOGO_CLARO));
  }

  public static Performable validarDireccionamiento() {
    return instrumented(Beneficios.class);
  }
}
