package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTexto;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class AdministrarAplicacionesNoPermitido implements Task {

    private static final String paso1 = "Ingresar Administra las aplicaciones incluidas";
    private static final String paso2 = "Validar popup - No tienes aplicaciones disponibles";

    @Override
    public <T extends Actor> void performAs(T actor) {

        // ADMINISTRA LAS APLICACIONES INCLUIDAS
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ADMINISTRA_LAS_APLICACIONES_INCLUIDAS),
                WaitFor.aTime(2000)
        );

        // VALIDAR POPUP - Plan que NO permite
        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ValidarTexto.validarTexto(NO_TIENES_APLICACIONES_DISPONIBLES_ADMINISTRAR),
                ValidarTexto.validarTexto(ACEPTAR_2),
                ClickTextoQueContengaX.elTextoContiene(ACEPTAR_2)
        );
    }

    public static Performable administrarYValidar() {
        return instrumented(AdministrarAplicacionesNoPermitido.class);
    }
}