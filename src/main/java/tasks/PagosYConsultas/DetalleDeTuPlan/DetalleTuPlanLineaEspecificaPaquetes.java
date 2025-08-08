package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.BTN_TRES_PUNTOS_MAS;
import static utils.Constants.*;

import hooks.ReportHooks;
import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.AndroidObject;
import utils.EvidenciaUtils;

public class DetalleTuPlanLineaEspecificaPaquetes implements Task {

  private static final String NUMERO_LINEA = "310 263 5941";
  private static final String paso1 = "Seleccionar Detalle de tu plan";
  private static final String paso2 = "Seleccionar línea postpago " + NUMERO_LINEA;
  private static final String paso3 = "Validar versión de miniprograma";
  private static final String paso4 = "Validar información detalle del plan";
  private static final String paso5 = "Validar opciones disponibles";

  @Override
  public <T extends Actor> void performAs(T actor) {

    ReportHooks.setLinea(NUMERO_LINEA);

    EvidenciaUtils.registrarCaptura(paso1);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(DETALLE_DE_TU_PLAN),
        WaitForResponse.withText(POSTPAGO));

    EvidenciaUtils.registrarCaptura(paso2);

    AndroidObject.scrollCorto2(actor, LINEA + " " + NUMERO_LINEA + " " + VER_DETALLE);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(NUMERO_LINEA),
        WaitForResponse.withText(DETALLE_DE_TU_PLAN));

    actor.attemptsTo(
        WaitFor.aTime(2000),
        Click.on(BTN_TRES_PUNTOS_MAS),
        ClickTextoQueContengaX.elTextoContiene(ACERCA_DE),
        WaitForResponse.withText(DETALLE_DE_TU_PLAN_POSTPAGO),
        ValidarTexto.validarTexto(DETALLE_DE_TU_PLAN_POSTPAGO),
        ValidarTexto.validarTexto(DECLARACION_SERVICIO),
        ValidarTextoQueContengaX.elTextoContiene(VER));

    EvidenciaUtils.registrarCaptura(paso3);

    actor.attemptsTo(
        Atras.irAtras(),
        ValidarTexto.validarTexto(CONSULTA_LA_FACTURA_DE_TU_PLAN_POSTPAGO),
        ValidarTexto.validarTexto(VER_FACTURA),
        ValidarTexto.validarTexto(TU_PLAN),
        ValidarTextoQueContengaX.elTextoContiene(CARGO_FIJO_MENSUAL_IVA_INCLUIDO),
        ValidarTexto.validarTexto(DATOS),
        ValidarTexto.validarTexto(ILIMITADO),
        ValidarTexto.validarTexto(VOZ),
        ValidarTextoQueContengaX.elTextoContiene(SEGUNDOS_PARA_LLAMAR));

    EvidenciaUtils.registrarCaptura(paso4);

    actor.attemptsTo(
        ValidarTexto.validarTexto(SMS),
        ValidarTexto.validarTexto(ILIMITADO),
        Scroll.scrollUnaVista(),
        ValidarTexto.validarTexto(LARGA_DISTANCIA_INTERNACIONAL),
        ValidarTexto.validarTexto(ILIMITADO),
        ValidarTexto.validarTexto(DESCRIPCION_DEL_PLAN),
        ValidarTexto.validarTexto(FAMILIA_Y_AMIGOS),
        ValidarTexto.validarTexto(APLICACIONES_ELEGIBLES),
        ValidarTexto.validarTexto(MEJORAR_PLAN),
        ValidarTexto.validarTexto(PAQUETES_ADICIONALES),
        ValidarTexto.validarTexto(ADMINISTRAR_ROAMING));

    EvidenciaUtils.registrarCaptura(paso5);
  }

  public static Performable validarDetalleTuPlan() {
    return instrumented(DetalleTuPlanLineaEspecificaPaquetes.class);
  }
}
