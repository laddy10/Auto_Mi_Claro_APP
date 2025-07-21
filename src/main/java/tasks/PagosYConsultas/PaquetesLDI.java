package tasks.PagosYConsultas;

import interactions.Click.ClickElementByText;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.questions.Presence;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.CBX_TIPO_PAQUETE;
import static userinterfaces.PagosYConsultasPage.LBL_CARGO_FACTURA_CLARO;
import static utils.Constants.*;

public class PaquetesLDI implements Task {
    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Seleccionar el tipo de Paquetes";
    private static final String paso2 = "Seleccionar tipo de paquete: " + user.getTipoPaquete();
    private static final String paso3 = "Ver detalle y comprar paquete";
    private static final String paso4 = "Verificar flujo de pago: " + user.getTipoPaquete();

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickElementByText.clickElementByText(PAQUETES),
                WaitForResponse.withText(ELIGE_TIPO_PAQUETE),
                ValidarTextoQueContengaX.elTextoContiene(user.getNumero()));

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                Click.on(CBX_TIPO_PAQUETE));

        EvidenciaUtils.registrarCaptura(paso2);

        // Seleccionar tipo de paquete dinámicamente desde JSON
        String tipoPaquete = user.getTipoPaquete(); // "PAQUETES_LDI" o "PAQUETES_DE_DATOS"

        actor.attemptsTo(
                ClickElementByText.clickElementByText(tipoPaquete),
                ClickElementByText.clickElementByText(VER_DETALLE_DEL_PAQUETE));

        EvidenciaUtils.registrarCaptura(paso3);

        // Flujo diferente según el tipo de paquete
        actor.attemptsTo(
                ClickElementByText.clickElementByText(COMPRAR));

        if (Presence.of(LBL_CARGO_FACTURA_CLARO).answeredBy(actor)) {
            // Flujo LDI - Cargo a la factura
            actor.attemptsTo(
                    WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO),
                    ValidarTexto.validarTexto(CARGO_FACTURA_CLARO),
                    ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR)
            );
        } else {
            // Flujo Paquetes de Datos - Medios de pago
            actor.attemptsTo(
                    WaitForResponse.withText(TARJETA_C_D),
                    ValidarTexto.validarTexto(ELEGIR_OTRO_MEDIO_PAGO),
                    ValidarTextoQueContengaX.elTextoContiene(TARJETA_C_D),
                    ValidarTextoQueContengaX.elTextoContiene(DAVIPLATA),
                    ValidarTextoQueContengaX.elTextoContiene(CODENSA),
                    ValidarTextoQueContengaX.elTextoContiene(OTROS_MEDIOS),
                    ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR)
            );
        }

        EvidenciaUtils.registrarCaptura(paso4);
    }

    public static Performable seleccionarPaquetes() {
        return instrumented(PaquetesLDI.class);
    }
}