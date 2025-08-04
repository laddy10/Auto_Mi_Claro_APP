package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class FamiliaYAmigos implements Task {

    private static final String paso1 = "Hacer clic en Familia y amigos";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(FAMILIA_Y_AMIGOS),
                WaitForResponse.withText(ELIGE_EL_NUMERO_DE_CUENTA)
        );
    }

    public static Performable ingresarFamiliaYAmigos() {
        return instrumented(FamiliaYAmigos.class);
    }
}