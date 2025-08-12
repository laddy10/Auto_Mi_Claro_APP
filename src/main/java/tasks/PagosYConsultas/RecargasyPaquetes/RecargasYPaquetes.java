package tasks.PagosYConsultas.RecargasyPaquetes;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.BTN_TRES_PUNTOS_MAS;
import static userinterfaces.PagosYConsultasPage.CBX_TIPO_PAQUETE;
import static utils.Constants.*;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

public class RecargasYPaquetes implements Task {
  private final User user = TestDataProvider.getRealUser();
  private static final String paso = "Ingreso a Comprar paquetes y recargas";
  private static final String paso2 = "Seleccionar la linea";
  private static final String paso3 = "Validar Mini Versión";
  private static final String paso4 = "Seleccionar la opción Paquetes";
  private static final String paso5 = "Seleccionar paquetes LDI";
  private static final String paso6 = "Seleccionar un paquete, ver detalle y dar clic en Comprar";
  private static final String paso7 = "Verificar opción cargo a la factura";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(RECARGAS_Y_PAQUETES),
        WaitForResponse.withText(POSTPAGO));

    EvidenciaUtils.registrarCaptura(paso);

    AndroidObject.scrollCorto2(actor, LINEA + " " + user.getNumero() + " " + ELEGIR);

    EvidenciaUtils.registrarCaptura(paso2);

    actor.attemptsTo(
        ClickTextoQueContengaX.elTextoContiene(user.getNumero()),
        WaitForResponse.withText(COMPRA_PAQUETES_Y_RECARGAS),
        Click.on(BTN_TRES_PUNTOS_MAS),
        ClickTextoQueContengaX.elTextoContiene(ACERCA_DE),
        WaitForResponse.withText(RECARGAS_Y_PAQUETES),
        ValidarTexto.validarTexto(RECARGAS_Y_PAQUETES),
        ValidarTexto.validarTexto(DECLARACION_SERVICIO),
        ValidarTextoQueContengaX.elTextoContiene(VER));

    EvidenciaUtils.registrarCaptura(paso3);

    actor.attemptsTo(Atras.irAtras());

    EvidenciaUtils.registrarCaptura(paso4);

    actor.attemptsTo(
        ClickElementByText.clickElementByText(PAQUETES),
        WaitForResponse.withText(ELIGE_TIPO_PAQUETE),
        ValidarTextoQueContengaX.elTextoContiene(user.getNumero()),
        Click.on(CBX_TIPO_PAQUETE));

    EvidenciaUtils.registrarCaptura(paso5);

    actor.attemptsTo(
        ClickElementByText.clickElementByText(PAQUETES_LDI),
        ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE));

    EvidenciaUtils.registrarCaptura(paso6);

    actor.attemptsTo(
        ClickElementByText.clickElementByText(COMPRAR),
        WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO),
        ValidarTexto.validarTexto(CARGO_FACTURA_CLARO));

    EvidenciaUtils.registrarCaptura(paso7);
  }

  public static Performable recargasYPaquetes() {
    return instrumented(RecargasYPaquetes.class);
  }
}
