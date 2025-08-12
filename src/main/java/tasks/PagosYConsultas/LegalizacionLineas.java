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

public class LegalizacionLineas implements Task {

  private static final String paso1 = "Hacer clic en Legalización de líneas";
  private static final String paso2 = "Verificar versión de miniprograma";
  private static final String paso3 = "Verificar ingreso a formulario";

  @Override
  public <T extends Actor> void performAs(T actor) {

    // 1. Hacer scroll hasta encontrar Legalización de líneas y hacer clic
    actor.attemptsTo(ScrollHastaTexto.conTexto(LEGALIZACION_DE_LINEAS));

    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(LEGALIZACION_DE_LINEAS),
        WaitForResponse.withText(LEGALIZA_TU_CUENTA_PREPAGO));

    // 2. Verificar versión de miniprograma
    actor.attemptsTo(
        Click.on(BTN_TRES_PUNTOS_MAS),
        ClickTextoQueContengaX.elTextoContiene(ACERCA_DE),
        WaitForResponse.withText(DECLARACION_SERVICIO),
        ValidarTexto.validarTexto(LEGALIZACION_DE_LINEAS),
        ValidarTexto.validarTexto(DECLARACION_SERVICIO),
        ValidarTextoQueContengaX.elTextoContiene(VER));

    EvidenciaUtils.registrarCaptura(paso2);

    actor.attemptsTo(Atras.irAtras());

    // 3. Verificar ingreso a formulario
    EvidenciaUtils.registrarCaptura(paso3);

    actor.attemptsTo(
        ValidarTexto.validarTexto(LEGALIZA_TU_CUENTA_PREPAGO),
        ValidarTextoQueContengaX.elTextoContiene(VERIFICA_Y_ACTUALIZA_TUS_DATOS),
        ValidarTextoQueContengaX.elTextoContiene(TIPO_DE_DOCUMENTO),
        ValidarTextoQueContengaX.elTextoContiene(NUMERO_DE_DOCUMENTO),
        ValidarTextoQueContengaX.elTextoContiene(NUMERO_DE_TU_LINEA_PREPAGO),
        ValidarTexto.validarTexto(CONTINUAR));
  }

  public static Performable validarDireccionamiento() {
    return instrumented(LegalizacionLineas.class);
  }
}
