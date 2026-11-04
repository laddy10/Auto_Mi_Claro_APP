package tasks.PagosYConsultas.RecargasyPaquetes;

import interactions.Click.ClickTextoQueContengaX;
import interactions.validations.ValidarTextoQueContengaX;
import interactions.wait.WaitFor;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Click;
import tasks.PagosYConsultas.DetalleDeTuPlan.ActivarServicioRoamingVigenciaDefinida;
import tasks.PagosYConsultas.DetalleDeTuPlan.DesactivarServicioRoaming;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static userinterfaces.PagosyConsultasPrePage.BTON_ACTIVAR_ROAMING;
import static utils.Constants.*;

public class DesactivarRoamingActivo implements Task {
    private static final String NUMERO_LINEA = "310 263 5974";
    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Hacer clic en Activar Roaming";
    private static final String paso2 = "Ingreso a menu activa roaming";
    private static final String paso3 = "Validacion de servicio de roaming activo de la linea " + NUMERO_LINEA;
    private static final String paso4 = "Desactivar Roaming";
    private static final String paso5 = "";
    private static final String paso6 = "";
    private static final String paso7 = "";
    private static final String paso8 = "";
    private static final String paso9 = "";


    @Override
    public <T extends Actor> void performAs(T actor) {

        // 1. Hacer clic en Activar Roaming
        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(ACTIVAR_ROAMING),
                WaitForResponse.withText(ACTIVA_ROAMING));

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(NUMERO_LINEA),
                WaitForResponse.withText("Activa Roaming"),
                Click.on(BTON_ACTIVAR_ROAMING),
                WaitForResponse.withText("Elige"),
                ClickTextoQueContengaX.elTextoContiene("Activar por tiempo indefinido"),
                WaitForResponse.withText("Indefinido"),
                ValidarTextoQueContengaX.elTextoContiene("Ver paquetes"),
                WaitFor.aTime(5000),
                ClickTextoQueContengaX.elTextoContiene("No, gracias"),
                WaitFor.aTime(5000),
                ClickTextoQueContengaX.elTextoContiene("Continuar"),
                WaitFor.aTime(5000),
                ClickTextoQueContengaX.elTextoContiene("Aceptar")
        );
        EvidenciaUtils.registrarCaptura(paso3);
        actor.attemptsTo(DesactivarServicioRoaming.desactivarServicioRoaming());
        EvidenciaUtils.registrarCaptura(paso4);

    }


    public static Performable desactivarRoamingActivo() {
        return instrumented(DesactivarRoamingActivo.class);
    }

}
