package tasks.PagosYConsultas.Portabilidad;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class PortabilidadPrepago implements Task {

    private static final String paso1 = "Ingresar a Portabilidad prepago";
    private static final String paso2 = "Validar redirección a formulario";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // Ingresar a Portabilidad prepago
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PORTABILIDAD_PREPAGO),
               //WaitForResponse.withText(DATOS_PERSONALES)
                WaitFor.aTime(6000)
        );

        // Validar redirección a formulario
        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ValidarTextoQueContengaX.elTextoContiene(DATOS_PERSONALES),
                ValidarTextoQueContengaX.elTextoContiene(TODOS_LOS_CAMPOS_OBLIGATORIOS),
                ValidarTextoQueContengaX.elTextoContiene(CORREO_ELECTRONICO)
        );
    }

    public static Performable validarPortabilidadPrepago() {
        return instrumented(PortabilidadPrepago.class);
    }
}