package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosYConsultasPage.BTN_FAMILIA_Y_AMIGOS;
import static utils.Constants.*;

public class IngresarFamiliaYAmigos implements Task {

    private static final String paso1 = "Ingresar opción Familia y amigos";

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                Click.on(BTN_FAMILIA_Y_AMIGOS),
                WaitForResponse.withText(NUMERO_DE_CUENTA)
        );
    }

    public static Performable ingresarOpcion() {
        return instrumented(IngresarFamiliaYAmigos.class);
    }
}