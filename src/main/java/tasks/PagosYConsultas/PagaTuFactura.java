package tasks.PagosYConsultas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.BTN_TRES_PUNTOS_MAS;
import static utils.Constants.*;
import static utils.Constants.DECLARACION_SERVICIO;

import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class PagaTuFactura implements Task {
  private static final User user = TestDataProvider.getRealUser();
  private static final String paso = "Ingreso a Paga y consulta tus facturas";
  private static final String paso2 = "Seleccionar la linea " + user.getNumero();
  private static final String paso3 = "Validar Mini Versión";
  private static final String paso4 = "Validar datos de pago: Referencia, fechas y valor";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(PAGA_TU_FACTURA),
        WaitForResponse.withText(POSTPAGO));

    EvidenciaUtils.registrarCaptura(paso);

    actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene(POSTPAGO));

    AndroidObject.scrollCorto2(actor, LINEA + " " + user.getNumero() + " " + VER_DETALLE);

    EvidenciaUtils.registrarCaptura(paso2);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(user.getNumero()),
        WaitForResponse.withText(PAGAR_FACTURA),
        Click.on(BTN_TRES_PUNTOS_MAS),
        ClickTextoQueContengaX.elTextoContiene(ACERCA_DE),
        WaitFor.aTime(1000),
        ValidarTexto.validarTexto(PAGA_TU_FACTURA),
        ValidarTexto.validarTexto(DECLARACION_SERVICIO),
        ValidarTextoQueContengaX.elTextoContiene(VER));

    EvidenciaUtils.registrarCaptura(paso3);

    actor.attemptsTo(
        Atras.irAtras(),
        WaitForResponse.withText(PAGAR_FACTURA),
        ValidarTextoQueContengaX.elTextoContiene(REFERENCIA_PAGO),
        ValidarTextoQueContengaX.elTextoContiene(FECHA_DE_CARGA),
        ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR),
        ValidarTextoQueContengaX.elTextoContiene(FECHA_PAGO_OPORTUNO));

    EvidenciaUtils.registrarCaptura(paso4);
  }

  public static Performable pagaTuFactura() {
    return instrumented(PagaTuFactura.class);
  }
}
