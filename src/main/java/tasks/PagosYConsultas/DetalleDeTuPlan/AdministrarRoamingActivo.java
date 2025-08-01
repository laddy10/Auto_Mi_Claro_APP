package tasks.PagosYConsultas.DetalleDeTuPlan;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForResponse;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.AndroidObject;
import utils.EvidenciaUtils;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class AdministrarRoamingActivo implements Task {

    private static final String NUMERO_LINEA = "310 262 8443";
    private static final String paso1 = "Hacer clic en Administrar Roaming";
    private static final String paso2 = "Seleccionar línea para administrar Roaming " + NUMERO_LINEA;

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ADMINISTRAR_ROAMING),
                WaitForResponse.withText(ACTIVA_ROAMING)
        );

        // Seleccionar la línea específica del usuario
        AndroidObject.scrollCorto2(actor, LINEA_TEXTO + " " + NUMERO_LINEA + " " + ADMINISTRAR);

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(NUMERO_LINEA),
                WaitForResponse.withText(SERVICIO_ROAMING)
        );
    }

    public static Performable ingresarRoaming() {
        return instrumented(AdministrarRoamingActivo.class);
    }
}