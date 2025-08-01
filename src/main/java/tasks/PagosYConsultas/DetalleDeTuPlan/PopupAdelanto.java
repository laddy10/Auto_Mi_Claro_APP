package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class PopupAdelanto implements Task {

    private static final String paso1 = "Verificar popup del adelanto y Cerrar";
    private static final String paso2 = "Cerrar popup";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ValidarTexto.validarTexto(RESUMEN_DEL_ADELANTO),
                ValidarTexto.validarTexto(VIGENCIA_LABEL),
                ValidarTexto.validarTexto(PRECIO_LABEL),
                ValidarTexto.validarTexto(VALOR_DEL_SERVICIO),
                ValidarTexto.validarTexto(VALOR_TOTAL),
                ValidarTexto.validarTexto(TERMINOS_Y_CONDICIONES),
                ValidarTexto.validarTexto(CONTINUAR),
                ClickTextoQueContengaX.elTextoContiene(CERRAR)
        );
    }

    public static Performable verificarPopup() {
        return instrumented(PopupAdelanto.class);
    }
}