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

public class SeleccionLineaPrepago implements Task {

    private static final User user = TestDataProvider.getRealUser();
    private static final String paso1 = "Ingresar a Recargas y paquetes";
    private static final String paso2 = "Seleccionar línea prepago " + user.getNumeroPrepago();
    ;

    @Override
    public <T extends Actor> void performAs(T actor) {

        EvidenciaUtils.registrarCaptura(paso1);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(RECARGAS_Y_PAQUETES),
                WaitForResponse.withText(POSTPAGO),
                ClickTextoQueContengaX.elTextoContiene(PREPAGO)
        );


        AndroidObject.scrollCorto2(actor, LINEA + " " + user.getNumeroPrepago() + " " + ELEGIR);


        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(user.getNumeroPrepago()),
                WaitForResponse.withText(ARMA_TU_PAQUETE)
        );
    }

    public static Performable seleccionar() {
        return instrumented(SeleccionLineaPrepago.class);
    }
}