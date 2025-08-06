package tasks.AtencionClienteSoporte;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class MedicionDeRed implements Task {

    private static final String paso1 = "Ingresar al botón Medición de red";

    @Override
    public <T extends Actor> void performAs(T actor) {
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(MEDICION_DE_RED),
                WaitForResponse.withText(AUTORIZO_MEDICION_CALIDAD_RED)
        );
    }

    public static Performable ingresarMedicionRed() {
        return instrumented(MedicionDeRed.class);
    }
}