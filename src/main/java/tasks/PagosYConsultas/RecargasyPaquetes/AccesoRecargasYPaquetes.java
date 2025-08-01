package tasks.PagosYConsultas.RecargasyPaquetes;

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

public class AccesoRecargasYPaquetes implements Task {
    private static final User user = TestDataProvider.getRealUser();
    private static final String paso = "Acceso al portal de Recargas y Paquetes";
    private static final String paso2 = "Seleccionar la línea " + user.getNumero();

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(RECARGAS_Y_PAQUETES),
                WaitForResponse.withText(POSTPAGO));

        EvidenciaUtils.registrarCaptura(paso);

        AndroidObject.scrollCorto2(actor, LINEA + " " + user.getNumero() + " " + ELEGIR);

        EvidenciaUtils.registrarCaptura(paso2);

        actor.attemptsTo(
                ClickTextoQueContengaX.elTextoContiene(user.getNumero()),
                WaitForResponse.withText(COMPRA_PAQUETES_Y_RECARGAS));
    }

    public static Performable accederRecargasYPaquetes() {
        return instrumented(AccesoRecargasYPaquetes.class);
    }
}