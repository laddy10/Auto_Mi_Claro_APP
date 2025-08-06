package tasks.GestionaEquipo;

import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.GestionaEquipoPage.*;

public class ConfirmarRegistroEquipo implements Task {

    private static final String paso = "Confirmar Registro de Equipo";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(BTN_REGISTRAR_MODAL),
                WaitFor.aTime(3000)
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable confirmar() {
        return instrumented(ConfirmarRegistroEquipo.class);
    }
}