package tasks.MediosDePagos;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.MediosPagoPage.BTN_MEDIO_PSE;
import static userinterfaces.MediosPagoPage.DROPDOWN_MEDIO_PAGO;
import static utils.Constants.*;

public class OtrosMediosDePagoPSE implements Task {

    private static final String paso1 = "Validar direccionamiento y menu desplegable medios de pago";
    private static final String PASO_PSE = "Por medio de PSE";

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                WaitFor.aTime(2500),
                ValidarTexto.validarTexto(COMPRA_DE_PAQUETES),
                ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR),
                ValidarTextoQueContengaX.elTextoContiene(NUMERO_FACTURA_RECARGAS),
                ValidarTextoQueContengaX.elTextoContiene(DESCRIPCION_COMPRA),
                ValidarTextoQueContengaX.elTextoContiene(ESCOGE_MEDIO_PAGO),
                WaitFor.aTime(1500),
                Click.on(DROPDOWN_MEDIO_PAGO),
                WaitFor.aTime(800)
        );

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                Click.on(BTN_MEDIO_PSE),
                WaitFor.aTime(1500));

        EvidenciaUtils.registrarCaptura(PASO_PSE);


        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CONTINUAR),
                WaitFor.aTime(2500),
                PSE.validarRedireccion()
        );
    }

    public static Performable validarOtrosMediosPSE() {
        return instrumented(OtrosMediosDePagoPSE.class);
    }
}