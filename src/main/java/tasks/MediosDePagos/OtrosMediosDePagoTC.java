package tasks.MediosDePagos;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.MediosPagoPage.BTN_OPCION_TARJETA_DROPDOWN;
import static userinterfaces.MediosPagoPage.DROPDOWN_MEDIO_PAGO;
import static utils.Constants.*;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.Scroll;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

public class OtrosMediosDePagoTC implements Task {

  private static final String paso1 = "Validar direccionamiento y menu desplegable medios de pago";
  private static final String PASO_TC = "Por medio de Tarjeta Crédito/Débito";

  @Override
  public <T extends Actor> void performAs(T actor) {
    actor.attemptsTo(
        WaitFor.aTime(3000),
        ValidarTexto.validarTexto(COMPRA_DE_PAQUETES),
        ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR),
        ValidarTextoQueContengaX.elTextoContiene(NUMERO_FACTURA_RECARGAS),
        ValidarTextoQueContengaX.elTextoContiene(DESCRIPCION_COMPRA),
        ValidarTextoQueContengaX.elTextoContiene(ESCOGE_MEDIO_PAGO),
        WaitFor.aTime(2500),
        Click.on(DROPDOWN_MEDIO_PAGO),
        WaitFor.aTime(1500));

    EvidenciaUtils.registrarCaptura(paso1);

    try {
      actor.attemptsTo(Click.on(BTN_OPCION_TARJETA_DROPDOWN), WaitFor.aTime(2000));
    } catch (Exception e) {
      actor.attemptsTo(ClickTextoQueContengaX.elTextoContiene("tarjeta de Crédito"), WaitFor.aTime(2000));
    }

    EvidenciaUtils.registrarCaptura(PASO_TC);

    actor.attemptsTo(
        Scroll.scrollMediaVista(),
        ClickTextoQueContengaX.elTextoContiene(CONTINUAR),
        WaitFor.aTime(6000),
        DiligenciarFormularioTarjetaCredito.diligenciar());
  }

  public static Performable validarOtrosMediosTC() {
    return instrumented(OtrosMediosDePagoTC.class);
  }
}
