package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

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
                ValidarTextoQueContengaX.elTextoContiene(FECHA_PAGO_OPORTUNO),
                ValidarTexto.validarTexto(PAGAR_FACTURA_BTN),
                ValidarTextoQueContengaX.elTextoContiene(REALIZAR_PAGO_PARCIAL),
                ValidarTexto.validarTexto(PAGA_Y_CONOCE_TUS_SERVICIOS),
                ValidarTexto.validarTexto(DESCARGA_TU_FACTURA),
                ValidarTexto.validarTexto(PAGOS_AUTOMATICOS)
        );
    }

    public static Performable validarDireccionamientoPagarFactura() {
        return instrumented(ValidarDireccionamientoPagarFactura.class);
    }
}