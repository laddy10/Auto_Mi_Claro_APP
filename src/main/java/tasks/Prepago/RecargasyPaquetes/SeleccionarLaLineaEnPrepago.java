package tasks.Prepago.RecargasyPaquetes;

import interactions.Click.ClickTextoQueContengaX;
import interactions.wait.WaitForResponse;
import models.User;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Task;
import utils.AndroidObject;
import utils.EvidenciaUtils;
import utils.TestDataProvider;

import static net.serenitybdd.screenplay.Tasks.instrumented;
import static utils.Constants.*;

public class SeleccionarLaLineaEnPrepago implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso2 = "Seleccionar línea prepago " + user.getNumeroPrepago();
    ;

    @Override
    public <T extends Actor> void performAs(T actor) {


        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(PREPAGO)
        );


        AndroidObject.scrollCorto2(actor, LINEA + " " + user.getNumeroPrepago() + " " + VER_DETALLE);


        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(user.getNumeroPrepago()),
                WaitForResponse.withText(CLARO_COLOMBIA)
        );
    }

    public static Performable seleccionar() {
        return instrumented(SeleccionarLaLineaEnPrepago.class);
    }
}