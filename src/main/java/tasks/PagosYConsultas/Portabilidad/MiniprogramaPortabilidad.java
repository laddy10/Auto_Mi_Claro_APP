package tasks.PagosYConsultas.Portabilidad;

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

public class MiniprogramaPortabilidad implements Task {

    private static final String paso1 = "Validar direccionamiento Portabilidad";
    private static final String paso2 = "Validar versión de miniprograma Portabilidad";


    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ValidarTexto.validarTexto(PORTABILIDAD),
                ValidarTexto.validarTexto(ESTADO_DE_PORTABILIDAD),
                ValidarTexto.validarTexto(ENVIO_DE_SIM_CARD),
                ValidarTexto.validarTexto(PORTABILIDAD_PREPAGO),
                ValidarTexto.validarTexto(PORTABILIDAD_POSTPAGO)
        );


        actor.attemptsTo(
                Click.on(BTN_TRES_PUNTOS_MAS),
                ClickTextoQueContengaX.elTextoContiene(ACERCA_DE),
                WaitForResponse.withText(PORTABILIDAD),
                ValidarTexto.validarTexto(PORTABILIDAD),
                ValidarTexto.validarTexto(DECLARACION_SERVICIO),
                ValidarTextoQueContengaX.elTextoContiene(VER));

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                Atras.irAtras()
        );
    }

    public static Performable validarVersion() {
        return instrumented(MiniprogramaPortabilidad.class);
    }
}