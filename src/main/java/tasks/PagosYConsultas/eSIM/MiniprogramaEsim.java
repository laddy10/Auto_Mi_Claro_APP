package tasks.PagosYConsultas.eSIM;

import interactions.Click.ClickTextoQueContengaX;
import interactions.comunes.Atras;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.BTN_TRES_PUNTOS_MAS;
import static utils.Constants.*;

public class MiniprogramaEsim implements Task {

    private static final String paso1 = "Validar versión de miniprograma eSIM";

    @Override
    public <T extends Actor> void performAs(T actor) {


        actor.attemptsTo(
                Click.on(BTN_TRES_PUNTOS_MAS),
                ClickTextoQueContengaX.elTextoContiene(ACERCA_DE),
                WaitForResponse.withText(DECLARACION_SERVICIO),
                ValidarTexto.validarTexto(ESIM_CLARO),
                ValidarTexto.validarTexto(DECLARACION_SERVICIO),
                ValidarTextoQueContengaX.elTextoContiene(VER));

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                Atras.irAtras()
        );
    }

    public static Performable validarVersion() {
        return instrumented(MiniprogramaEsim.class);
    }
}