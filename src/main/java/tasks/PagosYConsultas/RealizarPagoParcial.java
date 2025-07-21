package tasks.PagosYConsultas;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.input.IngresarMontoConTecladoNumerico;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.TXT_MONTO_PAGO_PARCIAL;
import static utils.Constants.*;

public class RealizarPagoParcial implements Task {
    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Seleccionar Realizar pago parcial";
    private static final String paso2 = "Verificar mensaje: Ingresa el monto del pago parcial";
    private static final String paso3 = "Ingresar monto pago parcial " + user.getMontoPagoParcial();
    private static final String paso4 = "Validar valor factura y saldo pendiente";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Seleccionar realizar pago parcial
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(REALIZAR_PAGO_PARCIAL),
                WaitForResponse.withText(PAGO_PARCIAL),
                ValidarTextoQueContengaX.elTextoContiene(user.getNumero().replace(" ", "")),
                ValidarTextoQueContengaX.elTextoContiene(POSTPAGO),
                ValidarTexto.validarTexto(INGRESA_MONTO_PAGO_PARCIAL)
        );

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                Click.on(TXT_MONTO_PAGO_PARCIAL),
                WaitFor.aTime(3000),
                IngresarMontoConTecladoNumerico.elValor(user.getMontoPagoParcial())
        );

        EvidenciaUtils.registrarCaptura(paso3);

        // Validar información de factura
        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(VALOR_FACTURA),
                ValidarTextoQueContengaX.elTextoContiene(SALDO_PENDIENTE),
                ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR)
        );

        EvidenciaUtils.registrarCaptura(paso4);

        // Continuar al proceso de pago
        actor.attemptsTo(
                ClickElementByText.clickElementByText(CONTINUAR),
                WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO)
        );

    }

    public static Performable conLosDatos() {
        return instrumented(RealizarPagoParcial.class);
    }
}