package tasks.PagosYConsultas.DetalleDeTuPlan;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.LBL_FECHA_PAGO_OPORTUNO;
import static utils.Constants.*;

import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import java.util.List;

public class ValidarDireccionamientoPagarFactura implements Task {

    private static final String paso1 = "Validar direccionamiento correcto Pagar factura";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ValidarTexto.validarTexto(PAGAR_FACTURA),
                ValidarTextoQueContengaX.elTextoContiene(REFERENCIA_PAGO),
                ValidarTextoQueContengaX.elTextoContiene(FECHA_DE_CARGA),
                ValidarTextoQueContengaX.elTextoContiene(VALOR_A_PAGAR),
                ValidarTexto.validarTexto(PAGAR_FACTURA_BTN),
                ValidarTexto.validarTexto(PAGA_Y_CONOCE_TUS_SERVICIOS),
                ValidarTexto.validarTexto(DESCARGA_TU_FACTURA),
                ValidarTexto.validarTexto(PAGOS_AUTOMATICOS));

        List<WebElementFacade> lblfechapagooportuno = LBL_FECHA_PAGO_OPORTUNO.resolveAllFor(actor);
        if (!lblfechapagooportuno.isEmpty()) {
            actor.attemptsTo(
                    ValidarTextoQueContengaX.elTextoContiene(FECHA_PAGO_OPORTUNO),
                    ValidarTextoQueContengaX.elTextoContiene(REALIZAR_PAGO_PARCIAL)
            );

        }
    }

    public static Performable validarDireccionamientoPagarFactura() {
        return instrumented(ValidarDireccionamientoPagarFactura.class);
    }
}
