package tasks.PagosYConsultas;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.BTN_PAGAR_FACTURA;
import static userinterfaces.PagosYConsultasPage.LBL_FECHA_PAGO_OPORTUNO;
import static utils.Constants.*;

import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import java.util.List;

public class RedireccionPagarFactura implements Task {
    private final User user = TestDataProvider.getRealUser();
    private static final String paso = "Clic boton Pagar factura";
    private static final String paso2 = "Validar redirección botón Pagar Factura";

    @Override
    public <T extends Actor> void performAs(T actor) {

        List<WebElementFacade> lblfechapagooportuno = LBL_FECHA_PAGO_OPORTUNO.resolveAllFor(actor);
        if (!lblfechapagooportuno.isEmpty()) {

            EvidenciaUtils.registrarCaptura(paso);

            actor.attemptsTo(
                    Click.on(BTN_PAGAR_FACTURA),
                    WaitForResponse.withText(ELEGIR_OTRO_MEDIO_PAGO));

            EvidenciaUtils.registrarCaptura(paso2);

            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(TARJETA_C_D),
                    ValidarTextoQueContengaX.elTextoContiene(BOTON_BANCOLOMBIA),
                    ValidarTextoQueContengaX.elTextoContiene(CODENSA),
                    ValidarTextoQueContengaX.elTextoContiene(OTROS_MEDIOS));
        }
    }

    public static Performable redireccionPagarFactura() {
        return instrumented(RedireccionPagarFactura.class);
    }
}