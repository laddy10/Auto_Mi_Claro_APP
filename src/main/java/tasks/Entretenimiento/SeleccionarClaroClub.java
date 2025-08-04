package tasks.Entretenimiento;

import interactions.Click.ClickTextoQueContengaX;
import interactions.Scroll.ScrollHastaTexto;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class SeleccionarClaroClub implements Task {
    private static final String paso = "Buscar y seleccionar Claro Club en pantalla";

    @Override
    public <T extends Actor> void performAs(T actor) {

        actor.attemptsTo(
                ScrollHastaTexto.conTexto(CLARO_CLUB),
                WaitFor.aTime(3000)
        );
        EvidenciaUtils.registrarCaptura(paso);
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(CLARO_CLUB)
        );

    }

    public static Performable seleccionar() {
        return instrumented(SeleccionarClaroClub.class);
    }
}
