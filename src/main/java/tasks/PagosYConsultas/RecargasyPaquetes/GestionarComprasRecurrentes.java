package tasks.PagosYConsultas.RecargasyPaquetes;

import interactions.Click.ClickElementByText;
import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class GestionarComprasRecurrentes implements Task {
    private static final String paso1 = "Seleccionar Gestionar compras recurrentes";
    private static final String paso2 = "Seleccionar Tarjeta de crédito";
    private static final String paso3 = "Verificar popup informativo";
    private static final String paso4 = "Cerrar popup";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        // Seleccionar "Gestionar compras recurrentes"
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(GESTIONAR_COMPRAS_RECURRENTES),
                WaitForResponse.withText(ELIGE_EL_MEDIO_DE_PAGO));

        EvidenciaUtils.registrarCaptura(paso2);

        // Seleccionar "Tarjeta de crédito"
        actor.attemptsTo(
                ValidarTexto.validarTexto(ELIGE_EL_MEDIO_DE_PAGO),
                ClickTextoQueContengaX.elTextoContiene(TARJETA_DE_CREDITO),
                WaitForResponse.withText(ACTUALMENTE_NO_CUENTAS_CON_PAQUETES)
        );

        EvidenciaUtils.registrarCaptura(paso3);

        // Verificar popup informativo
        actor.attemptsTo(
                ValidarTexto.validarTexto(ACTUALMENTE_NO_CUENTAS_CON_PAQUETES),
                ValidarTexto.validarTexto(CERRAR)
        );

        // Cerrar popup
        actor.attemptsTo(
                ClickElementByText.clickElementByText(CERRAR)
        );

        EvidenciaUtils.registrarCaptura(paso4);

    }

    public static Performable validarGestionarComprasRecurrentes() {
        return instrumented(GestionarComprasRecurrentes.class);
    }
}