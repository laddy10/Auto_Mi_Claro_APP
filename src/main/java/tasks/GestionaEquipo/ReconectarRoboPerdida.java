package tasks.GestionaEquipo;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class ReconectarRoboPerdida implements Task {

    private static final String paso = "Reconectar por Robo o Pérdida";

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(RECONECTAR_POR_ROBO_PERDIDA),
                WaitFor.aTime(2000)
               // ValidarTextoQueContengaX.elTextoContiene("no están reportados por robo o pérdida")
        );

        EvidenciaUtils.registrarCaptura(paso);
    }

    public static Performable reconectar() {
        return instrumented(ReconectarRoboPerdida.class);
    }
}
